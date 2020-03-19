package com.app.calendarfyapi.controller;

import com.app.calendarfyapi.common.CalendarfyConstants;
import com.app.calendarfyapi.exception.ProfileException;
import com.app.calendarfyapi.common.ResponseAdapter;
import com.app.calendarfyapi.dao.crud.ProfileCrud;
import com.app.calendarfyapi.model.mongo.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    ProfileCrud profileCrud;

    @RequestMapping( {"/", ""} )
    public ResponseEntity<Object> base() {
        return ResponseAdapter.getResponse(HttpStatus.OK, true, "profile service is running");
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(
            @RequestHeader String profileEmail
    ) {
        logger.info("Create request received");

        try {
            profileCrud.createProfile(profileEmail);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.CREATE_PROFILE_SUCCESS);
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.OK, false, e.getMessage());
        }
    }

    @PostMapping("/updateToken")
    public ResponseEntity<Object> updateToken(
            @RequestHeader String profileEmail,
            @RequestHeader String deviceToken
    ) {
        logger.info("update token request received");

        try {
            profileCrud.updateDeviceToken(profileEmail, deviceToken);

            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_PROFILE_SUCCESS);
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @GetMapping("/getEvents")
    public ResponseEntity<Object> getEvents(
            @RequestHeader String profileEmail
    ) {
        logger.info("get events request received");

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(profileCrud.getEventsFromProfile(profileEmail));
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/addEvent")
    public ResponseEntity<Object> addEvent(
            @RequestHeader(value = "requestingUser") String requestingUser,
            @RequestHeader(value = "profileEmail") String profileEmail,
            @RequestHeader(value = "eventTitle") String eventTitle,
            @RequestHeader(value = "eventDesc") String eventDesc
    ) {
        logger.info("add event request received");

        try {
            profileCrud.addEventToProfile(profileEmail, requestingUser, new Event(eventTitle, eventDesc));
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_PROFILE_SUCCESS);
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/removeEvent")
    public ResponseEntity<Object> removeEvent(
            @RequestHeader(value = "requestingUser") String requestingUser,
            @RequestHeader(value = "profileEmail") String profileEmail,
            @RequestHeader(value = "eventTitle") String eventTitle,
            @RequestHeader(value = "eventDesc") String eventDesc
    ) {
        logger.info("remove event request received");

        try {
            profileCrud.removeEventFromProfile(profileEmail, requestingUser, new Event(eventTitle, eventDesc));
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_PROFILE_SUCCESS);
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @GetMapping("/getGroups")
    public ResponseEntity<Object> getGroups(
            @RequestHeader(value = "profileEmail") String profileEmail
    ) {
        logger.info("get groups for profile request received");

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            profileCrud.getProfile(profileEmail).getGroups()
                    );
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/grantAccess")
    public ResponseEntity<Object> grantAccess(
            @RequestHeader(value = "profileEmail") String profileEmail,
            @RequestHeader(value = "authorizedUser") String authorizedUser
    ) {
        logger.info("grant access request received");

        try {
            profileCrud.addAuthorizedUser(profileEmail, authorizedUser);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_PROFILE_SUCCESS);
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @PostMapping("/revokeAccess")
    public ResponseEntity<Object> revokeAccess(
            @RequestHeader(value = "profileEmail") String profileEmail,
            @RequestHeader(value = "authorizedUser") String authorizedUser
    ) {
        logger.info("revoke access request received");

        try {
            profileCrud.removeAuthorizedUser(profileEmail, authorizedUser);
            return ResponseAdapter.getResponse(HttpStatus.OK, true, CalendarfyConstants.UPDATE_PROFILE_SUCCESS);
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }

    @GetMapping("/getAuthorizedUsers")
    public ResponseEntity<Object> getAuthorizedUsers (
            @RequestHeader(value = "profileEmail") String profileEmail
    ) {
        logger.info("get authorized users request received");

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(profileCrud.getProfile(profileEmail).getAuthorizedUsers());
        } catch (ProfileException e) {
            return ResponseAdapter.getResponse(HttpStatus.BAD_REQUEST, false, e.getMessage());
        }
    }
}