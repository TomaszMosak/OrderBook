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
import static com.mthree.bsm.entity.OrderStatus.CANCELLED;
import static com.mthree.bsm.entity.OrderStatus.FULFILLED;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class DatabaseOrderDaoTest {
    
    public DatabaseOrderDaoTest() {
    }
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private StockDao stockDao;
    
    private Stock tesla = new Stock("TSLA", "NASDAQ");
    private User tom = new User("TomB", false);
    private BigDecimal price = new BigDecimal(100.00);
    private LocalDateTime ldt = LocalDateTime.now();
    private Order order = new Order(tom, tesla, price, 100, 100, true, PENDING, ldt);

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
    public void testAddGetOrders() throws Exception {
        
        Order invalidOrder1 = new Order();
        assertThrows(InvalidEntityException.class, orderDao.addOrder(invalidOrder1));

        invalidOrder1 = order;
        
        invalidOrder1.setUser(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setUser(tom);
        invalidOrder1.setStock(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setStock(tesla);
        invalidOrder1.setLotSize(-1);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        invalidOrder1.setLotSize(100001);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setLotSize(100);
        BigDecimal negativePrice = new BigDecimal("-1.00");
        invalidOrder1.setPrice(negativePrice);
        asassertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        BigDecimal invalidPrice = new BigDecimal("10000000.00");
        invalidOrder1.setPrice(invalidPrice);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setPrice(price);
        invalidOrder1.setRemainingSize(-1);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        invalidOrder1.setRemainingSize(100001);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setRemainingSize(100);
        invalidOrder1.setStatus(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setStatus(PENDING);
        invalidOrder1.setCreationTime(null);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        // may need to adjust number of decimal places
        LocalDateTime futureLDT = LocalDateTime.of(2999, 03, 28, 14, 33, 48, 123456789);
        invalidOrder1.setCreationTime(futureLDT);
        assertThrows(InvalidEntityException.class, orderDao.createOrder(invalidOrder1));
        
        invalidOrder1.setCreationTime(ldt);
        
        
        Order validOrder1 = invalidOrder1;
        validOrder1 = orderDao.createOrder(validOrder1);
        
        Order validOrder2 = invalidOrder1;
        validOrder2.setBuy(false);
        validOrder2 = orderDao.createOrder(validOrder2);
        
        Order validOrder3 = invalidOrder1;
        validOrder3.setLotSize(150);
        
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
        
        Order buyOrder = orderDao.createOrder(order);
        
        order.setBuy(false);
        Order sellOrder = orderDao.createOrder(order);
 
        List<Order> orders = orderDao.getOrdersBySide(true);
        
        assertEquals(orders.size(), 1);
        assertTrue(orders.contains(buyOrder));
                
        orders = orderDao.getOrdersBySide(false);
        
        assertEquals(orders.size(), 1);
        assertTrue(orders.contains(sellOrder));
        
        order.setBuy(true);
        Order buyOrder2 = orderDao.createOrder(order);
        
        orders = orderDao.getOrdersBySide(true);
        
        assertEquals(orders.size(), 2);
        assertTrue(orders.contains(sellOrder) && orders.contains(buyOrder2));

    }

    /**
     * Test of getOrdersByStatus method, of class OrderRepository.
     */
    @Test
    public void testGetOrdersByStatus() {
        
        Order pendingOrder = orderDao.createOrder(order);
        
        order.setStatus(FULFILLED);
        Order fulfilledOrder = orderDao.createOrder(order);
        
        order.setStatus(CANCELLED);
        Order cancelledOrder = orderDao.createOrder();
        
        List<Order> pendingOrders = orderDao.getOrdersByStatus(PENDING);
        List<Order> fulfilledOrders = orderDao.getOrdersByStatus(FULFILLED);
        List<Order> cancelledOrders = orderDao.getOrdersByStatus(CANCELLED);
        
        assertEquals(pendingOrders.size(), 1);
        assertEquals(fulfilledOrders.size(), 1);
        assertEquals(cancelledOrders.size(), 1);
        assertTrue(pendingOrders.contains(pendingOrder));
        assertTrue(fulfilledOrders.contains(fulfilledOrder));
        assertTrue(cancelledOrders.contains(cancelledOrder));

        order.setStatus(PENDING);
        Order pendingOrder2 = orderDao.createOrder(order);
        
        pendingOrders = orderDao.getOrdersByStatus(PENDING);
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
        
        assertThrows(MissingEntityException.class, orderDao.getOrdersByUserId(0));
        assertThrows(MissingEntityException.class, orderDao.getOrdersByUserId(null));
        
        tom = userDao.addUser(tom);
        
        order.setUser(tom);
        
        Order order1 = orderDao.createOrder(order);
        Order order2 = orderDao.createOrder(order);
        
        User billy = new User("BillyS", false);
        billy = userDao.addUser(billy);
                
        Order order3 = order;
        order3.setUser(billy);
        order3 = orderDao.createOrder(order3);
        
        List<Order> orderTom = orderDao.getOrdersByUserId(tom.getId());
        
        assertEquals(orderTom.size(), 2);
        assertTrue(orderTom.contains(order1) && orderTom.contains(order1));
        
        List<Order> orderBilly = orderDao.getOrdersByUserId(billy.getId());
        
        assertEquals(orderTom.size(), 1);
        assertTrue(orderBilly.contains(order3));
        
    }

    /**
     * Test of getOrderById method, of class OrderRepository.
     */
    @Test
    public void testGetOrderById() {
        
        assertThrows(MissingEntityException.class, orderDao.getOrdersById(0));
        assertThrows(MissingEntityException.class, orderDao.getOrdersById(null));
        
        Order order1 = orderDao.createOrder(order);
        Order order2 = orderDao.createOrder(order);
        
        Order retrievedOrder1 = orderDao.getOrderById(order1.getId());
        assertEquals(order1, retrievedOrder1);
        
        Order retrievedOrder2 = orderDao.getOrderById(order2.getId());
        assertEquals(order2, retrievedOrder2);

    }

    /**
     * Test of editOrder method, of class OrderRepository.
     */
    @Test
    public void testEditOrder() throws Exception {
        
        order = orderDao.createOrder(order);
        
        Order invalidOrder1 = order; 
        
        // discuss which fields need editiing testing 
        
//        invalidOrder1.setUser(null);
//        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
//        
//        invalidOrder1.setUser(tom);
//        invalidOrder1.setStock(null);
//        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
//        
//        invalidOrder1.setStock(tesla);
//        invalidOrder1.setLotSize(-1);
//        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
//        invalidOrder1.setLotSize(100001);
//        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
//        
//        invalidOrder1.setLotSize(100);
//        BigDecimal negativePrice = new BigDecimal("-1.00");
//        invalidOrder1.setPrice(negativePrice);
//        asassertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
        
        BigDecimal invalidPrice = new BigDecimal("10000000.00");
        invalidOrder1.setPrice(invalidPrice);
        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
        
        invalidOrder1.setPrice(price);
        invalidOrder1.setRemainingSize(-1);
        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
        invalidOrder1.setRemainingSize(100001);
        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
        
        invalidOrder1.setRemainingSize(100);
        invalidOrder1.setStatus(null);
        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
        
        invalidOrder1.setStatus(PENDING);
        invalidOrder1.setCreationTime(null);
        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));
        // may need to adjust number of decimal places
//        LocalDateTime futureLDT = LocalDateTime.of(2999, 03, 28, 14, 33, 48, 123456789);
//        invalidOrder1.setCreationTime(futureLDT);
//        assertThrows(InvalidEntityException.class, orderDao.editOrder(invalidOrder1));

        Order originalOrder1 = order;
        
       BigDecimal editedPrice = new BigDecimal("123.12");
       order.setPrice(editedPrice);
       Order editedOrder1 = orderDao.EditOrder(order);
       
       Order retrievedOrder1 = orderDao.getOrderById(order.getId());
       
       assertEquals(editedOrder1, retrievedOrder1);
       assertNotEquals(editedOrder1, originalOrder1);
       
       Order originalOrder2 = order;
       
       order.setRemainingSize(237);
       Order editedOrder2 = orderDao.editOrder(order);
       
       Order retrievedOrder2 = orderDao.getOrderById(order.getId());
       
       assertEquals(editedOrder2, retrievedOrder2);
       assertNotEquals(editedOrder2, originalOrder2);
       
    }

   
}
