package Aufgabe16;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

enum ServerKeyWords {
    SAVE,
    GET,
}
enum ServerResponse {
    NOT_FOUND("Command not found!"),
    KEY("KEY"),
    FAILED("Execution failed"),
    OK("OK");

    private final String label;
    private ServerResponse(String st) {
        label = st;
    }
    @Override
    public String toString() {
        return this.label;
    }

}

public class Server16 {
    public final static int PORT = 7777;
    public final static String EXTENSION = ".txt";
    public static void main(String[] args)  {
        int port = PORT;


        try {
            ServerSocket server = new ServerSocket(port);
            while(true) {
                try {
                    Socket connection = server.accept();
                    PrintWriter out  = new PrintWriter(connection.getOutputStream());
                    BufferedReader in  = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    //Listening for messages and send a message back
                    while(true) {
                        var message = in.readLine();
                        var messageProps = message.split(" ");
                        if (messageProps != null && messageProps[0] != null && messageProps[1] != null) {
                            try {
                                ServerKeyWords keyWord = ServerKeyWords.valueOf(messageProps[0]);
                                switch (keyWord) {
                                    case SAVE:
                                        String id = saveFile(messageProps);
                                        out.println(ServerResponse.KEY + " " + id);
                                        break;

                                    case GET:
                                        readFile(messageProps[1], out);
                                        break;
                                }
                            } catch(Exception e) {
                                out.println(ServerResponse.NOT_FOUND);
                            }
                        } else {
                            out.println(ServerResponse.NOT_FOUND);
                        }
                        out.flush();
                    }
                } catch (Exception e) {}
            }
        }
        catch (Exception ex) {
        }
    }


    private static String saveFile(String[] messageProps) throws IOException {
        String[] contentArr = new String[messageProps.length -1];
        for(int i = 1; i < messageProps.length; i++) {
            contentArr[i-1] = messageProps[i];
        }

        String message = String.join(" ", Arrays.asList(contentArr));
        String id = java.util.UUID.randomUUID().toString();
        String fileName = "files/" + id + EXTENSION;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(message);
        writer.close();
        return id;
    }

    private static void readFile(String key, PrintWriter out) throws IOException {
        String fileName = "files/" + key + EXTENSION;
        File tmpDir = new File(fileName);
        if (tmpDir.exists()) {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));

            out.println(ServerResponse.OK + " "+ content);
        }else {
            out.println(ServerResponse.FAILED);
        }
    }
}
