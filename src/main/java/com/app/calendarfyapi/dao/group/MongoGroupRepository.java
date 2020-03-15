package com.app.calendarfyapi.dao.group;

import com.app.calendarfyapi.model.mongo.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGroupRepository extends MongoRepository<Group, String> {
    Group findFirstByGroupName(String groupName);
}