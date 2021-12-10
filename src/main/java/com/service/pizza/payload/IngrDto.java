package com.service.pizza.payload;

import com.service.pizza.enums.Status;
import lombok.Data;

@Data
public class IngrDto {
    Long orderId;
    Status status;
    Long ingredientId;
    String IngredientName;
    Double IngredientPrice;
    Integer ingredientCount;
}
