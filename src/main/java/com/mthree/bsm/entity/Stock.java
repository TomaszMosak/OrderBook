package com.mthree.bsm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The stock entity corresponds to a stock being traded at the exchange. It has an {@link #id}to act as the primary key,
 * a {@link #symbol}, and the {@link #exchange} the stock is traded at.
 * <p>
 * A {@link Stock} is valid if none of its fields are null, the symbol has length at most 5, and the exchange has length
 * at most 6.
 */
public class Stock {

    private int id;

    @NotNull(message = "The stock's symbol must not be null.")
    @Size(max = 5, message = "The stock's symbol must have length at most 5.")
    private String symbol;

    @NotNull(message = "The stock's exchange must not be null.")
    @Size(max = 6, message = "The stock's exchange must have length at most 6.")
    private String exchange;

    @Override
    public String toString() {
        return "Stock{" +
               "id=" + id +
               ", symbol='" + symbol + '\'' +
               ", exchange='" + exchange + '\'' +
               '}';
    }
    
   public Stock(){
   }
   
   public Stock(String symbol, String exchange){
       this.symbol = symbol;
       this.exchange = exchange;
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return id == stock.id &&
               Objects.equals(symbol, stock.symbol) &&
               Objects.equals(exchange, stock.exchange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol, exchange);
    }

    public int getId() {
        return id;
    }

    public Stock setId(int id) {
        this.id = id;
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

}
