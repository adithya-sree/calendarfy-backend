package com.app.calendarfyapi.common;

import com.app.calendarfyapi.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseAdapter {
    public static ResponseEntity<Object> getResponse(HttpStatus status, boolean success, String message) {
        return ResponseEntity
                .status(status)
                .body(new Response(success, message));
    }
}
