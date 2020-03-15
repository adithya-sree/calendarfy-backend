package com.app.calendarfyapi.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection = "group-v2")
public class Group {
    @Id
    private String id;

    private String groupName;

    private String groupOwner;

    private ArrayList<String> groupUsers = new ArrayList<>();

    private ArrayList<Event> groupEvents = new ArrayList<>();
}
