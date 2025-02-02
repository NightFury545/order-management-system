package org.nightfury.domain.entities.ProductEntity;

import org.nightfury.domain.entities.ProductEntity.ValueObjects.Price;
import org.nightfury.domain.entities.ProductEntity.ValueObjects.ProductName;
import org.nightfury.domain.entities.ProductEntity.ValueObjects.Stock;
import org.nightfury.domain.valueobjects.Id;
import org.nightfury.shared.Result;

public class Product {
    private final Id id;
    private final ProductName productName;
    private Stock stock;
    private final Price price;

    private Product(Id id, ProductName productName, Stock stock, Price price) {
        this.id = id;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
    }

    public static Result<Product> create(String productName, double price, int stock) {
        Result<ProductName> nameResult = ProductName.create(productName);
        if (!nameResult.isSuccess()) return Result.failure(nameResult.getError());

        Result<Price> priceResult = Price.create(price);
        if (!priceResult.isSuccess()) return Result.failure(priceResult.getError());

        Result<Stock> stockResult = Stock.create(stock);
        if (!stockResult.isSuccess()) return Result.failure(stockResult.getError());

        Result<Id> idResult = Id.createNew();
        if (!idResult.isSuccess()) return Result.failure(idResult.getError());

        return Result.success(new Product(
            idResult.getValue(),
            nameResult.getValue(),
            stockResult.getValue(),
            priceResult.getValue()
        ));
    }

    public Id getId() {
        return id;
    }

    public ProductName getProductName() {
        return productName;
    }

    public Stock getStock() {
        return stock;
    }

    public Price getPrice() {
        return price;
    }

    public Result<Void> reduceStock(int quantity) {
        Result<Stock> result = stock.subtract(quantity);
        if (!result.isSuccess()) return Result.failure(result.getError());
        this.stock = result.getValue();
        return Result.success(null);
    }

    public void addStock(int quantity) {
        this.stock = stock.add(quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product other) {
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
        return "Product { id=" + id + ", name=" + productName + ", stock=" + stock + ", price=" + price + " }";
    }
}
