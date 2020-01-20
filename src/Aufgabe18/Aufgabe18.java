package Aufgabe18;

import java.net.SocketException;

public class Aufgabe18 {
    public static void main(String[] args) {
        try {
            Client18 client = new Client18("localhost", 7777);
            Server18 server = new Server18(7777);
            server.start();
            client.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
