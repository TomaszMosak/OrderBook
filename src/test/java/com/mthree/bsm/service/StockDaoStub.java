package com.mthree.bsm.service;

import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.StockDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StockDaoStub implements StockDao {

    /**
     * Gets a stock in the system with a given ID. If there is no stock in the system with the given ID, the returned
     * {@link Optional} will not be present.
     *
     * @param stockId
     */
    @Override
    public Optional<Stock> getStockById(int stockId) {
        return Optional.empty();
    }

    /**
     * Gets a list of all stocks in the system.
     */
    @Override
    public List<Stock> getStocks() {
        return null;
    }

    /**
     * Deletes all stocks in the system, returning them in a list.
     */
    @Override
    public List<Stock> deleteStocks() {
        return null;
    }

    /**
     * Adds a stock to the system, validating it, and assigning it a new ID (no matter what ID the passed stock has),
     * returning it back.
     *
     * @param stock
     *
     * @throws InvalidEntityException when the given stock is invalid.
     */
    @Override
    public Stock addStock(Stock stock) throws InvalidEntityException {
        return null;
    }

}
