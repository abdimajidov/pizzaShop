package com.service.pizza.service;

import com.service.pizza.entity.Buyer;
import com.service.pizza.entity.Ingredient;
import com.service.pizza.entity.Order;
import com.service.pizza.entity.Pizza;
import com.service.pizza.enums.Status;
import com.service.pizza.payload.*;
import com.service.pizza.repository.BuyerRepository;
import com.service.pizza.repository.IngredientRepository;
import com.service.pizza.repository.OrderRepository;
import com.service.pizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    BuyerService buyerService;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    JournalService journalService;


    @Override
    public ApiResponse addPizzaToOrder(HttpSession session, Long id) {
        try {
            Buyer buyer = buyerRepository.findBuyerBySessionId(session.getId());
            if (buyer == null) {
                Buyer newBuyer = new Buyer(session.getId());
                Buyer savedBuyer = buyerRepository.save(newBuyer);
                Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
                if (optionalPizza.isEmpty()) return new ApiResponse("Pizza not found", false, "Pizza not found");
                creatNewOrder(savedBuyer, optionalPizza.get());
            } else {
                List<Order> orderList = orderRepository.findAllByBuyerSessionId(session.getId());
                int x = 0;
                for (Order order : orderList) {
                    if (order.getPizza() != null && order.getPizza().getId().equals(id)) {
                        order.setPizzaCount(order.getPizzaCount() + 1);
                        order.setTotalCost(order.getTotalCost() + order.getPizza().getPrice());
                        orderRepository.save(order);
                        x++;
                    }

                }
                if (x == 0) {
                    Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
                    if (optionalPizza.isEmpty()) return new ApiResponse("Pizza not found", false, "Pizza not found");
                    creatNewOrder(buyer, optionalPizza.get());
                }
            }
            return new ApiResponse("Pizza add to Order", true, "Pizza add to order");
        } catch (Exception e) {
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse addMorePizzaToOrder(HttpSession session, Long id, Integer quantity) {
        try {
            Buyer buyer = buyerRepository.findBuyerBySessionId(session.getId());
            if (buyer == null) {
                Buyer newBuyer = new Buyer(session.getId());
                Buyer savedBuyer = buyerRepository.save(newBuyer);
                Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
                if (optionalPizza.isEmpty()) return new ApiResponse("Pizza not found", false, "Pizza not found");
                Order order = creatNewOrder(savedBuyer, optionalPizza.get());
                if (quantity > 1) {
                    order.setPizzaCount(quantity);
                    order.setTotalCost(order.getPizza().getPrice() * quantity);
                } else {
                    Integer i = order.getPizzaCount();
                    order.setPizzaCount(order.getPizzaCount());
                    order.setTotalCost(order.getTotalCost() * i);
                }
                orderRepository.save(order);
                return new ApiResponse("Success", true, "Success add Buyer");
            } else {
                List<Order> list = orderRepository.findAllByBuyerSessionId(session.getId());
                int x = 0;
                for (Order orders : list) {
                    // Если есть продукт в корзине заказчика то только прибаляем количество продукта +1
                    if (orders.getPizza() != null && orders.getPizza().getId().equals(id)) {
                        if (quantity > 1) {
                            int change_quantity = orders.getPizzaCount() + quantity;
                            orders.setPizzaCount(change_quantity);
                        } else {
                            int i = orders.getPizzaCount() + 1;
                            orders.setPizzaCount(i);
                        }
                        orderRepository.save(orders);
                        x += 1;
                    }
                }
                // Если нет продукта в корзине то создаем новый Orders по сессии
                if (x == 0) {
                    Pizza pizza = pizzaRepository.getById(id);
                    creatNewOrderWithQuantity(pizza, buyer, quantity);
                }

            }

            return new ApiResponse("Pizza add to Order", true, "Pizza add to order");
        } catch (Exception e) {
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse addIngredientToOrder(HttpSession session, Long id) {
        try {
            Buyer buyer = buyerRepository.findBuyerBySessionId(session.getId());
            if (buyer == null) {
                Buyer newBuyer = new Buyer(session.getId());
                Buyer savedBuyer = buyerRepository.save(newBuyer);
                Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
                if (optionalIngredient.isEmpty())
                    return new ApiResponse("Ingredient not found", false, "Ingredient not found");
                creatNewOrder(savedBuyer, optionalIngredient.get());

            } else {
                List<Order> orderList = orderRepository.findAllByBuyerSessionId(session.getId());
                int x = 0;
                for (Order order : orderList) {
                    if (order.getIngredient() != null && order.getIngredient().getId().equals(id)) {
                        order.setIngredientCount((order.getIngredientCount() != null ? order.getIngredientCount() : 0) + 1);
                        order.setTotalCost(order.getTotalCost() + order.getIngredient().getPrice());
                        orderRepository.save(order);
                        x++;
                    }

                }
                if (x == 0) {
                    Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
                    if (optionalIngredient.isEmpty())
                        return new ApiResponse("Ingredient not found", false, "Ingredient not found");
                    creatNewOrder(buyer, optionalIngredient.get());
                }
            }
            return new ApiResponse("Ingredient add to Order", true, "Ingredient add to order");
        } catch (Exception e) {
            return new ApiResponse("Error", false, "Some Error");
        }
    }

    @Override
    public ApiResponse viewOrder(HttpSession session) {
        try {
            List<Order> order = orderRepository.findAllByBuyerSessionId(session.getId());
            if (!order.isEmpty()) {
                Map<String, List<Object>> result = new HashMap<>();


                BuyerDto buyerDto = new BuyerDto();
                Double priceOfCart = orderRepository.getTotalPriceOfCart(session.getId());

                List<IngrDto> ingrDtoList=new ArrayList<>();
                List<PizDto> pizDtoList=new ArrayList<>();
                for (Order orderEach : order) {
                    buyerDto.setId(orderEach.getBuyer().getId());
                    buyerDto.setAddress(orderEach.getBuyer().getAddress());
                    buyerDto.setName(orderEach.getBuyer().getName());
                    buyerDto.setPhone(orderEach.getBuyer().getPhone());



                    if (orderEach.getIngredient() != null) {
                        IngrDto ingrDto=new IngrDto();
                        ingrDto.setOrderId(orderEach.getId());
                        ingrDto.setStatus(orderEach.getStatus());
                        ingrDto.setIngredientId(orderEach.getIngredient().getId());
                        ingrDto.setIngredientCount(orderEach.getIngredientCount());
                        ingrDto.setIngredientName(orderEach.getIngredient().getName());
                        ingrDto.setIngredientPrice(orderEach.getIngredient().getPrice());
                        ingrDtoList.add(ingrDto);

                    }else {
                        PizDto pizDto=new PizDto();
                        pizDto.setOrderId(orderEach.getId());
                        pizDto.setStatus(orderEach.getStatus());
                        pizDto.setPizzaType(orderEach.getPizza().getType());
                        pizDto.setPizzaId(orderEach.getPizza().getId());
                        pizDto.setPizzaCount(orderEach.getPizzaCount());
                        pizDto.setPizzaName(orderEach.getPizza().getName());
                        pizDto.setPizzaPrice(orderEach.getPizza().getPrice());
                        pizDtoList.add(pizDto);
                    }

                    }

                    result.put("buyer", Collections.singletonList(buyerDto));
                    result.put("Pizza", Collections.singletonList(pizDtoList));
                    result.put("Ingredient", Collections.singletonList(ingrDtoList));
                    result.put("total_price_order", Collections.singletonList(priceOfCart));
                    return new ApiResponse("Success", true, result);
                } else{
                    return new ApiResponse("Cart is empty", false, "Cart is empty");
                }
            } catch(Exception e){
                return new ApiResponse("Error", false, "Some error");
            }
        }


        @Override
        public ApiResponse confirmOrder (HttpSession session){
            try {
                Optional<Buyer> optional = Optional.ofNullable(buyerRepository.findBuyerBySessionId(session.getId()));
                if (optional.isPresent()) {
                    List<Order> orders = orderRepository.findAllByBuyerSessionId(session.getId());
                    for (Order order : orders) {
                        ApiResponse response = journalService.migrateFromCartToJournal(order);
                        if (!response.isSuccess()) {
                            return new ApiResponse("Error", false, "Status is not found");
                        }
                    }
                    orderRepository.deleteOrderByBuyerSessionId(session.getId());
                    buyerRepository.deleteById(orders.get(0).getBuyer().getId());
                    return new ApiResponse("Success", true, "Order is confirmed");
                } else {
                    return new ApiResponse("Cart is empty", true, "У заказчика нет выбранных продуктов");
                }
            } catch (Exception e) {
                return new ApiResponse("Error", false, "Some error");
            }
        }

        @Override
        public ApiResponse deletePizzaFromOrder (Long id){
            try {
                Optional<Order> optional = orderRepository.findByPizzaId(id);
                if (optional.isPresent()) {
                    orderRepository.deleteByPizzaId(id);
                    return new ApiResponse("Success", true, "Pizza delete from cart");
                } else {
                    return new ApiResponse("Error", false, "Element not found");
                }

            } catch (Exception e) {
                return new ApiResponse("Error", false, "Some error");
            }
        }

        @Override
        public ApiResponse deleteIngredientFromOrder (Long id){
            try {
                Optional<Order> optional = orderRepository.findByIngredientId(id);
                if (optional.isPresent()) {
                    orderRepository.deleteByIngredientId(id);
                    return new ApiResponse("Success", true, "Ingredient delete from cart");
                } else {
                    return new ApiResponse("Error", false, "Element not found");
                }

            } catch (Exception e) {
                return new ApiResponse("Error", false, "Some error");
            }
        }

        @Override
        public ApiResponse updateIngredientQuantitiy (Long id, Integer quantity){
            try {
                Optional<Order> optional = orderRepository.findByIngredientId(id);
                if (optional.isPresent()) {
                    Order order = optional.get();
                    order.setIngredientCount(quantity);
                    order.setTotalCost(quantity * order.getIngredient().getPrice());
                    orderRepository.save(order);
                    return new ApiResponse("Success", true, "Success update quantity");
                } else {
                    return new ApiResponse("Error", false, "Order not found or quantity is null");
                }
            } catch (Exception e) {
                return new ApiResponse("Error", false, "Some error");
            }
        }

        @Override
        public ApiResponse updatePizzaQuantitiy (Long id, Integer quantity){
            try {
                Optional<Order> optional = orderRepository.findByPizzaId(id);
                if (optional.isPresent()) {
                    Order order = optional.get();
                    order.setPizzaCount(quantity);
                    order.setTotalCost(quantity * order.getPizza().getPrice());
                    orderRepository.save(order);
                    return new ApiResponse("Success", true, "Success update quantity");
                } else {
                    return new ApiResponse("Error", false, "Order not found or quantity is null");
                }
            } catch (Exception e) {
                return new ApiResponse("Error", false, "Some error");
            }
        }

        @Override
        public Order creatNewOrder (Buyer newBuyer, Pizza pizza){
            Order order = new Order();
            order.setPizzaCount(1);
            order.setStatus(Status.NOT_SERVED);
            order.setBuyer(newBuyer);
            order.setPizza(pizza);
            order.setTotalCost(pizza.getPrice());
            return orderRepository.save(order);
        }

        @Override
        public Order creatNewOrder (Buyer newBuyer, Ingredient ingredient){
            Order order = new Order();
            order.setIngredientCount(1);
            order.setStatus(Status.NOT_SERVED);
            order.setBuyer(newBuyer);
            order.setIngredient(ingredient);
            order.setTotalCost(ingredient.getPrice());
            return orderRepository.save(order);
        }

        @Override
        public void creatNewOrderWithQuantity (Pizza pizza, Buyer buyer, Integer quantity){
            Order order = new Order();
            order.setPizzaCount(1);
            order.setStatus(Status.NOT_SERVED);
            order.setBuyer(buyer);
            order.setPizza(pizza);
            order.setTotalCost(pizza.getPrice() * quantity);
            orderRepository.save(order);
        }

        @Override
        public void creatNewOrderWithQuantity (Ingredient ingredient, Buyer buyer, Integer quantity){
            Order order = new Order();
            order.setIngredientCount(quantity);
            order.setStatus(Status.NOT_SERVED);
            order.setBuyer(buyer);
            order.setIngredient(ingredient);
            order.setTotalCost(ingredient.getPrice() * quantity);
            orderRepository.save(order);
        }


    }
