package com.example.accountservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.accountservice.domain.Account;

public class GetAccount extends RepositoryHandler<Account, Account> {

    @Override
    public Account handleRequest(Account account, Context context) {
        context.getLogger().log("Account: " + account.getId());
        return repository.load(Account.class, account.getId());
    }
}
