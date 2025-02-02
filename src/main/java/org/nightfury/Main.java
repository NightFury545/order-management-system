package org.nightfury;

import java.util.List;
import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.domain.entities.CustomerEntity.Customer;
import org.nightfury.domain.entities.OrderItemEntity.OrderItem;
import org.nightfury.domain.entities.ProductEntity.Product;

public class Main {

    public static void main(String[] args) {
        Product product1 = Product.create("chocolate", 25.55, 120).getValue();
        Product product2 = Product.create("milk", 15.35, 50).getValue();
        Product product3 = Product.create("meat", 75.15, 20).getValue();

        Customer customer = Customer.create("ruslan@gmail.com", "Ruslan", "Ruslanovich",
            "Grude 1, Uzhorod, Ukraine", "380310401545").getValue();

        OrderItem item1 = OrderItem.create(product1, 5).getValue();
        OrderItem item2 = OrderItem.create(product2, 8).getValue();
        OrderItem item3 = OrderItem.create(product3, 3).getValue();

        List<OrderItem> items = List.of(item1, item2, item3);

        Order order = Order.create(items, "Pending").getValue();
        System.out.println("Order created successfully: " + order);
        System.out.println(order.getTotalPrice());

        System.out.println(product1.getStock());
        System.out.println(product2.getStock());
        System.out.println(product3.getStock());
    }
}