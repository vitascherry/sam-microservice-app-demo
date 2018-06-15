package com.example.accountservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.accountservice.domain.Account;

import java.util.List;

public class GetAccounts extends RepositoryHandler<Void, List<Account>> {

    @Override
    public List<Account> handleRequest(Void aVoid, Context context) {
        List<Account> accounts = repository.scan(Account.class, LIST_OF_FIVE);
        context.getLogger().log("Fetched accounts" + accounts.size());
        return accounts;
    }
}
