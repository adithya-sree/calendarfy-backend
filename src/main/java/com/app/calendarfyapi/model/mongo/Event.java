package com.app.calendarfyapi.model.mongo;

import lombok.Data;

@Data
public class Event {

    public Event(String eventTitle, String eventDesc) {
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
    }

    private String eventTitle;
    private String eventDesc;
}
