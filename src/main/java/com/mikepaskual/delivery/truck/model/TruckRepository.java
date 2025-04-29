package com.mikepaskual.delivery.truck.model;

import com.mikepaskual.delivery.driver.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    List<Truck> findByDriver(Driver driver);
}
