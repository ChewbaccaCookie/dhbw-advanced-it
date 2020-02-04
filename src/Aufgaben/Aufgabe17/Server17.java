package Aufgaben.Aufgabe17;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class Server17 extends Thread{
    private DatagramSocket server;
    public Server17(int port) throws SocketException {
        this.server = new DatagramSocket(port);
    }
    public void run() {
        try {
            while (true) {
                byte[] dataArr = new byte[4096];
                DatagramPacket msg = new DatagramPacket(dataArr, dataArr.length);
                server.receive(msg);
                String data = new String(msg.getData());
                System.out.println(String.format("[%s]: %s", msg.getAddress(), data));
            }
        } catch (Exception ex) {}
    }

}
