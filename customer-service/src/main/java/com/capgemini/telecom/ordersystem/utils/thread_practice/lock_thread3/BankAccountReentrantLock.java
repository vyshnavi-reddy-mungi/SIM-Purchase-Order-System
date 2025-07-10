package com.capgemini.telecom.ordersystem.utils.thread_practice.lock_thread3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// ReentrantLock is a type of lock that allows a thread to acquire the same lock multiple times without causing a deadlock.
// lock, unlock, tryLock
// Deadlock prevention, Lock interruption, Fairness

//Disadvantages of Synchronization:
// 1. Fairness: Synchronization does not guarantee fairness, meaning that threads may not acquire locks in the order they requested them. This can lead to starvation of some threads.
// 2. Blocking: When a thread tries to acquire a lock that is already held by another thread, it will block until the lock is released. This can lead to performance issues if threads are frequently blocked.
// 3. Interruption: If a thread is blocked waiting for a lock, it cannot be interrupted until it acquires the lock. This can lead to situations where threads are stuck waiting indefinitely.
// 4. Read-Write Locking: Synchronization does not provide a way to differentiate between read and write operations. If multiple threads are reading data, they will block each other, even though they are not modifying the data.

public class BankAccountReentrantLock {

    private int balance = 100;

    private final Lock lock = new ReentrantLock();

    public void withdraw(int amount) {
        System.out.println("Thread " + Thread.currentThread().getName() + " is going to withdraw " + amount);

        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                if (balance >= amount) {
                    try {
                        System.out.println("Thread " + Thread.currentThread().getName() + " is proceeding to withdraw " + amount);

                        Thread.sleep(3000); // Simulating a delay for demonstration purposes
                        balance -= amount;
                        System.out.println("Thread " + Thread.currentThread().getName() + " balance " + balance);
                    } catch (Exception e) {
                        // InterruptedException is caught here and the thread's interrupted status is restored
                        // This is important to ensure that the thread can be interrupted properly in future operations
                        // This is a good practice to handle InterruptedException
                        // This is especially important in a multi-threaded environment where threads may need to be interrupted for various reasons.
                         Thread.currentThread().interrupt(); // Restore interrupted status
                    }
                    finally {
                        lock.unlock();
                        System.out.println("Thread " + Thread.currentThread().getName() + " has released the lock.");
                    }
                } else {
                    System.out.println("Thread " + Thread.currentThread().getName() + " insufficient balance to withdraw " + amount);
                }
            }
            else{
                System.out.println("Thread " + Thread.currentThread().getName() + " could not acquire the lock to withdraw " + amount);
            }
        } catch (Exception e) {
            // Handle the InterruptedException if the thread is interrupted while waiting for the lock
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
