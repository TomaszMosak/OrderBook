/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Stock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tombarton
 */
public class DatabaseStockDaoTest {
    
    public DatabaseStockDaoTest() {
    }
    
    @Autowired
    StockDao stockDao;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        stockDao.deleteAllStocks();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getStocks method, of class StockRepository.
     */
    @Test
    public void testAddGetStocks() {
        
        Stock tesla = new Stock();
        tesla.setSymbol("TSLA");
        tesla.setExchange("NASDAQ");
        stockDao.addStock(tesla);
        
        Stock apple = new Stock();
        apple.setSymbol("APPl");
        apple.setExchange("NASDAQ");
        stockDao.addStock(apple);
        
        Stock tesla = new Stock();
        tesla.setSymbol("HSBC");
        tesla.setExchange("LSE");

        
        
        
    }

    
}
