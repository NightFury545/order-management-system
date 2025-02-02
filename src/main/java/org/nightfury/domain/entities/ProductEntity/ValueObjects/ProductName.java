package org.nightfury.domain.entities.ProductEntity.ValueObjects;

import org.nightfury.shared.Result;

public class ProductName {
    private final String value;

    private ProductName(String value) {
        this.value = value;
    }

    public static Result<ProductName> create(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Result.failure("Product name is required.");
        }
        if (name.length() < 3 || name.length() > 100) {
            return Result.failure("Product name must be between 3 and 100 characters.");
        }
        return Result.success(new ProductName(name));
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductName other) {
            return this.value.equals(other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
