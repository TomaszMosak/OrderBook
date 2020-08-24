package com.mthree.bsm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The party entity corresponds to a party (e.g. a company) trading a stock. It has an {@link #id} for the
 * primary key, a {@link #name}, and a {@link #symbol} to act as an abbreviation for the name.
 */
public class Party {

    private int id;

    @NotNull(message = "The party's symbol must not be null.")
    @Size(max = 25, message = "The party's name must have length at most 20.")
    private String name;

    @NotNull(message = "The party's symbol must not be null.")
    @Size(max = 5, message = "The party's symbol must have length at most 20.")
    private String symbol;
    
    public Party(){
    }
    
    public Party(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Party{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", symbol='" + symbol + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Party party = (Party) o;
        return id == party.id &&
               Objects.equals(name, party.name) &&
               Objects.equals(symbol, party.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol);
    }

    public int getId() {
        return id;
    }

    public Party setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Party setName(String name) {
        this.name = name;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public Party setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

}
