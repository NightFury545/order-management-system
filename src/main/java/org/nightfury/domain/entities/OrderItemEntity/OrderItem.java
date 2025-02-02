package org.nightfury.domain.entities.OrderItemEntity;

import org.nightfury.domain.entities.OrderItemEntity.ValueObjects.Quantity;
import org.nightfury.domain.entities.OrderItemEntity.ValueObjects.Subtotal;
import org.nightfury.domain.entities.ProductEntity.Product;
import org.nightfury.domain.valueobjects.Id;
import org.nightfury.shared.Result;

public class OrderItem {
    private final Id id;
    private final Product product;
    private Quantity quantity;
    private Subtotal subtotal;

    private OrderItem(Id id, Product product, Quantity quantity, Subtotal subtotal) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public static Result<OrderItem> create(Product product, int quantity) {
        if (product.getStock().getValue() < quantity) {
            return Result.failure("Not enough stock available for product: " + product.getProductName());
        }

        Result<Void> stockReductionResult = product.reduceStock(quantity);
        if (!stockReductionResult.isSuccess()) {
            return Result.failure(stockReductionResult.getError());
        }

        Result<Quantity> quantityResult = Quantity.create(quantity);
        if (!quantityResult.isSuccess()) return Result.failure(quantityResult.getError());

        Result<Subtotal> subtotalResult = Subtotal.create(product.getPrice().getValue(), quantity);
        if (!subtotalResult.isSuccess()) return Result.failure(subtotalResult.getError());

        Result<Id> idResult = Id.createNew();
        if (!idResult.isSuccess()) return Result.failure(idResult.getError());

        return Result.success(new OrderItem(
            idResult.getValue(),
            product,
            quantityResult.getValue(),
            subtotalResult.getValue()
        ));
    }

    public Id getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Subtotal getSubtotal() {
        return subtotal;
    }

    public Result<Void> updateQuantity(int newQuantity) {
        if (newQuantity > quantity.getValue()) {
            int additionalQuantity = newQuantity - quantity.getValue();
            if (product.getStock().getValue() < additionalQuantity) {
                return Result.failure("Not enough stock available to increase quantity.");
            }
            product.reduceStock(additionalQuantity);
        } else if (newQuantity < quantity.getValue()) {
            int returnedQuantity = quantity.getValue() - newQuantity;
            product.addStock(returnedQuantity);
        }

        Result<Quantity> result = Quantity.create(newQuantity);
        if (!result.isSuccess()) return Result.failure(result.getError());

        this.quantity = result.getValue();
        this.subtotal = Subtotal.create(product.getPrice().getValue(), newQuantity).getValue();
        return Result.success(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrderItem other) {
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
        return "OrderItem { id=" + id + ", product=" + product + ", quantity=" + quantity + ", subtotal=" + subtotal + " }";
    }
}
