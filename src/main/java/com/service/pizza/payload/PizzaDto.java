package com.service.pizza.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class PizzaDto {
    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Type is required")
    String type;


    @NotNull(message = "Price is null")
    Double price;
}
