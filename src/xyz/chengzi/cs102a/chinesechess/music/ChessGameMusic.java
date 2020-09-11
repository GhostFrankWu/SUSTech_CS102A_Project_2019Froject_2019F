package xyz.chengzi.cs102a.chinesechess.music;
//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////这个文件是放比较长的音频的，只接受WAV
/////////////////////////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class ChessGameMusic {

    SourceDataLine auline = null; // 源数据线721

    public void run(String fileName, float f) {
        File soundFile = new File(fileName); // 播放音乐的文件名
        AudioInputStream audioInputStream = null; // 创建音频输入流对象
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile); // 创建音频对象
        } catch (UnsupportedAudioFileException | IOException ignored) {
        }
        assert audioInputStream != null;
        AudioFormat format = audioInputStream.getFormat(); // 音频格式
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        int nBytesRead = 0;
        byte[] abData = new byte[524288];// 128k
        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
            FloatControl gainControl = (FloatControl) auline.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(f); // 10分贝降低音量。
            auline.start();
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
            auline.drain();
            auline.close();
        } catch (IOException | LineUnavailableException ignored) {
        }
    }
}
