package com.capgemini.telecom.ordersystem.utils.thread_practice.synchronize_thread2;

public class BankAccount {

    private int balance=100;

    public synchronized void withdraw(int amount) throws InterruptedException {
        System.out.println("Thread " + Thread.currentThread().getName() + " is going to withdraw " + amount);

        if(balance>=amount){
            Thread.sleep(1000); // Simulating a delay for demonstration purposes
            System.out.println("Thread " + Thread.currentThread().getName() + " is proceeding to withdraw " + amount);
            balance-=amount;
            System.out.println("Thread " + Thread.currentThread().getName() + " balance " + balance);
        }
        else {
            System.out.println("Thread " + Thread.currentThread().getName() + " insufficient balance to withdraw " + amount);
        }
    }
}
