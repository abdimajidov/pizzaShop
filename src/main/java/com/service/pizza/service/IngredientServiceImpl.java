package com.service.pizza.service;

import com.service.pizza.entity.Ingredient;
import com.service.pizza.entity.Pizza;
import com.service.pizza.payload.ApiResponse;
import com.service.pizza.payload.IngredientDto;
import com.service.pizza.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public ApiResponse addIngredient(IngredientDto ingredientDto, BindingResult result) {
        try {
            if (!result.hasErrors()) {
                ingredientRepository.save(new Ingredient(ingredientDto.getName(), ingredientDto.getPrice()));
                return new ApiResponse("Succesfully added", true, "Ingredient added");
            } else {
                return new ApiResponse("Error", false, "Some Errors");
            }
        } catch (Exception e) {
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse getIngredients() {
        try {
            List<Ingredient> ingredientList = ingredientRepository.findAll();
            return ingredientList.size() != 0 ? new ApiResponse("Ingredient list:", true, ingredientList) :
                    new ApiResponse("Ingredient list is empty", false, "Ingredient list is empty");
        } catch (Exception e) {
            return new ApiResponse("Some Errors", false, "Some errors");
        }
    }

    @Override
    public ApiResponse getIngredient(Long id) {
        try {
            Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
            if (optionalIngredient.isEmpty()) {
                return new ApiResponse("Ingredient not found", false, "Ingredient not found");
            }
            return new ApiResponse("Ingredient:", true, optionalIngredient.get());
        } catch (Exception e) {
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse editIngredient(Long id, IngredientDto ingredientDto, BindingResult result) {
        try {
            if (!result.hasErrors()) {
                Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
                if (optionalIngredient.isEmpty()) {
                    return new ApiResponse("Ingredient not found", false, "Ingredient not found");
                }
                Ingredient ingredient = optionalIngredient.get();
                ingredient.setName(ingredientDto.getName());
                ingredient.setPrice(ingredientDto.getPrice());

                ingredientRepository.save(ingredient);

                return new ApiResponse("Ingredient edited", true, "Ingredient edited");

            } else {
                return new ApiResponse("Error", false, "Some Errors");
            }
        } catch (Exception e) {
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse deletIngredient(Long id) {
        try {
            Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
            if (optionalIngredient.isEmpty()) {
                return new ApiResponse("Ingredient not found", false, "Ingredient not found");
            }
            ingredientRepository.deleteById(id);

            return new ApiResponse("Ingredient deleted", true, "Ingredient deleted");
        } catch (Exception e) {
            return new ApiResponse("Error", false, "Some Error");
        }
    }
}
