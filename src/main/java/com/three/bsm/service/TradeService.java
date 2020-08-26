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
 * @author tombarton
 */
public interface TradeService {

    /**
     * Gets a list of all {@link Trade}s in the system.
     */
    List<Trade> getTrades();

    /**
     * Gets a {@link Trade} in the system by its ID.
     *
     * @param id the ID of the {@link Trade} to look for.
     * @return an optional with a value present if there is a {@link Trade} with the given ID, otherwise the value won't
     * be present.
     */
    Optional<Trade> getTradeById(int id);

}
