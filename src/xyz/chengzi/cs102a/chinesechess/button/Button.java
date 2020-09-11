package xyz.chengzi.cs102a.chinesechess.button;

import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;
import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chess.MoveMap;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.*;

import static java.lang.Thread.sleep;

public class Button { ///////////////:按钮加弹窗
    private JFileChooser jFileChooser = new JFileChooser(new File("."));
    private long sl = 3000;

    public void SaveChessMap(ChessboardComponent chessboard) throws IOException {
        File file = new File(ChessGameFrame.findAnd()+".chessboard");
        FileOutputStream fs = new FileOutputStream(file);
        BufferedOutputStream bo = new BufferedOutputStream(fs);
        File file2 = new File("save.chessboard");
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file2.length());
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file2));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        bo.write(bos.toByteArray());
        bo.close();
        try {
            int status = jFileChooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                File saveFile = jFileChooser.getSelectedFile();
                FileOutputStream fos = new FileOutputStream(saveFile);
                OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osr);

                if (chessboard.getCurrentColor() == ChessColor.RED) {
                    bw.write("@LAST_MOVER=" + ChessColor.BLACK + "\n");
                } else {
                    bw.write("@LAST_MOVER=" + ChessColor.RED + "\n");
                }
                bw.write("@@\n");
                for (int i = 0; i < 10; i++) {
                    bw.write("\n");
                    if (i == 5) {
                        for (int j = 0; j < 9; j++) {
                            bw.write('-');
                        }
                        bw.write("\n");
                    }
                    for (int j = 0; j < 9; j++) {
                        bw.write(chessboard.getChessboard()[i][j].getChessName());
                    }
                }
                bw.flush();
                bw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void LoadChessMap(ChessboardComponent chessboard) {
        try {
            int status = jFileChooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                File loadMapFile = jFileChooser.getSelectedFile();
                FileInputStream fis = new FileInputStream(loadMapFile);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int[] chessSum = new int[16];//k=0==king, k=1==兵卒 ,black=2k,red=2k+1
                ChessboardComponent loadChessBoard = new ChessboardComponent(700, 700);
                boolean flag = false;//楚河汉界
                boolean startFlag = false;
                boolean space = true;
                boolean chessFlag = true;//棋子数
                boolean chessMapFlag = true;//长宽
                boolean invalidChess = false;
                boolean change = false;//是否成功
                int col = 0;
                int line = 0;
                loadChessBoard.setCurrentColor(ChessColor.NONE);

                while (true) {
                    String str = br.readLine();
                    line++;
                    if (null == str) {
                        break;
                    }//读完

                    if (loadChessBoard.getCurrentColor() == ChessColor.NONE) {//颜色
                        if (!str.contains("BLACK")) {
                            if (!str.contains("RED")) {
                                JOptionPane.showMessageDialog(null, "Color Missing", "Invalid", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                            loadChessBoard.setCurrentColor(ChessColor.BLACK);
                        } else {
                            loadChessBoard.setCurrentColor(ChessColor.RED);
                        }
                    }

                    if (str.contains("@@")) {//主体读入
                        String spaceTemp = br.readLine();
                        line++;
                        startFlag = true;
                        continue;
                    }

                    while (startFlag) {//棋盘读入
                        if (!chessFlag || !chessMapFlag) {
                            break;
                        }
                        if (str == null) {
                            break;
                        }

                        //楚河汉界
                        if (str.regionMatches(0, "---------", 0, 9)) {
                            flag = true;
                            str = br.readLine();
                            line++;
                            continue;
                        }

                        if (str.length() < 9) {
                            chessMapFlag = false;
                            JOptionPane.showMessageDialog(null, "Invalid Dimension At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        for (int i = 0; i < 9; i++) {
                            if (str.charAt(i) == ' ') {
                                space = false;
                                JOptionPane.showMessageDialog(null, "Space Missing At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                            if (str.charAt(i) == '.') {
                                loadChessBoard.removeChess(col, i);
                                continue;
                            }
                            if (str.charAt(i) == 'G') {
                                loadChessBoard.initChess(col, i, ChessColor.BLACK, str.charAt(i));
                                chessSum[0]++;
                                if (chessSum[0] > 1) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            if (str.charAt(i) == 'g') {
                                loadChessBoard.initChess(col, i, ChessColor.RED, str.charAt(i));
                                chessSum[1]++;
                                if (chessSum[1] > 1) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            //
                            if (str.charAt(i) == 'A') {
                                loadChessBoard.initChess(col, i, ChessColor.BLACK, str.charAt(i));
                                chessSum[2]++;
                                if (chessSum[2] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            if (str.charAt(i) == 'a') {
                                loadChessBoard.initChess(col, i, ChessColor.RED, str.charAt(i));
                                chessSum[3]++;
                                if (chessSum[3] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            //
                            if (str.charAt(i) == 'E') {
                                loadChessBoard.initChess(col, i, ChessColor.BLACK, str.charAt(i));
                                chessSum[4]++;
                                if (chessSum[4] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            if (str.charAt(i) == 'e') {
                                loadChessBoard.initChess(col, i, ChessColor.RED, str.charAt(i));
                                chessSum[5]++;
                                if (chessSum[5] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            //最后一个加else判断错误字符orz
                            if (str.charAt(i) == 'H') {
                                loadChessBoard.initChess(col, i, ChessColor.BLACK, str.charAt(i));
                                chessSum[6]++;
                                if (chessSum[6] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            if (str.charAt(i) == 'h') {
                                loadChessBoard.initChess(col, i, ChessColor.RED, str.charAt(i));
                                chessSum[7]++;
                                if (chessSum[7] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            //最后一个加else判断错误字符orz
                            if (str.charAt(i) == 'C') {
                                loadChessBoard.initChess(col, i, ChessColor.BLACK, str.charAt(i));
                                chessSum[8]++;
                                if (chessSum[8] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            if (str.charAt(i) == 'c') {
                                loadChessBoard.initChess(col, i, ChessColor.RED, str.charAt(i));
                                chessSum[9]++;
                                if (chessSum[9] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            //最后一个加else判断错误字符orz
                            if (str.charAt(i) == 'N') {
                                loadChessBoard.initChess(col, i, ChessColor.BLACK, str.charAt(i));
                                chessSum[10]++;
                                if (chessSum[10] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            if (str.charAt(i) == 'n') {
                                loadChessBoard.initChess(col, i, ChessColor.RED, str.charAt(i));
                                chessSum[11]++;
                                if (chessSum[11] > 2) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            //最后一个加else判断错误字符orz
                            if (str.charAt(i) == 'S') {
                                loadChessBoard.initChess(col, i, ChessColor.BLACK, str.charAt(i));
                                chessSum[12]++;
                                if (chessSum[12] > 5) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            if (str.charAt(i) == 's') {
                                loadChessBoard.initChess(col, i, ChessColor.RED, str.charAt(i));
                                chessSum[13]++;
                                if (chessSum[13] > 5) {
                                    chessFlag = false;
                                    JOptionPane.showMessageDialog(null, "Invalid Chess Amount At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                continue;
                            }
                            //
                            JOptionPane.showMessageDialog(null, "Unknown Chess At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                            invalidChess = true;
                            break;
                        }
                        if (invalidChess) {
                            break;
                        }
                        //注释与棋盘判断
                        if (str.length() > 9) {
                            if (str.charAt(9) != '#') {
                                chessMapFlag = false;
                                JOptionPane.showMessageDialog(null, "Invalid Dimension At Line" + line, "Invalid", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                        col++;
                        line++;
                        str = br.readLine();
                    }
                }
                //行数
                if (col != 10) {
                    chessMapFlag = false;
                    JOptionPane.showMessageDialog(null, "Invalid Dimension", "Invalid", JOptionPane.ERROR_MESSAGE);
                }

                if (!flag && chessMapFlag) {
                    JOptionPane.showMessageDialog(null, "River Missing", "Invalid", JOptionPane.ERROR_MESSAGE);
                }
                if (!space && flag && chessMapFlag) {
                    JOptionPane.showMessageDialog(null, "Space Missing", "Invalid", JOptionPane.ERROR_MESSAGE);
                }

                if (chessFlag && chessMapFlag) {
                    if (loadChessBoard.getCurrentColor() != ChessColor.NONE) {
                        if (flag && space && chessFlag && chessMapFlag) {
                            chessboard.setVisible(false);
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 9; j++) {
                                    ChessComponent chess = loadChessBoard.getChessboard()[i][j];
                                    chessboard.removeChess(i, j);
                                    if (chess.getChessName() != '.') {
                                        chessboard.initChess(i, j, chess.getChessColor(), chess.getChessName());
                                    }
                                    chessboard.setCurrentColor(loadChessBoard.getCurrentColor());
                                }
                            }
                            chessboard.setVisible(true);
                            change = true;
                        }
                    }
                }
                br.close();
                if (change) {
                    if (chessSum[0] == 0 && chessSum[1] == 0) {
                        JOptionPane.showMessageDialog(null, "Invalid Chess Amount", "Invalid", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (chessSum[0] == 0) {
                            JOptionPane.showMessageDialog(null, "红方胜利", "Invalid", JOptionPane.ERROR_MESSAGE);
                        }
                        if (chessSum[1] == 0) {
                            JOptionPane.showMessageDialog(null, "黑方胜利", "Invalid", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadMoveSeq(ChessboardComponent chessboard) {
        ChessboardComponent.setSkill(false);
        try {
            int status = jFileChooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                File loadSeqFile = jFileChooser.getSelectedFile();
                FileInputStream fis = new FileInputStream(loadSeqFile);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int total = 0;
                boolean startFlag = false;

                while (true) {
                    String str = br.readLine();
                    if (null == str) {
                        break;
                    }

                    if (str.contains("@TOTAL_STEP=")) {//步数
                        total = Integer.parseInt(str.substring(str.indexOf('=') + 1));
                        continue;
                    }

                    if (str.contains("@@")) {
                        startFlag = true;
                        continue;
                    }

                    if (startFlag) {
                        for (int i = 1; i <= total; i++) {
                            str = br.readLine();
                            int fromRow, fromCol, toRow, toCol;
                            fromCol = str.charAt(0) - '0';
                            fromRow = str.charAt(2) - '0';
                            toCol = str.charAt(4) - '0';
                            toRow = str.charAt(6) - '0';
                            if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                                fromRow--;
                                fromCol--;
                                toRow--;
                                toCol--;
                                //判断越界
                                boolean outMap = true;
                                if (fromRow > -1 && fromRow < 10 && fromCol > -1 && fromCol < 9) {
                                    if (toRow > -1 && toRow < 10 && toCol > -1 && toCol < 9) {
                                        outMap = false;
                                    }
                                }
                                if (outMap) {
                                    ChessGameFrame.text("Position Out Of Range At Line" + i);
                                    continue;
                                }

                                //起始棋子
                                if (chessboard.getChessboard()[fromRow][fromCol].getChessColor() != ChessColor.BLACK ||
                                        chessboard.getChessboard()[fromRow][fromCol].getChessName() == '.') {
                                    ChessGameFrame.text("Invalid From Position At Line" + i);
                                    continue;
                                }

                                //目标棋子
                                if (chessboard.getChessboard()[toRow][toCol].getChessColor() == ChessColor.BLACK) {
                                    ChessGameFrame.text("Invalid To Position At Line" + i);
                                    continue;
                                }

                                //move
                                if (chessboard.getChessboard()[fromRow][fromCol].canMoveTo(chessboard.getChessboard(), new ChessboardPoint(toRow, toCol))) {
                                    chessboard.setVisible(false);
                                    chessboard.swapChessComponents(chessboard.getChessboard()[fromRow][fromCol], chessboard.getChessboard()[toRow][toCol]);
                                    chessboard.setVisible(true);
                                    chessboard.setCurrentColor(ChessColor.RED);
                                } else {
                                    ChessGameFrame.text("Invalid Move Pattern At Line" + i);
                                }
                            } else {
                                fromRow = 10 - fromRow;
                                fromCol = 9 - fromCol;
                                toRow = 10 - toRow;
                                toCol = 9 - toCol;
                                //判断越界
                                boolean outMap = true;
                                if (fromRow > -1 && fromRow < 10 && fromCol > -1 && fromCol < 9) {
                                    if (toRow > -1 && toRow < 10 && toCol > -1 && toCol < 9) {
                                        outMap = false;
                                    }
                                }
                                if (outMap) {
                                    ChessGameFrame.text("Position Out Of Range At Line" + i);
                                    continue;
                                }

                                //起始棋子
                                if (chessboard.getChessboard()[fromRow][fromCol].getChessColor() != ChessColor.RED ||
                                        chessboard.getChessboard()[fromRow][fromCol].getChessName() == '.') {
                                    ChessGameFrame.text("Invalid From Position At Line" + i);
                                    continue;
                                }

                                //目标棋子
                                if (chessboard.getChessboard()[toRow][toCol].getChessColor() == ChessColor.RED) {
                                    ChessGameFrame.text("Invalid To Position At Line" + i);
                                    continue;
                                }

                                //move
                                if (chessboard.getChessboard()[fromRow][fromCol].canMoveTo(chessboard.getChessboard(), new ChessboardPoint(toRow, toCol))) {
                                    chessboard.setVisible(false);
                                    chessboard.swapChessComponents(chessboard.getChessboard()[fromRow][fromCol], chessboard.getChessboard()[toRow][toCol]);
                                    chessboard.setVisible(true);
                                    chessboard.setCurrentColor(ChessColor.BLACK);
                                } else {
                                    ChessGameFrame.text("Invalid Move Pattern At Line" + i);
                                    continue;
                                }
                            }
                        }
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChessboardComponent.setSkill(true);
    }
}