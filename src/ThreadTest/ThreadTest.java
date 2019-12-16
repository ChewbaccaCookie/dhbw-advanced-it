package ThreadTest;

import java.util.ArrayList;

public class ThreadTest extends Thread {
    private int id;
    private static ArrayList<Thread> runningThreads = new ArrayList<>();

    public ThreadTest(int id) {
        this.id = id;
    }
    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (Exception e) {}
        System.out.println("Thread " + this.id + " finished");
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 10; i++) {
            Thread t = new ThreadTest(i);
            runningThreads.add(t);
            t.start();
        }
        for(Thread t: ThreadTest.runningThreads) {
            t.join();
        }
        System.out.println("FINISHED!!");

    }
}
