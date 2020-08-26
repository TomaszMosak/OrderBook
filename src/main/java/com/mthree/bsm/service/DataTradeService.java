/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.entity.Trade;
import com.mthree.bsm.repository.TradeDao;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author tombarton
 */
@Service
public class DataTradeService implements TradeService {
    
    TradeDao tradeDao;

    @Autowired
    public DataTradeService(@Qualifier("databaseTradeDao") TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }

    @Override
    public List<Trade> getTrades() {
        return tradeDao.getTrades();
    }

    @Override
    public Optional<Trade> getTradeById(int id) {
        return tradeDao.getTradeById(id);
    } 
}
