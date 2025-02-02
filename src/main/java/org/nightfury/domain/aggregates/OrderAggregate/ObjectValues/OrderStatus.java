package org.nightfury.domain.aggregates.OrderAggregate.ObjectValues;

import java.util.Arrays;
import org.nightfury.shared.Result;

public class OrderStatus {
    private final String status;

    private OrderStatus(String status) {
        this.status = status;
    }

    public static Result<OrderStatus> create(String status) {
        if (status == null || status.trim().isEmpty()) {
            return Result.failure("Status cannot be null or empty.");
        }

        if (!Arrays.asList("Pending", "Shipped", "Delivered").contains(status)) {
            return Result.failure("Invalid status value.");
        }


        return Result.success(new OrderStatus(status));
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrderStatus other) {
            return this.status.equals(other.status);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    @Override
    public String toString() {
        return status;
    }
}


