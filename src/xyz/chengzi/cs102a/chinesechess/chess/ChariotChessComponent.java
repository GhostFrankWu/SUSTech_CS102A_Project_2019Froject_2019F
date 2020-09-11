package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ChariotChessComponent extends ChessComponent {
    //chessName存于父类,请用set and get
    public ChariotChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, char chessName) {
        super(chessboardPoint, location, color, chessName);
        this.setChessName(chessName);
    }


    /////////////////////////////////////////////////////////////////////////////////////////判断各种棋子是否能走
    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (chessboard[destination.getX()][destination.getY()].getChessColor() ==
                chessboard[source.getX()][source.getY()].getChessColor()) return false;
        if (this.getChessName() == 'c' || this.getChessName() == 'C') {
            if (source.getX() == destination.getX()) {
                int row = source.getX();
                for (int col = Math.min(source.getY(), destination.getY()) + 1; col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (source.getY() == destination.getY()) {
                int col = source.getY();
                for (int row = Math.min(source.getX(), destination.getX()) + 1; row < Math.max(source.getX(), destination.getX()); row++) {
                    if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        if (this.getChessName() == 'H' || this.getChessName() == 'h') {
            if ((source.getX() == destination.getX() + 1 || source.getX() == destination.getX() - 1) && (source.getY() == destination.getY() + 2 || source.getY() == destination.getY() - 2)) {
                if (source.getY() + 2 == destination.getY()) {
                    if (!(chessboard[source.getX()][source.getY() + 1] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else if (source.getY() - 2 == destination.getY()) {
                    if (!(chessboard[source.getX()][source.getY() - 1] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if ((source.getY() == destination.getY() + 1 || source.getY() == destination.getY() - 1) && (source.getX() == destination.getX() + 2 || source.getX() == destination.getX() - 2)) {
                if (source.getX() + 2 == destination.getX()) {
                    if (!(chessboard[source.getX() + 1][source.getY()] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else if (source.getX() - 2 == destination.getX()) {
                    if (!(chessboard[source.getX() - 1][source.getY()] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        if (this.getChessName() == 'E' || this.getChessName() == 'e') {
            if (this.getChessName() == 'e' && destination.getX() < 5) return false;
            if (this.getChessName() == 'E' && destination.getX() > 4) return false;
            if (source.getX() + 2 == destination.getX()) {
                if (source.getY() + 2 == destination.getY()) {
                    if (!(chessboard[source.getX() + 1][source.getY() + 1] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else if (source.getY() - 2 == destination.getY()) {
                    if (!(chessboard[source.getX() + 1][source.getY() - 1] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (source.getX() - 2 == destination.getX()) {
                if (source.getY() + 2 == destination.getY()) {
                    if (!(chessboard[source.getX() - 1][source.getY() + 1] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else if (source.getY() - 2 == destination.getY()) {
                    if (!(chessboard[source.getX() - 1][source.getY() - 1] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (this.getChessName() == 'a' || this.getChessName() == 'A') {
            if (this.getChessName() == 'a' && (destination.getY() < 3 || destination.getY() > 5 || destination.getX() < 7))
                return false;
            if (this.getChessName() == 'A' && (destination.getY() < 3 || destination.getY() > 5 || destination.getX() > 2))
                return false;
            if (destination.getY() + 1 == source.getY() || source.getY() + 1 == destination.getY()) {
                if (!(source.getX() + 1 == destination.getX() || source.getX() - 1 == destination.getX())) {
                    return false;
                }
            } else if (destination.getX() - 1 == source.getX() || source.getX() + 1 == destination.getX()) {
                if (!(source.getY() + 1 == destination.getY() || source.getY() - 1 == destination.getY())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (this.getChessName() == 'G' || this.getChessName() == 'g') {
            if (this.getChessName() == 'g' && (destination.getY() < 3 || destination.getY() > 5 || destination.getX() < 7))
                return false;
            if (this.getChessName() == 'G' && (destination.getY() < 3 || destination.getY() > 5 || destination.getX() > 2))
                return false;
            if (destination.getY() + 1 == source.getY() || source.getY() == destination.getY() - 1) {
                if (!(source.getX() == destination.getX())) {
                    return false;
                }
            } else if (destination.getX() - 1 == source.getX() || source.getX() == destination.getX() + 1) {
                if (!(source.getY() == destination.getY())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (this.getChessName() == 'S' || this.getChessName() == 's') {
            if (this.getChessName() == 's' && (destination.getX() > source.getX())) return false;
            if (this.getChessName() == 'S' && (destination.getX() < source.getX())) return false;
            if (this.getChessName() == 'S' && (source.getX() < 5)) {
                if (source.getX() == destination.getX() - 1) {
                    if (source.getY() != destination.getY()) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (this.getChessName() == 's' && (source.getX() > 4)) {
                if (source.getX() == destination.getX() + 1) {
                    if (source.getY() != destination.getY()) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (destination.getY() + 1 == source.getY() || source.getY() == destination.getY() - 1) {
                if (!(source.getX() == destination.getX())) {
                    return false;
                }
            } else if (destination.getX() - 1 == source.getX() || source.getX() == destination.getX() + 1) {
                if (!(source.getY() == destination.getY())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (this.getChessName() == 'n' || this.getChessName() == 'N') {
            int howManyInBetween = 0;
            if (source.getX() == destination.getX()) {
                int row = source.getX();
                for (int col = Math.min(source.getY(), destination.getY()) + 1; col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                        howManyInBetween++;
                    }
                }
                if (howManyInBetween > 1) {
                    return false;
                } else if (howManyInBetween == 0) {
                    return chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent;
                } else if (howManyInBetween == 1) {
                    return !(chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
                }
            } else if (source.getY() == destination.getY()) {
                int col = source.getY();
                for (int row = Math.min(source.getX(), destination.getX()) + 1; row < Math.max(source.getX(), destination.getX()); row++) {
                    if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                        howManyInBetween++;
                    }
                }
                if (howManyInBetween > 1) {
                    return false;
                } else if (howManyInBetween == 0) {
                    return chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent;
                } else if (howManyInBetween == 1) {
                    return !(chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
                }
            } else {
                return false;
            }
        }
        return true;
    }
    //////////////////////////////////////////////////////////////////////////////判断棋子是否能走结束

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage ChessComponentIcon;
        if (!isSelected()) try {
            ChessComponentIcon = ImageIO.read(new File("src/resources/" + "chess_pick_up" + ".png"));/////////////////////weixaunz
            g.drawImage(ChessComponentIcon, 0, 0, this.getWidth() + 55, this.getHeight() + 55, this);
        } catch (IOException ignored) {
        }
        else try {
            ChessComponentIcon = ImageIO.read(new File("src/resources/" + "chess_put_down" + ".png"));
            g.drawImage(ChessComponentIcon, -1, 2, this.getWidth() - 8, this.getHeight() - 15, this);
            ChessComponentIcon = ImageIO.read(new File("src/resources/huawen1.png"));
            g.drawImage(ChessComponentIcon, 0, 0, this.getWidth() - 10, this.getHeight() - 17, this);
        } catch (IOException ignored) {
        }
        if (!isSelected()) try {
            ChessComponentIcon = ImageIO.read(new File("src/resources/" + (int) this.getChessName() + ".png"));//////////weixuanz
            g.drawImage(ChessComponentIcon, 9, 5, this.getWidth() + 14, this.getHeight() + 13, this);
            //System.out.print(this.getWidth()+"  w-h  "+this.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        else try {
            ChessComponentIcon = ImageIO.read(new File("src/resources/" + (int) this.getChessName() + "_stand" + ".png"));
            g.drawImage(ChessComponentIcon, -6, -18, this.getWidth(), this.getHeight(), this);
        } catch (IOException ignored) {
        }
        if (super.isCanMoveTo()) {
            try {
                ChessComponentIcon = ImageIO.read(new File("src/resources/canMoveTo.png"));
                g.drawImage(ChessComponentIcon, 10, 10, this.getWidth() - 5, this.getHeight() - 5, this);
            } catch (IOException ignored) {
            }
        }
        if (isMoved()) { // Highlights the chess if selected.
            g.setColor(Color.BLUE);
            g.drawRect(3, 0, getWidth() - 10, getHeight() - 10);
        }
    }
}
