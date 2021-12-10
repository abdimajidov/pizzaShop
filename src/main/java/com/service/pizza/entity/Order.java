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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer pizzaCount;
    Integer ingredientCount;
    Double totalCost;

    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", foreignKey = @ForeignKey(name = "buyer_id"))
    Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizza_id", foreignKey = @ForeignKey(name = "pizza_id"))
    Pizza pizza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", foreignKey = @ForeignKey(name = "ingredient_id"))
    Ingredient ingredient;

    @JsonFormat(pattern = "dd.MM.YYYY")
    @CreationTimestamp
    Date createdDate;
}