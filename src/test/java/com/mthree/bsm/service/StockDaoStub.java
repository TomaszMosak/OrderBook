package com.mthree.bsm.service;

import com.mthree.bsm.entity.Stock;
import com.mthree.bsm.entity.User;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.StockDao;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StockDaoStub implements StockDao {

    private List<Stock> stocks = new ArrayList<>();

    /**
     * Gets a stock in the system with a given ID. If there is no stock in the system with the given ID, the returned
     * {@link Optional} will not be present.
     *
     * @param stockId
     */
    @Override
    public Optional<Stock> getStockById(int stockId) {
        return stocks.stream()
                     .filter(user -> user.getId() == stockId)
                     .findAny();
    }

    /**
     * Gets a list of all stocks in the system.
     */
    @Override
    public List<Stock> getStocks() {
        return stocks;
    }

    /**
     * Deletes all stocks in the system, returning them in a list.
     */
    @Override
    public List<Stock> deleteStocks() {
        List<Stock> oldStocks = new ArrayList<>(stocks);
        stocks = new ArrayList<>();

        return oldStocks;
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
        int newStockId = stocks.stream()
                               .map(Stock::getId)
                               .max(Comparator.naturalOrder())
                               .map(id -> id + 1)
                               .orElse(1);
        stock.setId(newStockId);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);
        if (!violations.isEmpty()) {
            throw new InvalidEntityException(violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList()));
        }

        stocks.add(stock);

        return stock;
    }

}
