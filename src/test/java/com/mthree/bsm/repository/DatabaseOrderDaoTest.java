/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import static com.mthree.bsm.entity.OrderStatus.PENDING;
import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.entity.User;
import com.mthree.bsm.entity.Order;
import java.time.LocalDateTime;
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
public class DatabaseOrderDaoTest {
    
    public DatabaseOrderDaoTest() {
    }
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private StockDao stockDao;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        orderDao.deleteOrders();
        userDao.deleteUsers();
        stockDao.deleteStocks();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getOrders method, of class OrderRepository.
     */
    @Test
    public void testAddGetOrders() {
        
        Order invalidOrder1 = new Order();
        assertThrows(InvalidEntityException.class, orderDao.addOrder(invalidOrder1));
        
        Stock tesla = new Stock();
        tesla.setSymbol("TSLA");
        tesla.setExchange("NASDAQ");
        stockDao.addStock(tesla);
        invalidOrder1.setStock(tesla);
        
        User tom = new User();
        tom.setUsername("TomB");
        tom.setDeleted(false);
        tom = userDao.addUser(tom);
        invalidOrder1.setUser(tom);
        
        invalidOrder1.setLotSize(100);
        invalidOrder1.setRemainingSize(100);
        invalidOrder1.setIsBuy(true);
        invalidOrder1.setOrderStatus(PENDING);
        invalidOrder1.setPrice(100);
        LocalDateTime ldt = LocalDateTime.now();
        invalidOrder1.setCreationDate(ldt);
        
        
        invalidOrder.setUser(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setuser(tom);
        invalidOrder1.setStock(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invaliorder1.setStock(tesla);
        invalidOrder1.setLotSize(-1);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        invalidOrder1.setLotSize(100001);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setLotSize(100);
        invalidOrder.setPrice(-1.00);
        asassertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        invalidOrder1.setPrice(10000000.00);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder.setPrice(100);
        invalidOrder1.setRemainingSize(-1);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        invalidOrder1.setRemainingSize(100001);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setRemainingSize(100);
        invalidOrder1.setIsBuy(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setIsBuy(true);
        invalidOrder1.setOrderStatus(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setOrderStatus(PENDING);
        invalidOrder1.setCreationDate(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        // may need to adjust number of decimal places
        LocalDateTime futureLDT = LocalDateTime.of(2999, 03, 28, 14, 33, 48, 123456789);
        invalidOrder1.setCreationTime(futureLDT);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setCreationTime(ldt);
        
        Order validOrder1 = invalidOrder1;
        validOrder1 = orderDao.createOrder(validOrder1);
        
        Order validOrder2 = invalidOrder1;
        validOrder2.setIsBuy(false);
        validOrder2 = orderDao.createOrder(validOrder2);
        
        Order validOrder3 = invalidOrder1;
        validOrder3.setPrice(150);
        
        List<Order> orders = orderDao.getOrders();
        
        assertEquals(orders.size(), 2);
        assertTrue(orders.contains(validOrder1) && orders.contains(validOrder2));
        assertFalse(orders.contains(validOrder3));
        
        validOrder3 = orderDao.createOdrer(validOrder3);
        orders = orderDao.getOrders();
        
        assertEquals(orders.size(), 3);
        assertTrue(orders.contains(validOrder1) && orders.contains(validOrder2) && orders.contains(validOrder3));
    }

    /**
     * Test of getOrdersBySide method, of class OrderRepository.
     */
    @Test
    public void testGetOrdersBySide() {
        
        Order order = new Order();
        
        User tom = new User();
        tom.setUsername("TomB");
        tom.setDeleted(false);
        tom = userDao.addUser(tom);
        order.setUser(tom);
        
        Stock tesla = new Stock();
        tesla.setSymbol("TSLA");
        tesla.setExchange("NASDAQ");
        stockDao.addStock(tesla);
        order.setStock(tesla);
        
        order.setLotSize(100);
        order.setRemainingSize(100);
        order.setIsBuy(true);
        order.setOrderStatus(PENDING);
        LocalDateTime ldt = LocalDateTime.now();
        order.setCreationDate(ldt);
        
        Order buyOrder = orderDao.createOrder(order);
        
        order.setIsBuy(false);
        Order sellOrder = orderDao.createOrder(order);
 
        List<Order> orders = getOrdersBySide(true);
        
        assertEquals(orders.size(), 1);
        assertTrue(orders.contains(buyOrder));
                
        orders = getOrdersBySide(false);
        
        assertEquals(orders.size(), 1);
        assertTrue(orders.contains(sellOrder));
        
        order.setRemainingSize(100);
        
        sellOrder2 = orderDao.createOrder(order);
        
        orders = getOrdersBySide(false);
        
        assertEquals(orders.size(), 2);
        assertTrue(orders.contains(sellOrder) && orders.contains(sellOrder2));
        
        
        
    }

    /**
     * Test of getOrdersByStatus method, of class OrderRepository.
     */
    @Test
    public void testGetOrdersByStatus() {
    }

    /**
     * Test of getOrdersByUserId method, of class OrderRepository.
     */
    @Test
    public void testGetOrdersByUserId() throws Exception {
    }

    /**
     * Test of getOrderById method, of class OrderRepository.
     */
    @Test
    public void testGetOrderById() {
    }

    /**
     * Test of createOrder method, of class OrderRepository.
     */
    @Test
    public void testCreateOrder() throws Exception {
    }

    /**
     * Test of editOrder method, of class OrderRepository.
     */
    @Test
    public void testEditOrder() throws Exception {
    }

   
}
