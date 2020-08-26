/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.repository.StockDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tombarton
 */
@Service
public class DataStockService implements StockService {

    @Autowired
    StockDao stockDao;
    
    @Override
    public List<Stock> getStocks() {
       return stockDao.getStocks();
       
       
    }
    
}
