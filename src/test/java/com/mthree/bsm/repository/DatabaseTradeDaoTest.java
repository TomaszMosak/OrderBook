/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Order;
import static com.mthree.bsm.entity.OrderStatus.FULFILLED;
import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.entity.Trade;
import com.mthree.bsm.entity.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
public class DatabaseTradeDaoTest {
    
    public DatabaseTradeDaoTest() {
    }
    
    @Autowired
    TradeDao tradeDao;
    
    @Autowired
    OrderDao orderDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    StockDao stockDao;
    
    
    private Party lch = new Party("London Clearing House", "LCH");
    private BigDecimal tickSize = new BigDecimal("0.1");
    private Stock tesla = new Stock(lch, "Tesla", "TSLA", "NASDAQ", tickSize);
    private User tom = new User("TomB", false);
    private User billy = new User("BillyS", false);
    private BigDecimal price = new BigDecimal("100.00");
    private LocalDateTime ldt = LocalDateTime.now();
    private Order buyOrder = new Order(tom, lch, tesla, price, 100, true, FULFILLED, ldt);
    private Order sellOrder = new Order(tom, lch, tesla, price, 100, false, FULFILLED, ldt);
    private Trade trade = new Trade(buyOrder, sellOrder, ldt);
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        tradeDao.deleteTrades();
        orderDao.deleteOrders();
        userDao.deleteUsers();
        stockDao.deleteStocks();
        tesla = stockDao.addStock(tesla);
        tom = userDao.addUser(tom);
        billy = userDao.addUser(billy);
        buyOrder = orderDao.createOrder(buyOrder);
        sellOrder = orderDao.createOrder(sellOrder);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createTrades and getTrades method, of class TradeRepository.
     */
    @Test
    public void testCreateGetTrades() throws Exception {
        
        Trade invalidTrade = new Trade();
        assertThrowsIEE(invalidTrade);
                
        invalidTrade = trade;
        invalidTrade.setBuyOrder(null);
        assertThrowsIEE(invalidTrade);
        
        invalidTrade.setBuyOrder(buyOrder);
        invalidTrade.setSellOrder(null);
        assertThrowsIEE(invalidTrade);
        
        invalidTrade.setSellOrder(sellOrder);
        invalidTrade.setExecutionTime(null);
        assertThrowsIEE(invalidTrade);
        
        // may need to adjust number of decimal places
        LocalDateTime futureLDT = LocalDateTime.of(2999, 03, 28, 14, 33, 48, 123456789);
        invalidTrade.setExecutionTime(futureLDT);
        assertThrowsIEE(invalidTrade);
        
        Trade validTrade1 = tradeDao.addTrade(trade);
        Trade validTrade2 = tradeDao.addTrade(trade);
        
        List<Trade> trades = tradeDao.getTrades();
        
        assertEquals(trades.size(), 2);
        assertTrue(trades.contains(validTrade1) && trades.contains(validTrade2));
        
        Trade validTrade3 = tradeDao.addTrade(trade);
        
        trades = tradeDao.getTrades();
        
        assertEquals(trades.size(), 3);
        assertTrue(trades.contains(validTrade1) && trades.contains(validTrade2) && trades.contains(validTrade3));
                
                
    }

    /**
     * Test of getTradeById method, of class TradeRepository.
     */
    @Test
    public void testGetTradeById() throws Exception {
        
        Optional<Trade> nullTrade = tradeDao.getTradeById(0);
        assertFalse(nullTrade.isPresent());
        
        Trade validTrade1 = tradeDao.addTrade(trade);
        Trade validTrade2 = tradeDao.addTrade(trade);
        
        Optional<Trade> retrievedTrade1 = tradeDao.getTradeById(validTrade1.getId());
        Optional<Trade> retrievedTrade2 = tradeDao.getTradeById(validTrade2.getId());
        
        assertEquals(retrievedTrade1.get(), validTrade1);
        assertEquals(retrievedTrade2.get(), validTrade2);
        assertNotEquals(retrievedTrade2.get(), validTrade1);
    }
    
    @Test
    public void testDeleteTrades() throws Exception {
        
        tradeDao.addTrade(trade);
        tradeDao.addTrade(trade);
        
        tradeDao.deleteTrades();
        
        List<Trade> trades = tradeDao.getTrades();
        
        assertEquals(trades, 0);
        
    }
    
    private void assertThrowsIEE(Trade trade) {
        try{
            tradeDao.addTrade(trade);
            fail("Invalid trade, should not be added");
        } catch(InvalidEntityException e) {
        }
    } 
}
