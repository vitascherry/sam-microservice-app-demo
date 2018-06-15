package com.example.customerservice.domain;

import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 8331074361667921244L;

    private String id;

    private String number;

    private int balance;

    private String customerId;

    public Account() {
    }

    public Account(String customerId) {
        this.customerId = customerId;
    }

    public Account(String id, String number, int balance, String customerId) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
}
