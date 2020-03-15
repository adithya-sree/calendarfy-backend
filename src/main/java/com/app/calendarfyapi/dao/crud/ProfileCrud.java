package com.app.calendarfyapi.dao.crud;

import com.app.calendarfyapi.exception.ProfileException;
import com.app.calendarfyapi.model.mongo.Event;
import com.app.calendarfyapi.model.mongo.Profile;

import java.util.List;

public interface ProfileCrud {
    Profile getProfile(String profileEmail) throws ProfileException;
    void createProfile(String profileEmail) throws ProfileException;
    void addEventToProfile(String profileEmail, String requestingUser, Event event) throws ProfileException;
    void removeEventFromProfile(String profileEmail, String requestingUser, Event event) throws ProfileException;
    List<Event> getEventsFromProfile(String profileEmail) throws ProfileException;
    void updateDeviceToken(String profileEmail, String token) throws ProfileException;
    void addAuthorizedUser(String profileEmail, String authorizedUser) throws ProfileException;
    void removeAuthorizedUser(String profileEmail, String authorizedUser) throws ProfileException;
    void addUserToGroup(String profileEmail, String group) throws ProfileException;
    void removeUserFromGroup(String profileEmail, String group) throws ProfileException;
}
