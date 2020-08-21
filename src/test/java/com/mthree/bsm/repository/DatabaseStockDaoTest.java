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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        
        // stockDao not yet wired in
        Stock invalidStock1 = new Stock();
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock1));
        
        invalidStock1.setExchange("NASDAQ");
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock1));
        
        invalidStock1.setSymbol("Invalid");
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock1));
        
        Stock invalidStock2 = new Stock();
        invalidStock2.setSymbol("APPL");
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock2));
        
        invalidStock2.setExchange("Invalid");
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock2));
        
        Stock tesla = new Stock();
        tesla.setSymbol("TSLA");
        tesla.setExchange("NASDAQ");
        stockDao.addStock(tesla);
        
        Stock apple = new Stock();
        apple.setSymbol("APPl");
        apple.setExchange("NASDAQ");
        stockDao.addStock(apple);
        
        Stock hsbc = new Stock();
        tesla.setSymbol("HSBC");
        tesla.setExchange("LSE");

        List<Stock> stocks = stockDao.getStocks();
        
        assertEquals(stocks.size(), 2);
        assertTrue(stocks.contains(tesla) && stocks.contains(apple));
        assertFalse(stocks.contains(hsbc));
        
        stockDao.addStock(hsbc);
        
        assertEquals(stocks.size(), 3);
        assertTrue(stocks.contains(tesla) && stocks.contains(apple) && stocks.contains(hsbc));
    }
    
    @Test
    public void testDeleteStock() {
        
        Stock tesla = new Stock();
        tesla.setSymbol("TSLA");
        tesla.setExchange("NASDAQ");
        stockDao.addStock(tesla);
        
        Stock apple = new Stock();
        apple.setSymbol("APPl");
        apple.setExchange("NASDAQ");
        stockDao.addStock(apple);
        
        List<Stock> stocks = stockDao.getStocks();
        
        assertEquals(stocks.size(), 2);
        
        stockDao.deleteAllStocks();
        
        stocks = stockDao.getStocks();
        
        assertEquals(stocks.size(), 0);
        
    }

    
}
