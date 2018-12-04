package com.example.demo;

import java.io.IOException;
import java.net.URLEncoder;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.client.RestTemplate;


@RestController
public class RESTController {

    @Autowired
    private EventDao eventDao = new EventDao();


    private static String consumerKeyStr = "";
    private static String consumerSecretStr = "";
    private static String accessTokenStr = "";
    private static String accessTokenSecretStr = "";

    RestTemplate restTemplate = new RestTemplate();
    int i = 0;
    @Scheduled(cron="0 45 12 * * *" ,zone = "EST")
    public  void postUpdate() throws Exception {

        OAuthConsumer OAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr, consumerSecretStr);
        OAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);

        i++;
        String getUrl = "http://localhost:8090/getEvent/" + i;
        Event e = restTemplate.getForObject(getUrl, Event.class);

        String event = URLEncoder.encode(e.toString(), "UTF-8");

        event.split("\\s+");

         HttpPost httpPost = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status=" + event);

        OAuthConsumer.sign(httpPost);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode + ':'
                + httpResponse.getStatusLine().getReasonPhrase());
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
    }

    @RequestMapping(value = "/directPost", method = RequestMethod.POST)
    public  void directPost(@RequestBody Event newEvent) throws Exception {

        OAuthConsumer OAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr, consumerSecretStr);
        OAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);

        i++;
        String getUrl = "http://localhost:8090/getEvent/" + i;
        Event e = restTemplate.getForObject(getUrl, Event.class);

        String event = URLEncoder.encode(newEvent.getEventMsg(), "UTF-8");

        event.split("\\s+");

        HttpPost httpPost = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status=" + event);

        OAuthConsumer.sign(httpPost);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode + ':'
                + httpResponse.getStatusLine().getReasonPhrase());
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
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
}