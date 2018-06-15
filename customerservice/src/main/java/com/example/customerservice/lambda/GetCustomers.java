package com.example.customerservice.lambda;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.example.customerservice.AccountServiceContract;
import com.example.customerservice.domain.Account;
import com.example.customerservice.domain.Customer;

import java.util.List;

public class GetCustomers extends RepositoryHandler<Void, List<Customer>> {

    private AccountServiceContract accountService;

    public GetCustomers() {
        accountService = LambdaInvokerFactory.builder()
            .lambdaClient(AWSLambdaClientBuilder.defaultClient())
            .build(AccountServiceContract.class);
    }

    @Override
    public List<Customer> handleRequest(Void aVoid, Context context) {
        List<Customer> customers = repository.scan(Customer.class, RepositoryHandler.LIST_OF_FIVE);
        for (Customer customer : customers) {
            Account stub = new Account(customer.getId());
            List<Account> accounts = accountService.getAccountsByCustomerId(stub);
            context.getLogger().log("Accounts: " + accounts);
            customer.setAccounts(accounts);
        }
        return customers;
    }
}
