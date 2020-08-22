/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.User;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tombarton
 */
public class DatabaseUserDaoTest {
    
    public DatabaseUserDaoTest() {
    }
    
    @Autowired
    UserDao userDao;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        userDao.deleteUsers();
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testAddGetUsers() {
        
        // UserDao not yet wired in
        User invalidUser1 = new User();
        assertThrows(InvalidEntityException.class, userDao.addUser(invalidUser1));
        
        invalidUser1.setUsername(null);
        assertThrows(InvalidEntityException.class, userDao.addUser(invalidUser1));
        
        invalidUser1.setUsername("Invalid Username, too long");
        assertThrows(InvalidEntityException.class, userDao.addUser(invalidUser1));
        
        User tom = new User();
        tom.setUsername("TomB");
        tom.setDeleted(false);
        tom = userDao.addUser(tom);
        
        User billy = new User();
        billy.setUsername("BillyS");
        billy.setDeleted(false);
        billy = userDao.addUser(billy);
        
        User tomasz = new User();
        tomasz.setUsername("TomaszM");
        tomasz.setDeleted(false);
        
        List<User> users = userDao.getUsers();
        
        assertEquals(users.size(), 2);
        assertTrue(users.contains(tom) && users.contains(billy));
        assertFalse(users.contains(tomasz));
       
        tomasz = userDao.addUser(tomasz);
        users = userDao.getUsers();
        
        assertEquals(users.size(), 3);
        assertTrue(users.contains(tom) && users.contains(billy) && users.contains(tomasz));
        
    }
    
    @Test
    public void deleteAllUsers(){
        
        User tom = new User();
        tom.setUsername("TomB");
        tom.setDeleted(false);
        tom = userDao.addUser(tom);
        
        User billy = new User();
        billy.setUsername("BillyS");
        billy.setDeleted(false);
        billy = userDao.addUser(billy);
        
        List<User> users = userDao.getUsers();
        
        assertEquals(users.size(), 2);
        
        userDao.deleteUsers();
        
        users = userDao.getAllUsers();
        
        assertEquals(users.size(), 2);
        
    }
    
    
    
 
}
