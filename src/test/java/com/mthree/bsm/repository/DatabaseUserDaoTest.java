/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author tombarton
 */
@SpringBootTest
public class DatabaseUserDaoTest {
    
    public DatabaseUserDaoTest() {
    }
    
    @Autowired
    UserDao userDao;
    
    private User tom = new User("TomB", false);
    private User billy = new User("BillyS", false);
    
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
    public void testAddGetUsers() throws Exception {
       
        User invalidUser1 = tom;
        
        invalidUser1.setUsername(null);
        assertThrowsIEE(invalidUser1);
        invalidUser1.setUsername("Invalid Username, too long");
        assertThrowsIEE(invalidUser1);
        
        tom.setUsername("TomB");

        tom = userDao.addUser(tom);
        billy = userDao.addUser(billy);
        
        User tomasz = new User("TomaszM", true);
        
        List<User> users = userDao.getUsers();
        
        assertEquals(users.size(), 2);
        assertTrue(users.contains(tom) && users.contains(billy));
       
        tomasz = userDao.addUser(tomasz);
        users = userDao.getUsers();
        
        assertEquals(users.size(), 3);
        assertTrue(users.contains(tom) && users.contains(billy) && users.contains(tomasz));
        
    }
    
    @Test
    public void testGetUserById() throws Exception {
        
        tom = userDao.addUser(tom);
        billy = userDao.addUser(billy);
        
        Optional<User> retrievedTom = userDao.getUserById(tom.getId());
        Optional<User> retrievedBilly = userDao.getUserById(billy.getId());
        
        assertEquals(tom, retrievedTom);
        assertEquals(billy, retrievedBilly);
        
        Optional<User> nullUser = userDao.getUserById(0);
        assertFalse(nullUser.isPresent());
    }
    
    
    @Test
    public void deleteAllUsers() throws Exception {
        
        tom = userDao.addUser(tom);
        billy = userDao.addUser(billy);
        
        userDao.deleteUsers();
        
        List<User> users = userDao.getUsers();
        
        assertEquals(users.size(), 0);
        
    }
    
    private void assertThrowsIEE(User user) {
        try{
            userDao.addUser(user);
            fail("Invalid user, should not be added");
        } catch(InvalidEntityException e) {
        }
        
    }
    
    
    
 
}
