package Aufgaben.Aufgabe19;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class Dispatcher {
    private static ArrayList<Server19> workerPool = new ArrayList<>();

    public static void handleRequest(DatagramPacket dataPackage, DatagramSocket socket) {
        /*Optional<Server19> optionalWorker =  workerPool.stream().filter(x -> x.isRunning() == false).findFirst();
        String request = new String(dataPackage.getData(), 0,dataPackage.getLength());
        if (optionalWorker.isPresent()) {
            System.out.println("'" +request + "' handled by thread " + optionalWorker.get().getIdentifier());
            optionalWorker.get().setData(dataPackage, socket);
        } else {
            Server19 worker = new Server19(workerPool.size());
            System.out.println("'" +request + "' handled by new thread " + workerPool.size());
            worker.start();
            worker.setData(dataPackage,socket);
            workerPool.add(worker);
        }*/
    }
}
