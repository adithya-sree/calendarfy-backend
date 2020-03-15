package com.app.calendarfyapi.model.response;

import lombok.Data;

@Data
public class BaseResponse {

    private boolean success;

    public BaseResponse (boolean success) {
        this.success = success;
    }
}