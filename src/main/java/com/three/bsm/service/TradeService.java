/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.Trade;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author tombarton
 */
public interface TradeService {
    
    List<Trade> getTrades();
    
    Optional<Trade> getTradeById(int id);
    
}
