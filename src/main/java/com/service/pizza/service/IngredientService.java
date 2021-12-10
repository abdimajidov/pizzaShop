package com.service.pizza.service;

import com.service.pizza.payload.ApiResponse;
import com.service.pizza.payload.IngredientDto;
import org.springframework.validation.BindingResult;

public interface IngredientService {
    ApiResponse addIngredient(IngredientDto ingredientDto, BindingResult result);

    ApiResponse getIngredients();

    ApiResponse getIngredient(Long id);

    ApiResponse editIngredient(Long id, IngredientDto ingredientDto, BindingResult result);

    ApiResponse deletIngredient(Long id);
}
