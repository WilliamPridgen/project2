package com.example.demo;

import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class RESTController {
    @Autowired
    private VehicleDao vehicleDao;

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

    @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() throws IOException { return vehicleDao.getLatestVehicles(); }

    @RequestMapping(value = "/sellVehicle/{id}", method = RequestMethod.PUT)
    public Vehicle sellVehicle(@RequestBody Vehicle newVehicle,@PathVariable("id") int id ) throws IOException {
        vehicleDao.sellVehicle(newVehicle,id);
        return newVehicle;
    }
}