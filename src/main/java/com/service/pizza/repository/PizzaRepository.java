package com.service.pizza.repository;

import com.service.pizza.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza,Long> {
}
