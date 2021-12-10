package com.service.pizza.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuyerDto {
    Long id;
    String name;
    String phone;
    String address;
}
