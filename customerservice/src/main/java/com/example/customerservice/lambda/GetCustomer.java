package com.example.customerservice.lambda;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.example.customerservice.AccountServiceContract;
import com.example.customerservice.domain.Account;
import com.example.customerservice.domain.Customer;

import java.util.List;

public class GetCustomer extends RepositoryHandler<Customer, Customer> {

    private AccountServiceContract accountService;

    public GetCustomer() {
        accountService = LambdaInvokerFactory.builder()
            .lambdaClient(AWSLambdaClientBuilder.defaultClient())
            .build(AccountServiceContract.class);
    }

    @Override
    public Customer handleRequest(Customer customer, Context context) {
        context.getLogger().log("Customer: " + customer.getId());
        Customer e = repository.load(Customer.class, customer.getId());
        Account stub = new Account(e.getId());
        List<Account> aa = accountService.getAccountsByCustomerId(stub);
        e.setAccounts(aa);
        return e;
    }
}
