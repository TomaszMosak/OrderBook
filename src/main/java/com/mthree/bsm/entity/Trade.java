package com.mthree.bsm.entity;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The trade entity corresponds to a trade made by the application matching a buy order and a sell order. It has an
 * {@link #id} to act as the primary key, {@link #buyOrder} and {@link #sellOrder} referencing the buy and sell order
 * respectively at the time the trade was made, a {@link #quantity} of stock being traded at price {@link #price} and an
 * {@link #executionTime} when the trade is made.
 * <p>
 * A trade is valid if none of its fields are null, the {@link #price} is nonnegative and has at most 7 digits with 1
 * digit after the decimal point, the {@link #quantity} is less than the maximum of the {@link Order#getRemainingSize()}
 * for the {@link #buyOrder} and the {@link #sellOrder}, and the {@link #executionTime} is in the past.
 */
@MaxQuantity(message = "The trade's quantity must be at most the the maximum of the remaining size of its buy and " +
                       "sell orders.")
public class Trade {

    private int id;

    @NotNull(message = "The trade's buy order must not be null.")
    private Order buyOrder;

    @NotNull(message = "The trade's sell order must not be null.")
    private Order sellOrder;

    private int quantity;

    @NotNull(message = "The trade's price must not be null.")
    @Digits(integer = 6,
            fraction = 1,
            message = "The trades's price must have at most 7 digits with 1 digit after the decimal point")
    @DecimalMin(value = "0.0", message = "The trade's price must be nonnegative.")
    private BigDecimal price;

    @NotNull(message = "The trade's execution time must not be null.")
    @PastOrPresent(message = "The trade's execution time must be in the past.")
    private LocalDateTime executionTime;

    @Override
    public String toString() {
        return "Trade{" +
               "id=" + id +
               ", buyOrder=" + buyOrder +
               ", sellOrder=" + sellOrder +
               ", quantity=" + quantity +
               ", price=" + price +
               ", executionTime=" + executionTime +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return id == trade.id &&
               quantity == trade.quantity &&
               Objects.equals(buyOrder, trade.buyOrder) &&
               Objects.equals(sellOrder, trade.sellOrder) &&
               Objects.equals(price, trade.price) &&
               Objects.equals(executionTime, trade.executionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyOrder, sellOrder, quantity, price, executionTime);
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
        return quantity;
    }

    public Trade setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Trade setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getExecutionTime() {
        return executionTime;
    }

    public Trade setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
        return this;
    }

}
