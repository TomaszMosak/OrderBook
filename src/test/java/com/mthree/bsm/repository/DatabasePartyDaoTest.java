/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Party;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class DatabasePartyDaoTest {
    
    public DatabasePartyDaoTest() {
    }
    
    @Autowired
    PartyDao partyDao;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    private Party party = new Party("London Clearing House", "LCH");
    
    @BeforeEach
    public void setUp() {
        partyDao.deleteParties();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getPartyById method, of class PartyDao.
     */
    @Test
    public void testAddGetPartyById() throws Exception {
        
        Party invalidParty = new Party();
        assertThrowsIEE(invalidParty);
        
        invalidParty = party;
        
        invalidParty.setName(null);
        assertThrowsIEE(invalidParty);
        
        invalidParty.setName("London Clearing House");
        invalidParty.setSymbol(null);
        assertThrowsIEE(invalidParty);
        
        party.setSymbol("lch");
        
        // optional contains either the party or a null value, is present 
        // returns a boolean based on whether it contains an object or not
        Optional<Party> nullParty = partyDao.getPartyById(0);
        assertFalse(nullParty.isPresent());
        
        party = partyDao.addParty(party);
        
        Optional<Party> retrievedParty = partyDao.getPartyById(party.getId());
        
        assertEquals(party, retrievedParty);
        
    }

    /**
     * Test of getParties method, of class PartyDao.
     */
    @Test
    public void testGetParties() throws Exception {
        
        Party party1 = partyDao.addParty(party);
        Party party2 = partyDao.addParty(party);
        
        List<Party> parties = partyDao.getParties();
        
        assertEquals(parties.size(), 2);
        assertTrue(parties.contains(party1) && parties.contains(party2));
        
        Party party3 = partyDao.addParty(party);
        
        parties = partyDao.getParties();
        
        assertEquals(parties.size(), 3);
        assertTrue(parties.contains(party1) && parties.contains(party2) && parties.contains(party3));
        
    }

    /**
     * Test of deleteParties method, of class PartyDao.
     */
    @Test
    public void testDeleteParties() throws Exception {
        
        Party party1 = partyDao.addParty(party);
        Party party2 = partyDao.addParty(party);
        
        List<Party> deletedParties = partyDao.deleteParties();
        
        assertEquals(deletedParties.size(), 2);
        assertTrue(deletedParties.contains(party1) && deletedParties.contains(party2));
        
        List<Party> parties = partyDao.getParties();
        
        assertEquals(parties.size(), 0);
        
    }

    private void assertThrowsIEE(Party party) {
        try {
            partyDao.addParty(party);
            fail("Invalid Entity, should not pass");
        } catch(InvalidEntityException e) {
        }
    }




    
}
