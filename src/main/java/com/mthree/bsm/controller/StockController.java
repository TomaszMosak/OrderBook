/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.controller;

import com.mthree.bsm.entity.Stock;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tombarton
 */
@RestController
public class StockController {
    
    @GetMapping("/stock")
    public List<Stock> displayStocks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}