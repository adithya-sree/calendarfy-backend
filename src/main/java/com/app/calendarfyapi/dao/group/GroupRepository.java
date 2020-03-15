package com.app.calendarfyapi.dao.group;

import com.app.calendarfyapi.model.mongo.Event;
import com.app.calendarfyapi.model.mongo.Group;

public interface GroupRepository {
    void addEventToGroup(Group group, Event event);
    void removeEventFromGroup(Group group, Event event);
    void addUserToGroup(Group group, String profileEmail);
    void removeUserFromGroup(Group group, String profileEmail);
}
