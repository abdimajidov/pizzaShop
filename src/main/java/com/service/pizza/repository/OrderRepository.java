package com.service.pizza.repository;

import com.service.pizza.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByBuyerSessionId(String buyer_sessionId);

    @Query(value = "select SUM(total_cost) from orders od inner join buyer buy on od.buyer_id = buy.id where buy.session_id = :session_id", nativeQuery = true)
    Double getTotalPriceOfCart(@Param("session_id") String session_id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from orders od using buyer buy where od.buyer_id = buy.id and buy.session_id = :sessionId", nativeQuery = true)
    void deleteOrderByBuyerSessionId(@Param("sessionId") String sessionId);

    Optional<Order> findByPizzaId(Long pizza_id);
    Optional<Order> findByIngredientId(Long ingredientId);

    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteByPizzaId(Long pizzaId);

    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteByIngredientId(Long ingredientId);




}
