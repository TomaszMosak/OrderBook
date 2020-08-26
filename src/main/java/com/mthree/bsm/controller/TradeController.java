/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.controller;

import com.mthree.bsm.entity.Trade;

import java.util.List;
import java.util.Optional;

import com.three.bsm.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tombarton
 */
@RestController
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/trade")
    public List<Trade> displayTrades() {
        return tradeService.getTrades();
    }

    @GetMapping("/trade/{id}")
    public ResponseEntity<Trade> displayTradeById(int id) {
        return tradeService.getTradeById(id).map(ResponseEntity::ok)
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
