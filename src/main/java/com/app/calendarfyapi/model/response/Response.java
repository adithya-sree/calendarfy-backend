package com.app.calendarfyapi.model.response;

import lombok.Data;

@Data
public class Response extends BaseResponse {

    private String message;

    public Response(Boolean success, String message) {
        super(success);

        this.message = message;
    }
}
