package xyz.chengzi.cs102a.chinesechess.socket;

import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;
import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.listener.ChessboardChessListener;
import xyz.chengzi.cs102a.chinesechess.music.ChessGameMusicSmall;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.net.InetAddress;


public class Socket_Special {
/*
    public static void main(String[] args){
        String IP="",A="",B="",C="";
        IP="10.17.42.220";
        A=Wow(IP);
        B=calculate(A);
        C=reverse(B);
        System.out.print(C);
    }
*/
    private static String Wow(String ip) {
        String[] parts = ip.split("\\.");
        String re = "";

        for (int i = 0; i < parts.length; i++) {
            while (parts[i].length() < 3) {
                parts[i] = "0" + parts[i];
            }
            re = re + parts[i];
            if (i != parts.length - 1) {
                re = re + '.';
            }
        }
        return re;
    }

    public static String GetIpAddress() {
        String str = "";
        try {
            InetAddress res = InetAddress.getLocalHost();
            str = res.getHostAddress();
        } catch (Exception e) {
            return "010.017.002.217";
        }
        str = Wow(str);
        //return "010.017.002.217";
        return str;
    }

    public static String calculate(String ip) {
        char[] list = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};
        Map<Integer, Character> map0 = new HashMap<>();
        for (int i = 0; i <= 25; i++) {
            map0.put(i, list[i]);
        }
        int[] charList = {((ip.charAt(0) - 48) * 10 + (ip.charAt(1) - 48)), (ip.charAt(2) - 48),
                ((ip.charAt(4) - 48) * 10 + (ip.charAt(5) - 48)), (ip.charAt(6) - 48),
                ((ip.charAt(8) - 48) * 10 + (ip.charAt(9) - 48)), (ip.charAt(10) - 48),
                ((ip.charAt(12) - 48) * 10 + (ip.charAt(13) - 48)), (ip.charAt(14) - 48)
        };
        String str = "";
        for (int i = 0; i < 8; i++) {
            str += map0.get(charList[i]);
        }
        return str;
    }

    public static String reverse(String input) {
        char[] list = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};
        Map<Character, Integer> map1 = new HashMap<>();
        for (int i = 0; i <= 25; i++) {
            map1.put(list[i], i);
        }
        String output = "";
        for (int i = 0; i < 8; i++) {
            if ((i == 0 || i == 2 || i == 4 || i == 6) && (input.charAt(i) == 'A'))
                continue;
            output += map1.get(input.charAt(i));
            if (i == 1 || i == 3 || i == 5)
                output += ".";
        }
        return (output);
    }
}
