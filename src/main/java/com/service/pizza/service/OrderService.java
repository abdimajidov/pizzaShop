package com.service.pizza.service;

import com.service.pizza.entity.Buyer;
import com.service.pizza.entity.Ingredient;
import com.service.pizza.entity.Order;
import com.service.pizza.entity.Pizza;
import com.service.pizza.payload.ApiResponse;

import javax.servlet.http.HttpSession;

public interface OrderService {
    ApiResponse addPizzaToOrder(HttpSession session, Long id);

    ApiResponse addMorePizzaToOrder(HttpSession session, Long id, Integer quantity);

    ApiResponse addIngredientToOrder(HttpSession session, Long id);

    ApiResponse viewOrder(HttpSession session);

    ApiResponse confirmOrder(HttpSession session);

    ApiResponse deletePizzaFromOrder(Long id);

    ApiResponse updatePizzaQuantitiy(Long id, Integer quantity);

    Order creatNewOrder(Buyer newBuyer, Pizza pizza);

    Order creatNewOrder(Buyer newBuyer, Ingredient ingredient);

    void creatNewOrderWithQuantity(Pizza pizza, Buyer buyer, Integer quantity);

    void creatNewOrderWithQuantity(Ingredient ingredient, Buyer buyer, Integer quantity);

    ApiResponse deleteIngredientFromOrder(Long id);

    ApiResponse updateIngredientQuantitiy(Long id, Integer quantity);
}
