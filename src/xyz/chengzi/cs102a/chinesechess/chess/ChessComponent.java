package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;
import xyz.chengzi.cs102a.chinesechess.listener.ChessListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;

public abstract class ChessComponent extends JComponent {
    public final static Dimension CHESS_SIZE = new Dimension(75, 75);

    private static List<ChessListener> listenerList = new ArrayList<>();

    private ChessboardPoint chessboardPoint;
    private ChessColor chessColor;
    private boolean selected, canMoveTo, moved;
    private char chessName;
    private int chessValue=1;

    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, char chessName) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(CHESS_SIZE);

        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.chessName = chessName;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void swapLocation(ChessComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            for (ChessListener listener : listenerList) {
                try {
                    listener.onClick(this);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public abstract boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination);

    public static boolean registerListener(ChessListener listener) {
        return listenerList.add(listener);
    }

    public static boolean unregisterListener(ChessListener listener) {
        return listenerList.remove(listener);
    }

    public char getChessName() {
        return chessName;
    }

    public void setChessName(char chessName) {
        this.chessName = chessName;
    }

    public boolean isCanMoveTo() {
        return canMoveTo;
    }

    public void setCanMoveTo(boolean canMoveTo) {
        this.canMoveTo = canMoveTo;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public int getChessValue() {
        return chessValue;
    }

    public void setChessValue(int chessValue) {
        this.chessValue = chessValue;
    }
}
