package com.service.pizza.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.pizza.enums.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long buyerId;
    String name;
    String phone;
    String address;
    String sessionId;

    Long pizzaId;
    String pizzaName;
    String pizzaType;
    Double pizzaPrice;

    Long ingredientId;
    String ingredientName;
    Double ingredientPrice;

    String count;
    Double totalCost;

    Date date_of_served;
    Date date_of_register;
    Integer pizzaCount;
    Integer ingredientCount;

    @Enumerated(EnumType.STRING)
    Status status;


    @JsonFormat(pattern = "dd.MM.YYYY")
    @CreationTimestamp
    Date createdDate;
}
