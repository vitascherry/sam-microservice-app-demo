package com.example.orderservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.orderservice.domain.Order;

public class GetOrder extends RepositoryHandler<Order, Order> {

    @Override
    public Order handleRequest(Order order, Context context) {
        context.getLogger().log("Order: " + order.getId());
        return repository.load(Order.class, order.getId());
    }
}
