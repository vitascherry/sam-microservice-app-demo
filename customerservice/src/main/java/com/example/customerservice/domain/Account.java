package com.example.customerservice.domain;

import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 8331074361667921244L;

    private String id;

    private AccountStatus status;

    private int balance;

    private String customerId;

    public Account() {
    }

    public Account(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        if (id != null ? !id.equals(account.id) : account.id != null) {
            return false;
        }
        return customerId != null ? customerId.equals(account.customerId) : account.customerId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
               "id='" + id + '\'' +
               ", status='" + status + '\'' +
               ", balance=" + balance +
               ", customerId='" + customerId + '\'' +
               '}';
    }
}
