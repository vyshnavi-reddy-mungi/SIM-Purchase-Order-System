package com.capgemini.telecom.ordersystem.utils.thread_practice.lock_thread3;

public class RunnableBankThread {
    public static void main(String[] args) {
        BankAccountReentrantLock bankAccount = new BankAccountReentrantLock();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                bankAccount.withdraw(50);
            }
        };

        Thread thread1 = new Thread(task,"Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        thread1.start();
        thread2.start();
    }
}
