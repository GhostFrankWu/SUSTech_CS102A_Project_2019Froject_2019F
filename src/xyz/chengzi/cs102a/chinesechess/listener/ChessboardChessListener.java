package xyz.chengzi.cs102a.chinesechess.listener;

import xyz.chengzi.cs102a.chinesechess.AI.ComputerMove;
import xyz.chengzi.cs102a.chinesechess.AI.MoveCount;
import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;
import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;
import xyz.chengzi.cs102a.chinesechess.socket.Client;
import xyz.chengzi.cs102a.chinesechess.socket.Server;

public class ChessboardChessListener extends ChessListener {
    private ChessboardComponent chessboardComponent;
    private ChessComponent first;
    private static boolean mulitply = false, ai = false;
    private String str = "0011", str1 = "";
    private static int s = 0;

    public static int getS() {
        return s;
    }

    public static boolean getMulitply() {
        return mulitply;
    }

    public static void setS(int s1) {
        s = s1;
    }

    public ChessboardChessListener(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }


    public static void setMulitply(boolean mulitply1) {
        mulitply = mulitply1;
    }

    public static boolean isAi() {
        return ai;
    }

    public static void setAi(boolean ai) {
        ChessboardChessListener.ai = ai;
    }

    public void onClick(ChessComponent chessComponent) throws InterruptedException {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                if (mulitply) {
                    str1 = "MO" + chessComponent.getChessboardPoint().getX() + "" + chessComponent.getChessboardPoint().getY() + "";
                    Thread P2 = new Processor2();
                    P2.start();
                }
                chessComponent.setSelected(true);
                first = chessComponent;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 9; j++) {
                        ChessboardPoint cp = new ChessboardPoint(i, j);
                        if (first.canMoveTo(chessboardComponent.getChessboard(), cp)) {
                            chessboardComponent.setCanMoveTo(i, j, true);
                        }
                    }
                }
                chessboardComponent.repaint();
            }
        } else {
            if (first == chessComponent) { // Double-click to unselect.
                chessComponent.setSelected(false);
                first = null;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 9; j++) {
                        chessboardComponent.setCanMoveTo(i, j, false);
                    }
                }
                chessboardComponent.repaint();
            } else if (handleSecond(chessComponent)) {
                chessComponent.setMoved(true);
                int x0 = 0, y0 = 0, x1 = 0, y1 = 0;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (first == chessboardComponent.getChessboard()[i][j]) {
                            x0 = i;
                            y0 = j;
                        }
                        if (chessComponent == chessboardComponent.getChessboard()[i][j]) {
                            x1 = i;
                            y1 = j;
                        }
                    }
                }
                if (mulitply) {
                    str = x0 + "" + y0 + "" + x1 + "" + y1 + "";
                    Thread P1 = new Processor();
                    P1.start();
                }
                chessboardComponent.swapChessComponents(first, chessComponent);
                chessboardComponent.swapColor(mulitply);
                chessboardComponent.checkIfChecked();
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 9; j++) {
                        chessboardComponent.setCanMoveTo(i, j, false);
                        if (i != x0 || j != y0)
                            chessboardComponent.setCanMoved(i, j, false);
                    }
                }
                first.setMoved(true);
                first.setSelected(false);
                first = null;
                chessboardComponent.repaint();
                if (mulitply) {
                    Thread P1 = new Processor1();
                    P1.start();
                }
                if (ai) {
                    chessboardComponent.swapColor(true);
                    MoveCount asda = new ComputerMove(chessboardComponent).
                            BlackCalculateMove(new ComputerMove(chessboardComponent), chessboardComponent);
                    chessboardComponent.swapChessComponents(
                            chessboardComponent.getChessboard()[asda.getFromRow()][asda.getFromCol()],
                            chessboardComponent.getChessboard()[asda.getToRow()][asda.getToCol()]);
                    chessboardComponent.setCurrentColor(ChessColor.RED);
                }
            }
        }
    }

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboardComponent.getCurrentColor();
    }

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboardComponent.getCurrentColor() &&
                first.canMoveTo(chessboardComponent.getChessboard(), chessComponent.getChessboardPoint());
    }

    class Processor extends Thread {
        public void run() {
            new Server().Server(str, s == 1 ? 1 : 0);
        }
    }

    class Processor2 extends Thread {
        public void run() {
            new Server().Server(str1, s != 0 ? 20 : 21);
        }
    }

    class Processor1 extends Thread {
        public void run() {
            Client cil = new Client();
            String back = "FFFFFFFF";
            while (back.equals("FFFFFFFF"))
                back = cil.receive(ChessGameFrame.getHost(), s == 1 ? 0 : 1);
            System.out.println(back);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    chessboardComponent.getChessboard()[i][j].setMoved(false);
                    chessboardComponent.getChessboard()[i][j].setCanMoveTo(false);
                    chessboardComponent.getChessboard()[i][j].setSelected(false);
                }
            }
            chessboardComponent.swapChessComponents(
                    chessboardComponent.getChessboard()[back.charAt(0) - 48][back.charAt(1) - 48],
                    chessboardComponent.getChessboard()[back.charAt(2) - 48][back.charAt(3) - 48]);
            chessboardComponent.getChessboard()[back.charAt(0) - 48][back.charAt(1) - 48].setMoved(true);
            chessboardComponent.getChessboard()[back.charAt(2) - 48][back.charAt(3) - 48].setMoved(true);
            chessboardComponent.swapColor(true);
            chessboardComponent.checkIfChecked();
            chessboardComponent.repaint();
        }
    }
}
