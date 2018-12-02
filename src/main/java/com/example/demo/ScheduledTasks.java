package com.example.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ScheduledTasks {

    RestTemplate restTemplate = new RestTemplate();

    int i = 0;
    @Scheduled(cron="0 45 12 * 5 *" ,zone = "EST")
    public void deleteVehicle() {
    i++;
    int id = i;
    String url = "http://localhost:8090/deleteVehicle/" + id;
    restTemplate.delete(url);
    }

}