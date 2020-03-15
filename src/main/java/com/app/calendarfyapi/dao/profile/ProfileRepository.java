package com.app.calendarfyapi.dao.profile;

import com.app.calendarfyapi.model.mongo.Event;
import com.app.calendarfyapi.model.mongo.Profile;

public interface ProfileRepository {
    void addEvent(Profile profile, Event event);

    void deleteEvent(Profile profile, Event event);

    void updateToken(Profile profile, String token);

    void addAuthorizedUser(Profile profile, String user);

    void deleteAuthorizedUser(Profile profile, String user);

    void addGroup(Profile profile, String groupName);

    void deleteGroup(Profile profile, String groupName);
}
