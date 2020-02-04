package Aufgaben.ThreadTest;

public class ThreadPerformanceTest {
    int[] werte = new int[(int) Math.pow(2, 28)];

    private void generateNumbers() {
        for (int i = 0; i < werte.length; i++) {
            werte[i] = (int) (Math.random() * 20);
        }
    }

    public ThreadPerformanceTest() {
        this.generateNumbers();
    }
    private void runInMainThread() {
        long startMillis = System.currentTimeMillis();
        long sum = 0;
        for (var wert : werte) {
            sum += wert;
        }
        long duration = System.currentTimeMillis() - startMillis;
        System.out.println("Duration with 1 Thread: " + duration + " | Sum: "+  sum);
    }

    private void runInThreads(int threadNum) throws InterruptedException {

        int size = werte.length / threadNum;
        int start = 0;
        SumThread[] threads = new SumThread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new SumThread(this.werte, start, start + size);
            start += size;
        }

        long startMillis = System.currentTimeMillis();
        for (var t: threads) {
            t.start();
        }
        long sum = 0;
        for (var t : threads) {
            t.join();
            sum += t.getSum();
        }
        long duration = System.currentTimeMillis() - startMillis;
        System.out.println("Duration with " + threadNum + " Threads: " + duration + " | Sum: " + sum);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPerformanceTest pt = new ThreadPerformanceTest();
        pt.generateNumbers();
        pt.runInMainThread();
        pt.runInThreads(2);
        pt.runInThreads(4);
        pt.runInThreads(8);
        pt.runInThreads(16);
        pt.runInThreads(32);
        pt.runInThreads(64);
        pt.runInThreads(128);
        pt.runInThreads(256);

    }
}


class SumThread extends Thread {
    protected int sum = 0;
    private int from;
    private int to;
    private int[] arr;

    protected SumThread(int[] arr, int from, int to) {
        this.from = from;
        this.to = to;
        this.arr = arr;
    }

    @Override
    public void run() {
        for (int i = from; i < to; i++) {
            this.sum += arr[i];
        }
    }

    public int getSum() {
        return sum;
    }
}