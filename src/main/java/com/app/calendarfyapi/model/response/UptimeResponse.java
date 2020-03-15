package com.app.calendarfyapi.model.response;

import lombok.Data;

@Data
public class UptimeResponse extends BaseResponse {

    private String uptime;

    public UptimeResponse(boolean success, String uptime) {
        super(success);

        this.uptime = uptime;
    }
}
