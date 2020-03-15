package com.app.calendarfyapi.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class CalendarfyControllerTest {

    CalendarfyController controller = new CalendarfyController();

    @Test
    public void testRunningResponse() {
        ResponseEntity<Object> response = controller.running();

        Assert.assertTrue(response.getBody().toString().contains("true"));
    }

    @Test
    public void testUptimeResponse() {
        ResponseEntity<Object> response = controller.uptime();

        Assert.assertTrue(response.getBody().toString().contains("uptime"));
    }
}
