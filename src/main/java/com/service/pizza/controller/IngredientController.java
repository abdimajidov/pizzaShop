package com.service.pizza.controller;

import com.service.pizza.payload.ApiResponse;
import com.service.pizza.payload.IngredientDto;
import com.service.pizza.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;

    @PostMapping("/add")
    public HttpEntity<?> addIngredient(@Valid @RequestBody IngredientDto ingredientDto, BindingResult result) {
        ApiResponse response = ingredientService.addIngredient(ingredientDto, result);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @GetMapping("/get")
    @Description("Метод приносит всю ингредиент")
    public HttpEntity<?> getIngredients() {
        ApiResponse response = ingredientService.getIngredients();
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @GetMapping("/get/{id}")
    @Description("Метод приносит ингредиент с идентификатором.")
    public HttpEntity<?> getIngredient(@PathVariable Long id) {
        ApiResponse response = ingredientService.getIngredient(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @PutMapping("/edit/{id}")
    @Description("Метод редактирует ингредиент с идентификатором.")
    public HttpEntity<?> editIngredient(@PathVariable Long id,@Valid @RequestBody IngredientDto ingredientDto,BindingResult result) {
        ApiResponse response = ingredientService.editIngredient(id, ingredientDto, result);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @DeleteMapping("/delete/{id}")
    @Description("Метод удаляет ингредиент с идентификатором")
    public HttpEntity<?> deletIngredient(@PathVariable Long id) {
        ApiResponse response = ingredientService.deletIngredient(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

}
