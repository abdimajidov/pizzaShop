package com.service.pizza.service;

import com.service.pizza.entity.Buyer;
import com.service.pizza.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService{
    @Autowired
    BuyerRepository buyerRepository;

    @Override
    public void save(Buyer newBuyer) {
        buyerRepository.save(newBuyer);
    }
}
