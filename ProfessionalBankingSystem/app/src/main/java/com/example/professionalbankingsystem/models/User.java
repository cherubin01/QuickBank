package com.example.professionalbankingsystem.models;

public class User {
    private String email;
    private String userId;
    private double balance;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String userId, double balance) {
        this.email = email;
        this.userId = userId;
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
