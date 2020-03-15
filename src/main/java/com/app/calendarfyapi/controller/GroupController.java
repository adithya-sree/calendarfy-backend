package com.app.calendarfyapi.controller;

import com.app.calendarfyapi.common.CalendarfyConstants;
import com.app.calendarfyapi.common.ResponseAdapter;
import com.app.calendarfyapi.dao.crud.GroupCrud;
import com.app.calendarfyapi.exception.GroupException;
import com.app.calendarfyapi.model.mongo.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    GroupCrud groupCrud;

    @RequestMapping({"/", ""})
    public ResponseEntity<Object> base() {
        return ResponseAdapter.getResponse(HttpStatus.OK, true, "group service is running");
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(
            @RequestHeader(value = "groupName") String groupName,
            @RequestHeader(value = "profileEmail") String profileEmail
    ) {
        logger.info("create request received");

        try {
            groupCrud.createNewGroup(groupName, profileEmail);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.CREATE_GROUP_SUCCESS);
        } catch (GroupException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> delete(
            @RequestHeader(value = "groupName") String groupName,
            @RequestHeader(value = "requestingUser") String profileEmail
    ) {
        logger.info("delete request received");

        try {
            groupCrud.deleteGroup(groupName, profileEmail);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_GROUP_SUCCESS);
        } catch (GroupException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @GetMapping("/getEvents")
    public ResponseEntity<Object> getEvents(
            @RequestHeader(value = "groupName") String groupName
    ) {
        logger.info("get events request received");

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(groupCrud.getGroupEvents(groupName));
        } catch (GroupException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/addEvent")
    public ResponseEntity<Object> addEvent(
            @RequestHeader(value = "groupName") String groupName,
            @RequestHeader(value = "eventTitle") String eventTitle,
            @RequestHeader(value = "eventDesc") String eventDesc
    ) {
        logger.info("add event request received");

        Event event = new Event(eventTitle, eventDesc);

        try {
            groupCrud.addEventToGroup(groupName, event);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_GROUP_SUCCESS);
        } catch (GroupException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/removeEvent")
    public ResponseEntity<Object> removeEvent(
            @RequestHeader(value = "groupName") String groupName,
            @RequestHeader(value = "eventTitle") String eventTitle,
            @RequestHeader(value = "eventDesc") String eventDesc
    ) {
        logger.info("remove event request received");

        Event event = new Event(eventTitle, eventDesc);

        try {
            groupCrud.removeEventFromGroup(groupName, event);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_GROUP_SUCCESS);
        } catch (GroupException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(
            @RequestHeader(value = "groupName") String groupName,
            @RequestHeader(value = "profileEmail") String profileEmail
    ) {
        logger.info("add user request received");

        try {
            groupCrud.addUserToGroup(groupName, profileEmail);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_GROUP_SUCCESS);
        } catch (GroupException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/removeUser")
    public ResponseEntity<Object> removeUser(
            @RequestHeader(value = "groupName") String groupName,
            @RequestHeader(value = "profileEmail") String profileEmail
    ) {
        logger.info("add user request received");

        try {
            groupCrud.removeUserFromGroup(groupName, profileEmail);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_GROUP_SUCCESS);
        } catch (GroupException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }
}