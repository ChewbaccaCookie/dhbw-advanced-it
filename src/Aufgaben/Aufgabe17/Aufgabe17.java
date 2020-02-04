package Aufgaben.Aufgabe17;

import java.net.SocketException;

public class Aufgabe17 {
    public static void main(String[] args) {
        try {
            Client17 client = new Client17("255.255.255.255", 4998);
            Server17 server = new Server17(4998);
            server.start();
            client.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
