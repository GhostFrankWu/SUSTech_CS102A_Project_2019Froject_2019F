package xyz.chengzi.cs102a.chinesechess.music;
//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////这个是两秒男，只接受MP3，
/////////////////////////////////////////////////////////////////////////////////////////////////

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import java.io.File;

import javafx.embed.swing.JFXPanel;

public class ChessGameMusicSmall {
    private String str;

    public void play(String str, int a) {
        this.str = str;
        Thread t1;
        try {
            if (a == 1) {
                t1 = new Processor2();
            } else if (a == 2) {
                t1 = new Processor3();
            } else if (a == 3) {
                t1 = new Processor4();
            } else if (a == 9) {
                t1 = new Processor9();
            } else {
                t1 = new Processor1();
            }
            t1.start();
        } catch (Exception ignored) {
        }
    }


    class Processor1 extends Thread {                  //线程

        public void run() {
            final JFXPanel fxPanel = new JFXPanel();
            // to initialize JavaFx
            File file = new File(str);
            final Media media = new Media(file.toURI().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    class Processor2 extends Thread {                  //线程

        public void run() {
            final JFXPanel fxPanel = new JFXPanel();
            // to initialize JavaFx
            File file = new File(str);
            final Media media = new Media(file.toURI().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    class Processor3 extends Thread {                  //线程

        public void run() {
            final JFXPanel fxPanel = new JFXPanel();
            // to initialize JavaFx
            File file = new File(str);
            final Media media = new Media(file.toURI().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    class Processor4 extends Thread {                  //线程

        public void run() {
            final JFXPanel fxPanel = new JFXPanel();
            // to initialize JavaFx
            File file = new File(str);
            final Media media = new Media(file.toURI().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    class Processor9 extends Thread {                  //线程

        public void run() {
            final JFXPanel fxPanel = new JFXPanel();
            // to initialize JavaFx
            File file = new File(str);
            final Media media = new Media(file.toURI().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }
}
