package org.nightfury.domain.aggregates.OrderAggregate;

import org.nightfury.domain.aggregates.OrderAggregate.ObjectValues.OrderStatus;
import org.nightfury.domain.aggregates.OrderAggregate.ObjectValues.TotalPrice;
import org.nightfury.domain.entities.OrderItemEntity.OrderItem;
import org.nightfury.domain.valueobjects.Id;
import org.nightfury.shared.Result;

import java.util.List;

public class Order {
    private final Id id;
    private OrderStatus status;
    private final List<OrderItem> items;
    private TotalPrice totalPrice;

    private Order(Id id, OrderStatus status, List<OrderItem> items, TotalPrice totalPrice) {
        this.id = id;
        this.status = status;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public static Result<Order> create(List<OrderItem> items, String status) {
        Result<OrderStatus> statusResult = OrderStatus.create(status);
        if (!statusResult.isSuccess()) {
            return Result.failure(statusResult.getError());
        }

        double total = items.stream()
            .mapToDouble(item -> item.getSubtotal().getValue().doubleValue())
            .sum();
        Result<TotalPrice> totalPriceResult = TotalPrice.create(total);
        if (!totalPriceResult.isSuccess()) {
            return Result.failure(totalPriceResult.getError());
        }

        Result<Id> idResult = Id.createNew();
        if (!idResult.isSuccess()) {
            return Result.failure(idResult.getError());
        }

        return Result.success(new Order(
            idResult.getValue(),
            statusResult.getValue(),
            items,
            totalPriceResult.getValue()
        ));
    }

    public Id getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TotalPrice getTotalPrice() {
        return totalPrice;
    }

    public Result<Void> updateStatus(String status) {
        Result<OrderStatus> statusResult = OrderStatus.create(status);
        if (!statusResult.isSuccess()) {
            return Result.failure(statusResult.getError());
        }
        this.status = statusResult.getValue();
        return Result.success(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order other) {
            return this.id.equals(other.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Order { id=" + id + ", status=" + status + ", items=" + items + ", totalPrice=" + totalPrice + " }";
    }
}

