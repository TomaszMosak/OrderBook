/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Order;
import static com.mthree.bsm.entity.OrderStatus.FULFILLED;
import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.entity.Trade;
import com.mthree.bsm.entity.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    stockDao stockDao;
    
    private Stock tesla = new Stock("TSLA", "NASDAQ");
    private User tom = new User("TomB", false);
    private User billy = new User("BillyS", false);
    private BigDecimal price = new BigDecimal(100.00);
    private LocalDateTime ldt = LocalDateTime.now();
    private Order buyOrder = new Order(tom, tesla, price, 100, 0, true, FULFILLED, ldt);
    private Order sellOrder = new Order(billy, tesla, price, 100, 0, false, FULFILLED, ldt);
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
    public void testCreateGetTrades() {
        
        Trade invalidTrade = new Trade();
        assertThrows(InvalidEntityException.class, tradeDao.addTrade(invalidTrade));
                
        invalidTrade = trade;
        invalidTrade.setBuyOrder(null);
        assertThrows(InvalidEntityException.class, tradeDao.addTrade(invalidTrade));
        
        invalidTrade.setBuyOrder(buyOrder);
        invalidTrade.setSellOrder(null);
        assertThrows(InvalidEntityException.class, tradeDao.addTrade(invalidTrade));
        
        invalidTrade.setSellOrder(sellOrder);
        invalidTrade.setExecutionTime(null);
        assertThrows(InvalidEntityException.class, tradeDao.addTrade(invalidTrade));
        
        // may need to adjust number of decimal places
        LocalDateTime futureLDT = LocalDateTime.of(2999, 03, 28, 14, 33, 48, 123456789);
        invalidTrade.setExecutionTime(futureLDT);
        assertThrows(InvalidEntityException.class, tradeDao.addTrade(invalidTrade));
        
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
    public void testGetTradeById() {
        
        assertThrows(MissingEntityException.class, tradeDao.getTradeById(0));
        assertThrows(MissingEntityException.class, tradeDao.getTradeById(null));
        
        Trade validTrade1 = tradeDao.addTrade(trade);
        Trade validTrade2 = tradeDao.addTrade(trade);
        
        Trade retrievedTrade1 = tradeDao.getTradeById(validTrade1.getId());
        Trade retrievedTrade2 = tradeDao.getTradeById(validTrade2.getId());
        
        assertEquals(retrievedTrade1, validTrade1);
        assertEquals(retrievedTrade2, validTrade2);
        assertNotEquals(retrievedTrade2, validTrade1);
    }
    
    @Test
    public void testDeleteTrades() throws Exception {
        
        Trade trade1 = tradeDao.addTrade(trade);
        Trade trade2 = tradeDao.addTrade(trade);
        
        tradeDao.deleteTrades();
        
        List<Trade> trades = tradeDao.getTrades();
        
        assertEquals(trades, 0);
        
    }



    
}
