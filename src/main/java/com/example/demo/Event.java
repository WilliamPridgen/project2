package com.example.demo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "events")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String eventDate;
    private String eventMsg;

    public Event() {

    }

   public Event(String date, String eventMsg) {
        this.id = id;
        this.eventDate = eventDate;
        this.eventMsg = eventMsg;

    }

    public Event(int id, String eventDate, String eventMsg) {
        this.id = id;
        this.eventDate = eventDate;
        this.eventMsg = eventMsg;
    }

    public String toString() {
        return this.eventMsg + " on " + this.getDate();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDate() { return eventDate; }

    public void setDate(String eventDate) { this.eventDate = eventDate; }

    public String getEventMsg() { return eventMsg; }

    public void setEventMsg(String eventMsg) { this.eventMsg = eventMsg; }
}
