package com.example.demo;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
@Transactional
public class VehicleDao {
    @PersistenceContext
    private EntityManager entityManager;
    public void create(Vehicle vehicle) {
        entityManager.persist(vehicle);
        return;
    }

    public Vehicle updateVehicle(Vehicle vehicle,int id ) {
        if (entityManager.find(Vehicle.class,id) != null) {
            entityManager.merge(vehicle);
        }else if(entityManager.find(Vehicle.class, id ) == null){
            System.out.print("There is no vehicle found with that ID");
        }
        return vehicle;
    }

    public Vehicle deleteVehicle(int id) {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);
        if (vehicle != null) {
            entityManager.remove(vehicle);
        }else if(vehicle == null){
        System.out.print("There is no vehicle found with that ID");
    }
        return vehicle;
    }

    public Vehicle getById(int id) {
        if (entityManager.find(Vehicle.class,id) != null) {
            return entityManager.find(Vehicle.class, id);
        }else{
            System.out.print("There is no vehicle found with that ID");
        }
        return null;
    }

    public List<Vehicle> getLatestVehicles() {
        List<Vehicle> lastVehicles = new ArrayList<Vehicle>();
            String queryStr = "SELECT * FROM vehicles ORDER BY id DESC LIMIT 10";
            Query query = entityManager.createNativeQuery(queryStr, Vehicle.class);
            lastVehicles = query.getResultList();
        if(lastVehicles.size()<=10) {
            return lastVehicles;
        }else{
            System.out.print("There aren't enough vehicles in your database to execute this action");
        }
        return null;
    }
    public Vehicle sellVehicle(Vehicle vehicle,int id ) {
        if (entityManager.find(Vehicle.class,id) != null) {
            entityManager.merge(vehicle);
        }else if(entityManager.find(Vehicle.class, id ) == null){
            System.out.print("There is no vehicle found with that ID");
        }
        return vehicle;
    }
}