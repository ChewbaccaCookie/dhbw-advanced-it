package Aufgaben.Aufgabe19;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

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


public class Server19 extends Thread{
    private DatagramSocket server;
    private final String EXTENSION = ".txt";
    private static HashMap<String, Semaphore> writer = new HashMap<>();

    private final int id;
    public Server19(int id,DatagramSocket socket) {
        this.id = id + 1;
        this.server = socket;
    }

    public void run() {
        try {
            while(true) {
                DatagramPacket dataPackage = Aufgabe19.monitor.getJob(id);
                String data = new String(dataPackage.getData(), 0, dataPackage.getLength());
                System.out.println(String.format("Request '%s' will be handled by thread %s", data, id));
                String[] messageProps = data.split("\\s|,");
                for (int i = 4; i < messageProps.length; i++) {
                    messageProps[3] += " " + messageProps[i];
                }

                String response = this.handleCommand(messageProps).toString();
                Thread.sleep(100);
                DatagramPacket responsePackage = new DatagramPacket(response.getBytes(), response.getBytes().length, dataPackage.getAddress(), dataPackage.getPort());
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

    private void acquireFile(String filename) throws InterruptedException {
        Semaphore waiter = writer.get(filename);
        if (waiter == null) {
            writer.put(filename, new Semaphore(0));
        } else {
            waiter.acquire();
        }
    }
    private void releaseFile(String filename) throws InterruptedException {
        Semaphore waiter = writer.get(filename);
        waiter.release();
    }

    private String saveFile(String fileName, int line, String data) throws IOException, InterruptedException {
        this.acquireFile(fileName);
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
        this.releaseFile(fileName);
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
