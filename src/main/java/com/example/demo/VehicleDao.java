package com.example.demo;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class VehicleDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Vehicle vehicle) {
        entityManager.persist(vehicle);
        return;
    }

    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        if (entityManager.find(Vehicle.class, vehicle.getId()) != null) {
            entityManager.merge(vehicle);
        }
        return vehicle;
    }
    public Vehicle deleteVehicle(int id) {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);
        if (vehicle != null) {
            entityManager.remove(vehicle);
        }
        return vehicle;
    }
    public List<Vehicle> getLatestVehicles() {
        List<Vehicle> latestVehicles = new ArrayList<Vehicle>();
        String queryStr = "SELECT * FROM vehicles ORDER BY id DESC LIMIT 10";
        Query query = entityManager.createNativeQuery(queryStr, Vehicle.class);
        latestVehicles = query.getResultList();
        return latestVehicles;
    }
}