/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.controller;

import com.mthree.bsm.entity.Trade;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tombarton
 */
@RestController
public class TradeController {
    
    @GetMapping("/trade")
    public List<Trade> displayTrades() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @GetMapping("/trade/{id}")
    public Trade displayTradeById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
