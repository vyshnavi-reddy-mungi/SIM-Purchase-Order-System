package com.capgemini.telecom.ordersystem.utils.thread_practice.executorframework6;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

// Executor framework is a high-level API for managing threads and tasks in Java.
// methods: submit(runnable), submit(callable), submit(runnable, result)
// shutdown(), shutdownNow(), awaitTermination(),isShutDown(), invoke(), invokeAll(), invokeAny()

// future: f.get(), f.get(time), f.isDone(), f.isCancelled(), f.cancel()

// Executors.newSingleThreadExecutor() creates a thread pool with a single thread.
// Executors.newFixedThreadPool(n) creates a thread pool with a fixed number of threads.
// Executors.newCachedThreadPool(), for short-lived variable requests,  creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available.


public class FutureExecutorService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // submit can take runnable or callable type.
        // Callable: If you want to return a value from the task, it has call method, method signature has throws exception.
        // Runnable: If you don't need to return a value, it has run method, should use try catch to handle the exception.

        Future<?> future = executorService.submit(() -> System.out.println("hello")); // runnable
        Future<?> future2 = executorService.submit(() -> "hello");// callable
        Future<?> future3 = executorService.submit(() -> System.out.println("hello runnable"), "abc");// runnable with result
        // Future is used to retrieve the result of an asynchronous computation.

//        System.out.println(future.get());// This will block until the task is complete and return "hello"
//        System.out.println(future2.get()); // This will block until the task is complete and return "hello"
//        System.out.println(future3.get()); // This will block until the task is complete and return "abc"
        executorService.shutdown();
        System.out.println("-------- Executor service 1 is shutdown: " + executorService.isShutdown());
// --------------------------------------------isTerminated--------------------------------
        ExecutorService executorService2 = Executors.newFixedThreadPool(2);
        Future<Integer> result = executorService2.submit(() -> 1 + 2);
        System.out.println("Result of the computation: " + result.get()); // This will block until the task is complete and return 3
        executorService2.shutdown();
        System.out.println(executorService2.isShutdown());
        Thread.sleep(1);
        System.out.println(executorService2.isTerminated());
        System.out.println("-------------Executor service 2 is shutdown: " + executorService2.isShutdown());
// -----------------------------------------invokeAll, invokeAll with time waiting, invokeAny(only 1 gets executed and others do not get) ----------------------------------

        ExecutorService executorService3 = Executors.newFixedThreadPool(2);
        Callable<Integer> callable1 = () -> {
            System.out.println("Task1");
            return 1;
        };

        Callable<Integer> callable2 = () -> {
            System.out.println("Task2");
            return 2;
        };

        Callable<Integer> callable3 = () -> {
            System.out.println("Task3");
            return 3;
        };
        List<Callable<Integer>> callables = Arrays.asList(callable1, callable2, callable3);

//        List<Future<Integer>> futures = executorService3.invokeAll(callables);
//        List<Future<Integer>> futures = executorService3.invokeAll(callables, 1, TimeUnit.SECONDS);
        // This will block until all tasks are complete or the timeout occurs
        Integer i = executorService3.invokeAny(callables);
//        for (Future<Integer> futureResult : futures) {
//            System.out.println("Result: " + futureResult.get()); // This will block until the task is complete and return the result
//        }
        System.out.println(i);
        executorService3.shutdown();
        System.out.println("-----------Executor service 3 is shutdown: " + executorService3.isShutdown());

//        ---------------- future: f.get(), f.get(time), f.isDone()

        ExecutorService executorService4 = Executors.newFixedThreadPool(2);
        Future<Integer> future4 = executorService4.submit(() -> {
            Thread.sleep(2000); // Simulating a long-running task
            return 42;
        });

        try {
            Integer f2=future4.get(1, TimeUnit.SECONDS); // This will block until the task is complete or timeout occurs
            System.out.println("Future result: " + f2);
        } catch (TimeoutException e) {
            System.out.println("Task timed out before completion.");
        } catch (ExecutionException e) {
            System.out.println("An error occurred while executing the task: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.out.println("Task was interrupted.");
        }

        System.out.println("Task completed with result: " + future4.get()); // This will block until the task is complete and return 42
        executorService4.shutdown();
        System.out.println("-----------Executor service 4 is shutdown: " + executorService4.isShutdown());

        // --------------------------------- future.cancel(), future.isCancelled(), future.isDone() ----------------------------------

        ExecutorService executorService5 = Executors.newFixedThreadPool(2);
        Future<Integer> future5 = executorService5.submit(() -> {
            Thread.sleep(2000); // Simulating a long-running task
            return 42;
        });
        future5.cancel(true); // Attempt to cancel the task
        System.out.println(future5.isCancelled());
        System.out.println(future5.isDone()); // This will return true if the task is completed or cancelled
        executorService5.shutdown();
    }
}
