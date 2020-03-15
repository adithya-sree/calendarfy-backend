package com.app.calendarfyapi.dao.group.impl;

import com.app.calendarfyapi.dao.group.GroupRepository;
import com.app.calendarfyapi.model.mongo.Event;
import com.app.calendarfyapi.model.mongo.Group;
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
public class GroupRepositoryImpl implements GroupRepository {

    @Autowired
    MongoTemplate template;

    Logger logger = LoggerFactory.getLogger(GroupRepositoryImpl.class);

    @Override
    public void addEventToGroup(Group group, Event event) {
        Query query = new Query(Criteria.where("groupName").is(group.getGroupName()));
        Update update = new Update();

        ArrayList<Event> updatedEventList = group.getGroupEvents();
        updatedEventList.add(event);
        update.set("groupEvents", updatedEventList);

        UpdateResult result = template.updateFirst(query, update, Group.class);

        logger.info("add event to group result: " + result.toString());
    }

    @Override
    public void removeEventFromGroup(Group group, Event event) {
        Query query = new Query(Criteria.where("groupName").is(group.getGroupName()));
        Update update = new Update();

        ArrayList<Event> updatedEventList = group.getGroupEvents();
        updatedEventList.removeIf(e -> e.getEventTitle().equals(event.getEventTitle()));
        update.set("groupEvents", updatedEventList);

        UpdateResult result = template.updateFirst(query, update, Group.class);

        logger.info("remove event from group result: " + result.toString());
    }

    @Override
    public void addUserToGroup(Group group, String profileEmail) {
        Query query = new Query(Criteria.where("groupName").is(group.getGroupName()));
        Update update = new Update();

        ArrayList<String> updatedEventList = group.getGroupUsers();
        updatedEventList.add(profileEmail);
        update.set("groupUsers", updatedEventList);

        UpdateResult result = template.updateFirst(query, update, Group.class);

        logger.info("add user to group result: " + result.toString());
    }

    @Override
    public void removeUserFromGroup(Group group, String profileEmail) {
        Query query = new Query(Criteria.where("groupName").is(group.getGroupName()));
        Update update = new Update();

        ArrayList<String> updatedEventList = group.getGroupUsers();
        updatedEventList.remove(profileEmail);
        update.set("groupUsers", updatedEventList);

        UpdateResult result =  template.updateFirst(query, update, Group.class);

        logger.info("remove user from group result: " + result.toString());
    }
}