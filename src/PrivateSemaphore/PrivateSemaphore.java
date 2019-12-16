package PrivateSemaphore;

import java.util.concurrent.Semaphore;

public class PrivateSemaphore {
    private int readerctr = 0;
    private int writerctr = 0;
    private Semaphore pReadSemaphore = new Semaphore(0,true);
    private Semaphore pWriteSemaphore = new Semaphore(0,true);
    private Semaphore mutex = new Semaphore(1, true);
    private boolean readerWaiting = false;
    private boolean writerWaiting = false;
    private boolean readerActive = false;
    private boolean writerActive = false;


    private void read() throws InterruptedException {
        mutex.acquire();
        readerctr++;
        if (writerActive == false) {
            pReadSemaphore.release();
        } else {
            readerWaiting = true;
        }
        mutex.release();
        pReadSemaphore.acquire();
        // Lesen

        mutex.acquire();
        readerctr--;
        if (readerctr == 0 && writerctr > 0) {
            pWriteSemaphore.release();
            writerctr--;
            writerActive = true;
        }
        mutex.release();

    }


    private void write() throws InterruptedException {
        mutex.acquire();
        if (writerActive == false && readerctr == 0) {
            writerActive = true;
            pWriteSemaphore.release();
        } else {
            writerctr++;
        }
        mutex.release();
        pWriteSemaphore.acquire();
        // Schreib
        mutex.acquire();
        writerActive = false;
        if(readerctr > 0) {
            readerWaiting = false;
            for (int i = 0; i < readerctr; i++) {
                pReadSemaphore.release();
            }
        } else if(writerctr > 0) {
            writerctr--;
            writerActive = false;
            pWriteSemaphore.release();
        }
        mutex.release();

    }
}
