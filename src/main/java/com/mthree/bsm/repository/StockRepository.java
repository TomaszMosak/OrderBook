package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Stock;

import java.util.List;

/**
 * Repository for accessing {@link Stock}s.
 */
public interface StockRepository {

    /**
     * Gets a list of all stocks in the system.
     */
    List<Stock> getStocks();
    
    /**
     * Deletes all stocks in the system.
     */
    void deleteStocks();
    
    /**
     * Adds a stock in the system.
     */
    Stock addStock() throws InvalidEntityException;

}
