package com.service.pizza.controller;

import com.service.pizza.payload.ApiResponse;
import com.service.pizza.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/journal")
public class JournalController {
    @Autowired
    JournalService journalService;

    @GetMapping("/journal/{buyerId}")
    public HttpEntity<?> getJournalByBuyerId(@PathVariable Long buyerId){
        ApiResponse response = journalService.updateIngredientQuantitiy(buyerId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response.getObject());
    }

}
