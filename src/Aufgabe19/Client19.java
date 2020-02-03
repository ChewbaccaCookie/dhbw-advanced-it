package Aufgabe19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Client19 extends Thread {
    private InetSocketAddress serverAddress;
    private DatagramSocket client;
    private Semaphore hasText = new Semaphore(0);
    private byte[] bytes;
    private final int id;

    public Client19(int id, String address, int port) {
        this.id = id + 1;
        this.serverAddress = new InetSocketAddress(address, port);
        try {
            this.client = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void setText(byte[] bytes) {
        this.bytes = bytes;
        hasText.release();
    }

    @Override
    public void run() {
        while (true) {
            try {
                client.setSoTimeout(1000);
                hasText.acquire();
                DatagramPacket msg = new DatagramPacket(bytes, bytes.length, this.serverAddress);
                System.out.println("[CLIENT: " + id + "]: Request send");
                client.send(msg);
                byte[] dataArr = new byte[4096];
                DatagramPacket response = new DatagramPacket(dataArr, dataArr.length);
                client.receive(response);
                System.out.println("[CLIENT: " + id + "]: " + new String(response.getData(), 0, response.getLength()));
            } catch (SocketTimeoutException e) {
                System.out.println("[CLIENT: " + id + "]: Request timeout");
            } catch (SocketException e) {
            } catch (IOException e) {
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        final int CLIENT_NUM = 25;
        ArrayList<Client19> clients = new ArrayList<>();
        for (int i = 0; i < CLIENT_NUM; i++) {
            Client19 client = new Client19(i, "localhost", 7777);
            client.start();
            clients.add(client);
        }
        System.out.println(CLIENT_NUM + " Clients created");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String str = br.readLine();
            int i = 0;
            for (Client19 client : clients) {
                byte[] bytes = str.getBytes();
                client.setText(bytes);
                i++;
            }
        }
    }
}
