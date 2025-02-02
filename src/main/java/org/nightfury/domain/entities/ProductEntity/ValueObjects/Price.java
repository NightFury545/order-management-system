package org.nightfury.domain.entities.ProductEntity.ValueObjects;

import org.nightfury.shared.Result;

import java.math.BigDecimal;

public class Price {
    private final BigDecimal value;

    private Price(double value) {
        this.value = BigDecimal.valueOf(value);
    }

    public static Result<Price> create(double price) {
        if (price <= 0) {
            return Result.failure("Price must be greater than zero.");
        }
        return Result.success(new Price(price));
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Price other) {
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
