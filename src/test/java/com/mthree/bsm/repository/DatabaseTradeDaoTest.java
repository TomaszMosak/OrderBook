/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Trade;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tombarton
 */
public class DatabaseTradeDaoTest {
    
    public DatabaseTradeDaoTest() {
    }
    
    @Autowired
    TradeDao tradeDao;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        tradeDao.deleteTrades();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getTrades method, of class TradeRepository.
     */
    @Test
    public void testGetTrades() {
        
        Trade trade1 = new Trade();
        
        
    }

    /**
     * Test of getTradeById method, of class TradeRepository.
     */
    @Test
    public void testGetTradeById() {
    }

    /**
     * Test of createTrade method, of class TradeRepository.
     */
    @Test
    public void testCreateTrade() throws Exception {
    }

    
}
