/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.three.bsm.service;

import com.mthree.bsm.entity.Party;
import java.util.List;

/**
 *
 * @author tombarton
 */
public interface PartyService {
    
    List<Party> getParties();
    
}
