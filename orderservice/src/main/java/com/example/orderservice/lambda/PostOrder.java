package com.example.orderservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderStatus;

public class PostOrder extends RepositoryHandler<Order, Order> {

    @Override
    public Order handleRequest(Order order, Context context) {
        order.setStatus(OrderStatus.OPEN);
        repository.save(order);
        Order e = order;
        context.getLogger().log("Order saved: " + e.getId());
        return e;
    }
}
