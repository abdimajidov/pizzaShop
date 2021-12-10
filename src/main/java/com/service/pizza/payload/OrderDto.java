package com.service.pizza.payload;

import com.service.pizza.enums.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

}
