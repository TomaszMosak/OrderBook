/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.entity.Order;

import static com.mthree.bsm.entity.OrderStatus.ACTIVE;
import static com.mthree.bsm.entity.OrderStatus.CANCELLED;
import static com.mthree.bsm.entity.OrderStatus.FULFILLED;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.entity.Trade;
import com.mthree.bsm.entity.User;
import com.mthree.bsm.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author tombarton
 */
@SpringBootTest
public class DataOrderServiceTest {

    private OrderDao orderDao;
    private TradeDao tradeDao;
    private UserDao userDao;
    private StockDao stockDao;
    private PartyDao partyDao;
    private AuditDao auditDao;
    OrderService orderService;

    @Autowired
    public DataOrderServiceTest(@Qualifier("orderDaoStub") OrderDao orderDao,
                                @Qualifier("tradeDaoStub") TradeDao tradeDao,
                                @Qualifier("userDaoStub") UserDao userDao,
                                @Qualifier("stockDaoStub") StockDao stockDao,
                                @Qualifier("partyDaoStub") PartyDao partyDao,
                                @Qualifier("auditDaoStub") AuditDao auditDao) {
        this.orderDao = orderDao;
        this.tradeDao = tradeDao;
        this.userDao = userDao;
        this.stockDao = stockDao;
        this.partyDao = partyDao;
        this.auditDao = auditDao;
        this.orderService = new DataOrderService(orderDao, tradeDao, stockDao, userDao, partyDao, auditDao);
    }

    public DataOrderServiceTest() {
    }

    private BigDecimal tickSize = new BigDecimal("0.1");
    private Party lch = new Party("London Clearing House", "LCH");
    private Stock tesla = new Stock(lch, "Tesla", "TSLA", "NASDAQ", tickSize);
    private User tom = new User("TomB", false);
    private BigDecimal highPrice = new BigDecimal("100.00");
    private BigDecimal lowPrice = new BigDecimal("75.00");
    private LocalDateTime ldt = LocalDateTime.now();
    private Order order1 = new Order(tom, lch, tesla, highPrice, 100, true, ACTIVE, ldt);
    private Order order2 = new Order(tom, lch, tesla, lowPrice, 150, false, ACTIVE, ldt);
    private Order order3 = new Order(tom, lch, tesla, lowPrice, 50, true, ACTIVE, ldt);
    private Order order4 = new Order(tom, lch, tesla, highPrice, 125, false, ACTIVE, ldt);

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    // clears memory
    @BeforeEach
    public void setUp() {
        orderDao.deleteOrders();
        tradeDao.deleteTrades();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createOrder method, of class DataOrderService.
     */
    @Test
    public void testCreateOrder() throws Exception {

        order1 = orderDao.createOrder(order1);
        order2 = orderService.createOrder(tesla.getId(), lch.getId(), tom.getId(), false, lowPrice, 150);

        assertEquals(order2.getSize(), 50);

        List<Trade> trades = tradeDao.getTrades();

        assertEquals(trades.size(), 1);

        Trade trade = trades.get(0);

        assertEquals(trade.getQuantity(), 100);
        assertEquals(trade.getPrice(), highPrice);

    }

    /**
     * Test of cancelOrder method, of class DataOrderService.
     */
    @Test
    public void testCancelOrder() throws Exception {

        order1 = orderDao.createOrder(order1);

        // might need to find another way of doing this
        orderService.cancelOrder(order1.getId(), tom.getId());

        assertEquals(order1.getStatus(), CANCELLED);
    }

    /**
     * Test of editOrder method, of class DataOrderService.
     */
    @Test
    public void testEditOrder() throws Exception {

        order3 = orderDao.createOrder(order3);
        order4 = orderDao.createOrder(order4);

        orderService.matchOrders();

        List<Trade> trades = tradeDao.getTrades();

        assertEquals(trades.size(), 0);

        order3.setPrice(highPrice);

        // going to attempt to write to and from database, which is an issue?
        orderService.editOrder(order3.getId(), lowPrice, 50, tom.getId());

        assertEquals(trades.size(), 1);

        Trade trade = trades.get(0);

        // needs building out probably
        assertEquals(trade.getPrice(), lowPrice);
        assertEquals(trade.getQuantity(), 50);

    }

    /**
     * Test of matchOrders method, of class DataOrderService.
     */
    @Test
    public void testMatchOrders() throws Exception {

        orderDao.createOrder(order1);
        orderDao.createOrder(order2);

        orderService.matchOrders();

        List<Trade> trades = tradeDao.getTrades();

        assertEquals(trades.size(), 1);

        assertEquals(order1.getStatus(), FULFILLED);
        assertEquals(order2.getStatus(), ACTIVE);
        assertEquals(order1.getSize(), 0);
        assertEquals(order2.getSize(), 50);

        Trade trade = trades.get(0);

        assertEquals(trade.getQuantity(), 100);

        // order 1 added first, lower history id = that price
        assertEquals(trade.getPrice(), highPrice);

    }

}
