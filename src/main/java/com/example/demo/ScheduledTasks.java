package com.example.demo;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ScheduledTasks {

    List<Vehicle> vehicleList = new ArrayList<Vehicle>();

    RestTemplate restTemplate = new RestTemplate();
    @Timed
    //@Scheduled(fixedRate = 2000)
    public void addVehicle() {
        String url = "http://localhost:8090/addVehicle";
        Vehicle newVehicle = new Vehicle(RandomStringUtils.randomAlphabetic(10),
                RandomUtils.nextInt(1970, 2019), RandomUtils.nextInt(15000, 45000));
        restTemplate.postForObject(url, newVehicle, Vehicle.class);
        vehicleList.add(newVehicle);
        System.out.println("ADD");
        getLatestVehicles();
        System.out.println();


    }
    @Timed
    //@Scheduled(fixedRate = 7000)
    public void deleteVehicle() {
        Random r = new Random();
        int randID = r.nextInt((20 - 1) + 1) + 1;
        String url = "http://localhost:8090/deleteVehicle/" + randID;
        restTemplate.delete(url);
        System.out.println("DELETE");
        getLatestVehicles();
        System.out.println();



    }
    @Timed
    //@Scheduled(fixedRate = 10000)
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
    @Timed
    //@Scheduled(cron ="0 * * * * *")
    public void getLatestVehicles() {
        String getUrl = "http://localhost:8090/getLatestVehicles";
        List<Vehicle> vehicles = restTemplate.getForObject(getUrl, List.class);
        System.out.println();
        System.out.println("LAST VEHICLES");
        for (int i = vehicles.size() - 1; i >= 0; i--) {
            System.out.println(vehicles.get(i));

        }
    }

    @Timed
    @Scheduled(fixedRate = 10000)
    public void sellVehicle() {
        Random r = new Random();
        int randID = r.nextInt(( 4- 1) + 1) + 1;
        String url = "http://localhost:8090/sellVehicle/" + randID;
        Vehicle newVehicle = new Vehicle(randID,"SOLD",2019, RandomUtils.nextInt(15000, 45000));
        restTemplate.put(url,newVehicle);
        System.out.println("SOLD UPDATE");
        System.out.println();
    }
}