package com.capgemini.telecom.ordersystem.utils.thread_practice;

// This example demonstrates the use of a lambda expression to create a thread in Java.
// Lambda expressions provide a clear and concise way to represent a single method interface (functional interface).
// In this case, we are using a lambda expression to implement the Runnable interface.
// The lambda expression is passed to the Thread constructor, which allows us to define the thread's behavior in a more readable way.
// sending an anonymous class to the Thread constructor is a common practice in Java, a runnable interface instance, which has only SAM
// (Single Abstract Method) run method can be implemented using a lambda expression.

public class LambdaExpressionExample {
    public static void main(String[] args) {


        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread is running: " + i);
                try {
                    Thread.sleep(1000); // Simulating some work
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted: " + e.getMessage());
                }
            }
        });

        thread.start();


    }
}
