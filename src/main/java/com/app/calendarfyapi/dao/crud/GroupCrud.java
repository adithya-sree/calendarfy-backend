package com.app.calendarfyapi.dao.crud;

import com.app.calendarfyapi.exception.GroupException;
import com.app.calendarfyapi.model.mongo.Event;

import java.util.List;

public interface GroupCrud {
    void createNewGroup(String groupName, String profileEmail) throws GroupException;
    List<Event> getGroupEvents(String groupName) throws GroupException;
    String getGroupOwner(String groupName) throws GroupException;
    List<String> getGroupUsers(String groupName) throws GroupException;
    void addEventToGroup(String groupName, Event event) throws GroupException;
    void removeEventFromGroup(String groupName, Event event) throws GroupException;
    void addUserToGroup(String groupName, String profileEmail) throws GroupException;
    void removeUserFromGroup(String groupName, String profileEmail) throws GroupException;
    void deleteGroup(String groupName, String requestingUser) throws GroupException;
}
