package org.nightfury.domain.entities.ProductEntity.ValueObjects;

import org.nightfury.shared.Result;

public class Stock {
    private final int value;

    private Stock(int value) {
        this.value = value;
    }

    public static Result<Stock> create(int stock) {
        if (stock < 0) {
            return Result.failure("Stock quantity cannot be negative.");
        }
        return Result.success(new Stock(stock));
    }

    public int getValue() {
        return value;
    }

    public Stock add(int amount) {
        return new Stock(this.value + amount);
    }

    public Result<Stock> subtract(int amount) {
        if (this.value - amount < 0) {
            return Result.failure("Not enough stock available.");
        }
        return Result.success(new Stock(this.value - amount));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Stock other) {
            return this.value == other.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
