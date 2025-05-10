package com.mikepaskual.delivery.customer.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackRepository extends JpaRepository<Pack, Long> {

    List<Pack> findBySenderId(Long customerId);

    List<Pack> findByReceiverId(Long customerId);
}
