/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.entity.User;
import com.mthree.bsm.entity.Order;
import static com.mthree.bsm.entity.OrderStatus.ACTIVE;
import static com.mthree.bsm.entity.OrderStatus.CANCELLED;
import static com.mthree.bsm.entity.OrderStatus.FULFILLED;
import com.mthree.bsm.entity.Party;
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
public class DatabaseOrderDaoTest {
    
    public DatabaseOrderDaoTest() {
    }
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private StockDao stockDao;
    
    // User user, Party party, Stock stock, BigDecimal price, int size, 
    // boolean isbuy, OrderStatus status, LocalDateTime versionTime
    
    
    private BigDecimal tickSize = new BigDecimal("0.1");
    private Party lch = new Party("London Clearing House", "LCH");
    private Stock tesla = new Stock(lch, "Tesla", "TSLA", "NASDAQ", tickSize);
    private User tom = new User("TomB", false);
    private BigDecimal price = new BigDecimal("100.00");
    private LocalDateTime ldt = LocalDateTime.now();
    private Order order = new Order(tom, lch, tesla, price, 100, true, FULFILLED, ldt);

    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        orderDao.deleteOrders();
        userDao.deleteUsers();
        stockDao.deleteStocks();
        tesla = stockDao.addStock(tesla);
        tom = userDao.addUser(tom);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getOrders method, of class OrderRepository.
     */
    @Test
    public void testAddGetOrders() throws Exception {
        
        
        
        Order invalidOrder1 = new Order();
        assertThrowsAddIEE(invalidOrder1);

        invalidOrder1 = order;
        
        invalidOrder1.setUser(null);
        assertThrowsAddIEE(invalidOrder1);
        
        invalidOrder1.setUser(tom);
        invalidOrder1.setStock(null);
        assertThrowsAddIEE(invalidOrder1);
        
        invalidOrder1.setStock(tesla);
        invalidOrder1.setParty(null);
        assertThrowsAddIEE(invalidOrder1);
        
        invalidOrder1.setParty(lch);
        invalidOrder1.setSize(-1);
        assertThrowsAddIEE(invalidOrder1);
        invalidOrder1.setSize(100001);
        assertThrowsAddIEE(invalidOrder1);
        
        invalidOrder1.setSize(100);
        BigDecimal negativePrice = new BigDecimal("-1.00");
        invalidOrder1.setPrice(negativePrice);
        assertThrowsAddIEE(invalidOrder1);
        
        BigDecimal invalidPrice = new BigDecimal("10000000.00");
        invalidOrder1.setPrice(invalidPrice);
        assertThrowsAddIEE(invalidOrder1);
        
        invalidOrder1.setPrice(price);
        invalidOrder1.setStatus(null);
        assertThrowsAddIEE(invalidOrder1);
        
        invalidOrder1.setStatus(FULFILLED);
        invalidOrder1.setVersionTime(null);
        assertThrowsAddIEE(invalidOrder1);
        // may need to adjust number of decimal places
        LocalDateTime futureLDT = LocalDateTime.of(2999, 03, 28, 14, 33, 48, 123456789);
        invalidOrder1.setVersionTime(futureLDT);
        assertThrowsAddIEE(invalidOrder1);
        
        invalidOrder1.setVersionTime(ldt);
        
        Order validOrder1 = invalidOrder1;
        Order validOrder2 = new Order(tom, lch, tesla, price, 100, true, FULFILLED, ldt);
        Order validOrder3 = new Order(tom, lch, tesla, price, 100, true, FULFILLED, ldt);
        
        validOrder1 = orderDao.createOrder(validOrder1);
        
 
        validOrder2.setBuy(false);
        validOrder2 = orderDao.createOrder(validOrder2);
        

        validOrder3.setSize(150);
        
        List<Order> orders = orderDao.getOrders();
        
        assertEquals(orders.size(), 2);
        assertTrue(orders.contains(validOrder1) && orders.contains(validOrder2));
        assertFalse(orders.contains(validOrder3));
        
        validOrder3 = orderDao.createOrder(validOrder3);
        orders = orderDao.getOrders();
        
        assertEquals(orders.size(), 3);
        assertTrue(orders.contains(validOrder1) && orders.contains(validOrder2) && orders.contains(validOrder3));
    }

