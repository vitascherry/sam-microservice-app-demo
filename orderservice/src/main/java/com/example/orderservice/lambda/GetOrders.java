package com.example.orderservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.orderservice.domain.Order;

import java.util.List;

public class GetOrders extends RepositoryHandler<Void, List<Order>> {

    @Override
    public List<Order> handleRequest(Void aVoid, Context context) {
        List<Order> orders = repository.scan(Order.class, LIST_OF_FIVE);
        context.getLogger().log("Fetched " + orders.size() + " orders");
        return orders;
    }
}
