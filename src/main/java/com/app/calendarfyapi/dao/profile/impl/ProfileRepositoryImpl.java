package com.app.calendarfyapi.dao.profile.impl;

import com.app.calendarfyapi.dao.profile.ProfileRepository;
import com.app.calendarfyapi.model.mongo.Event;
import com.app.calendarfyapi.model.mongo.Profile;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProfileRepositoryImpl implements ProfileRepository {

    @Autowired
    MongoTemplate template;

    Logger logger = LoggerFactory.getLogger(ProfileRepositoryImpl.class);

    @Override
    public void addEvent(Profile profile, Event event) {
        Query query = new Query(Criteria.where("profileEmail").is(profile.getProfileEmail()));
        Update update = new Update();

        ArrayList<Event> updatedEventList = profile.getEvents();
        updatedEventList.add(event);
        update.set("events", updatedEventList);

        UpdateResult result = template.updateFirst(query, update, Profile.class);

        logger.info("add event update result: " + result.toString());
    }

    @Override
    public void deleteEvent(Profile profile, Event event) {
        Query query = new Query(Criteria.where("profileEmail").is(profile.getProfileEmail()));
        Update update = new Update();

        ArrayList<Event> updatedEventList = profile.getEvents();
        updatedEventList.removeIf(e -> e.getEventTitle().equals(event.getEventTitle()));
        update.set("events", updatedEventList);

        UpdateResult result = template.updateFirst(query, update, Profile.class);

        logger.info("delete event update result: " + result.toString());
    }

    @Override
    public void updateToken(Profile profile, String token) {
        Query query = new Query(Criteria.where("profileEmail").is(profile.getProfileEmail()));
        Update update = new Update();

        update.set("deviceToken", token);

        UpdateResult result = template.updateFirst(query, update, Profile.class);

        logger.info("update token result: " + result.toString());
    }

    @Override
    public void addAuthorizedUser(Profile profile, String user) {
        Query query = new Query(Criteria.where("profileEmail").is(profile.getProfileEmail()));
        Update update = new Update();

        ArrayList<String> updatedEventList = profile.getAuthorizedUsers();
        updatedEventList.add(user);

        update.set("authorizedUsers", updatedEventList);

        UpdateResult result = template.updateFirst(query, update, Profile.class);

        logger.info("add authorized user result: " + result.toString());
    }

    @Override
    public void deleteAuthorizedUser(Profile profile, String user) {
        Query query = new Query(Criteria.where("profileEmail").is(profile.getProfileEmail()));
        Update update = new Update();

        ArrayList<String> updatedEventList = profile.getAuthorizedUsers();
        updatedEventList.remove(user);
        update.set("authorizedUsers", updatedEventList);

        UpdateResult result = template.updateFirst(query, update, Profile.class);

        logger.info("delete authorized user result: " + result.toString());
    }

    @Override
    public void addGroup(Profile profile, String groupName) {
        Query query = new Query(Criteria.where("profileEmail").is(profile.getProfileEmail()));
        Update update = new Update();

        ArrayList<String> groupsList = profile.getGroups();
        groupsList.add(groupName);

        update.set("groups", groupsList);

        UpdateResult result = template.updateFirst(query, update, Profile.class);

        logger.info("add group update result: " + result.toString());
    }

    @Override
    public void deleteGroup(Profile profile, String groupName) {
        Query query = new Query(Criteria.where("profileEmail").is(profile.getProfileEmail()));
        Update update = new Update();

        ArrayList<String> groupsList = profile.getGroups();
        groupsList.remove(groupName);

        update.set("groups", groupsList);

        UpdateResult result = template.updateFirst(query, update, Profile.class);

        logger.info("delete group update result: " + result.toString());
    }
}