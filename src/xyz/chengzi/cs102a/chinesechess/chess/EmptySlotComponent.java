package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EmptySlotComponent extends ChessComponent {
    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location) {
        super(chessboardPoint, location, ChessColor.NONE, '.');
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage ChessComponentIcon;
        if(super.isCanMoveTo()){
            try {
                ChessComponentIcon = ImageIO.read(new File("src/resources/canMove.png"));
                g.drawImage(ChessComponentIcon,15,15,this.getWidth()-10,this.getHeight()-10,this);
            } catch (IOException ignored) {}
        }
        if (super.isMoved()) { // Highlights the chess if moved
            g.setColor(Color.BLUE);
            g.drawRect(3, 0, getWidth() - 10, getHeight() - 10);
        }
    }
}
