package Aufgaben.Aufgabe18;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

enum ServerKeyWords {
    READ,
    WRITE,
}
enum Responses {
    NOT_FOUND("Command not found!"),
    FILE_NOT_FOUND("File cannot be found!"),
    LINE_NOT_FOUND("Line not found"),
    FAILED("Execution failed"),
    DATA_MISSING("Data is missing"),
    ERROR("ERROR"),
    OK("OK");

    private final String label;
    private Responses(String st) {
        label = st;
    }
    @Override
    public String toString() {
        return this.label;
    }

}


public class Server18 extends Thread{
    private DatagramSocket server;
    private final String EXTENSION = ".txt";

    public Server18(int port) throws SocketException {
        this.server = new DatagramSocket(port);
    }
    public void run() {
        try {
            while (true) {
                byte[] dataArr = new byte[4096];
                DatagramPacket msg = new DatagramPacket(dataArr, dataArr.length);
                server.receive(msg);
                String data = new String(msg.getData(), 0,msg.getLength());
                String[] messageProps = data.split("\\s|,");
                for(int i = 4; i < messageProps.length;i++) { messageProps[3] += " " + messageProps[i]; }
                String response = this.handleCommand(messageProps).toString();
                DatagramPacket responsePackage = new DatagramPacket(response.getBytes(), response.getBytes().length,msg.getAddress(), msg.getPort());
                server.send(responsePackage);
            }
        } catch (Exception ex) {}
    }

    private String handleCommand(String[] mP) {
        if (mP != null && mP.length >= 3 && mP[0] != null && mP[1] != null && mP[2] != null) {
            try {
                ServerKeyWords keyWord = ServerKeyWords.valueOf(mP[0]);
                switch (keyWord) {
                    case READ:
                        return this.readFile(mP[1], Integer.parseInt(mP[2]));

                    case WRITE:
                        if (mP[3] != null) {
                            return this.saveFile(mP[1], Integer.parseInt(mP[2]), mP[3]);
                        } else {
                            return Responses.DATA_MISSING.toString();
                        }

                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
                return Responses.ERROR.toString();
            }
        } else {
            return Responses.NOT_FOUND.toString();
        }
        return Responses.FAILED.toString();
    }


    private String saveFile(String fileName, int line, String data) throws IOException {
        String path = "files/" + fileName + EXTENSION;
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String l = "";
        ArrayList<String> lines = new ArrayList<>();
        while ((l = reader.readLine()) != null) {
            lines.add(l);
        }
        while (lines.size() <= line) {
            lines.add("");
        }
        lines.set(line, data);
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for(String ll : lines) {
            writer.write(ll);
            writer.newLine();
            writer.flush();
        }
        writer.close();
        return Responses.OK.toString();
    }

    private String readFile(String fileName, int line) throws IOException {
        String path = "files/" + fileName + EXTENSION;
        File file = new File(path);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String currentLine = reader.readLine();
            int i = 0;
            while (currentLine != null) {
                if (i == line) {
                    break;
                }
                i++;
                currentLine = reader.readLine();
            }
            reader.close();
            if (currentLine != null) {
                return currentLine;
            } else {
                return Responses.LINE_NOT_FOUND.toString();
            }

        }else {
            return Responses.FILE_NOT_FOUND.toString();
        }
    }

}
