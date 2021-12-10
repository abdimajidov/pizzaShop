package com.service.pizza.repository;

import com.service.pizza.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer,Long> {
    Buyer findBuyerBySessionId(String sessionId);
}
