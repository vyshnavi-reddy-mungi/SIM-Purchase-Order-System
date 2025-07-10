package com.capgemini.telecom.ordersystem.utils.thread_practice.executorframework6;

// Executors Framework is a part of the Java Concurrency framework that provides a high-level API for managing threads and tasks.
// The Executors framework was introduced in Java 5 as part of the java.util.concurrent package.
// To simplify the development of concurrent applications by abstracting away many of the complexities involved in the creating and managing the threads.
// It provides a set of factory methods for creating thread pools, scheduling tasks, and managing the execution of tasks in a concurrent environment.

// prior to executors framework, disadvantages were:
// 1. Manual Thread Management: Developers had to manually create, manage, and destroy threads, leading to complex code.
// 2. Resource Management: It was difficult to manage resources efficiently, leading to potential resource leaks.
// 3. Lack of Scalability: Applications could not easily scale with varying workloads, leading to performance issues.
// 4. Thread reuse: There was no built-in mechanism for reusing threads, leading to overhead in thread creation and destruction.
// 5. Error Handling: Error handling in multi-threaded applications was complex and error-prone.


// 3 cores: Executor, ExecutorService, ScheduledExecutorService

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FactorialThread {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3); // Create a thread pool with 9 threads

        for (int i = 1; i < 10; i++) {
            int finalI = i;
            executorService.submit(() -> {
                long result = factorial(finalI);
                System.out.println("Factorial of " + finalI + " is: " + result);
            });
        }
        executorService.shutdown();

        // executorService.awaitTermination(1, TimeUnit.MINUTES); // Wait for all tasks to complete
        // Note: The above line is optional and can be used if you want to wait for all tasks to complete before proceeding.
    }



// Using threads to calculate factorials concurrently
//    long startTime = System.currentTimeMillis();

//    Thread[] threads = new Thread[9];
//        for(int i=1;i<10;i++) {
//        int finalI = i; // effectively final variable for lambda expression
//        threads[i - 1] = new Thread(
//                () -> {
//                    long result = factorial(finalI);
//                    System.out.println("Factorial of " + finalI + " is: " + result);
//                }
//        );
//        threads[i - 1].start();
//    }
//            for(Thread thread: threads){
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//    }
//    long endTime = System.currentTimeMillis();
//        System.out.println("Time : " + (endTime - startTime) + " ms");
//}


//    without threads
//    {
//        long startTime = System.currentTimeMillis();
//
//        for(int i=1;i<10;i++){
//            long result = factorial(i);
//            System.out.println("Factorial of " + i + " is: " + result);
//        }
//        long endTime = System.currentTimeMillis();
//        System.out.println("Time : " + (endTime - startTime) + " ms");
//    }

public static long factorial(int n) {
    long result = 1;
    for (int i = 1; i <= n; i++)
        result *= i;
    return result;
}
}
