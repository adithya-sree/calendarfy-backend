package com.app.calendarfyapi.dao.crud.impl;

import com.app.calendarfyapi.common.CalendarfyConstants;
import com.app.calendarfyapi.dao.crud.GroupCrud;
import com.app.calendarfyapi.dao.crud.ProfileCrud;
import com.app.calendarfyapi.dao.group.GroupRepository;
import com.app.calendarfyapi.dao.group.MongoGroupRepository;
import com.app.calendarfyapi.exception.GroupException;
import com.app.calendarfyapi.exception.ProfileException;
import com.app.calendarfyapi.model.mongo.Event;
import com.app.calendarfyapi.model.mongo.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupCrudImpl implements GroupCrud {

    @Autowired
    MongoGroupRepository mongoGroupRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ProfileCrud profileCrud;

    Logger logger = LoggerFactory.getLogger(GroupCrudImpl.class);

    @Override
    public void createNewGroup(String groupName, String profileEmail) throws GroupException {
        logger.info("attempting to create new group with name: " + groupName);

        Group existingGroup = mongoGroupRepository.findFirstByGroupName(groupName);

        if (existingGroup == null) {
            logger.info("group does not already exist, creating group now");

            try {
                profileCrud.addUserToGroup(profileEmail, groupName);

                Group newGroup = new Group();
                newGroup.setGroupName(groupName);
                newGroup.setGroupOwner(profileEmail);

                mongoGroupRepository.insert(newGroup);

                logger.info(CalendarfyConstants.CREATE_GROUP_SUCCESS + " groupName: " + groupName);
            } catch (ProfileException e) {
                logger.error("error while creating group " + e);
                throw new GroupException(e.getMessage());
            }

        } else {
            logger.error("error while creating group: " + CalendarfyConstants.GROUP_ALREADY_EXISTS);
            throw new GroupException(CalendarfyConstants.GROUP_ALREADY_EXISTS);
        }
    }

    @Override
    public List<Event> getGroupEvents(String groupName) throws GroupException {
        logger.info("attempting to get events for group: " + groupName);

        Group existingGroup = mongoGroupRepository.findFirstByGroupName(groupName);

        if (existingGroup == null) {
            logger.error("unable to get events for group: " + CalendarfyConstants.GROUP_DOES_NOT_EXIST);
            throw new GroupException(CalendarfyConstants.GROUP_DOES_NOT_EXIST);
        } else {
            logger.info("successfully got events for group: " + groupName);
            return existingGroup.getGroupEvents();
        }
    }

    @Override
    public void addEventToGroup(String groupName, Event event) throws GroupException {
        logger.info("attempting to add event to group: " + groupName);

        Group existingGroup = mongoGroupRepository.findFirstByGroupName(groupName);

        if (existingGroup == null) {
            logger.error("unable to add event to group: " + CalendarfyConstants.GROUP_DOES_NOT_EXIST);
            throw new GroupException(CalendarfyConstants.GROUP_DOES_NOT_EXIST);
        } else {
            logger.info("successfully found group, adding event");
            groupRepository.addEventToGroup(existingGroup, event);
        }
    }

    @Override
    public void removeEventFromGroup(String groupName, Event event) throws GroupException {
        logger.info("attempting to remove event from group: " + groupName);

        Group existingGroup = mongoGroupRepository.findFirstByGroupName(groupName);

        if (existingGroup == null) {
            logger.error("unable to remove event to group: " + CalendarfyConstants.GROUP_DOES_NOT_EXIST);
            throw new GroupException(CalendarfyConstants.GROUP_DOES_NOT_EXIST);
        } else {
            logger.info("successfully found group, removing event");
            groupRepository.removeEventFromGroup(existingGroup, event);
        }
    }

    @Override
    public void addUserToGroup(String groupName, String profileEmail) throws GroupException {
        logger.info("attempting to add user to group: " + groupName);

        Group existingGroup = mongoGroupRepository.findFirstByGroupName(groupName);

        if (existingGroup == null) {
            logger.error("unable to add user to group: " + CalendarfyConstants.GROUP_DOES_NOT_EXIST);
            throw new GroupException(CalendarfyConstants.GROUP_DOES_NOT_EXIST);
        } else {
            logger.info("successfully found group, adding user");

            try {
                profileCrud.addUserToGroup(profileEmail, groupName);
                groupRepository.addUserToGroup(existingGroup, profileEmail);
            } catch (ProfileException e) {
                logger.error("unable to add user to group: " + e.getMessage());
                throw new GroupException(e.getMessage());
            }
        }
    }

    @Override
    public void removeUserFromGroup(String groupName, String profileEmail) throws GroupException {
        logger.info("attempting to remove user from group: " + groupName);

        Group existingGroup = mongoGroupRepository.findFirstByGroupName(groupName);

        if (existingGroup == null) {
            logger.error("unable to remove event from group: " + CalendarfyConstants.GROUP_DOES_NOT_EXIST);
            throw new GroupException(CalendarfyConstants.GROUP_DOES_NOT_EXIST);
        } else {
            logger.info("successfully found group, removing user");

            try {
                profileCrud.removeUserFromGroup(profileEmail, groupName);
                groupRepository.removeUserFromGroup(existingGroup, profileEmail);
            } catch (ProfileException e) {
                logger.error("unable to remove user to group: " + e.getMessage());
                throw new GroupException(e.getMessage());
            }
        }
    }

    @Override
    public void deleteGroup(String groupName, String requestingUser) throws GroupException {
        logger.info("attempting to delete group: " + groupName);

        Group existingGroup = mongoGroupRepository.findFirstByGroupName(groupName);

        if (existingGroup == null) {
            logger.error("unable to delete group: " + CalendarfyConstants.GROUP_DOES_NOT_EXIST);
            throw new GroupException(CalendarfyConstants.GROUP_DOES_NOT_EXIST);
        } else if (!existingGroup.getGroupOwner().equals(requestingUser)){
            logger.error("unable to delete group: " + CalendarfyConstants.REQUESTING_USER_NOT_AUTHORIZED);
            throw new GroupException(CalendarfyConstants.REQUESTING_USER_NOT_AUTHORIZED);
        } else {
            mongoGroupRepository.delete(existingGroup);
        }
    }
}
