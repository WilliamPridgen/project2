package com.example.demo;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

    @Component
    public class ScheduledTasks {

        List<Vehicle> vehicleList = new ArrayList<Vehicle>();

        RestTemplate restTemplate = new RestTemplate();

        @Scheduled(fixedRate = 2000)
        public void addVehicle() {
            String url = "http://localhost:8090/addVehicle";
            Vehicle newVehicle = new Vehicle(RandomStringUtils.randomAlphabetic(10),
                    RandomUtils.nextInt(1986, 2016), RandomUtils.nextInt(15000, 45000));
            restTemplate.postForObject(url, newVehicle, Vehicle.class);
            vehicleList.add(newVehicle);
            System.out.println("ADD");
            getLatestVehicles();
            System.out.println();


        }

       @Scheduled(fixedRate = 7000)
        public void deleteVehicle() {
           Random r = new Random();
           int randID = r.nextInt((20 - 1) + 1) + 1;
            String url = "http://localhost:8090/deleteVehicle/" + randID;
           restTemplate.delete(url);
           System.out.println("DELETE");
           getLatestVehicles();
           System.out.println();



       }

        @Scheduled(fixedRate = 10000)
        public void updateVehicle() {
            Random r = new Random();
                int randID = r.nextInt(( 4- 1) + 1) + 1;
            String url = "http://localhost:8090/updateVehicle/" + randID;
            Vehicle newVehicle = new Vehicle(randID,"Changed Vehicle",2019, RandomUtils.nextInt(15000, 45000));
                restTemplate.put(url,newVehicle);
            System.out.println("UPDATE");
            getLatestVehicles();
            System.out.println();

        }

        @Scheduled(cron ="0 * * * * *")
        public void getLatestVehicles() {
            String getUrl = "http://localhost:8090/getLatestVehicles";
            List<Vehicle> vehicles = restTemplate.getForObject(getUrl, List.class);
            System.out.println();
            System.out.println("LAST VEHICLES");
            for (int i = vehicles.size() - 1; i >= 0; i--) {
                    System.out.println(vehicles.get(i));

                }

        }
    }