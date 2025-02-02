package org.nightfury.domain.entities.OrderItemEntity.ValueObjects;

import java.math.BigDecimal;
import org.nightfury.shared.Result;

public class Subtotal {
    public final BigDecimal value;

    private Subtotal(BigDecimal value) {
        this.value = value;
    }

    public static Result<Subtotal> create(BigDecimal price, int quantity) {
        if (quantity <= 0) {
            return Result.failure("Quantity must be greater than zero.");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.failure("Price must be greater than zero.");
        }

        BigDecimal subtotalValue = price.multiply(BigDecimal.valueOf(quantity));
        return Result.success(new Subtotal(subtotalValue));
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Subtotal other) {
            return this.value.equals(other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
