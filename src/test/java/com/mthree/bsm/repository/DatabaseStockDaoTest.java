/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.Stock;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
    
    @Autowired
    PartyDao partyDao;
    
    //Party centralParty, String companyName, String symbol, String exchange, BigDecimal tickSize
    private Party lch = new Party("London Clearing House", "LCH");
    private BigDecimal tickSize = new BigDecimal("0.1");
    private Stock tesla = new Stock(lch, "Tesla", "TSLA", "NASDAQ", tickSize);
    private Stock apple = new Stock(lch, "Apple", "APPL", "NASDAQ", tickSize);
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        stockDao.deleteStocks();
        lch = partyDao.addParty(lch);
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
        
        invalidStock1.setCentralParty(null);
        assertThrowsIEE(invalidStock1);
        
        invalidStock1.setCentralParty(lch);
        invalidStock1.setCompanyName(null);
        assertThrowsIEE(invalidStock1);
        
        invalidStock1.setCompanyName("Invalid name, larger than 30 characters");
        assertThrowsIEE(invalidStock1);
        invalidStock1.setCompanyName("Tesla");
        
        invalidStock1.setSymbol(null);
        assertThrowsIEE(invalidStock1);
        invalidStock1.setSymbol("Invalid");
        assertThrowsIEE(invalidStock1);

        invalidStock1.setExchange(null);
        assertThrowsIEE(invalidStock1);
        invalidStock1.setExchange("Invalid");
        assertThrowsIEE(invalidStock1);
        
        invalidStock1.setTickSize(null);
        assertThrowsIEE(invalidStock1);
        BigDecimal negTickSize = new BigDecimal("-1");
        invalidStock1.setTickSize(negTickSize);
        BigDecimal zeroTickSize = new BigDecimal("0");
        invalidStock1.setTickSize(zeroTickSize);
        BigDecimal largeTickSize = new BigDecimal("1000");
        invalidStock1.setTickSize(largeTickSize);
        
        
        apple = stockDao.addStock(apple);
        tesla = stockDao.addStock(tesla);
        
        Stock hsbc = new Stock(lch, "HSBC Bank", "HSBC", "LSE", tickSize);

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
        
        Optional <Stock> nullStock = stockDao.getStockById(0);
        
        assertFalse(nullStock.isPresent());
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
    
    private void assertThrowsIEE(Stock stock) {
        try{
            stockDao.addStock(stock);
            fail("Invalid user, should not be added");
        } catch(InvalidEntityException e) {
        }
    }

    
}
