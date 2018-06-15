package com.example.customerservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.customerservice.domain.Customer;

import java.util.List;

public class GetCustomers extends RepositoryHandler<Void, List<Customer>> {

    @Override
    public List<Customer> handleRequest(Void aVoid, Context context) {
        List<Customer> customers = repository.scan(Customer.class, RepositoryHandler.LIST_OF_FIVE);
        context.getLogger().log("Fetched customers: " + customers.size());
        return customers;
    }
}
