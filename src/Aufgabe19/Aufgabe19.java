package Aufgabe19;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.Month;
import java.util.ArrayList;


public class Aufgabe19 {
    public static Monitor monitor = new Monitor();

    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(7777);
        ArrayList<Server19> workerPool = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            Server19 worker = new Server19(i, server);
            workerPool.add(worker);
            worker.start();
        }

        while (true) {
            byte[] dataArr = new byte[10000000];
            DatagramPacket msg = new DatagramPacket(dataArr, dataArr.length);
            server.receive(msg);
            monitor.addJob(msg);
        }
    }
}