    /**
     * Test of getOrdersBySide method, of class OrderRepository.
     */
    @Test
    public void testGetOrdersBySide() throws Exception{
        
        Order buyOrder = orderDao.createOrder(order);
        
        Order sellOrder = new Order(tom, lch, tesla, price, 100, false, FULFILLED, ldt);
        sellOrder = orderDao.createOrder(sellOrder);
 
        List<Order> orders = orderDao.getOrdersBySide(true);
        
        assertEquals(orders.size(), 1);
        assertTrue(orders.contains(buyOrder));
                
        orders = orderDao.getOrdersBySide(false);
        
        assertEquals(orders.size(), 1);
        assertTrue(orders.contains(sellOrder));
        
        Order buyOrder2 = new Order(tom, lch, tesla, price, 100, true, FULFILLED, ldt);
        buyOrder2 = orderDao.createOrder(buyOrder2);
        
        orders = orderDao.getOrdersBySide(true);
        
        assertEquals(orders.size(), 2);
        assertTrue(orders.contains(sellOrder) && orders.contains(buyOrder2));

    }

    /**
     * Test of getOrdersByStatus method, of class OrderRepository.
     */
    // needs fixing
    @Test
    public void testGetOrdersByStatus() throws Exception {
        
        Order pendingOrder = orderDao.createOrder(order);
        
        order.setStatus(FULFILLED);
        Order fulfilledOrder = orderDao.createOrder(order);
        
        order.setStatus(CANCELLED);
        Order cancelledOrder = orderDao.createOrder(order);
        
        List<Order> pendingOrders = orderDao.getOrdersByStatus(ACTIVE);
        List<Order> fulfilledOrders = orderDao.getOrdersByStatus(FULFILLED);
        List<Order> cancelledOrders = orderDao.getOrdersByStatus(CANCELLED);
        
        assertEquals(pendingOrders.size(), 1);
        assertEquals(fulfilledOrders.size(), 1);
        assertEquals(cancelledOrders.size(), 1);
        assertTrue(pendingOrders.contains(pendingOrder));
        assertTrue(fulfilledOrders.contains(fulfilledOrder));
        assertTrue(cancelledOrders.contains(cancelledOrder));

        order.setStatus(ACTIVE);
        Order pendingOrder2 = orderDao.createOrder(order);
        
        pendingOrders = orderDao.getOrdersByStatus(ACTIVE);
        assertEquals(pendingOrders.size(), 2);
        assertTrue(pendingOrders.contains(pendingOrder) && pendingOrders.contains(pendingOrder2));
        
        order.setStatus(FULFILLED);
        Order fulfilledOrder2 = orderDao.createOrder(order);
        
        fulfilledOrders = orderDao.getOrdersByStatus(FULFILLED);
        assertEquals(fulfilledOrders.size(), 2);
        assertTrue(fulfilledOrders.contains(fulfilledOrder) && fulfilledOrders.contains(fulfilledOrder2));
        
        order.setStatus(CANCELLED);
        Order cancelledOrder2 = orderDao.createOrder(order);
        
        cancelledOrders = orderDao.getOrdersByStatus(CANCELLED);
        assertEquals(cancelledOrders.size(), 2);
        assertTrue(cancelledOrders.contains(cancelledOrder) && cancelledOrders.contains(cancelledOrder2));
    }

    /**
     * Test of getOrdersByUserId method, of class OrderRepository.
     */
    @Test
    public void testGetOrdersByUserId() throws Exception {
        
        assertThrowsGetUserMEE(0);
        
        Order order1 = orderDao.createOrder(order);
        Order order2 = orderDao.createOrder(order);
        
        User billy = new User("BillyS", false);
        billy = userDao.addUser(billy);
                
        Order order3 = order;
        order3.setUser(billy);
        order3 = orderDao.createOrder(order3);
        
        List<Order> orderTom = orderDao.getOrdersByUserId(tom.getId());
        
        assertEquals(orderTom.size(), 2);
        assertTrue(orderTom.contains(order1) && orderTom.contains(order2));
        
        List<Order> orderBilly = orderDao.getOrdersByUserId(billy.getId());
        
        assertEquals(orderBilly.size(), 1);
        assertTrue(orderBilly.contains(order3));
        
    }
    
