package com.app.calendarfyapi.dao.profile;

import com.app.calendarfyapi.model.mongo.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProfileRepository extends MongoRepository<Profile, String> {
    Profile findFirstByProfileEmail(String profileEmail);
}