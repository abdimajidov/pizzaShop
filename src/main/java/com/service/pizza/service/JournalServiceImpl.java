package com.service.pizza.service;

import com.service.pizza.entity.Journal;
import com.service.pizza.entity.Order;
import com.service.pizza.enums.Status;
import com.service.pizza.payload.ApiResponse;
import com.service.pizza.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JournalServiceImpl implements JournalService{
    @Autowired
    JournalRepository journalRepository;
    @Override
    public ApiResponse migrateFromCartToJournal(Order order) {
        Date date = new Date();
        if (order != null) {
            Journal journal = new Journal();
            journal.setBuyerId(order.getBuyer().getId());
            journal.setName(order.getBuyer().getName());
            journal.setAddress(order.getBuyer().getAddress());
            journal.setPhone(order.getBuyer().getPhone());
            journal.setSessionId(order.getBuyer().getSessionId());
            journal.setStatus(Status.IN_PROGRESS);
            if(order.getIngredient()==null) {
                journal.setPizzaId(order.getPizza().getId());
                journal.setPizzaName(order.getPizza().getName());
                journal.setPizzaPrice(order.getPizza().getPrice());
                journal.setPizzaType(order.getPizza().getType());
                journal.setPizzaCount(order.getPizzaCount());
            }else {
                journal.setIngredientId(order.getIngredient().getId());
                journal.setIngredientName(order.getIngredient().getName());
                journal.setIngredientPrice(order.getIngredient().getPrice());
                journal.setIngredientCount(order.getIngredientCount());
            }
            journal.setDate_of_register(date);
            journal.setTotalCost(order.getTotalCost());
            journalRepository.save(journal);
            return new ApiResponse("Success", true,"Seccusfully confirmed");
        } else {
            return new ApiResponse("Error", false,"Some errors");
        }

    }

    @Override
    public ApiResponse updateIngredientQuantitiy(Long buyerId) {

        return journalRepository.findByBuyerId(buyerId).size()!=0
                ?
                new ApiResponse("Journal list:",true,journalRepository.findByBuyerId(buyerId))
                :
                new ApiResponse("Journal list is Empty",false,"Empty");
    }
}
