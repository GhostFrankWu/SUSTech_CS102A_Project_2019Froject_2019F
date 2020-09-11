package xyz.chengzi.cs102a.chinesechess.chessboard;

import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;
import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chess.EmptySlotComponent;
import xyz.chengzi.cs102a.chinesechess.chess.ChariotChessComponent;
import xyz.chengzi.cs102a.chinesechess.listener.ChessListener;
import xyz.chengzi.cs102a.chinesechess.listener.ChessboardChessListener;
import xyz.chengzi.cs102a.chinesechess.chess.MoveMap;
import xyz.chengzi.cs102a.chinesechess.music.ChessGameMusicSmall;
import xyz.chengzi.cs102a.chinesechess.openingCase.ChessOperater;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class ChessboardComponent extends JComponent {
    private ArrayList<MoveMap> moveMap = new ArrayList<MoveMap>();
    private Map<Character, String> NAME = new HashMap<>();
    private Map<Integer, String> NUM = new HashMap<>();
    private ChessListener chessListener = new ChessboardChessListener(this);
    private ChessComponent[][] chessboard = new ChessComponent[10][9];
    private ChessColor currentColor = ChessColor.RED, colorBefore = ChessColor.NONE;//开始颜色

    private static boolean skill = true;
    private ChessComponent chess01, chess02;

    public static void setSkill(boolean f) {
        skill = f;
    }

    public static void setSkill0() {
        skill = !skill;
    }

    public ChessColor getColorBefore() {
        return colorBefore;
    }

    public void removeChess(int x, int y) {
        remove(chessboard[x][y]);
        putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(x, y), calculatePoint(x, y)));
    }

    public ChessboardComponent(int width, int height) {
        NAME.put('c', "俥");
        NAME.put('h', "马");
        NAME.put('n', "炮");
        NAME.put('s', "兵");
        NAME.put('e', "相");
        NAME.put(
                'a', "仕");
        NAME.put('g', "帥");
        NAME.put('C', "車");
        NAME.put('H', "馬");
        NAME.put(
                'N', "砲");
        NAME.put('S', "卒");
        NAME.put('E', "象");
        NAME.put('A', "士");
        NAME.put('G', "將");
        setLayout(null); // Use absolute layout.
        NUM.put(1, "一");
        NUM.put(2, "二");
        NUM.put(3, "三");
        NUM.put(4, "四");
        NUM.put(5, "五");
        NUM.put(6, "六");
        NUM.put(7, "七");
        NUM.put(8, "八");
        NUM.put(9, "九");
        setSize(width, height);
        ChessComponent.registerListener(chessListener);
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j)));
            }
        }
        allChessIni();//初始化棋子
    }

    public ChessComponent[][] getChessboard() {
        return chessboard;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessboard[row][col] != null) {
            remove(chessboard[row][col]);
        }
        add(chessboard[row][col] = chessComponent);
    }

    public void setCanMoveTo(int x, int y, boolean f) {
        chessboard[x][y].setCanMoveTo(f);
    }

    public void setCanMoved(int x, int y, boolean f) {
        chessboard[x][y].setMoved(f);
    }

    public void checkIfChecked() {
        boolean f = false;
        int b = 0, c = 0;
        for (int i = 0; i < 10; i++) {
            if (b == 1) break;
            for (int j = 0; j < 9; j++) {
                if (getKing('g', 'x') == -1 || getKing('G', 'x') == -1) {
                    JOptionPane.showMessageDialog(this, "游戏已经结束！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    b = 1;
                    currentColor=ChessColor.NONE;
                    break;
                }
                if (c == 4) c = 0;
                if (chessboard[i][j].canMoveTo(chessboard, chessboard[getKing('g', 'x')][getKing('g', 'y')].getChessboardPoint())) {
                    new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\Man_jiangjun.mp3", c);
                    c++;
                    ChessGameFrame.setChecked();
                    f = true;
                }
                if (chessboard[i][j].canMoveTo(chessboard, chessboard[getKing('G', 'x')][getKing('G', 'y')].getChessboardPoint())) {
                    new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\Woman_jiangjun.mp3", c);
                    //dialog("黑方被将军！");
                    c++;
                    ChessGameFrame.setChecked();
                    f = true;
                }
            }
        }
        if (!f) ChessGameFrame.setUnchecked();
        if (getKing('g', 'y') == getKing('G', 'y')) {
            int isNull = 1;
            for (int i = getKing('G', 'x') + 1; i < getKing('g', 'x'); i++) {
                if (chessboard[i][getKing('g', 'y')].getChessName() != '.')
                    isNull = 0;
            }
            if (isNull == 1) {
                if (currentColor == ChessColor.RED) {
                    ChessComponent chess1 = chessboard[getKing('g', 'x')][getKing('g', 'y')];
                    ChessComponent chess2 = chessboard[getKing('G', 'x')][getKing('G', 'y')];
                    swapChessComponents(chess1, chess2);
                } else if (currentColor == ChessColor.BLACK) {
                    ChessComponent chess1 = chessboard[getKing('g', 'x')][getKing('g', 'y')];
                    ChessComponent chess2 = chessboard[getKing('G', 'x')][getKing('G', 'y')];
                    swapChessComponents(chess2, chess1);
                }
            }
        }
    }

    public int getKing(char p, char xy) {////////////////////////////////////////////////////p是棋子名字，红黑将///xy就是找x还是y
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (chessboard[i][j].getChessName() == p && xy == 'x')
                    return i;
                else if (chessboard[i][j].getChessName() == p && xy == 'y')
                    return j;
            }
        }
        //setCurrentColor(ChessColor.NONE);
        return -1;
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////找棋子结束

    private int c = 0, row1 = 0, row2 = 0, col1 = 0, col2 = 0, totalRed = 0, totalBlack = 0;

    Timer timer2 = new Timer(10, null);

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        if (skill) {
            String rec = "";
            rec += NAME.get(chess1.getChessName());
            char chess2Name = chess2.getChessName();
            if (chess2Name != '.') {
                if (chess2Name > 95) {
                    totalRed++;
                    ChessGameFrame.text("======黑吃红" + NAME.get(chess2Name) + "，共计" + totalRed + "子");
                } else {
                    totalBlack++;
                    ChessGameFrame.text("======红吃黑" + NAME.get(chess2Name) + "，共计" + totalBlack + "子");
                }
            }
            if (currentColor == ChessColor.BLACK) {
                ChessGameFrame.text("----------------------------轮到红方行棋");
            } else {
                ChessGameFrame.text("----------------------------轮到黑方行棋");
            }
            moveMap.add(new MoveMap(chess1, chess2, currentColor));
            ///////////////////////////////////////////////////////
            chess01 = chess1;
            chess02 = chess2;
            ///////////////////////////////////////////////////////
            row1 = chess1.getChessboardPoint().getX();
            col1 = chess1.getChessboardPoint().getY();
            row2 = chess2.getChessboardPoint().getX();
            col2 = chess2.getChessboardPoint().getY();
            chess1.setVisible(false);
            AtomicInteger dx = new AtomicInteger(chess01.getX());
            AtomicInteger dy = new AtomicInteger(chess01.getY());
            ChessGameFrame.appearChess(dx.get(), dy.get(), (int) chess01.getChessName());
            int fx = chess02.getX(), fy = chess02.getY();
            Timer timer = new Timer(1, e -> {
                if (dx.get() != fx) {
                    if (dx.get() > fx)
                        dx.getAndDecrement();
                    else
                        dx.getAndIncrement();
                }
                if (dy.get() != fy) {
                    if (dy.get() > fy)
                        dy.getAndDecrement();
                    else
                        dy.getAndIncrement();
                }
                ChessGameFrame.moveChess(dx.get(), dy.get());
            });
            timer.start();
            timer2.addActionListener(e -> {
                if (dx.get() == fx && dy.get() == fy) {
                    if (c == 4) c = 0;
                    new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\go.mp3", c);
                    timer.stop();
                    ChessGameFrame.disappearChess();
                    chess01.swapLocation(chess02);
                    if (!(chess02 instanceof EmptySlotComponent)) {
                        remove(chess02);
                        add(chess02 = new EmptySlotComponent(chess02.getChessboardPoint(), chess02.getLocation()));
                    }
                    dy.getAndIncrement();
                    chessboard[row1][col1] = chess02;
                    chessboard[row2][col2] = chess01;
                    chess01.setVisible(true);
                    cancel();
                }
            });
            timer2.start();

            rec += NUM.get(col2 + 1);
            if ((row1 - row2) > 0) {
                rec += "进";
            } else if (row1 - row2 < 0) {
                rec += "退";
            } else {
                rec += "平";
            }
            rec += NUM.get(col1 + 1);
            ChessGameFrame.text(rec);

            c++;
            if (chess2Name == 'G') {//将被吃
                //dialog("红方胜利~");
                ChessGameFrame.setUnchecked();
                ChessGameFrame.text("\n=========================\n红方胜利~\n=========================\n");
                ChessGameFrame.setRed_win();
                if (ChessboardChessListener.getMulitply()) {
                    if (currentColor == ChessColor.RED || colorBefore == ChessColor.RED) {
                        new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\victory.mp3", 9);
                        if (ChessboardChessListener.isAi()) {
                            ChessOperater.findAndWrite(0);
                        } else {
                            ChessOperater.findAndWrite(1);
                        }
                    } else {
                        new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\defeat.mp3", c);
                        if (ChessboardChessListener.isAi()) {
                            ChessOperater.findAndWrite(1);
                        } else {
                            ChessOperater.findAndWrite(3);
                        }
                    }
                }
                currentColor = ChessColor.NONE;
            }
            if (chess2Name == 'g') {//帅被吃
                ChessGameFrame.setUnchecked();
                ChessGameFrame.text("\n=========================\n黑方胜利~\n=========================\n");
                //dialog("黑方胜利~");
                ChessGameFrame.setBlack_win();
                if (ChessboardChessListener.getMulitply()) {
                    if (currentColor == ChessColor.BLACK || colorBefore == ChessColor.BLACK) {
                        if (ChessboardChessListener.isAi()) {
                            ChessOperater.findAndWrite(0);
                        } else {
                            ChessOperater.findAndWrite(2);
                        }
                        new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\victory.mp3", 9);
                    } else {
                        new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\defeat.mp3", 9);
                        if (ChessboardChessListener.isAi()) {
                            ChessOperater.findAndWrite(1);
                        } else {
                            ChessOperater.findAndWrite(3);
                        }
                    }
                }
                currentColor = ChessColor.NONE;
            }
        } else {
            String rec = "";
            rec += NAME.get(chess1.getChessName());
            char chess2Name = chess2.getChessName();
            moveMap.add(new MoveMap(chess1, chess2, currentColor));
            if (!(chess2 instanceof EmptySlotComponent)) {
                remove(chess2);
                add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
            }
            chess1.swapLocation(chess2);
            int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
            chessboard[row1][col1] = chess1;
            int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
            chessboard[row2][col2] = chess2;
            if (c == 4) c = 0;
            new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\go.mp3", c);

            rec += NUM.get(col2 + 1);
            if ((row1 - row2) > 0) {
                rec += "进";
            } else if (row1 - row2 < 0) {
                rec += "退";
            } else {
                rec += "平";
            }
            rec += NUM.get(col1 + 1);
            ChessGameFrame.text(rec);

            c++;
            if (chess2Name == 'G') {//将被吃
                //dialog("红方胜利~");
                ChessGameFrame.setUnchecked();
                ChessGameFrame.text("\n=========================\n红方胜利~\n=========================\n");
                ChessGameFrame.setRed_win();
                if (ChessboardChessListener.getMulitply()) {
                    if (currentColor == ChessColor.RED || colorBefore == ChessColor.RED) {
                        new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\victory.mp3", 9);
                        if (ChessboardChessListener.isAi()) {
                            ChessOperater.findAndWrite(0);
                        } else {
                            ChessOperater.findAndWrite(1);
                        }
                    } else {
                        new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\defeat.mp3", c);
                        if (ChessboardChessListener.isAi()) {
                            ChessOperater.findAndWrite(1);
                        } else {
                            ChessOperater.findAndWrite(3);
                        }
                    }
                }
                currentColor = ChessColor.NONE;
            }
            if (chess2Name == 'g') {//帅被吃
                ChessGameFrame.setUnchecked();
                ChessGameFrame.text("\n=========================\n黑方胜利~\n=========================\n");
                //dialog("黑方胜利~");
                ChessGameFrame.setBlack_win();
                if (ChessboardChessListener.getMulitply()) {
                    if (currentColor == ChessColor.BLACK || colorBefore == ChessColor.BLACK) {
                        if (ChessboardChessListener.isAi()) {
                            ChessOperater.findAndWrite(0);
                        } else {
                            ChessOperater.findAndWrite(2);
                        }
                        new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\victory.mp3", 9);
                    } else {
                        new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\defeat.mp3", 9);
                        if (ChessboardChessListener.isAi()) {
                            ChessOperater.findAndWrite(1);
                        } else {
                            ChessOperater.findAndWrite(3);
                        }
                    }
                }
                currentColor = ChessColor.NONE;
            }
        }
    }

    private void dialog(String msg) {/////////////////////////////////////////////////子线程 弹个对话框，标题默认，内容为传入的参数
        Thread t = new Thread(() ->
                JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE));
        t.start();
    }//////////////////////////////////////////////////////////////////////////////////////////////////

    private void dialogT(String msg, String tit) {/////////////////////////////////////////////////弹个对话框，标题\内容为传入的参数
        Thread t = new Thread(() ->
                JOptionPane.showMessageDialog(null, msg, tit, JOptionPane.INFORMATION_MESSAGE));
        t.start();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////对话框线程结束

    public void swapColor(boolean f) {
        if (!f) {
            currentColor = currentColor == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK;
        } else {
            if (currentColor != ChessColor.NONE) {
                colorBefore = currentColor;
                currentColor = ChessColor.NONE;
            } else {
                currentColor = colorBefore == ChessColor.BLACK ? ChessColor.BLACK : ChessColor.RED;
            }
        }
    }

    private void initChessBoard(int row, int col, ChessColor color, char chessName) {
        ChessComponent chessComponent = new ChariotChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color, chessName);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    ////////////////////////////////////////////////////////////////////////////初始化全部棋子
    private void allChessIni() {
        // create the original chess board
        for (int i = 0; i <= 8; i++) {
            if (i % 2 != 0) continue;
            initChessBoard(3, i, ChessColor.BLACK, 'S');
        }
        for (int i = 0; i <= 8; i++) {
            if (i % 2 != 0) continue;
            initChessBoard(6, i, ChessColor.RED, 's');
        }
        initChessBoard(0, 1, ChessColor.BLACK, 'H');
        initChessBoard(9, 1, ChessColor.RED, 'h');
        initChessBoard(0, 7, ChessColor.BLACK, 'H');
        initChessBoard(9, 7, ChessColor.RED, 'h');
        initChessBoard(0, 0, ChessColor.BLACK, 'C');
        initChessBoard(0, 8, ChessColor.BLACK, 'C');
        initChessBoard(9, 0, ChessColor.RED, 'c');
        initChessBoard(9, 8, ChessColor.RED, 'c');
        initChessBoard(0, 2, ChessColor.BLACK, 'E');
        initChessBoard(9, 2, ChessColor.RED, 'e');
        initChessBoard(0, 6, ChessColor.BLACK, 'E');
        initChessBoard(9, 6, ChessColor.RED, 'e');
        initChessBoard(0, 3, ChessColor.BLACK, 'A');
        initChessBoard(9, 3, ChessColor.RED, 'a');
        initChessBoard(0, 5, ChessColor.BLACK, 'A');
        initChessBoard(9, 5, ChessColor.RED, 'a');
        initChessBoard(0, 4, ChessColor.BLACK, 'G');
        initChessBoard(9, 4, ChessColor.RED, 'g');
        initChessBoard(2, 1, ChessColor.BLACK, 'N');
        initChessBoard(7, 1, ChessColor.RED, 'n');
        initChessBoard(2, 7, ChessColor.BLACK, 'N');
        initChessBoard(7, 7, ChessColor.RED, 'n');
        initChessBoard(0, 2, ChessColor.BLACK, 'E');
        initChessBoard(9, 2, ChessColor.RED, 'e');
        initChessBoard(0, 6, ChessColor.BLACK, 'E');
        initChessBoard(9, 6, ChessColor.RED, 'e');
    }/////////////////////////////////////////////////////////////////////////////////结束

    private Point calculatePoint(int row, int col) {
        return new Point(col * getWidth() / 9, row * getHeight() / 10);
    }

    public void restart() {
        for (int i = 0; i < 10; i++) {
            setVisible(false);
            for (int j = 0; j < 9; j++) {
                removeChess(i, j);
            }
        }
        allChessIni();
        currentColor = ChessColor.RED;
        setVisible(true);
    }

    public void restart2() {
        for (int i = 0; i < 10; i++) {
            setVisible(false);
            for (int j = 0; j < 9; j++) {
                removeChess(i, j);
            }
        }
        currentColor = ChessColor.RED;
        setVisible(true);
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void initChess(int row, int col, ChessColor color, char chessName) {
        initChessBoard(row, col, color, chessName);
    }

    public void regret() {
        setVisible(false);
        MoveMap move = moveMap.get(moveMap.size() - 1);
        currentColor = move.getMoveColor();
        if (move.getMoveColor() == ChessColor.BLACK) {
            int fromRow = move.getFromRow() - 1;
            int fromCol = move.getFromCol() - 1;
            int toRow = move.getToRow() - 1;
            int toCol = move.getToCol() - 1;
            ChessComponent fromChess = move.getFromChess(), toChess = move.getToChess();
            removeChess(fromRow, fromCol);
            removeChess(toRow, toCol);
            if (fromChess.getChessName() != '.') {
                initChess(fromRow, fromCol, fromChess.getChessColor(), fromChess.getChessName());
            }
            if (toChess.getChessName() != '.') {
                initChess(toRow, toCol, toChess.getChessColor(), toChess.getChessName());
            }
        } else {
            int fromRow = 10 - move.getFromRow();
            int fromCol = 9 - move.getFromCol();
            int toRow = 10 - move.getToRow();
            int toCol = 9 - move.getToCol();
            ChessComponent fromChess = move.getFromChess(), toChess = move.getToChess();
            removeChess(fromRow, fromCol);
            removeChess(toRow, toCol);
            if (fromChess.getChessName() != '.') {
                initChess(fromRow, fromCol, fromChess.getChessColor(), fromChess.getChessName());
            }
            if (toChess.getChessName() != '.') {
                initChess(toRow, toCol, toChess.getChessColor(), toChess.getChessName());
            }
        }
        moveMap.remove(moveMap.size() - 1);
        setVisible(true);
    }

    private void cancel() {
        timer2.stop();
    }

    public ArrayList<MoveMap> getMoveMap() {
        return moveMap;
    }
}
