package Semaphore.ErzeugerVerbraucherProb;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class ErzeugerVerbraucherProb {
    private Object[] dataBuffer = new Object[10];
    private Semaphore empty = new Semaphore(10, true);
    private Semaphore full = new Semaphore(0, true);
    private int nextFree = 0;
    private int nextFull = 0;

    public void remove() {
        try {
            full.acquire();
            dataBuffer[nextFull] = null;
            nextFull = (nextFull + 1) % dataBuffer.length;
            empty.release();
        } catch (Exception e) {
        }
    }

    public void append(int item) {
        try {
            empty.acquire();
            dataBuffer[nextFree] = item;
            nextFree = (nextFree + 1) % dataBuffer.length;
            full.release();
        } catch (Exception e) {
        }

    }

    @Override
    public String toString() {
        return Arrays.toString(dataBuffer);
    }

    public static void main(String[] args) {
        try {
            ErzeugerVerbraucherProb evp = new ErzeugerVerbraucherProb();
            Writer w = new Writer(evp);
            Reader r = new Reader(evp);
            w.start();
            r.start();
            w.join();
            r.join();
            System.out.println(evp);
        } catch (Exception e) {
        }
    }
}

class Writer extends Thread {
    ErzeugerVerbraucherProb evp;

    public Writer(ErzeugerVerbraucherProb evp) {
        this.evp = evp;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            evp.append(i);
        }
    }
}

class Reader extends Thread {
    ErzeugerVerbraucherProb evp;

    public Reader(ErzeugerVerbraucherProb evp) {
        this.evp = evp;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            evp.remove();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}