package com.service.pizza.service;

import com.service.pizza.entity.Order;
import com.service.pizza.payload.ApiResponse;

public interface JournalService {
    ApiResponse migrateFromCartToJournal(Order order);

    ApiResponse updateIngredientQuantitiy(Long buyerId);
}
