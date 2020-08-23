package com.mthree.bsm.repository;

import com.mthree.bsm.entity.Party;
import com.mthree.bsm.entity.Stock;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing {@link Stock}s.
 */
public interface StockDao {

    /**
     * Gets a stock in the system with a given ID. If there is no stock in the system with the given ID, the returned
     * {@link Optional} will not be present.
     */
    Optional<Stock> getStockById(int stockId);

    /**
     * Gets a list of all stocks in the system.
     */
    List<Stock> getStocks();

    /**
     * Deletes all stocks in the system, returning them in a list.
     */
    List<Stock> deleteStocks();

    /**
     * Adds a stock to the system, validating it, and assigning it a new ID (no matter what ID the passed stock has),
     * returning it back.
     *
     * @throws InvalidEntityException when the given stock is invalid.
     */
    Stock addStock(Stock stock) throws InvalidEntityException;

}
