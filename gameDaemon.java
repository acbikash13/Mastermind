import java.io.*;
import java.net.*;

public class gameDaemon {
    public static void main(String[] args) {
        ServerSocket server;
        Socket toClientSocket;
        try {
            server = new ServerSocket(42456);
            System.out.println("Waiting for connnection!!");
            while(true) {
                toClientSocket = server.accept();
                System.out.println("Request Received!");
                gameThread  gameTh = new gameThread(toClientSocket);
                gameTh.start();
            }
        }
        catch (IOException e) {
            System.out.println("Something went wrong with the ServerSocket creation");
        }
    }
}
