package com.service.pizza.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class IngredientDto {
    @NotBlank(message = "Name is required")
    String name;

    @NotNull(message = "Price is required")
    Double price;
}
