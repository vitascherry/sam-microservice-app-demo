package com.example.customerservice;

import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.example.customerservice.domain.Account;

import java.util.List;

public interface AccountServiceContract {

    @LambdaFunction(functionName = "GetAccountsByCustomerIdFunction")
    List<Account> getAccountsByCustomerId(Account account);
}
