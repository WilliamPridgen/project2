package com.example.demo;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.client.RestTemplate;

//getTweets();

@RestController
public class RESTController {

    @Autowired
    private EventDao eventDao = new EventDao();
    //private VehicleDao vehicleDao = new VehicleDao();


    private static String consumerKeyStr = "tTWmHjntTtuvjqfTguxiqj8Ui";
    private static String consumerSecretStr = "zDmZbsuqVjQKXTSymtriLZNV8ATNgY4nCosptqxplKdcudcgkk";
    private static String accessTokenStr = "37442729-pOKHLPydB9ECRYaBjbVUD6SBy3GNtmJuHKBnbGfyM";
    private static String accessTokenSecretStr = "hWZVNKidg8dl5iaArGrKO2V0bbCzAV6t11HTqyTfJgtKQ";

    RestTemplate restTemplate = new RestTemplate();
    public static void getTweets() throws Exception{
        OAuthConsumer OAuthConsumer =  new CommonsHttpOAuthConsumer(consumerKeyStr,consumerSecretStr);
        OAuthConsumer.setTokenWithSecret(accessTokenStr,accessTokenSecretStr);
        HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/search/tweets.json?q=%23ggc&count=4");
        OAuthConsumer.sign(httpGet);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode+ ':'
                + httpResponse.getStatusLine().getReasonPhrase());
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
    }

    int i = 0;
    //@Scheduled(fixedRate = 10000)
    public  void postUpdate() throws Exception {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = dateFormat.format(date);
        String search = URLEncoder.encode(strDate, "UTF-8");

        OAuthConsumer OAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr, consumerSecretStr);
        OAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);
        i++;

        String getUrl = "http://localhost:8090/getEvent/" + i;
        Event e = restTemplate.getForObject(getUrl, Event.class);

        String testDate = "12/02/2018";
        String noEvents = "No events today";



        String event = URLEncoder.encode(e.toString(), "UTF-8");
        String alternative = URLEncoder.encode(noEvents, "UTF-8");

        event.split("\\s+");


       // if (strDate.equals(testDate)){
            HttpPost httpPost = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status=" + event);//+v.getMakeModel());


        OAuthConsumer.sign(httpPost);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode + ':'
                + httpResponse.getStatusLine().getReasonPhrase());
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
    //}

    /*if(getUrl==null){
            HttpPost httpPost = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status=" + alternative);//+v.getMakeModel());


            OAuthConsumer.sign(httpPost);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println(statusCode + ':'
                    + httpResponse.getStatusLine().getReasonPhrase());
            System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
        }*/
    }
    @RequestMapping(value = "/createEvent", method = RequestMethod.POST)
    public Event createEvent(@RequestBody Event newEvent) throws IOException {
        eventDao.create(newEvent);
        return newEvent;
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.PUT)
    public Event updateVehicle(@RequestBody Event newEvent) throws IOException {
        eventDao.updateEvent(newEvent);
        return newEvent;
    }

    @RequestMapping(value = "/deleteEvent/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEvent(@PathVariable("id") int id) throws IOException {
        eventDao.deleteEvent(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getEvent/{id}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable("id") int id) throws IOException {
        return eventDao.getById(id);
    }

   /* @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() throws IOException { return vehicleDao.getLatestVehicles(); }

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        vehicleDao.create(newVehicle);
        return newVehicle;
    }

    @RequestMapping(value = "/updateVehicle/{id}", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle,@PathVariable("id") int id ) throws IOException {
        vehicleDao.updateVehicle(newVehicle,id);
        return newVehicle;
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        vehicleDao.deleteVehicle(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        return vehicleDao.getById(id);
    }



    @RequestMapping(value = "/sellVehicle/{id}", method = RequestMethod.PUT)
    public Vehicle sellVehicle(@RequestBody Vehicle newVehicle,@PathVariable("id") int id ) throws IOException {
        vehicleDao.sellVehicle(newVehicle,id);
        return newVehicle;
    }*/
}