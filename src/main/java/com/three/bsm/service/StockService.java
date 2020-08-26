/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.Stock;
import java.util.List;

/**
 *
 * @author tombarton
 */
public interface StockService {

    /**
     * Gets a list of all {@link Stock}s in the system.
     */
    List<Stock> getStocks();
    
}
