package com.example.customerservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.customerservice.domain.Customer;

public class PostCustomer extends RepositoryHandler<Customer, Customer> {

    @Override
    public Customer handleRequest(Customer customer, Context context) {
        repository.save(customer);
        Customer e = customer;
        context.getLogger().log("Customer: " + e.getId());
        return e;
    }
}
