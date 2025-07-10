package com.capgemini.telecom.ordersystem.utils.thread_practice.thread_communication5;

// Communication between threads helps in coordinating actions and accomplishing tasks that require multiple threads to work together.

// if threads do not communicate, busy waiting can occur, checking for cpu when their turn occurs, leading to inefficiency and wasted CPU cycles.

// inter thread communication: wait, notify, notifyAll are used only in synchronized methods or blocks.
// wait: releases the lock and puts the thread into a waiting state until notified.
// notify: wakes up a single thread that is waiting on the object's monitor.
// notifyAll: wakes up all threads that are waiting on the object's monitor.

// This example demonstrates a producer-consumer scenario where one thread produces data and another consumes it.
// The producer thread generates data and notifies the consumer thread when data is available.
// The consumer thread waits for data to be produced and consumes it when available.


class SharedResource {
    private int data;
    private boolean hasData;

    public synchronized void produce(int value) {
        while (hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data = value;
        hasData = true;
        System.out.println("Produced data : " + value);
        notify();
    }

    public synchronized int consume() {
        while (!hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        hasData = false;
        System.out.println("Consumed data : " + data);
        notify();
        return data;
    }
}

class Producer implements Runnable {
    private SharedResource resource;

    public Producer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            resource.produce(i);
            try {
                Thread.sleep(100); // Simulating time taken to produce data
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private final SharedResource resource;

    public Consumer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            resource.consume();
            try {
                Thread.sleep(150); // Simulating time taken to consume data
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class MultiThreadCommunication {

    public static void main(String[] args) {


        SharedResource resource = new SharedResource();
        Thread producerThread = new Thread(new Producer(resource), "ProducerThread");
        Thread consumerThread = new Thread(new Consumer(resource), "ConsumerThread");

        producerThread.start();
        consumerThread.start();
    }
}
