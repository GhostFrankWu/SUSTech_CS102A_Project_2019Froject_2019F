package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.button.Button;
import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;
import xyz.chengzi.cs102a.chinesechess.listener.ChessboardChessListener;
import xyz.chengzi.cs102a.chinesechess.music.ChessGameMusicSmall;
import xyz.chengzi.cs102a.chinesechess.openingCase.*;
import xyz.chengzi.cs102a.chinesechess.music.ChessGameMusic;
import xyz.chengzi.cs102a.chinesechess.socket.Client;
import xyz.chengzi.cs102a.chinesechess.socket.Server;
import xyz.chengzi.cs102a.chinesechess.socket.Socket_Special;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

class Processor1 extends Thread {                  //主线程
    public void run() {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(0);
            mainFrame.setVisible(true);
        });
    }
}//////////////////////////////////////////////////猪线程结束

class Processor2 extends Thread {                  //音乐线程
    public void run() {
        ChessGameMusic bgm = new ChessGameMusic();
        bgm.run("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\bgm" + ChessGameFrame.getBgmLevel() + ".wav", -10);
    }
}

class ProcessorM extends Thread {
    public void run() {
        new Server().Server(ChessGameFrame.getMessagedDelivered(), ChessboardChessListener.getS() != 0 ? 20 : 21);
    }
}

public class ChessGameFrame extends JFrame {
    private ChessComponent first;
    private JLabel time1 = new JLabel();
    private static Vector<String> qss = new Vector<>();
    private static Vector<String> bgs = new Vector<>();
    private static Vector<String> bgms = new Vector<>();
    private static int bgmLevel = 2, Kejin = 0;
    private ChessboardComponent chessboard = new ChessboardComponent(700, 700);
    private static ImageIcon BJ = new ImageIcon("src/GS/beijing2.jpg"), BJ2 = new ImageIcon(new ImageIcon("src/GS/vs.png").getImage().getScaledInstance
            (100, 110, Image.SCALE_FAST)), BJ3 = new ImageIcon(new ImageIcon("src/resources/jiang.png").getImage().getScaledInstance
            (300, 110, Image.SCALE_FAST)), BJ4 = new ImageIcon(new ImageIcon("src/resources/blackwin.png").getImage().getScaledInstance
            (300, 110, Image.SCALE_FAST)), BJ5 = new ImageIcon(new ImageIcon("src/resources/redwin.png").getImage().getScaledInstance
            (300, 110, Image.SCALE_FAST)), BJ6 = new ImageIcon(new ImageIcon("src/resources/peace.png").getImage().getScaledInstance
            (300, 110, Image.SCALE_FAST)), BJ7 = new ImageIcon(new ImageIcon("src/resources/wait.png").getImage().getScaledInstance
            (300, 110, Image.SCALE_FAST)), BJX = new ImageIcon(new ImageIcon("src/resources/black.png").getImage().getScaledInstance
            (452, 70, Image.SCALE_FAST)), BJ8 = new ImageIcon(new ImageIcon("src/resources/start.png").getImage().getScaledInstance
            (120, 50, Image.SCALE_FAST)), BJ9 = new ImageIcon(new ImageIcon("src/resources/ready.png").getImage().getScaledInstance
            (120, 50, Image.SCALE_FAST)), BJ10 = new ImageIcon(new ImageIcon("src/resources/stop.png").getImage().getScaledInstance
            (120, 50, Image.SCALE_FAST)), BJ11 = new ImageIcon(new ImageIcon("src/resources/friend.png").getImage().getScaledInstance
            (120, 50, Image.SCALE_FAST)), BJ12 = new ImageIcon(new ImageIcon("src/resources/AI.png").getImage().getScaledInstance
            (86, 90, Image.SCALE_FAST)), BJ13 = new ImageIcon(new ImageIcon("src/resources/skill.jpg").getImage().getScaledInstance
            (86, 90, Image.SCALE_FAST));
    private JButton button_qs = new JButton(), button_dfqs = new JButton();
    private ImageIcon sqssss, sqsssss;
    private int index_qs = 99, index_dfqs = 99;
    private static JLabel background = new JLabel(), BGMAP = new JLabel(BJ), BGMAP2 = new JLabel(BJX);
    private static String host = "10.17.2.217", messagedDelivered = "";
    private JTextField host_text = new JTextField();
    private JButton Client1 = new JButton(BJ8), Server = new JButton(BJ9), StopMatch = new JButton(BJ10);
    private static JButton checked = new JButton(BJ3), black_win = new JButton(BJ4), red_win = new JButton(BJ5),
            chess0 = new JButton(), chesss1 = new JButton(), peace = new JButton(BJ6), waiting = new JButton(BJ7);
    private static final String[] qses = {"1-红衫木桌", "2-橡木棋桌", "3-梦魇战场", "1-仲夏律动", "2-早春常樱",
            "3-竹取之语", "李唯荨", "汪次郎", "夏岚", "欧阳夏生", "韩商言", "谷邵美", "燕心语", "北梦涵", "苏尼岚",
            "尹丽雅", "泽尼娅", "卡维", "莎拉", "南之宫花", "白石奈奈", "小鸟游雏田", "一姬", "吴梓涵"};
    private static Thread t1 = new Processor1();///t1.setPriority(9);
    private static Thread t2 = new Processor2();
    private Thread t9 = new Processor9(), t7 = new Processor7(), t6 = new Processor6(),
            t5 = new Processor5(), t10 = new Processor10(), t11 = new ProcessorM();
    private static JTextArea textArea = new JTextArea();

