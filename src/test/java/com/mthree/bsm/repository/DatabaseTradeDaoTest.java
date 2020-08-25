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
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author tombarton
 */
@SpringBootTest
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

    @Autowired
    PartyDao partyDao;
    
    private Party lch = new Party("London Clearing House", "LCH");
    private BigDecimal tickSize = new BigDecimal("0.1");
    private Stock tesla = new Stock(lch, "Tesla", "TSLA", "NASDAQ", tickSize);
    private User tom = new User("TomB", false);
    private User billy = new User("BillyS", false);
    private BigDecimal price = new BigDecimal("100.00");
    private LocalDateTime ldt = LocalDateTime.now();
    private Order buyOrder;
    private Order sellOrder;
    private Trade trade;

    @BeforeEach
    public void setUp() throws Exception {
        tradeDao.deleteTrades();
        orderDao.deleteOrders();
        userDao.deleteUsers();
        stockDao.deleteStocks();
        partyDao.deleteParties();

        partyDao.addParty(lch);
        tesla = stockDao.addStock(tesla);
        tom = userDao.addUser(tom);
        billy = userDao.addUser(billy);

        buyOrder = new Order();
        buyOrder.setUser(tom);
        buyOrder.setParty(lch);
        buyOrder.setStock(tesla);
        buyOrder.setVersionTime(ldt);
        buyOrder.setStatus(FULFILLED);
        buyOrder.setBuy(true);
        buyOrder.setPrice(price);
        buyOrder.setSize(100);
        orderDao.createOrder(buyOrder);

        sellOrder = new Order();
        sellOrder.setUser(tom);
        sellOrder.setParty(lch);
        sellOrder.setStock(tesla);
        sellOrder.setVersionTime(ldt);
        sellOrder.setStatus(FULFILLED);
        sellOrder.setBuy(false);
        sellOrder.setPrice(price);
        sellOrder.setSize(100);
        orderDao.createOrder(sellOrder);

        trade = new Trade();
        trade.setBuyOrder(buyOrder);
        trade.setSellOrder(sellOrder);
        trade.setExecutionTime(ldt);
        tradeDao.addTrade(trade);
    }

    /**
     * Test of createTrades and getTrades method, of class TradeRepository.
     */
    @Test
    public void testCreateGetTrades() throws Exception {
        trade.setExecutionTime(ldt);
        
        Trade validTrade1 = tradeDao.addTrade(trade);
        Trade validTrade2 = tradeDao.addTrade(trade);
        
        List<Trade> trades = tradeDao.getTrades();
        
        assertEquals(3, trades.size());
        assertTrue(trades.contains(trade) && trades.contains(validTrade1) && trades.contains(validTrade2));
        
        Trade validTrade3 = tradeDao.addTrade(trade);
        
        trades = tradeDao.getTrades();
        
        assertEquals(4, trades.size());
        assertTrue(trades.contains(validTrade1) && trades.contains(validTrade2) && trades.contains(validTrade3));

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
    }

    /**
     * Test of getTradeById method, of class TradeRepository.
     */
    @Test
    public void testGetTradeById() throws Exception {
        
        Optional<Trade> nullTrade = tradeDao.getTradeById(0);
        assertFalse(nullTrade.isPresent());
        
        Optional<Trade> retrievedTrade = tradeDao.getTradeById(trade.getId());

        assertEquals(trade, retrievedTrade.get());
    }
    
    @Test
    public void testDeleteTrades() throws Exception {
        
        tradeDao.addTrade(trade);
        tradeDao.addTrade(trade);
        
        tradeDao.deleteTrades();
        
        List<Trade> trades = tradeDao.getTrades();
        
        assertEquals(0, trades.size());
        
    }
    
    private void assertThrowsIEE(Trade trade) {
        try{
            tradeDao.addTrade(trade);
            fail("Invalid trade, should not be added");
        } catch(InvalidEntityException e) {
        }
    } 
}
