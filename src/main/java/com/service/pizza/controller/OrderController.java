package com.service.pizza.controller;

import com.service.pizza.payload.ApiResponse;
import com.service.pizza.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add-pizza")
    public HttpEntity<?> addPizzaToOrder(HttpSession session, @RequestParam Long id){
        ApiResponse response = orderService.addPizzaToOrder(session,id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @PostMapping("/add-more-pizza")
    public HttpEntity<?> addMorePizzaToOrder(HttpSession session,@RequestParam Long id,@RequestParam Integer quantity){
        ApiResponse response = orderService.addMorePizzaToOrder(session,id,quantity);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @PostMapping("/add-ingred")
    public HttpEntity<?> addIngredientToOrder(HttpSession session, @RequestParam Long id){
        ApiResponse response = orderService.addIngredientToOrder(session,id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @GetMapping("/view-order")
    public HttpEntity<?> viewOrder(HttpSession session){
        ApiResponse response = orderService.viewOrder(session);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @PostMapping("/confirm")
    public HttpEntity<?> confirmOrder(HttpSession session){
        ApiResponse response = orderService.confirmOrder(session);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @DeleteMapping("/delete-pizza")
    public HttpEntity<?> deletePizzaFromOrder(@RequestParam Long id){
        ApiResponse response = orderService.deletePizzaFromOrder(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @DeleteMapping("/delete-ingredient")
    public HttpEntity<?> deleteIngredientFromOrder(@RequestParam Long id){
        ApiResponse response = orderService.deleteIngredientFromOrder(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @PutMapping("/update-pizza-quantity")
    public HttpEntity<?> updatePizzaQuantitiy(@RequestParam Long id, @RequestParam Integer quantity){
        ApiResponse response = orderService.updatePizzaQuantitiy(id,quantity);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

    @PutMapping("/update-ingredient-quantity")
    public HttpEntity<?> updateIngredientQuantitiy(@RequestParam Long id, @RequestParam Integer quantity){
        ApiResponse response = orderService.updateIngredientQuantitiy(id,quantity);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }







}
