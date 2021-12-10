package com.service.pizza.service;

import com.service.pizza.payload.ApiResponse;
import com.service.pizza.payload.PizzaDto;
import org.springframework.validation.BindingResult;

public interface PizzaService {

    ApiResponse addPizza(PizzaDto pizzaDto, BindingResult result);

    ApiResponse getPizzas();

    ApiResponse getPizza(Long id);

    ApiResponse editPizza(Long id, PizzaDto pizzaDto, BindingResult result);

    ApiResponse deletePizza(Long id);
}
