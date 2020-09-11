package xyz.chengzi.cs102a.chinesechess.chess;

import java.awt.*;

public enum ChessColor {
    BLACK("Black", Color.BLACK), RED("Red", Color.RED), NONE("No Player", Color.WHITE);

    private String name;
    private Color color;

    ChessColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
