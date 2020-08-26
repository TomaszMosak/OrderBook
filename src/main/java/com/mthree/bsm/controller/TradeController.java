/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.controller;

import com.mthree.bsm.entity.Trade;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.MissingEntityException;
import com.mthree.bsm.service.OrderService;

import java.util.List;

import com.mthree.bsm.service.TradeService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author tombarton
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TradeController {

    private final TradeService tradeService;
    private final OrderService orderService;

    @Autowired
    public TradeController(TradeService tradeService, OrderService orderService) {
        this.tradeService = tradeService;
        this.orderService = orderService;
    }

    @GetMapping("/trade")
    public List<Trade> displayTrades() throws IOException, MissingEntityException, InvalidEntityException {
        orderService.matchOrders();
        return tradeService.getTrades();
    }

    @GetMapping("/trade/{id}")
    public ResponseEntity<Trade> displayTradeById(int id) {
        return tradeService.getTradeById(id).map(ResponseEntity::ok)
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<String> handleInvalidEntityException(InvalidEntityException e) {
        return e.getValidationErrors();
    }

}
