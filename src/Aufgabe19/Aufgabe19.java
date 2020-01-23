package Aufgabe19;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;


public class Aufgabe19 {
    public static void main(String[] args) throws IOException {

        DatagramSocket server = new DatagramSocket(7777);
        while (true) {
            byte[] dataArr = new byte[4096];
            DatagramPacket msg = new DatagramPacket(dataArr, dataArr.length);
            server.receive(msg);

            Dispatcher.handleRequest(msg, server);
        }

    }
}
