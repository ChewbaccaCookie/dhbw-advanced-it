package Aufgabe13;

public class PhilosophMonitor {
    boolean[] isEating;

    public PhilosophMonitor(int num) {
        this.isEating = new boolean[num];
    }

    public synchronized void startEat(int id) throws InterruptedException {
        if(isEating[(id + 1) % 5] || isEating[(id + 4) % 5]) {
            wait();
            startEat(id);
        } else {
            isEating[id] = true;
        }
    }
    public synchronized void endEat(int id) {
        isEating[id] = false;
        notifyAll();
    }
}
