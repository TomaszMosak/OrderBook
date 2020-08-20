package com.mthree.bsm.entity;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The order entity corresponds to a buy or sell order made in the application. It has an {@link #id} to act as the
 * primary key, a {@link #user} referencing the user making the trade, a {@link #stock} referencing the stock being
 * bought/sold, a boolean {@link #isBuy} representing whether the order is a buy order or a sell order, a {@link
 * #status} for the order, a current {@link #price} for the order, the {@link #remainingSize} of the order and the
 * {@link #lotSize} from when the order was first made, and a {@link #creationTime}.
 * <p>
 * An order is valid if none of its fields are null, the {@link #price} is nonnegative and has at most 7 digits with 1
 * digit after the decimal point, the {@link #lotSize} and {@link #remainingSize} are between 0 and 100 000, and the
 * creation time is in the past.
 */
public class Order {

    private int id;

    @NotNull(message = "The order's user cannot be null.")
    private User user;

    @NotNull(message = "The order's stock cannot be null.")
    private Stock stock;

    @NotNull(message = "The order's price cannot be null.")
    @Digits(integer = 6,
            fraction = 1,
            message = "The order's price must have at most 7 digits with 1 digit after the decimal point")
    @DecimalMin(value = "0.0", message = "The order's price must be nonnegative.")
    private BigDecimal price;

    @Max(value = 100_000, message = "The order's lot size must be at most 100 000.")
    @Min(value = 0, message = "The order's lot size must be at least 0.")
    private int lotSize;

    @Max(value = 100_000, message = "The order's lot size must be at most 100 000.")
    @Min(value = 0, message = "The order's lot size must be at least 0.")
    private int remainingSize;

    private boolean isBuy;

    @NotNull(message = "The order's status cannot be null.")
    private OrderStatus status;

    @NotNull(message = "The order's creation time cannot be null.")
    @PastOrPresent(message = "The order's creation time must be in the past.")
    private LocalDateTime creationTime;

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", user=" + user +
               ", stock=" + stock +
               ", price=" + price +
               ", lotSize=" + lotSize +
               ", remainingSize=" + remainingSize +
               ", isBuy=" + isBuy +
               ", status=" + status +
               ", creationTime=" + creationTime +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
               lotSize == order.lotSize &&
               remainingSize == order.remainingSize &&
               isBuy == order.isBuy &&
               Objects.equals(user, order.user) &&
               Objects.equals(stock, order.stock) &&
               Objects.equals(price, order.price) &&
               status == order.status &&
               Objects.equals(creationTime, order.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, stock, price, lotSize, remainingSize, isBuy, status, creationTime);
    }

    public int getId() {
        return id;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Order setUser(User user) {
        this.user = user;
        return this;
    }

    public Stock getStock() {
        return stock;
    }

    public Order setStock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Order setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getLotSize() {
        return lotSize;
    }

    public Order setLotSize(int lotSize) {
        this.lotSize = lotSize;
        return this;
    }

    public int getRemainingSize() {
        return remainingSize;
    }

    public Order setRemainingSize(int remainingSize) {
        this.remainingSize = remainingSize;
        return this;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public Order setBuy(boolean buy) {
        isBuy = buy;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Order setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

}
