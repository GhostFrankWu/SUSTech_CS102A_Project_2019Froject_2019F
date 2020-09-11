package xyz.chengzi.cs102a.chinesechess.socket;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;




public class Server {
    public void Server(String output, int s) {
        ServerSocket serverSocket = null;
        PrintWriter out = null;
        Socket clientsSocket = null;
        try {
            serverSocket = new ServerSocket(37510 + s);
            clientsSocket = serverSocket.accept();
            out = new PrintWriter(clientsSocket.getOutputStream(), true);
            out.println(output);
            clientsSocket.close();
            serverSocket.close();
        } catch (Exception ignored) {
        }
    }
}