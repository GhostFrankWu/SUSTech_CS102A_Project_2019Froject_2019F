package xyz.chengzi.cs102a.chinesechess;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class before extends JFrame {
    private static JLabel background = new JLabel();

    private before() {
        setTitle("2019 CS102A Project Group_65");
        setSize(600, 599);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);


        JTextField host_text = new JTextField();
        host_text.setText("                 欢迎回来！\n请在下方输入您的用户名");
        host_text.setLocation(170, 100);
        host_text.setSize(300, 100);
        add(host_text);

        JTextField host_text2 = new JTextField();
        host_text2.setLocation(220, 220);
        host_text2.setSize(200, 60);
        add(host_text2);

        JButton button = new JButton("进入游戏！");
        button.setLocation(220, 300);
        button.addActionListener((e) -> {
            if (host_text2.getText().length() > 0) {
                try {
                    Runtime.getRuntime().exec("java.exe -Dfile.encoding=UTF-8 " +
                            "-classpath out\\production\\CS102A-ChineseChess xyz.chengzi.cs102a.chinesechess.ChessGameFrame\n");
                    record(host_text2.getText());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            } else {
                showMessageDialog(null, "请输入用户名", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        button.setSize(200, 60);
        add(button);

        JButton button2 = new JButton("退出");
        button2.addActionListener((e) -> System.exit(0));
        button2.setLocation(220, 400);
        button2.setSize(200, 60);
        add(button2);

        ImageIcon icon = new ImageIcon("src/resources/logo.jpg");
        this.setIconImage(icon.getImage());

        ImageIcon bg = new ImageIcon("src/resources/bg123.jpg");    //棋盘背景
        Image img = bg.getImage();
        int hight = this.getHeight(), width = this.getWidth();
        img = img.getScaledInstance(width, hight, Image.SCALE_DEFAULT);
        bg.setImage(img);
        background.setIcon(bg);
        this.add(background);
        background.setSize(width, hight);

    }

    private void record(String name) throws Exception {
        File file = new File("src/GS/" + name + ".txt");
        if (file.exists()) {
            ByteArrayOutputStream bos = null;
            BufferedInputStream in = null;
            in = new BufferedInputStream(new FileInputStream(file));
            bos = new ByteArrayOutputStream((int) file.length());
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            copyFile(bos.toByteArray(), "src/GS/data.txt");
        } else {
            File file2 = new File("src/GS/data2.txt");
            ByteArrayOutputStream bos = null;
            BufferedInputStream in = null;
            in = new BufferedInputStream(new FileInputStream(file2));
            bos = new ByteArrayOutputStream((int) file2.length());
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            copyFile(bos.toByteArray(), "src/GS/data.txt");
        }
        findAndWrite(name);
    }

    private static void copyFile(byte[] fileByte, String filePath)
            throws Exception {
        File file = new File(filePath);
        FileOutputStream fs = new FileOutputStream(file);
        BufferedOutputStream bo = new BufferedOutputStream(fs);
        bo.write(fileByte);
        bo.close();
    }

    private void findAndWrite(String write) {
        try {
            File file1 = new File("src/GS/data.txt");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
            List list = new ArrayList();
            while (true) {
                String str = br1.readLine();
                if (str == null) break;
                int index = str.indexOf("INTERESTING");
                if (index != -1) {
                    str += "\n" + write;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            before b = new before();
            b.setVisible(true);
        });
    }
}
