package xyz.chengzi.cs102a.chinesechess.socket;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private static
    String output = "FFFFFFFF";

    public String receive(String host, int s) {
        Socket helloSocket = null;
        BufferedReader in = null;
        try {
            helloSocket = new Socket(host, 37510 + s);            // 得到内容
            in = new BufferedReader(new InputStreamReader(helloSocket.getInputStream()));
            output = in.readLine();
            in.close();
            helloSocket.close();
        } catch (Exception e) {
            output = "FFFFFFFF";
        }
        return output;
    }
}