/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Stock;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    
    private Stock tesla = new Stock("TSLA", "NASDAQ");
    private Stock apple = new Stock("APPL", "NASDAQ");
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        stockDao.deleteStocks();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getStocks method, of class StockRepository.
     */
    @Test
    public void testAddGetStocks() throws Exception {
        
        Stock invalidStock1 = tesla;
        invalidStock1.setSymbol(null);
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock1));
        invalidStock1.setSymbol("Invalid");
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock1));
        
        Stock invalidStock2 = apple;
                
        invalidStock2.setExchange(null);
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock2));
        invalidStock2.setExchange("Invalid");
        assertThrows(InvalidEntityException.class, stockDao.addStock(invalidStock2));
        
        apple = stockDao.addStock(apple);
        tesla = stockDao.addStock(tesla);
        
        Stock hsbc = new Stock("HSBC", "LSE");

        List<Stock> stocks = stockDao.getStocks();
        
        assertEquals(stocks.size(), 2);
        assertTrue(stocks.contains(tesla) && stocks.contains(apple));
        
        hsbc = stockDao.addStock(hsbc);
        stocks = stockDao.getStocks();
        
        assertEquals(stocks.size(), 3);
        assertTrue(stocks.contains(tesla) && stocks.contains(apple) && stocks.contains(hsbc));
    }
    
    @Test
    public void testGetStockById() throws Exception {
        
        apple = stockDao.addStock(apple);
        tesla = stockDao.addStock(tesla);
        
        Optional <Stock> retrievedApple = stockDao.getStockById(apple.getId());
        Optional <Stock> retrievedTesla = stockDao.getStockById(tesla.getId());
        
        assertEquals(apple, retrievedApple);
        assertEquals(tesla, retrievedTesla);
        
    }
    
    @Test
    public void testDeleteStocks() throws Exception {
        
        stockDao.addStock(tesla);
        stockDao.addStock(apple);
        
        List<Stock> stocks = stockDao.getStocks();
        
        assertEquals(stocks.size(), 2);
        
        stockDao.deleteStocks();
        stocks = stockDao.getStocks();
        
        assertEquals(stocks.size(), 0);
        
    }

    
}
