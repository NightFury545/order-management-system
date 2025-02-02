package org.nightfury.domain.entities.OrderItemEntity.ValueObjects;

import org.nightfury.shared.Result;

public class Quantity {
    public final int value;

    private Quantity(int value) {
        this.value = value;
    }

    public static Result<Quantity> create(int quantity) {
        if (quantity <= 0) {
            return Result.failure("Quantity must be greater than zero.");
        }
        return Result.success(new Quantity(quantity));
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Quantity other) {
            return this.value == other.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}

