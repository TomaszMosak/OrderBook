/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.bsm.service;

import com.mthree.bsm.entity.Order;
import com.mthree.bsm.entity.Trade;
import com.mthree.bsm.repository.InvalidEntityException;
import com.mthree.bsm.repository.TradeDao;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tombarton
 */
@Repository
public class TradeDaoStub implements TradeDao {

    private List<Trade> trades = new ArrayList<>();

    /**
     * Gets all {@link Trade}s in the system.
     */
    @Override
    public List<Trade> getTrades() {
        return trades;
    }

    /**
     * Gets a {@link Trade} by a given ID. If there is no {@link Trade} in the system with the given ID, the returned
     * {@link Optional} will not be present.
     *
     * @param id
     */
    @Override
    public Optional<Trade> getTradeById(int id) {
        return trades.stream()
                     .filter(trade -> trade.getId() == id)
                     .findAny();
    }

    /**
     * Creates a trade in the system, validates it, and assigns it a new ID (no matter what ID the passed trade has),
     * returning it back.
     *
     * @param trade
     *
     * @throws InvalidEntityException when the given trade is invalid.
     */
    @Override
    public Trade addTrade(Trade trade) throws InvalidEntityException {
        int newTradeId = trades.stream()
                               .map(Trade::getId)
                               .max(Comparator.naturalOrder())
                               .map(id -> id + 1)
                               .orElse(1);
        trade.setId(newTradeId);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Trade>> violations = validator.validate(trade);
        if (!violations.isEmpty()) {
            throw new InvalidEntityException(violations.stream()
                                                       .map(ConstraintViolation::getMessage)
                                                       .collect(Collectors.toList()));
        }

        trades.add(trade);

        return trade;
    }

    /**
     * Deletes all trades in the system, returning them in a list.
     */
    @Override
    public List<Trade> deleteTrades() {
        List<Trade> oldTrades = new ArrayList<>(trades);
        trades = new ArrayList<>();

        return oldTrades;
    }

}
