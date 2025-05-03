package com.mikepaskual.delivery.truck.service;

import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.truck.dto.CreateTruckRequest;
import com.mikepaskual.delivery.truck.exception.TruckNotFoundException;
import com.mikepaskual.delivery.truck.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Truck updateStatus(Long truckId) {
        Truck truck = getTruckOrThrow(truckId);
        StatusTruck newStatus = (truck.getStatus() == StatusTruck.ACTIVE) ? StatusTruck.INACTIVE : StatusTruck.ACTIVE;
        truck.setStatus(newStatus);
        return truckRepository.save(truck);
    }

    public void registerTruck(Long driverId, CreateTruckRequest request) {
        truckRepository.save(Truck.builder()
                .setCapacity(request.getCapacity())
                .setCreatedAt(LocalDateTime.now())
                .setColor(request.getColor())
                .setDriver(driverService.getDriverOrThrow(driverId))
                .setFuelType(FuelType.valueOf(request.getFuelType()))
                .setHeight(request.getHeight())
                .setLength(request.getLength())
                .setMake(request.getMake())
                .setModel(request.getModel())
                .setPlate(request.getPlate())
                .setPurchaseDate(request.getPurchaseDate())
                .setStatus(StatusTruck.INACTIVE)
                .setTransmission(Transmission.valueOf(request.getTransmission()))
                .setWidth(request.getWidth())
                .setYear(request.getYear()).build());
    }

    public Truck getTruckOrThrow(Long truckId) {
        return truckRepository.findById(truckId)
                .orElseThrow(() -> new TruckNotFoundException(truckId));
    }

    public List<Truck> getTrucksByDriver(Long userId) {
        return truckRepository.findByDriver(driverService.getDriverOrThrow(userId));
    }
}
