/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Stock;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tombarton
 */
public class DatabaseStockDaoTest {
    
    public DatabaseStockDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // deleteAllStocks();
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
        
        Stock apple = new Stock();
        apple.setSymbol("APPl");
        apple.setExchange("NASDAQ");
        
        
        
    }

    
}
