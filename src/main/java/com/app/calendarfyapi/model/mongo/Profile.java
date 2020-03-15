package com.app.calendarfyapi.model.mongo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection = "profile-v2")
public class Profile {
    @Id
    private String id;

    private String profileEmail;

    private ArrayList<Event> events = new ArrayList<>();

    private ArrayList<String> authorizedUsers = new ArrayList<>();

    private String deviceToken;

    private ArrayList<String> groups = new ArrayList<>();

    public void addAuthorizedUser(String user) {
        authorizedUsers.add(user);
    }

    public void deleteAuthorizedUser(String user) {
        authorizedUsers.remove(user);
    }
}
