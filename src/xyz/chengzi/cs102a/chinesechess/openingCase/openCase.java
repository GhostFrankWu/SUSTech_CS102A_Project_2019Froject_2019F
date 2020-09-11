package xyz.chengzi.cs102a.chinesechess.openingCase;

import xyz.chengzi.cs102a.chinesechess.ChessGameFrame;
import xyz.chengzi.cs102a.chinesechess.music.ChessGameMusic;
import xyz.chengzi.cs102a.chinesechess.music.ChessGameMusicSmall;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class openCase extends JDialog {
    static int[] index = new int[100];
    private ImageIcon[] imgs = {
            new ImageIcon("src/GS/gs1_.png"),
            new ImageIcon("src/GS/gs2_.png"),
            new ImageIcon("src/GS/gs3_.png"),
            new ImageIcon("src/GS/gs4_.png"),
            new ImageIcon("src/GS/gs5_.png"),
            new ImageIcon("src/GS/gs6_.png"),
            new ImageIcon("src/GS/gs7_.png"),
            new ImageIcon("src/GS/gs8_.png"),
            new ImageIcon("src/GS/gs9_.png"),
            new ImageIcon("src/GS/gs10_.png"),
            new ImageIcon("src/GS/gs11_.png"),
            new ImageIcon("src/GS/gs12_.png"),
            new ImageIcon("src/GS/gs13_.png"),
            new ImageIcon("src/GS/gs14_.png"),
            new ImageIcon("src/GS/gs15_.png"),
            new ImageIcon("src/GS/gs16_.png"),
            new ImageIcon("src/GS/gs17_.png"),
            new ImageIcon("src/GS/gs18_.png"),
            new ImageIcon("src/GS/gs1_.png"),
            new ImageIcon("src/GS/gs2_.png"),
            new ImageIcon("src/GS/gs3_.png"),
            new ImageIcon("src/GS/gs4_.png"),
            new ImageIcon("src/GS/gs5_.png"),
            new ImageIcon("src/GS/gs6_.png"),
            new ImageIcon("src/GS/gs7_.png"),
            new ImageIcon("src/GS/gs8_.png"),
            new ImageIcon("src/GS/gs9_.png"),
            new ImageIcon("src/GS/gs10_.png"),
            new ImageIcon("src/GS/gs11_.png"),
            new ImageIcon("src/GS/gs12_.png"),
            new ImageIcon("src/GS/gs13_.png"),
            new ImageIcon("src/GS/gs14_.png"),
            new ImageIcon("src/GS/gs15_.png"),
            new ImageIcon("src/GS/gs16_.png"),
            new ImageIcon("src/GS/gs17_.png"),
            new ImageIcon("src/GS/gs18_.png"),
            new ImageIcon("src/GS/gold.png"),
    };
    private int moveY = 0; //
    private MyJPanel mp;

    public openCase() {
        Thread t2 = new Processor2();//创建线程 //t1.setName("t1");//给线程起名//t2.setPriority(10);//设置优先级(由低到高1~10)
        Thread t1 = new Processor1();//创建线程 //t1.setName("t1");//给线程起名//t1.setPriority(9);//设置优先级(由低到高1~10)
        t1.setPriority(1);
        t2.setPriority(10);
        t2.start();//告诉JVM再分配一个新的栈给t线程，run不需程序员手动调用//t1.run2(); //系统线程启动后自动调用run方法
        t1.start();
    }

    private String qs_data = "", qs_name = "";

    public void openCase2() {
        Random random = new Random();
        for (int i = 0; i < 100; i++)
            index[i] = random.nextInt(37);
        setKeJin();
        mp = new MyJPanel();
        this.add(mp);
        this.setSize(1000, 170);
        this.setLocation(250, 250);
        ImageIcon icon = new ImageIcon("src/resources/logo.jpg");  //左上角图标
        this.setIconImage(icon.getImage());
        this.setVisible(true);

        Timer timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mp.repaint();
            }
        });
        timer.start();
        final int[] j = {1};
        Timer timer2 = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                j[0]++;
                timer.setDelay(j[0] * 5);
                if (j[0] == 10) {
                    timer.stop();
                    Thread t3 = new Processor3();//创建线程 //t1.setName("t1");//给线程起名//t2.setPriority(10);//设置优先级(由低到高1~10)
                    t3.start();
                    if ((index[24] + 1) % 37 != 0) {
                        saveResult((index[24] + 1) > 18 ? (index[24] - 18) : index[24] + 1);
                        ImageIcon qs_ = new ImageIcon("src/GS/gs" + ((index[24] + 1) > 18 ? (index[24] - 17) : index[24] + 1) + ".png");
                        Image imaqs = qs_.getImage();                         // 为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
                        float a = qs_.getIconWidth();
                        a /= 2;
                        Image imgsqs = imaqs.getScaledInstance((int) a + 1, 512, Image.SCALE_FAST);//再由修改后的Image来生成合适的Icon
                        ImageIcon smallqs = new ImageIcon(imgsqs);
                        JOptionPane.showMessageDialog(null, qs_data, "获得新棋手~~" + qs_name, 1, smallqs);
                        qs_name = "";
                        qs_data = "";
                    } else {
                        Random random = new Random();
                        int k = random.nextInt(5)+1;
                        switch (k) {
                            case 1:
                                findAndWrite("BG1");
                                break;
                            case 2:
                                findAndWrite("BG2");
                                break;
                            case 3:
                                findAndWrite("BG3");
                                break;
                            case 4:
                                findAndWrite("BGM1");
                                break;
                            case 5:
                                findAndWrite("BGM2");
                                break;
                            case 6:
                                findAndWrite("BGM3");
                                break;
                        }
                        if (k > 3) {
                            JOptionPane.showMessageDialog(null, "获得稀有BGM!~~", "血统认证", 1);
                        } else {
                            JOptionPane.showMessageDialog(null, "获得稀有背景!~~", "血统认证", 1);
                        }
                    }
                }
            }
        });
        timer2.start();
    }

    private void findAndWrite(String find) {
        try {
            File file1 = new File("src/GS/data.txt");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
            List list = new ArrayList();
            while (true) {
                String str = br1.readLine();
                if (str == null) break;
                int index = str.indexOf(find);
                if (index != -1) {
                    str += "_HAD";
                }
                list.add(str);
            }
            br1.close();
            BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8));
            for (Object o : list) pw.write((String) o + "\r\n");
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveResult(int x) {
        String find = x != 1 ? ("" + x) : ("0" + x);
        findAndWrite("QS" + find);
        try {
            File file = new File("src/GS/data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            while (true) {
                String str = br.readLine();
                if (str == null) break;
                find = x != 1 ? ("START" + x) : ("START0" + x);
                int index = str.indexOf(find);
                if (index != -1) {
                    qs_name = br.readLine();
                    qs_name = qs_name.replace("NAME", "");
                    qs_data += qs_name + "\n";
                    while (true) {
                        String str2 = br.readLine();
                        if (str2 == null) break;
                        int index2 = str2.indexOf("END");
                        if (index2 != -1) {
                            break;
                        }
                        qs_data += str2 + "\n";
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Processor1 extends Thread {                  //主线程
        public void run() {
            openCase2();
        }
        ///////////////////////////////////////////猪线程结束
    }

    static class Processor2 extends Thread {                  //音乐线程
        public void run() {
            ChessGameMusic bgm = new ChessGameMusic();
            bgm.run("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\open_case.wav", 0);
        }
    }

    static class Processor3 extends Thread {                  //音乐线程
        public void run() {
            ChessGameMusic bgm = new ChessGameMusic();
            bgm.run("src\\xyz\\chengzi\\cs102a\\chinesechess\\music\\display.wav", 5);
        }
    }

    class MyJPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int i = 0; i < 35; i++)
                g.drawImage(imgs[index[i]].getImage(), getX(), getY(), getX() + 2000, getY() + 205,
                        -2000 + 255 * i - moveY, 0, -2000 + 255 * i + 4000 - moveY, 400, null);
            moveY += 10;
            for (int i = 0; i < 4; i++) g.drawLine(499 + i, 0, 499 + i, 205);
        }
    }

    public void setKeJin() {
        int x = ChessGameFrame.getKejin();
        if (x == 1)
            for (int i = 0; i < 30; i++)
                index[i] = 36;
    }
}