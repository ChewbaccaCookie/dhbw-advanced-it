package Aufgabe17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Client17 extends Thread{
    private InetSocketAddress serverAddress;
    private DatagramSocket client;

    public Client17(String address,int port) {
        this.serverAddress = new InetSocketAddress(address, port);
        try {
            this.client = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                var data = br.readLine().getBytes();
                DatagramPacket msg = new DatagramPacket(data,data.length, this.serverAddress);
                client.send(msg);
            }
        } catch (SocketException e) {} catch (IOException e) {}
    }
}
