package com.mikepaskual.delivery.truck.service;

import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.truck.dto.CreateTruckRequest;
import com.mikepaskual.delivery.truck.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {

    @Autowired
    private final DriverService driverService;
    @Autowired
    private final TruckRepository truckRepository;

    public TruckService(DriverService driverService, TruckRepository truckRepository) {
        this.driverService = driverService;
        this.truckRepository = truckRepository;
    }

    public Truck registerTruck(CreateTruckRequest request) {
        return truckRepository.save(Truck.builder()
                .setCapacity(request.getCapacity())
                .setCreatedAt(request.getCreatedAt())
                .setColor(request.getColor())
                .setDriver(request.getDriver())
                .setFuelType(FuelType.valueOf(request.getFuelType()))
                .setHeight(request.getHeight())
                .setId(request.getId())
                .setLength(request.getLength())
                .setMake(request.getMake())
                .setModel(request.getModel())
                .setPlate(request.getPlate())
                .setPurchaseDate(request.getPurchaseDate())
                .setStatus(StatusTruck.valueOf(request.getStatus()))
                .setTransmission(Transmission.valueOf(request.getTransmission()))
                .setWidth(request.getWidth())
                .setYear(request.getYear()).build());
    }

    public Truck findBy(Long truckId) {
        return truckRepository.findById(truckId)
                .orElseThrow(() -> new IllegalArgumentException("Truck not found with ID: " + truckId));
    }

    public List<Truck> findTrucks(Long userId) {
        return truckRepository.findByDriver(driverService.findById(userId));
    }
}
