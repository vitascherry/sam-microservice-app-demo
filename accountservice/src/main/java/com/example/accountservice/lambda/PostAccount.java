package com.example.accountservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.accountservice.domain.Account;

public class PostAccount extends RepositoryHandler<Account, Account> {

    @Override
    public Account handleRequest(Account account, Context context) {
        repository.save(account);
        Account e = account;
        context.getLogger().log("Account: " + e.getId());
        return e;
    }
}
