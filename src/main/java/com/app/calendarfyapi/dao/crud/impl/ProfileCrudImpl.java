package com.app.calendarfyapi.dao.crud.impl;

import com.app.calendarfyapi.common.CalendarfyConstants;
import com.app.calendarfyapi.exception.ProfileException;
import com.app.calendarfyapi.dao.crud.ProfileCrud;
import com.app.calendarfyapi.dao.profile.MongoProfileRepository;
import com.app.calendarfyapi.dao.profile.ProfileRepository;
import com.app.calendarfyapi.model.mongo.Event;
import com.app.calendarfyapi.model.mongo.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileCrudImpl implements ProfileCrud {

    Logger logger = LoggerFactory.getLogger(ProfileCrudImpl.class);

    @Autowired
    MongoProfileRepository mongoProfileRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public Profile getProfile(String profileEmail) throws ProfileException {
        logger.info("attempting to getProfile: " + profileEmail);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile != null) {
            logger.info("successfully found profile: " + profileEmail);
            return existingProfile;
        }

        logger.error("error while trying to get profile: " + profileEmail + " error: " + CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
        throw new ProfileException(CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
    }

    @Override
    public void createProfile(String profileEmail) throws ProfileException {
        logger.info("attempting to create a new profile with profileEmail: " + profileEmail);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile == null) {
            Profile newProfile = new Profile();
            newProfile.setProfileEmail(profileEmail);

            mongoProfileRepository.insert(newProfile);
            logger.info(CalendarfyConstants.CREATE_PROFILE_SUCCESS);
        } else {
            logger.error("unable to create profile: " + CalendarfyConstants.PROFILE_ALREADY_EXISTS);
            throw new ProfileException(CalendarfyConstants.PROFILE_ALREADY_EXISTS);
        }
    }

    @Override
    public void addEventToProfile(String profileEmail, String requestingUser, Event event) throws ProfileException {
        logger.info("attempting to add event to profile: " + profileEmail);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile == null) {
            logger.error(CalendarfyConstants.PROFILE_DOES_NOT_EXIST + ", creating profile");

            createProfile(profileEmail);
            existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

            profileRepository.addEvent(existingProfile, event);
        } else if (checkIfUserAuthorized(existingProfile, requestingUser)) {
            logger.info("found profile, adding event to profile: " + profileEmail);
            profileRepository.addEvent(existingProfile, event);
        } else {
            logger.error("error while adding event for profile: " + profileEmail + " error: " + CalendarfyConstants.REQUESTING_USER_NOT_AUTHORIZED);
            throw new ProfileException(CalendarfyConstants.REQUESTING_USER_NOT_AUTHORIZED);
        }
    }

    @Override
    public void removeEventFromProfile(String profileEmail, String requestingUser, Event event) throws ProfileException {
        logger.info("attempting to remove event from profile: " + profileEmail);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile == null) {
            logger.error("error while removing event to profile: " + profileEmail + " error: " + CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
            throw new ProfileException(CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
        } else {
            if (checkIfUserAuthorized(existingProfile, requestingUser)) {
                logger.info("found profile, adding event to profile: " + profileEmail);
                profileRepository.deleteEvent(existingProfile, event);
            } else {
                logger.error("error while removing event for profile: " + profileEmail + " error: " + CalendarfyConstants.REQUESTING_USER_NOT_AUTHORIZED);
                throw new ProfileException(CalendarfyConstants.REQUESTING_USER_NOT_AUTHORIZED);
            }
        }
    }

    @Override
    public List<Event> getEventsFromProfile(String profileEmail) throws ProfileException {
        logger.info("attempting to get events for profile: " + profileEmail);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile == null) {
            logger.error("error while getting events: " + CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
            throw new ProfileException(CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
        }
        else {
            logger.info("successfully got events for profile: " + profileEmail);
            return existingProfile.getEvents();
        }
    }

    @Override
    public void updateDeviceToken(String profileEmail, String token) throws ProfileException {
        logger.info("attempting to update device token for profile: " + profileEmail);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile != null) {
            logger.info("found profile, updating device token");
            profileRepository.updateToken(existingProfile, token);
        } else {
            logger.error("error updating device token: " + CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
            throw new ProfileException(CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
        }
    }

    @Override
    public void addAuthorizedUser(String profileEmail, String authorizedUser) throws ProfileException {
        logger.info("attempting to add authorized user to profile: " + profileEmail);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile != null) {
            logger.info("found profile, adding authorized user");
            profileRepository.addAuthorizedUser(existingProfile, authorizedUser);
        } else {
            logger.error("unable to add authorized user to profile: " + profileEmail + " error: " + CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
            throw new ProfileException(CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
        }
    }

    @Override
    public void removeAuthorizedUser(String profileEmail, String authorizedUser) throws ProfileException {
        logger.info("attempting to remove authorized user to profile: " + profileEmail);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile != null) {
            logger.info("found profile, removing authorized user");
            profileRepository.deleteAuthorizedUser(existingProfile, authorizedUser);
        } else {
            logger.error("unable to remove authorized user to profile: " + profileEmail + " error: " + CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
            throw new ProfileException(CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
        }
    }

    @Override
    public void addUserToGroup(String profileEmail, String group) throws ProfileException {
        logger.info("attempting to add user: " + profileEmail + " to group: " + group);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile != null) {
            logger.info("found profile, adding user to group");
            profileRepository.addGroup(existingProfile, group);
        } else {
            logger.error("unable to add group to profile: " + profileEmail + " error: " + CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
            throw new ProfileException(CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
        }
    }

    @Override
    public void removeUserFromGroup(String profileEmail, String group) throws ProfileException {
        logger.info("attempting to remove user: " + profileEmail + " from group: " + group);

        Profile existingProfile = mongoProfileRepository.findFirstByProfileEmail(profileEmail);

        if (existingProfile != null) {
            logger.info("found profile, removing user to group");
            profileRepository.deleteGroup(existingProfile, group);
        } else {
            logger.error("unable to remove group to profile: " + profileEmail + " error: " + CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
            throw new ProfileException(CalendarfyConstants.PROFILE_DOES_NOT_EXIST);
        }
    }

    private boolean checkIfUserAuthorized(Profile profile, String requestingUser) {
        if (profile.getProfileEmail().equals(requestingUser))
            return true;

        for (String user :  profile.getAuthorizedUsers()) {
            if (requestingUser.equalsIgnoreCase(user))
                return true;
        }

        return false;
    }
}