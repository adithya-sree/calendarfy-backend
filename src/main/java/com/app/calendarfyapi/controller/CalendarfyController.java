package com.app.calendarfyapi.controller;

import com.app.calendarfyapi.model.response.BaseResponse;
import com.app.calendarfyapi.model.response.UptimeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.management.ManagementFactory;
import java.sql.SQLException;

@RestController
@RequestMapping("/api")
public class CalendarfyController {

    Logger logger = LoggerFactory.getLogger(CalendarfyController.class);

    @Autowired
    public RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping("/running")
    public ResponseEntity<Object> running() {
        logger.info("Running check received");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse(true));
    }

    @RequestMapping("/uptime")
    public ResponseEntity<Object> uptime() {
        String uptime = String.valueOf(ManagementFactory.getRuntimeMXBean().getUptime());

        logger.info("server uptime: " + uptime);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UptimeResponse(true, uptime));
    }

    @RequestMapping("/routes")
    public ResponseEntity<Object> routes() throws SQLException
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    requestMappingHandlerMapping.getHandlerMethods().keySet().stream().map(t ->
                        (t.getMethodsCondition().getMethods().size() == 0 ? "GET" : t.getMethodsCondition().getMethods().toArray()[0]) + " " +
                                t.getPatternsCondition().getPatterns().toArray()[0]
                    ).toArray()
                );
    }
}