    public static void main(String[] args) {
        t1.start();
        t2.start();
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ChessGameFrame(int backGroundLevel) {
        GameTitleIni();     //标题
        add(chessboard);    //棋盘
        dialogTest(chessboard);
        GameBackgroundIni(backGroundLevel);//背景
        add(BGMAP);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    record0();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                super.windowClosing(e);
                t9.stop();
                t7.stop();
                t6.stop();
                t5.stop();
                t10.stop();
                t11.stop();
            }
        });
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////初始化结束

    static int getBgmLevel() {
        return bgmLevel;
    }

    ////////////////////////////////////////////////////////////////////////./////////////游戏背景初始化
    private void GameBackgroundIni(int backGroundLevel) {
        ImageIcon bg = new ImageIcon("src/resources/bg" + backGroundLevel + ".png");    //棋盘背景
        Image img = bg.getImage();
        int hight = this.getHeight() - 67, width = this.getWidth() - 637;
        img = img.getScaledInstance(width, hight, Image.SCALE_DEFAULT);
        bg.setImage(img);
        background.setIcon(bg);
        this.add(background);
        background.setSize(width, hight);
    }///////////////////////////////////////////////////////////////////////////////////////////////结束

    /////////////////////////////////////////////////////////////// //////////////游戏标题初始化&图标初始化
    private void GameTitleIni() {
        setSize(1330, 820);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);
        String blank_blank = "                                     ";
        setTitle("Chess_Soul" + blank_blank + blank_blank + blank_blank + blank_blank + " CS102A_Project_Group65");
        ImageIcon icon = new ImageIcon("src/resources/logo.jpg");  //左上角图标
        this.setIconImage(icon.getImage());
    }////////////////////////////////////////////////////////////////////////////////////////////////结束

    private void MakeMoney(ImageIcon qs, ImageIcon qs_) {
        Image imaqs = qs_.getImage();                         // 为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
        float a = qs_.getIconWidth();
        a /= 2;
        Image imgsqs = imaqs.getScaledInstance((int) a + 1, 512, Image.SCALE_FAST);//    再由修改后的Image来生成合适的Icon
        sqssss = new ImageIcon(imgsqs);
        button_qs.setIcon(qs);
        button_qs.setLocation(690, 160);
        button_qs.setSize(256, 255);
        add(button_qs);////////////////////////////////////////////////////////////////////////////////////////////=====人物
    }

    private void MakeMoreMoney(ImageIcon qs, ImageIcon qSs_) {
        Image imaqs = qSs_.getImage();                         // 为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
        float a = qSs_.getIconWidth();
        a /= 2;
        Image imgsSqs = imaqs.getScaledInstance((int) a + 1, 512, Image.SCALE_FAST);//    再由修改后的Image来生成合适的Icon
        sqsssss = new ImageIcon(imgsSqs);
        button_dfqs.setIcon(qs);
        button_dfqs.setLocation(1046, 160);
        button_dfqs.setSize(256, 255);
        add(button_dfqs);////////////////////////////////////////////////////////////////////////////////////////////=====人物
    }


    private int rankx = 0;

    private void dialogTest(ChessboardComponent chessboard) {
        NowTime();
        checked.setLocation(865, 90);
        checked.setContentAreaFilled(false);
        checked.setBorderPainted(false);
        checked.setSize(300, 100);
        add(checked);
        checked.setVisible(false);
        ////////////////////////////////////////////////////////
        chesss1.setContentAreaFilled(false);
        chesss1.setBorderPainted(false);
        chesss1.setSize(100, 100);
        add(chesss1);
        chesss1.setVisible(false);
        chess0.setContentAreaFilled(false);
        chess0.setBorderPainted(false);
        chess0.setSize(300, 300);
        add(chess0);
        chess0.setVisible(false);
        ///////////////////////////////////////////////////////////////
        black_win.setLocation(865, 90);
        black_win.setContentAreaFilled(false);
        black_win.setBorderPainted(false);
        black_win.setSize(300, 100);
        add(black_win);
        black_win.setVisible(false);
        red_win.setLocation(865, 90);
        red_win.setContentAreaFilled(false);
        red_win.setBorderPainted(false);
        red_win.setSize(300, 100);
        add(red_win);
        red_win.setVisible(false);
        peace.setLocation(865, 90);
        peace.setContentAreaFilled(false);
        peace.setBorderPainted(false);
        peace.setSize(300, 100);
        add(peace);
        peace.setVisible(false);
        waiting.setLocation(865, 90);
        waiting.setContentAreaFilled(false);
        waiting.setBorderPainted(false);
        waiting.setSize(300, 100);
        add(waiting);
        waiting.setVisible(false);
        ImageIcon OPEN_case = new ImageIcon("src/GS/OPEN_case.png");
        JButton VS = new JButton(BJ2), fried = new JButton(BJ11), AI_BUTT = new JButton(BJ12), skill_butt = new JButton("动画");
        skill_butt.setLocation(1250, 45);
        skill_butt.setSize(60, 30);
        VS.setContentAreaFilled(false);
        fried.setContentAreaFilled(false);
        fried.setLocation(700, 30);
        fried.setBorderPainted(false);
        AI_BUTT.setContentAreaFilled(false);
        AI_BUTT.setLocation(1150, 0);
        add(skill_butt);
        skill_butt.addActionListener((e) -> ChessboardComponent.setSkill0());
        add(AI_BUTT);
        AI_BUTT.addActionListener((e) -> {
            if (!ChessboardChessListener.getMulitply()) {
                if (!ChessboardChessListener.isAi()) {
                    text("=========================\n和电脑对战\n=========================");
                    ChessboardChessListener.setAi(true);
                } else {
                    text("=========================\n关闭电脑\n=========================");
                    ChessboardChessListener.setAi(false);
                }
            } else {
                text("=========================\n人人模式不可开启AI\n=========================");
            }
        });
        VS.setLocation(946, 230);
        add(VS);
        VS.setBorderPainted(false);
        add(fried);//add(AI_BUTT);
        VS.setSize(100, 110);
        fried.setSize(120, 50);
        AI_BUTT.setSize(86, 90);
        MakeMoney(new ImageIcon("src/GS/99.png"), new ImageIcon("src/GS/99.png"));
        MakeMoreMoney(new ImageIcon("src/GS/99.png"), new ImageIcon("src/GS/99.png"));
        JButton open_Case = new JButton(OPEN_case);
        open_Case.addActionListener((e) -> new openCase());
        open_Case.setLocation(690, 415);
        open_Case.setSize(80, 70);
        open_Case.setContentAreaFilled(false);
        add(open_Case);////////////////////////////////////////////////////////////////////////////////////////////=====开箱
        ImageIcon save_ico = new ImageIcon(new ImageIcon("src/GS/save_ico.png").getImage().getScaledInstance
                (296, 105, Image.SCALE_FAST));
        JButton saveButton = new JButton(save_ico);//搞save按钮
        saveButton.setLocation(690, 685);
        saveButton.setSize(280, 100);
        saveButton.setContentAreaFilled(false);
        saveButton.addActionListener((e) -> {
            Button bu = new Button();
            try {
                bu.SaveChessMap(chessboard);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(saveButton);///////////////////////////////////////////////////////////////////////////////////////////=====save
        ImageIcon restart_ico = new ImageIcon("src/GS/restart_ico.png");
        JButton restartButton = new JButton(restart_ico);
        restartButton.addActionListener((e) -> {
            if (!ChessboardChessListener.getMulitply()) {
                chessboard.restart();
                text("=========================\n重新开始\n=========================");
                stoptime();
                setNoWin();
                chessboard.setCurrentColor(ChessColor.RED);
                starttime();
            } else {
                int option =
                        JOptionPane.showConfirmDialog(this, "确认请求重新开始? ", "提示 ", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    t11.stop();
                    messagedDelivered = "A_restart";
                    t11 = new ProcessorM();
                    t11.start();
                }
            }
        });//刷新
        restartButton.setLocation(500, 705);
        restartButton.setContentAreaFilled(false);
        restartButton.setSize(60, 80);
        add(restartButton);///////////////////////////////////////////////////////////////////////////////////////======restart
        ImageIcon regret_ico = new ImageIcon("src/GS/regret_ico.png");
        JButton regretButton = new JButton(regret_ico);
        regretButton.setLocation(140, 705);
        regretButton.setContentAreaFilled(false);
        regretButton.setSize(70, 80);
        regretButton.addActionListener((e) -> {
            if (ChessboardChessListener.getMulitply()) {
                t11.stop();
                t11 = new ProcessorM();
                messagedDelivered = "A_regret";
                t11.start();
            } else {
                regret();
                text("=========================\n悔棋\n=========================");
            }
        });
        add(regretButton);///////////////////////////////////////////////////////////////////////////////////////////=====悔棋
        ImageIcon qiuhe_ico = new ImageIcon(new ImageIcon("src/resources/qiuhe.png").getImage().getScaledInstance
                (80, 80, Image.SCALE_FAST));
        JButton quheButton = new JButton(qiuhe_ico);
        quheButton.setLocation(220, 705);
        quheButton.setContentAreaFilled(false);
        quheButton.setSize(80, 80);
        quheButton.addActionListener((e) -> {
            int option = JOptionPane.showConfirmDialog(
                    this, "确定求和? ", "提示 ", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (ChessboardChessListener.getMulitply()) {
                    t11.stop();
                    t11 = new ProcessorM();
                    messagedDelivered = "A_peace";
                    t11.start();
                } else {
                    setPeace();
                    text("=========================\n和棋\n=========================");
                    chessboard.setCurrentColor(ChessColor.NONE);
                    stoptime();
                }

            }//else{JOptionPane.showConfirmDialog(this, "您有正在进行的请求", "提示 ", JOptionPane.DEFAULT_OPTION);}
        });
        add(quheButton);///////////////////////////////////////////////////////////////////////////////////////////=====求和
        ImageIcon renshu_ico = new ImageIcon(new ImageIcon("src/resources/renshu.png").getImage().getScaledInstance
                (80, 80, Image.SCALE_FAST));
        JButton loseButton = new JButton(renshu_ico);
        loseButton.setLocation(310, 705);
        loseButton.setContentAreaFilled(false);
        loseButton.setSize(80, 80);
        loseButton.addActionListener((e) -> {
            int option = JOptionPane.showConfirmDialog(
                    this, "确定认输? ", "提示 ", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                stoptime();
                if (ChessboardChessListener.getMulitply()) {
                    t11.stop();
                    ChessOperater.findAndWrite(3);
                    messagedDelivered = "A-lose";
                    t11 = new ProcessorM();
                    t11.start();
                    if (chessboard.getCurrentColor() == ChessColor.RED || chessboard.getColorBefore() == ChessColor.RED) {
                        black_win.setVisible(true);
                    } else {
                        red_win.setVisible(true);
                    }
                } else {
                    ChessOperater.findAndWrite(1);
                    int[] rank = ChessOperater.readRank();
                    textArea.append("您的战绩：\nVS电脑：" + rank[0] + "胜" + rank[1] + "负\nVS对手：" + rank[2] + "胜" + rank[3] + "负\n********************************\n");
                    if (chessboard.getCurrentColor() == ChessColor.RED) {
                        black_win.setVisible(true);
                    } else {
                        red_win.setVisible(true);
                    }
                }
                chessboard.setCurrentColor(ChessColor.NONE);
            }
        });
        add(loseButton);///////////////////////////////////////////////////////////////////////////////////////////=====认输
        ImageIcon quit_ico = new ImageIcon(new ImageIcon("src/resources/likai.png").getImage().getScaledInstance
                (80, 80, Image.SCALE_FAST));
        JButton quitButton = new JButton(quit_ico);
        quitButton.setLocation(410, 705);
        quitButton.setContentAreaFilled(false);
        quitButton.setSize(80, 80);
        quitButton.addActionListener((e) -> reboot());
        add(quitButton);///////////////////////////////////////////////////////////////////////////////////////////=====离开
        ImageIcon KE_JIN = new ImageIcon("src/GS/KE_JIN.png");
        JButton keJinButton = new JButton(KE_JIN);
        ImageIcon KE_JIN_zhi = new ImageIcon("src/GS/ZC.png");
        keJinButton.addActionListener((e) -> {
            if (Kejin == 0) {
                Kejin = 1;
            } else {
                Kejin = 0;
            }
            showMessageDialog(null, "感谢打赏~~", "请支持~", JOptionPane.INFORMATION_MESSAGE, KE_JIN_zhi);
        });
        keJinButton.setLocation(890, 415);
        keJinButton.setMargin(new Insets(0, 0, 0, 0));
        keJinButton.setSize(80, 70);
        keJinButton.setContentAreaFilled(false);
        add(keJinButton);///////////////////////////////////////////////////////////////////////////////////////========kejin
        ImageIcon load_ic = new ImageIcon(new ImageIcon("src/GS/load_ic.png").getImage().getScaledInstance
                (296, 105, Image.SCALE_FAST));
        JButton loadButton = new JButton(load_ic);//搞load按钮
        loadButton.setLocation(690, 485);
        loadButton.setContentAreaFilled(false);
        loadButton.setSize(280, 100);
        loadButton.addActionListener((e) -> {
            if (!ChessboardChessListener.getMulitply()) {
                chessboard.restart2();
                Button bu = new Button();
                bu.LoadChessMap(chessboard);
            } else {
                text("=========================\n人人模式不可使用\n=========================");
            }
        });
        add(loadButton);//////////////////////////////////////////////////////////////////////////////////////////======load
        ImageIcon load_ico = new ImageIcon(new ImageIcon("src/GS/load_ico.png").getImage().getScaledInstance
                (296, 105, Image.SCALE_FAST));
        JButton loadButton2 = new JButton(load_ico);//搞load按钮
        loadButton2.setLocation(690, 585);
        loadButton2.setContentAreaFilled(false);
        loadButton2.setSize(280, 100);
        loadButton2.addActionListener((e) -> {
            if (!ChessboardChessListener.getMulitply()) {
                chessboard.restart();
                Button load2 = new Button();
                load2.LoadMoveSeq(chessboard);
            } else {
                text("=========================\n人人模式不可使用\n=========================");
            }
        });
        add(loadButton2);//////////////////////////////////////////////////////////////////////////////////////////////=棋谱

        ChessOperater reader = new ChessOperater();
        JComboBox<String> combo = new JComboBox<>(qss);
        button_qs.addActionListener((e) -> showMessageDialog(this,
                reader.readData(index_qs), reader.readName(index_qs), JOptionPane.INFORMATION_MESSAGE, sqssss));
        button_dfqs.addActionListener((e) -> showMessageDialog(this,
                reader.readData(index_dfqs), reader.readName(index_dfqs), JOptionPane.INFORMATION_MESSAGE, sqsssss));
        combo.setBorder(BorderFactory.createTitledBorder("选择您的棋手"));
        read();
        combo.addActionListener((e) -> {
            read();
            remove(BGMAP);
            for (int i = 5; i < qses.length; i++)
                if (qses[i] == (combo.getSelectedItem()))
                    index_qs = i - 5;
            remove(button_qs);
            if (ChessboardChessListener.getMulitply()) {
                t11.stop();
                t11 = new ProcessorM();
                messagedDelivered = "QS" + index_qs;
                t11.start();
            }
            MakeMoney(new ImageIcon("src/GS/gs" + index_qs + "_.png"), new ImageIcon("src/GS/gs" + index_qs + ".png"));
            add(BGMAP);
            this.setVisible(false);
            this.setVisible(true);
        });
        combo.setSize(120, 20);
        combo.setLocation(770, 420);
        add(combo);//////////////////////////////////////////////////////////////////////////////////////////////=======棋手选择

        JComboBox<String> combo1 = new JComboBox<>(bgms);
        combo1.setBorder(BorderFactory.createTitledBorder("选择您的背景音乐"));
        read();
        combo1.addActionListener((e) -> {
            read();
            t2.stop();
            bgmLevel = combo1.getSelectedItem() == null ? 0 : (int) combo1.getSelectedItem().toString().charAt(0) - 48;
            t2 = new Processor2();//创建线程 //t1.setName("t1");//给线程起名//t2.setPriority(10);//设置优先级(由低到高1~10)
            t2.start();
        });
        combo1.setSize(120, 20);
        combo1.setLocation(770, 440);
        add(combo1);//////////////////////////////////////////////////////////////////////////////////////////////=======bgm选择

        host_text.setText(Socket_Special.calculate(Socket_Special.GetIpAddress()));
        host_text.setLocation(700, 80);
        host_text.setSize(120, 50);
        add(host_text);///////////////////////////////////////////////////////////////////////////////////////=======IP

        Server.addActionListener((e) -> {
            host = Socket_Special.reverse(host_text.getText());
            disable0();
            t10.start();
            waiting.setVisible(true);
            ChessboardChessListener.setMulitply(true);
            t9.start();
        });
        Server.setLocation(820, 80);
        Server.setContentAreaFilled(false);
        Server.setSize(120, 50);
        add(Server);///////////////////////////////////////////////////////////////////////////////////////========SERVER

        Client1.addActionListener((e) -> {
            host = Socket_Special.reverse(host_text.getText());
            waiting.setVisible(true);
            ChessboardChessListener.setMulitply(true);
            t6.start();
            t7.start();
            disable0();
            t10.start();
            ChessboardChessListener.setS(1);
        });
        Client1.setContentAreaFilled(false);
        Client1.setSize(120, 50);
        Client1.setLocation(820, 30);
        add(Client1);///////////////////////////////////////////////////////////////////////////////////////=======CLIENT

        StopMatch.addActionListener((e) -> {
            t11.stop();
            messagedDelivered = "B-run";
            t11 = new ProcessorM();
            t11.start();
            reboot();
        });
        StopMatch.setContentAreaFilled(false);
        StopMatch.setSize(120, 50);
        StopMatch.setLocation(905, 40);/////////////////////////////////////////////////////////////////==========STOP

        textArea.setLocation(970, 420);
        textArea.setSize(310, 350);
        add(textArea);
        textArea.append("**********" + findAnd() + ",欢迎回来！**********\n");
        int[] rank = ChessOperater.readRank();
        rankx = rank[2];
        textArea.append("您的战绩：\nVS电脑：" + rank[0] + "胜" + rank[1] + "负\nVS对手：" + rank[2] + "胜" + rank[3] + "负\n******************************************\n");
        String[] bend = findRank();
        textArea.append("当前的排行榜前三名是：\n第一名：" + bend[1] + "人人对战胜场" + bend[0] + "\n第二名：" + bend[3] + "人人对战胜场" + bend[2] + "\n第三名：" + bend[5] + "人人对战胜场" + bend[4]);
        textArea.append("\n您的邀请码为：" + Socket_Special.calculate(Socket_Special.GetIpAddress()));
        textArea.append("\n双方在上方链接框内输入邀请码后\n红方点击开始，黑方点击连接即可多人游戏！\n");
        textArea.setCaretPosition(textArea.getText().length());
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(990, 420, 310, 350);
        scrollPane_1.setViewportView(textArea);
        add(scrollPane_1);
        //////////////////////

        JComboBox<String> combo2 = new JComboBox<>(bgs);
        combo2.setBorder(BorderFactory.createTitledBorder("选择您的背景"));
        read();
        combo2.setSize(120, 20);
        combo2.setLocation(770, 460);
        add(combo2);
        BGMAP.setLocation(-370, 0);
        BGMAP2.setLocation(120, 705);
        add(BGMAP2, Integer.valueOf(Integer.MIN_VALUE));
        BGMAP.setSize(1920, 1080);
        BGMAP2.setSize(452, 75);
        combo2.addActionListener((e) -> {
            read();
            this.setVisible(false);
            remove(background);
            remove(BGMAP);
            GameBackgroundIni(combo2.getSelectedItem() == null ? 0 : combo2.getSelectedItem().toString().charAt(0) - 48);
            add(BGMAP2);
            add(BGMAP);
            this.setVisible(true);
        });
        //////////////////////////////////////////////////////////////////////////////////////////////=======bg选择
    }

    private void regret() {
        if (chessboard.getMoveMap().size() != 0) {
            chessboard.regret();
        }
        chessboard.checkIfChecked();
    }

    public static void setChecked() {
        checked.setVisible(true);
    }

    public static void setUnchecked() {
        checked.setVisible(false);
    }

    public static void setBlack_win() {
        int[] rank = ChessOperater.readRank();
        textArea.append("您的战绩：\nVS电脑：" + rank[0] + "胜" + rank[1] + "负\nVS对手：" + rank[2] + "胜" + rank[3] + "负\n********************************\n");
        black_win.setVisible(true);
        setUnchecked();
    }

    public static void appearChess(int x0, int y0, int n1) {
        chess0.setLocation(x0, y0);
        chesss1.setLocation(x0, y0);
        chess0.setIcon(new ImageIcon(new ImageIcon("src/resources/chess_pick_up.png").getImage().getScaledInstance
                (130, 130, Image.SCALE_FAST)));
        chesss1.setIcon(new ImageIcon(new ImageIcon("src/resources/" + n1 + ".png").getImage().getScaledInstance
                (90, 90, Image.SCALE_FAST)));
        chess0.setVisible(true);
        chesss1.setVisible(true);
    }

    public static void moveChess(int dx, int dy) {
        chess0.setLocation(dx + 14 - 100, dy + 17 - 100);
        chesss1.setLocation(dx + 2, dy);

        /*
        chess0.setVisible(false);
        chesss1.setVisible(false);
        chess0.setVisible(true);
        chesss1.setVisible(true);*/
    }

    public static void disappearChess() {
        chesss1.setVisible(false);
        chess0.setVisible(false);
    }

    public static void setRed_win() {
        red_win.setVisible(true);
        setUnchecked();
        int[] rank = ChessOperater.readRank();
        textArea.append("您的战绩：\nVS电脑：" + rank[0] + "胜" + rank[1] + "负\nVS对手：" + rank[2] + "胜" + rank[3] + "负\n********************************\n");
    }

    private static void setNoWin() {
        black_win.setVisible(false);
        red_win.setVisible(false);
        setUnchecked();
        peace.setVisible(false);
    }

    private static void setPeace() {
        peace.setVisible(true);
        setUnchecked();
    }

    private static void setUnWaiting() {
        waiting.setVisible(false);
    }

    private void read() {
        qss.clear();
        bgs.clear();
        bgms.clear();
        bgs.addElement("0-白桦木棋桌");
        bgms.addElement("0-平淡无奇");
        try {
            File file = new File("src/GS/data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            for (int i = 0; i < 25; i++) {
                String str = br.readLine();
                int index = str.indexOf("HAD");
                if (index != -1 && i > 5) {
                    qss.addElement(qses[i]);
                }
                if (index != -1 && i < 3) {
                    bgs.addElement(qses[i]);
                }
                if (index != -1 && i > 2 && i < 6) {
                    bgms.addElement(qses[i]);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////======读取氪金情况

    //////////////////////////////////////////////////////////////////////////////////////////======load
    class Processor9 extends Thread {
        public void run() {
            Client cil = new Client();
            String back = "FFFFFFFF";
            while (!back.equals("GET_FromServer")) {
                back = cil.receive(ChessGameFrame.getHost(), 9);
                text("正在连接中，请稍等...\n");
            }
            text("======连接成功！等待对方先行\n");
            t5.run();
            back = "FFFFFFFF";
            messagedDelivered = "QS" + index_qs;
            t11.start();
            setUnWaiting();
            chessboard.restart();
            chessboard.setCurrentColor(ChessColor.NONE);
            while (back.equals("FFFFFFFF")) {
                back = cil.receive(ChessGameFrame.getHost(), 1);
            }
            chessboard.setCurrentColor(ChessColor.BLACK);
            chessboard.swapColor(true);
            chessboard.swapChessComponents(
                    chessboard.getChessboard()[back.charAt(0) - 48][back.charAt(1) - 48],
                    chessboard.getChessboard()[back.charAt(2) - 48][back.charAt(3) - 48]);

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    chessboard.getChessboard()[i][j].setCanMoveTo(false);
                }
            }
            chessboard.getChessboard()[back.charAt(0) - 48][back.charAt(1) - 48].setMoved(true);
            chessboard.getChessboard()[back.charAt(2) - 48][back.charAt(3) - 48].setMoved(true);
            chessboard.swapColor(true);
            chessboard.checkIfChecked();
        }
    }

    static class Processor6 extends Thread {
        public void run() {
            new Server().Server("GET_FromServer", 9);
            text("=======连接好友中======\n");
        }
    }

    static class Processor5 extends Thread {
        public void run() {
            new Server().Server("GET_FromClient", 7);
            text("=======连接好友中======\n");
        }
    }


    class Processor7 extends Thread {
        public void run() {
            Client cil = new Client();
            String back = "FFFFFFFF";
            System.out.println(ChessGameFrame.getHost());
            while (!back.equals("GET_FromClient")) {
                back = cil.receive(
                        ChessGameFrame.getHost(), 7);
                text("正在连接，请稍等....\n");
            }
            text("======连接成功！请先行\n");
            chessboard.restart();
            setUnWaiting();
            messagedDelivered = "QS" + index_qs;
            t11.start();
        }
    }

    class Processor10 extends Thread {
        public void run() {
            Client cil11 = new Client();
            while (true) {
                String back = "FFFFFFFF";
                while (back.equals("FFFFFFFF")) {
                    back = cil11.receive(ChessGameFrame.getHost(), ChessboardChessListener.getS() == 0 ? 20 : 21);
                }
                handle(back);
            }
        }
    }

    public static void text(String input) {
        textArea.append(input);
        textArea.append("\n");
        textArea.setCaretPosition(textArea.getText().length());
    }

    private static void reboot() {
        try {
            Runtime.getRuntime().exec("java.exe -Dfile.encoding=UTF-8 " +
                    "-classpath out\\production\\CS102A-ChineseChess xyz.chengzi.cs102a.chinesechess.ChessGameFrame\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void disable0() {
        remove(Client1);
        remove(Server);
        remove(host_text);
        add(StopMatch);
        remove(BGMAP);
        add(BGMAP);
        setVisible(false);
        setVisible(true);
    }

    private void handle(String specialInput) {
        if (specialInput.startsWith("QS")) {
            if (specialInput.length() == 3)
                index_dfqs = (int) specialInput.charAt(2) - 48;
            if (specialInput.length() == 4)
                index_dfqs = (specialInput.charAt(2) - 48) * 10 + specialInput.charAt(3) - 48;
            System.out.println(index_dfqs);
            remove(BGMAP);
            remove(button_dfqs);
            MakeMoreMoney(new ImageIcon("src/GS/gs" + index_dfqs + "_.png"), new ImageIcon("src/GS/gs" + index_dfqs + ".png"));
            add(BGMAP);
            this.setVisible(false);
            this.setVisible(true);
        }
        if (specialInput.startsWith("MO")) {
            if (first != null)
                first.setSelected(false);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    chessboard.setCanMoveTo(i, j, false);
                }
            }
            first = chessboard.getChessboard()[specialInput.charAt(2) - 48][specialInput.charAt(3) - 48];
            first.setSelected(true);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    ChessboardPoint cp = new ChessboardPoint(i, j);
                    if (chessboard.getChessboard()[specialInput.charAt(2) - 48][specialInput.charAt(3) - 48].canMoveTo(chessboard.getChessboard(), cp)) {
                        chessboard.setCanMoveTo(i, j, true);
                    }
                }
            }
            chessboard.repaint();
        }
        if (specialInput.startsWith("DATA")) {
            Random r = new Random();
            new ChessGameMusicSmall().play("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\condition" + specialInput.charAt(4) + ".mp3", r.nextInt(3));
        }
        if (specialInput.startsWith("A")) {
            if (specialInput.contains("lose")) {
                if (chessboard.getCurrentColor() == ChessColor.RED || chessboard.getColorBefore() == ChessColor.RED) {
                    setRed_win();
                } else {
                    setBlack_win();
                }
                chessboard.setCurrentColor(ChessColor.NONE);
                JOptionPane.showConfirmDialog(this, "对方认输了 ", "提示 ", JOptionPane.DEFAULT_OPTION);
                ChessOperater.findAndWrite(2);
            }
            if (specialInput.contains("peace")) {
                {
                    int option = JOptionPane.showConfirmDialog(
                            this, "对方请求和棋，同意吗? ", "提示 ", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        text("=========================\n和棋\n=========================");
                        stoptime();
                        t11.stop();
                        setPeace();
                        chessboard.setCurrentColor(ChessColor.NONE);
                        t11 = new ProcessorM();
                        messagedDelivered = "B-peace-agree";
                        t11.start();
                    } else {
                        t11.stop();
                        t11 = new ProcessorM();
                        messagedDelivered = "B-peace-dis";
                        t11.start();
                    }
                }
            }
            if (specialInput.contains("regret")) {
                {
                    int option = JOptionPane.showConfirmDialog(
                            this, "对方请求悔棋，同意吗? ", "提示 ", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        text("=========================\n悔棋\n=========================");
                        t11.stop();
                        messagedDelivered = "B-regret-agree";
                        t11 = new ProcessorM();
                        t11.start();
                        regret();
                        Client cil = new Client();
                        String back = "FFFFFFFF";
                        while (back.equals("FFFFFFFF"))
                            back = cil.receive(ChessGameFrame.getHost(), ChessboardChessListener.getS() == 1 ? 0 : 1);
                        chessboard.swapChessComponents(
                                chessboard.getChessboard()[back.charAt(0) - 48][back.charAt(1) - 48],
                                chessboard.getChessboard()[back.charAt(2) - 48][back.charAt(3) - 48]);
                        chessboard.getChessboard()[back.charAt(0) - 48][back.charAt(1) - 48].setMoved(true);
                        chessboard.getChessboard()[back.charAt(2) - 48][back.charAt(3) - 48].setMoved(true);
                        chessboard.swapColor(true);
                        chessboard.checkIfChecked();
                    } else {
                        t11.stop();
                        messagedDelivered = "B-regret-dis";
                        t11 = new ProcessorM();
                        t11.start();
                    }
                }
            }
            if (specialInput.contains("restart")) {
                {
                    int option = JOptionPane.showConfirmDialog(
                            this, "对方请求重开，同意吗? ", "提示 ", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        text("\n=========================\n重新开始\n=========================");
                        text("\n=========================\n由您先行\n=========================");
                        stoptime();
                        starttime();
                        t11.stop();
                        chessboard.restart();
                        chessboard.setCurrentColor(ChessColor.RED);
                        t11 = new ProcessorM();
                        messagedDelivered = "B-restart-agree";
                        t11.start();
                    } else {
                        t11.stop();
                        t11 = new ProcessorM();
                        messagedDelivered = "B-restart-dis";
                        t11.start();
                    }
                }
            }
        }
        if (specialInput.startsWith("B")) {
            if (specialInput.contains("regret")) {
                if (specialInput.contains("agree")) {
                    text("=========================\n悔棋\n=========================");
                    regret();
                } else {
                    JOptionPane.showConfirmDialog(this, "对方拒绝了悔棋请求", "提示 ", JOptionPane.DEFAULT_OPTION);
                }
            }
            if (specialInput.contains("peace")) {
                if (specialInput.contains("agree")) {
                    setPeace();
                    chessboard.setCurrentColor(ChessColor.NONE);
                    text("=========================\n和棋\n=========================");
                    stoptime();
                } else {
                    JOptionPane.showConfirmDialog(this, "对方拒绝了和棋请求 ", "提示 ", JOptionPane.DEFAULT_OPTION);
                }
            }
            if (specialInput.contains("restart")) {
                if (specialInput.contains("agree")) {
                    stoptime();
                    text("\n=========================\n重新开始\n=========================\n");
                    text("\n=========================\n对方先行\n=========================\n");
                    stoptime();
                    starttime();
                    chessboard.restart();
                    chessboard.swapColor(true);
                    Client cil = new Client();
                    String back = "FFFFFFFF";
                    while (back.equals("FFFFFFFF"))
                        back = cil.receive(ChessGameFrame.getHost(), ChessboardChessListener.getS() == 1 ? 0 : 1);
                    chessboard.swapChessComponents(
                            chessboard.getChessboard()[back.charAt(0) - 48][back.charAt(1) - 48],
                            chessboard.getChessboard()[back.charAt(2) - 48][back.charAt(3) - 48]);
                    chessboard.getChessboard()[back.charAt(0) - 48][back.charAt(1) - 48].setMoved(true);
                    chessboard.getChessboard()[back.charAt(2) - 48][back.charAt(3) - 48].setMoved(true);
                    chessboard.swapColor(true);
                    chessboard.checkIfChecked();
                } else {
                    JOptionPane.showConfirmDialog(this, "对方拒绝了重开请求 ", "提示 ", JOptionPane.DEFAULT_OPTION);
                }
            }
            if (specialInput.contains("run")) {
                JOptionPane.showConfirmDialog(this, "对方逃跑了 ", "提示 ", JOptionPane.DEFAULT_OPTION);
                chessboard.setCurrentColor(ChessColor.NONE);
                ChessboardChessListener.setMulitply(false);
            }
        }
    }


    public static int getKejin() {
        return Kejin;
    }

    public static String getHost() {
        return host;
    }

    static String getMessagedDelivered() {
        return messagedDelivered;
    }

    private void NowTime() {
        add(time1);
        time1.setLocation(800, 100);
        time1.setSize(200, 100);
        this.setTimer(time1);
    }

    private JLabel varTime = new JLabel();
    private long timemillis1 = 0;
    private Timer timeAction = null;
    private long timemillis = 0;

    private void starttime() {
        timemillis1 = 0;
        timemillis = 0;
        timeAction.restart();
        varTime.setVisible(true);

    }

    private void stoptime() {
        varTime.setVisible(false);
    }

    private void setTimer(JLabel time) {
        varTime = time;
        timeAction = new Timer(1000, new ActionListener() {
            long timeMillis2 = (System.currentTimeMillis() - 1000);

            public void actionPerformed(ActionEvent e) {
                timemillis1 = System.currentTimeMillis();
                timemillis = timemillis1 - timeMillis2;
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    } //

    private void record0() throws Exception {
        File file2 = new File("src/GS/data.txt");
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file2.length());
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file2));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        String name = findAnd();
        findAndWrite(name, rankx);
        copyFile(bos.toByteArray(), ("src/GS/" + name + ".txt"));
    }

    public static void findAndWrite(String name, int score) {
        try {
            boolean flag = true, flag2 = false;
            File file1 = new File("src/GS/rank.txt");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
            List list = new ArrayList();
            for (int i = 0; i < 6; i++) {
                if (!(i == 1 || i == 3 || i == 5)) {
                    int x = -1;
                    while (x < 0)
                        x = br1.read() - 48;
                    int a = br1.read();
                    while (a >= 48) {
                        x *= 10;
                        x += a - 48;
                        a = br1.read();
                    }
                    if (score > x && flag) {
                        flag = false;
                        flag2 = true;
                        br1.readLine();
                        String stt = br1.readLine();
                        if (name.equals(stt)) {
                            list.add(x + "");
                            list.add(stt);
                            continue;
                        }
                        list.add(score + "");
                        list.add(name);
                        list.add(x + "");
                        list.add(stt);
                        continue;
                    }
                    list.add(x + "");
                } else {
                    if (flag2) {
                        flag2 = false;
                        continue;
                    }
                    br1.readLine();
                    String s = br1.readLine();
                    list.add(s);
                }
            }
            br1.close();
            BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8));
            for (int i = 0; i < list.size(); i++)
                pw.write((String) list.get(i) + "\r\n");
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(byte[] fileByte, String filePath)
            throws Exception {
        File file = new File(filePath);
        FileOutputStream fs = new FileOutputStream(file);
        BufferedOutputStream bo = new BufferedOutputStream(fs);
        bo.write(fileByte);
        bo.close();
    }

    public static String findAnd() {
        String str2 = "";
        try {
            File file1 = new File("src/GS/data.txt");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
            while (true) {
                String str = br1.readLine();
                if (str == null) break;
                int index = str.indexOf("INTERESTING");
                if (index != -1) {
                    str2 = br1.readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2;
    }

    private String[] findRank() {
        String[] str2 = new String[6];
        try {
            File file1 = new File("src/GS/rank.txt");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
            for (int i = 0; i < 6; i++) {
                str2[i] = br1.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2;
    }
}