package com.capgemini.telecom.ordersystem.utils.thread_practice.deadlock_thread4;

// Deadlock occurs when 4 conditions are met simultaneously:
// 1. Mutual Exclusion: Only one thread can access a resource at a time.
// 2. Hold and Wait: A process holding at least one resource is waiting to acquire additional resources held by other thread.
// 3. No Preemption: Resources cannot be forcibly taken from threads holding them.
// 4. Circular Wait: A set of threads are waiting for each other in a circular chain.

// This code demonstrates a deadlock scenario where two threads are trying to write on a pen and paper, leading to a situation where each thread is waiting for the other to release the lock, causing both threads to be blocked indefinitely.
// // To resolve this deadlock, we can use a synchronized block to ensure that only one thread can access the pen or paper at a time, preventing the circular wait condition.
class Pen{

    public synchronized void writeWithPenAndPaper(Paper paper){
        System.out.println(Thread.currentThread().getName() + " is writing with pen on paper.");

        paper.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println(Thread.currentThread().getName() + " has finished writing on paper.");
    }
}

class Paper{

    public synchronized void writeWithPaperAndPen(Pen pen){
        System.out.println(Thread.currentThread().getName() + " is writing on paper with pen.");

        pen.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println(Thread.currentThread().getName() + " has finished writing on paper with pen.");
    }
}

class Task1 implements Runnable {

    private Pen pen;
    private Paper paper;

    public Task1(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        pen.writeWithPenAndPaper(paper);
    }
}

class Task2 implements Runnable {

    private Pen pen;
    private Paper paper;

    public Task2(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        synchronized (pen){
        paper.writeWithPaperAndPen(pen);
    }
    }
}

public class DeadLockExample {

    public static void main(String[] args) {
        Pen pen = new Pen();
        Paper paper = new Paper();

        Task1 task1 = new Task1(pen, paper);
        Task2 task2 = new Task2(pen, paper);

        Thread thread1 = new Thread(task1, "Thread-1");
        Thread thread2 = new Thread(task2, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
