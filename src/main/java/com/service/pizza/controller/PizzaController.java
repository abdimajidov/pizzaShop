package com.service.pizza.controller;

import com.service.pizza.payload.ApiResponse;
import com.service.pizza.payload.PizzaDto;
import com.service.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pizza")
public class PizzaController {
    @Autowired
    PizzaService pizzaService;

    @PostMapping("/add")
    public HttpEntity<?> addPizza(@Valid @RequestBody PizzaDto pizzaDto, BindingResult result) {
        ApiResponse response = pizzaService.addPizza(pizzaDto, result);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @GetMapping("/get")
    @Description("Метод приносит всю пиццу")
    public HttpEntity<?> getPizzas() {
        ApiResponse response = pizzaService.getPizzas();
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @GetMapping("/get/{id}")
    @Description("Метод приносит пиццу с идентификатором.")
    public HttpEntity<?> getPizza(@PathVariable Long id) {
        ApiResponse response = pizzaService.getPizza(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @PutMapping("/edit/{id}")
    @Description("Метод редактирует пиццу с идентификатором.")
    public HttpEntity<?> editPizza(@PathVariable Long id,@Valid  @RequestBody PizzaDto pizzaDto,BindingResult result) {
        ApiResponse response = pizzaService.editPizza(id, pizzaDto, result);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @DeleteMapping("/delete/{id}")
    @Description("Метод удаляет пиццу с идентификатором")
    public HttpEntity<?> deletePizza(@PathVariable Long id) {
        ApiResponse response = pizzaService.deletePizza(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

}
