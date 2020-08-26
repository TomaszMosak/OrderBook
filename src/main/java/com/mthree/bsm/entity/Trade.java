package com.mthree.bsm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * The trade entity corresponds to a trade made by the application matching a buy order and a sell order. It has an
 * {@link #id} to act as the primary key, {@link #buyOrder} and {@link #sellOrder} referencing the buy and sell order
 * respectively at the time the trade was made, and an {@link #executionTime} when the trade is made. There are
 * additional properties {@link #getQuantity()}, and {@link #getPrice()} representing how much is traded and at what
 * price, calculated from the relevant quantities in the {@link #buyOrder} and {@link #sellOrder}.
 * <p>
 * A trade is valid if none of its fields are null, and the {@link #executionTime} is in the past.
 */
public class Trade {
    
    public Trade(){
    }
    
    public Trade(Order buyOrder, Order sellOrder, LocalDateTime executionTime){
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
        this.executionTime = executionTime;
    }

    private int id;

    @NotNull(message = "The trade's buy order must not be null.")
    private Order buyOrder;

    @NotNull(message = "The trade's sell order must not be null.")
    private Order sellOrder;

    @NotNull(message = "The trade's execution time must not be null.")
    @PastOrPresent(message = "The trade's execution time must be in the past.")
    private LocalDateTime executionTime;

    @Override
    public String toString() {
        return "Trade{" +
               "id=" + id +
               ", buyOrder=" + buyOrder +
               ", sellOrder=" + sellOrder +
               ", executionTime=" + executionTime +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return id == trade.id &&
               Objects.equals(buyOrder, trade.buyOrder) &&
               Objects.equals(sellOrder, trade.sellOrder) &&
               executionTime.truncatedTo(ChronoUnit.SECONDS).equals(trade.executionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyOrder, sellOrder, executionTime);
    }

    public int getId() {
        return id;
    }

    public Trade setId(int id) {
        this.id = id;
        return this;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public Trade setBuyOrder(Order buyOrder) {
        this.buyOrder = buyOrder;
        return this;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public Trade setSellOrder(Order sellOrder) {
        this.sellOrder = sellOrder;
        return this;
    }

    public int getQuantity() {
        return Math.min(buyOrder.getSize(), sellOrder.getSize());
    }

    public BigDecimal getPrice() {
        if(buyOrder.getHistoryId()>sellOrder.getHistoryId()){
            return buyOrder.getPrice();
        } else {
            return sellOrder.getPrice();
        }
    }

    public LocalDateTime getExecutionTime() {
        return executionTime;
    }

    public Trade setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
        return this;
    }

}
