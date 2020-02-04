package Aufgaben.Server16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client16 {
    public static void main(String[] args) throws IOException {
        String hostname = "localhost";
        PrintWriter networkOut = null;
        BufferedReader networkIn = null;
        Socket s = null;
         try {
             s = new Socket(hostname, Server16.PORT);
             System.out.println("Connected to server");
             networkIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
             networkOut = new PrintWriter(s.getOutputStream());

             while(true) {
                 String theLine = userIn.readLine();
                 if (theLine.equals(".")) break;
                 networkOut.println(theLine);
                 networkOut.flush();
                 System.out.println(networkIn.readLine());
             }
         } finally {
             if (s != null) s.close();
         }
    }
}