    @Test
    public void testGetOrderByPartyId() throws Exception {
        
        assertThrowsGetPartyMEE(0);
        
        Order order1 = orderDao.createOrder(order);
        Order order2 = orderDao.createOrder(order);
        
        Party nycha = new Party("New York CHA", "NYCHA");
        
        Order order3 = order;
        order3.setParty(nycha);
        order3 = orderDao.createOrder(order3);
        
        List<Order> lchOrders = orderDao.getOrdersByPartyId(lch.getId());
        List<Order> nychaOrders = orderDao.getOrdersByPartyId(nycha.getId());
        
        assertEquals(lchOrders.size(), 2);
        assertTrue(lchOrders.contains(order1) && lchOrders.contains(order2));
        assertEquals(nychaOrders.size(), 1);
        assertTrue(nychaOrders.contains(order3));
    }

    /**
     * Test of getOrderById method, of class OrderRepository.
     */
    @Test
    public void testGetOrderById() throws Exception {
        
        Optional<Order> nullOrder = orderDao.getOrderById(0);
        assertFalse(nullOrder.isPresent());
        
        Order order1 = orderDao.createOrder(order);
        Order order2 = orderDao.createOrder(order);
        
        Optional<Order> retrievedOrder1 = orderDao.getOrderById(order1.getId());
        assertEquals(order1, retrievedOrder1.get());
        
        Optional<Order> retrievedOrder2 = orderDao.getOrderById(order2.getId());
        assertEquals(order2, retrievedOrder2.get());

    }

    /**
     * Test of editOrder method, of class OrderRepository.
     */
    @Test
    public void testEditOrder() throws Exception {
        
        order = orderDao.createOrder(order);
        
        Order invalidOrder1 = order; 
        
        // discuss which fields need editiing testing 
        
        BigDecimal invalidPrice = new BigDecimal("10000000.00");
        invalidOrder1.setPrice(invalidPrice);
        assertThrowsEditIEE(invalidOrder1);
        
        invalidOrder1.setPrice(price);
        invalidOrder1.setSize(-1);
        assertThrowsEditIEE(invalidOrder1);
        invalidOrder1.setSize(100001);
        assertThrowsEditIEE(invalidOrder1);
        
        invalidOrder1.setSize(100);
        invalidOrder1.setStatus(null);
        assertThrowsEditIEE(invalidOrder1);
        
        invalidOrder1.setStatus(FULFILLED);
        invalidOrder1.setVersionTime(null);
        assertThrowsEditIEE(invalidOrder1);
        
        invalidOrder1.setId(0);
        assertThrowsEditMEE(invalidOrder1);

        Order originalOrder1 = order;
        
        BigDecimal editedPrice = new BigDecimal("123.12");
        order.setPrice(editedPrice);
        orderDao.editOrder(order);
       
        Optional<Order> retrievedOrder1 = orderDao.getOrderById(order.getId());
       
        assertEquals(order, retrievedOrder1.get());
        assertNotEquals(retrievedOrder1.get(), originalOrder1);
       
        Order originalOrder2 = order;
       
        order.setSize(237);
        orderDao.editOrder(order);
       
        Optional<Order> retrievedOrder2 = orderDao.getOrderById(order.getId());
       
        assertEquals(order, retrievedOrder2.get());
        assertNotEquals(retrievedOrder2.get(), originalOrder2);
       
    }
    
    @Test
    public void testDeleteOrders() throws Exception {
        
        orderDao.createOrder(order);
        orderDao.createOrder(order);
        
        orderDao.deleteOrders();
        
        List<Order> orders = orderDao.getOrders();
        
        assertEquals(orders.size(), 0);
        
    }
    
    private void assertThrowsAddIEE(Order order) {
        try{
            orderDao.createOrder(order);
            fail("Invalid order, should not be added");
        } catch(InvalidEntityException e) {
        }
    }
    
    public void assertThrowsGetUserMEE(int id) {
        try{
            orderDao.getOrdersByUserId(id);
            fail("Invalid id, should not return anything");
        } catch(MissingEntityException e) {
        }
    }
    
    public void assertThrowsGetPartyMEE(int id) {
        try{
            orderDao.getOrdersByPartyId(id);
            fail("Invalid id, should not return anything");
        } catch(MissingEntityException e) {
        }
    }

    private void assertThrowsEditIEE(Order order) {
        try{
            orderDao.editOrder(order);
            fail("Invalid order, should not be edited");
        } catch(InvalidEntityException e) {
        } catch(MissingEntityException ex) {
            fail("Order should exist");
        }
    }
    
    private void assertThrowsEditMEE(Order order) {
        try{
            orderDao.editOrder(order);
            fail("Invalid order, should not be edited");
        } catch(InvalidEntityException e) {
            fail("Order shouldn't exist");
        } catch(MissingEntityException ex) {
        }
    }
   
}
