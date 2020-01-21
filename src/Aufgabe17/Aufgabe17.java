package Aufgabe17;

import java.net.SocketException;

public class Aufgabe17 {
    public static void main(String[] args) {
        try {
            Client17 client = new Client17("255.255.255.255", 7777);
            Server17 server = server = new Server17(7777);
            server.start();
            client.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
