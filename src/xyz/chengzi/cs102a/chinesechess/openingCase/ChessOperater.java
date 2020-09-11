package xyz.chengzi.cs102a.chinesechess.openingCase;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChessOperater {
    private String qs_name;
    private String qs_data;

    public String readName(int x) {
        qs_data = "";
        qs_name = "";
        String find = x != 1 ? ("" + x) : ("0" + x);
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
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qs_name;
    }

    public static void findAndWrite(int x) {
        try {
            File file1 = new File("src/GS/data.txt");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
            List list = new ArrayList();
            while (true) {
                String str = br1.readLine();
                if (str == null) break;
                int index = str.indexOf("Rank");
                if (index != -1) {
                    list.add(str);
                    String st="";
                    int[] Rank={-1,-1,-1,-1};
                    for(int i=0;i<4;i++) {
                        while (Rank[i] < 0)
                            Rank[i] = br1.read() - 48;
                        int a = br1.read();
                        while ( a>= 48) {
                            Rank[i] *= 10;
                            Rank[i] += a - 48;
                            a=br1.read();
                        }
                        if (i == x)
                            Rank[i] += 1;
                        st=(Rank[i]+"");
                        list.add(st);
                        st="";
                    }
                }else {
                    list.add(str);
                }
            }
            br1.close();
            BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8));
            for (int i=0;i<list.size();i++)
                pw.write((String) list.get(i) + "\r\n");
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] readRank() {
        int[] Rank ={-1,-1,-1,-1};
        try {
            File file = new File("src/GS/data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            while (true) {
                String str = br.readLine();
                if (str == null) break;
                int index = str.indexOf("Rank");
                if (index != -1) {
                    for(int i=0;i<4;i++) {
                        while (Rank[i]<0)
                        Rank[i] = br.read() - 48;
                        int a = br.read();
                        if (a >= 48) {
                            Rank[i] *= 10;
                            Rank[i] += a - 48;
                        } else {
                            br.read();
                        }
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Rank;
    }

    public String readData(int x) {
        String find = x != 1 ? ("" + x) : ("0" + x);
        qs_data = "";
        qs_name = "";
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
        return qs_data;
    }
}
