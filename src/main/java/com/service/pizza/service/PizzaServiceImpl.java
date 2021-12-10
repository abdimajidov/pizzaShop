package com.service.pizza.service;

import com.service.pizza.entity.Pizza;
import com.service.pizza.payload.ApiResponse;
import com.service.pizza.payload.PizzaDto;
import com.service.pizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService{
    @Autowired
    PizzaRepository pizzaRepository;

    @Override
    public ApiResponse addPizza(PizzaDto pizzaDto, BindingResult result) {
        try {
            if (!result.hasErrors()) {
                pizzaRepository.save(new Pizza(pizzaDto.getName(), pizzaDto.getType(), pizzaDto.getPrice()));
                return new ApiResponse("Succesfully added",true,"Pizza added");
            }else {
                return new ApiResponse("Error",false,"Some Errors");
            }
        }catch (Exception e){
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse getPizzas() {
        try {
            List<Pizza> pizzaList = pizzaRepository.findAll();
            return pizzaList.size()!=0?new ApiResponse("Pizza list:",true,pizzaList):
                    new ApiResponse("Pizza list is empty",false,"Pizza list is empty");
        }catch (Exception e){
            return new ApiResponse("Some Errors",false,"Some errors");
        }
    }

    @Override
    public ApiResponse getPizza(Long id) {
        try {
            Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
            if (optionalPizza.isEmpty()) {
                return new ApiResponse("Pizza not found",false,"Pizza not found");
            }
            return new ApiResponse("Pizza:",true,optionalPizza.get());
        }catch (Exception e){
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse editPizza(Long id, PizzaDto pizzaDto, BindingResult result) {
        try {
            if (!result.hasErrors()) {
            Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
            if (optionalPizza.isEmpty()) {
                return new ApiResponse("Pizza not found",false,"Pizza not found");
            }
            Pizza pizza = optionalPizza.get();
            pizza.setName(pizzaDto.getName());
            pizza.setPrice(pizzaDto.getPrice());
            pizza.setType(pizzaDto.getType());
            pizzaRepository.save(pizza);

            return new ApiResponse("Pizza edited",true,"Pizza edited");

        }else {
                return new ApiResponse("Error",false,"Some Errors");
            }
        }catch (Exception e){
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse deletePizza(Long id) {
        try {
            Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
            if (optionalPizza.isEmpty()) {
                return new ApiResponse("Pizza not found",false,"Pizza not found");
            }
            pizzaRepository.deleteById(id);

            return new ApiResponse("Pizza deleted",true,"Pizza deleted");
        }catch (Exception e){
            return new ApiResponse("Error", false, "Some Error");
        }
    }
}
