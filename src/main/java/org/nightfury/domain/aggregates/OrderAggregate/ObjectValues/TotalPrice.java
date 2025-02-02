package org.nightfury.domain.aggregates.OrderAggregate.ObjectValues;

import org.nightfury.shared.Result;

import java.math.BigDecimal;

public class TotalPrice {
    private final BigDecimal value;

    private TotalPrice(double value) {
        this.value = BigDecimal.valueOf(value);
    }

    public static Result<TotalPrice> create(double totalPrice) {
        if (totalPrice < 0) {
            return Result.failure("Total price cannot be negative.");
        }
        return Result.success(new TotalPrice(totalPrice));
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TotalPrice other) {
            return this.value.compareTo(other.value) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

