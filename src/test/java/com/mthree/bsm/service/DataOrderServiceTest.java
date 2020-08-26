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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author tombarton
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DataOrderServiceTest {

    OrderDao orderDao = new OrderDaoStub();
    TradeDao tradeDao = new TradeDaoStub();
    UserDao userDao = new UserDaoStub();
    StockDao stockDao = new StockDaoStub();
    PartyDao partyDao = new PartyDaoStub();
    AuditDao auditDao = new AuditDaoStub();

    OrderService orderService = new DataOrderService(orderDao, tradeDao, stockDao, userDao, partyDao, auditDao);

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

    // clears memory
    @BeforeEach
    public void setUp() throws InvalidEntityException {
        partyDao.deleteParties();

        partyDao.addParty(lch);
        stockDao.addStock(tesla);
        userDao.addUser(tom);
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
        orderService.editOrder(order3.getId(), highPrice, 50, tom.getId());

        assertEquals(1, trades.size());

        Trade trade = trades.get(0);

        // needs building out probably
        assertEquals(highPrice, trade.getPrice());
        assertEquals(50, trade.getQuantity());
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

        order1 = orderDao.getOrderById(order1.getId()).orElseThrow();
        order2 = orderDao.getOrderById(order2.getId()).orElseThrow();

        assertEquals(FULFILLED, order1.getStatus());
        assertEquals(ACTIVE, order2.getStatus());
        assertEquals(0, order1.getSize());
        assertEquals(50, order2.getSize());

        Trade trade = trades.get(0);

        assertEquals(trade.getQuantity(), 100);

        // order 1 added first, lower history id = that price
        assertEquals(trade.getPrice(), highPrice);

    }

}
