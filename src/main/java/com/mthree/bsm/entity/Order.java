package com.mthree.bsm.entity;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Objects;

/**
 * The order entity corresponds to a buy or sell order made in the application. It has an {@link #id} to act as the
 * primary key, the current {@link #version} of the order, a {@link #user} referencing the user making the order and the
 * {@link #party} owning the stock, a {@link #stock} referencing the stock being bought/sold, a boolean {@link #isBuy}
 * representing whether the order is a buy order or a sell order, a {@link #status} for the order, a current {@link
 * #price} for the order, the {@link #size} of the order and the {@link #versionTime} when the current version of the
 * order came into being.
 */
public class Order {

    private int id;

    private int historyId;

    private int version;

    @NotNull(message = "The order's user cannot be null.")
    private User user;

    @NotNull(message = "The order's party cannot be null.")
    private Party party;

    @NotNull(message = "The order's stock cannot be null.")
    private Stock stock;

    @NotNull(message = "The order's price cannot be null.")
    @Digits(integer = 6,
            fraction = 2,
            message = "The order's price must have at most 8 digits with 2 digit after the decimal point")
    @DecimalMin(value = "0.0", message = "The order's price must be nonnegative.")
    private BigDecimal price;

    @Max(value = 10_000_000, message = "The order's size must be at most 10 000 000.")
    @Min(value = 0, message = "The order's size must be nonnegative.")
    private int size;

    private boolean isBuy;

    @NotNull(message = "The order's status cannot be null.")
    private OrderStatus status;

    @NotNull(message = "The order's creation time cannot be null.")
    @PastOrPresent(message = "The order's creation time must be in the past.")
    private LocalDateTime versionTime;

    public Order() {
    }

    public Order(@NotNull(message = "The order's user cannot be null.") User user,
                 @NotNull(message = "The order's party cannot be null.") Party party,
                 @NotNull(message = "The order's stock cannot be null.") Stock stock,
                 @NotNull(message = "The order's price cannot be null.") @Digits(integer = 6,
                         fraction = 2,
                         message = "The order's price must have at most 8 digits with 2 digit after the decimal point") @DecimalMin(value = "0.0", message = "The order's price must be nonnegative.") BigDecimal price,
                 @Max(value = 10_000_000, message = "The order's size must be at most 10 000 000.") @Min(value = 0, message = "The order's size must be nonnegative.") int size,
                 boolean isBuy,
                 @NotNull(message = "The order's status cannot be null.") OrderStatus status,
                 @NotNull(message = "The order's creation time cannot be null.") @PastOrPresent(message = "The order's creation time must be in the past.") LocalDateTime versionTime) {
        this.user = user;
        this.party = party;
        this.stock = stock;
        this.price = price;
        this.size = size;
        this.isBuy = isBuy;
        this.status = status;
        this.versionTime = versionTime;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", historyId=" + historyId +
               ", version=" + version +
               ", user=" + user +
               ", party=" + party +
               ", stock=" + stock +
               ", price=" + price +
               ", size=" + size +
               ", isBuy=" + isBuy +
               ", status=" + status +
               ", versionTime=" + versionTime +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
               historyId == order.historyId &&
               version == order.version &&
               size == order.size &&
               isBuy == order.isBuy &&
               Objects.equals(user, order.user) &&
               Objects.equals(party, order.party) &&
               Objects.equals(stock, order.stock) &&
               Objects.equals(price, order.price) &&
               status == order.status &&
               versionTime.truncatedTo(ChronoUnit.SECONDS).equals(order.versionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, historyId, version, user, party, stock, price, size, isBuy, status, versionTime);
    }

    public int getId() {
        return id;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public int getHistoryId() {
        return historyId;
    }

    public Order setHistoryId(int historyId) {
        this.historyId = historyId;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public Order setVersion(int version) {
        this.version = version;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Order setUser(User user) {
        this.user = user;
        return this;
    }

    public Party getParty() {
        return party;
    }

    public Order setParty(Party party) {
        this.party = party;
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

    public int getSize() {
        return size;
    }

    public Order setSize(int size) {
        this.size = size;
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

    public LocalDateTime getVersionTime() {
        return versionTime;
    }

    public Order setVersionTime(LocalDateTime versionTime) {
        this.versionTime = versionTime;
        return this;
    }

}
