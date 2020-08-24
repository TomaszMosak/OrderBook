package com.mthree.bsm.entity;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * The stock entity corresponds to a stock being traded at the exchange. It has an {@link #id} to act as the primary
 * key, a {@link #centralParty} representing the party trading the stock (should be LCH), a {@link #symbol}, the
 * {@link #exchange} the stock is traded at, and a {@link #tickSize}.
 */
public class Stock {

    private int id;

    @NotNull(message = "The stock's central party must not be null.")
    private Party centralParty;

    @NotNull(message = "The stock's company name must not be null.")
    @Size(max = 30, message = "The stock's company name must have length at most 30.")
    private String companyName;

    @NotNull(message = "The stock's symbol must not be null.")
    @Size(max = 5, message = "The stock's symbol must have length at most 5.")
    private String symbol;

    @NotNull(message = "The stock's exchange must not be null.")
    @Size(max = 6, message = "The stock's exchange must have length at most 6.")
    private String exchange;

    @NotNull(message = "The stock's tick size must not be null.")
    @DecimalMin(value = "0.001", message = "The stock's tick size must be at least 0.001.")
    @DecimalMax(value = "100.000", message = "The stock's tick size must be at most 100.000")
    @Digits(integer = 3, fraction = 3, message = "The stock's tick size must have at most 3 digits after the decimal.")
    private BigDecimal tickSize;

    @Override
    public String toString() {
        return "Stock{" +
               "id=" + id +
               ", centralParty=" + centralParty +
               ", companyName='" + companyName + '\'' +
               ", symbol='" + symbol + '\'' +
               ", exchange='" + exchange + '\'' +
               ", tickSize=" + tickSize +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return id == stock.id &&
               Objects.equals(centralParty, stock.centralParty) &&
               Objects.equals(companyName, stock.companyName) &&
               Objects.equals(symbol, stock.symbol) &&
               Objects.equals(exchange, stock.exchange) &&
               Objects.equals(tickSize, stock.tickSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, centralParty, companyName, symbol, exchange, tickSize);
    }

    public int getId() {
        return id;
    }

    public Stock setId(int id) {
        this.id = id;
        return this;
    }

    public Party getCentralParty() {
        return centralParty;
    }

    public Stock setCentralParty(Party centralParty) {
        this.centralParty = centralParty;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Stock setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public Stock setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getExchange() {
        return exchange;
    }

    public Stock setExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public BigDecimal getTickSize() {
        return tickSize;
    }

    public Stock setTickSize(BigDecimal tickSize) {
        this.tickSize = tickSize;
        return this;
    }

}
