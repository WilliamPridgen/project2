package com.example.demo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;


@Repository
@Transactional
public class EventDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Event event) {
        entityManager.persist(event);
        return;
    }

    public Event updateEvent(Event event ) {
            entityManager.merge(event);
        return event;
    }

    public Event deleteEvent(int id) {
        Event event = entityManager.find(Event.class, id);
        if (event != null) {
            entityManager.remove(event);
        }else if(event == null){
            System.out.print("There is no event found with that ID");
        }
        return event;
    }

    public Event getById(int id) {
        if (entityManager.find(Event.class,id) != null) {
            return entityManager.find(Event.class, id);
        }else{
            System.out.print("There is no event found with that ID");
        }
        return null;
    }

}