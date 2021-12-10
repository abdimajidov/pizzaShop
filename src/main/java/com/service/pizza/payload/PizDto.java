package com.service.pizza.payload;

import com.service.pizza.enums.Status;
import lombok.Data;

@Data
public class PizDto {
    Long orderId;
    Status status;
    Long pizzaId;
    String pizzaName;
    String pizzaType;
    Double pizzaPrice;
    Integer pizzaCount;
}
