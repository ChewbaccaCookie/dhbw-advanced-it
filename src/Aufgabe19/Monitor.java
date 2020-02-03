package Aufgabe19;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;

public class Monitor {
    public static Queue waitingQueue = new LinkedList();
    public synchronized void addJob(DatagramPacket dp) {
        if (waitingQueue.size() < 10) {
            waitingQueue.add(dp);
            notifyAll();
        }
    }
    public synchronized DatagramPacket getJob(int id) throws InterruptedException {
        if(waitingQueue.size() == 0) {
            wait();
            return getJob(id);
        }
        return (DatagramPacket) waitingQueue.remove();
    }
}
