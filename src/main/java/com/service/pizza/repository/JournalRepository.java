package com.service.pizza.repository;

import com.service.pizza.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal,Long> {
    List<Journal> findByBuyerId(Long buyerId);
}
