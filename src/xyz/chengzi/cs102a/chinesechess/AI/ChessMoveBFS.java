package xyz.chengzi.cs102a.chinesechess.AI;

import xyz.chengzi.cs102a.chinesechess.chess.ChariotChessComponent;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

public class ChessMoveBFS {

    public MoveCount moveG(ChessboardComponent chessboard, ChessComponent chessG) {
        MoveCount bestMove = new MoveCount(-1);
        int row = chessG.getChessboardPoint().getX(), col = chessG.getChessboardPoint().getY();
        ChariotChessComponent check = new ChariotChessComponent(chessG.getChessboardPoint(), chessG.getLocation(), chessG.getChessColor(), chessG.getChessName());
        if (col - 1 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row, col - 1))) {
                int stepValue = chessboard.getChessboard()[row][col - 1].getChessValue() - 1;
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row, col - 1, stepValue);
                }
            }
        }
        if (col + 1 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row, col + 1))) {
                int stepValue = chessboard.getChessboard()[row][col + 1].getChessValue() - 1;
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row, col + 1, stepValue);
                }
            }
        }
        if (row - 1 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 1, col))) {
                int stepValue = chessboard.getChessboard()[row - 1][col].getChessValue() - 1;
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 1, col, stepValue);
                }
            }
        }
        if (row + 1 <= 9) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 1, col))) {
                int stepValue = chessboard.getChessboard()[row + 1][col].getChessValue() - 1;
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 1, col, stepValue);
                }
            }
        }
        return bestMove;
    }

    public MoveCount moveA(ChessboardComponent chessboard, ChessComponent chessA) {
        MoveCount bestMove = new MoveCount(-1);
        int row = chessA.getChessboardPoint().getX(), col = chessA.getChessboardPoint().getY();
        ChariotChessComponent check = new ChariotChessComponent(chessA.getChessboardPoint(), chessA.getLocation(), chessA.getChessColor(), chessA.getChessName());
        if (row + 1 <= 9 && col + 1 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 1, col + 1))) {
                int stepValue = chessboard.getChessboard()[row + 1][col + 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 1, col + 1, stepValue);
                }
            }
        }
        if (row + 1 <= 9 && col - 1 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 1, col - 1))) {
                int stepValue = chessboard.getChessboard()[row + 1][col - 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 1, col - 1, stepValue);
                }
            }
        }
        if (row - 1 >= 0 && col - 1 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 1, col - 1))) {
                int stepValue = chessboard.getChessboard()[row - 1][col - 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 1, col - 1, stepValue);
                }
            }
        }
        if (row - 1 >= 0 && col + 1 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 1, col + 1))) {
                int stepValue = chessboard.getChessboard()[row - 1][col + 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 1, col + 1, stepValue);
                }
            }
        }
        return bestMove;
    }

    public MoveCount moveE(ChessboardComponent chessboard, ChessComponent chessE) {
        MoveCount bestMove = new MoveCount(-1);
        int row = chessE.getChessboardPoint().getX(), col = chessE.getChessboardPoint().getY();
        ChariotChessComponent check = new ChariotChessComponent(chessE.getChessboardPoint(), chessE.getLocation(), chessE.getChessColor(), chessE.getChessName());
        if (row + 2 <= 9 && col + 2 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 2, col + 2))) {
                int stepValue = chessboard.getChessboard()[row + 2][col + 2].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 2, col + 2, stepValue);
                }
            }
        }
        if (row + 2 <= 9 && col - 2 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 2, col - 2))) {
                int stepValue = chessboard.getChessboard()[row + 2][col - 2].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 2, col - 2, stepValue);
                }
            }
        }
        if (row - 2 >= 0 && col - 2 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 2, col - 2))) {
                int stepValue = chessboard.getChessboard()[row - 2][col - 2].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 2, col - 2, stepValue);
                }
            }
        }
        if (row - 2 >= 0 && col + 2 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 2, col + 2))) {
                int stepValue = chessboard.getChessboard()[row - 2][col + 2].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 2, col + 2, stepValue);
                }
            }
        }
        return bestMove;
    }

    public MoveCount moveH(ChessboardComponent chessboard, ChessComponent chessH) {
        MoveCount bestMove = new MoveCount(-1);
        int row = chessH.getChessboardPoint().getX(), col = chessH.getChessboardPoint().getY();
        ChariotChessComponent check = new ChariotChessComponent(chessH.getChessboardPoint(), chessH.getLocation(), chessH.getChessColor(), chessH.getChessName());
        if (row + 2 <= 9 && col + 1 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 2, col + 1))) {
                int stepValue = chessboard.getChessboard()[row + 2][col + 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 2, col + 1, stepValue);
                }
            }
        }
        if (row + 2 <= 9 && col - 1 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 2, col - 1))) {
                int stepValue = chessboard.getChessboard()[row + 2][col - 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 2, col - 1, stepValue);
                }
            }
        }
        if (row - 2 >= 0 && col - 1 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 2, col - 1))) {
                int stepValue = chessboard.getChessboard()[row - 2][col - 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 2, col - 1, stepValue);
                }
            }
        }
        if (row - 2 >= 0 && col + 1 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 2, col + 1))) {
                int stepValue = chessboard.getChessboard()[row - 2][col + 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 2, col + 1, stepValue);
                }
            }
        }
        if (row + 1 <= 9 && col + 2 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 1, col + 2))) {
                int stepValue = chessboard.getChessboard()[row + 1][col + 2].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 1, col + 2, stepValue);
                }
            }
        }
        if (row + 1 <= 9 && col - 2 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 1, col - 2))) {
                int stepValue = chessboard.getChessboard()[row + 1][col - 2].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 1, col - 2, stepValue);
                }
            }
        }
        if (row - 1 >= 0 && col - 2 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 1, col - 2))) {
                int stepValue = chessboard.getChessboard()[row - 1][col - 2].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 1, col - 2, stepValue);
                }
            }
        }
        if (row - 1 >= 0 && col + 2 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 1, col + 2))) {
                int stepValue = chessboard.getChessboard()[row - 1][col + 2].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 1, col + 2, stepValue);
                }
            }
        }
        return bestMove;
    }

    public MoveCount moveC(ChessboardComponent chessboard, ChessComponent chessC) {
        MoveCount bestMove = new MoveCount(-1);
        int row = chessC.getChessboardPoint().getX(), col = chessC.getChessboardPoint().getY();
        ChariotChessComponent check = new ChariotChessComponent(chessC.getChessboardPoint(), chessC.getLocation(), chessC.getChessColor(), chessC.getChessName());
        for (int i = 0; i <= 9; i++) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(i, col))) {
                int stepValue = chessboard.getChessboard()[i][col].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, i, col, stepValue);
                }
            }
        }
        for (int i = 0; i <= 8; i++) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row, i))) {
                int stepValue = chessboard.getChessboard()[row][i].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row, i, stepValue);
                }
            }
        }
        return bestMove;
    }

    public MoveCount moveN(ChessboardComponent chessboard, ChessComponent chessN) {
        MoveCount bestMove = new MoveCount(-1);
        int row = chessN.getChessboardPoint().getX(), col = chessN.getChessboardPoint().getY();
        ChariotChessComponent check = new ChariotChessComponent(chessN.getChessboardPoint(), chessN.getLocation(), chessN.getChessColor(), chessN.getChessName());
        for (int i = 0; i <= 9; i++) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(i, col))) {
                int stepValue = chessboard.getChessboard()[i][col].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, i, col, stepValue);
                }
            }
        }
        for (int i = 0; i <= 8; i++) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row, i))) {
                int stepValue = chessboard.getChessboard()[row][i].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row, i, stepValue);
                }
            }
        }
        return bestMove;
    }

    public MoveCount moveS(ChessboardComponent chessboard, ChessComponent chessS) {
        MoveCount bestMove = new MoveCount(-1);
        int row = chessS.getChessboardPoint().getX(), col = chessS.getChessboardPoint().getY();
        ChariotChessComponent check = new ChariotChessComponent(chessS.getChessboardPoint(), chessS.getLocation(), chessS.getChessColor(), chessS.getChessName());
        if (col - 1 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row, col - 1))) {
                int stepValue = chessboard.getChessboard()[row][col - 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row, col - 1, stepValue);
                }
            }
        }
        if (col + 1 <= 8) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row, col + 1))) {
                int stepValue = chessboard.getChessboard()[row][col + 1].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row, col + 1, stepValue);
                }
            }
        }
        if (row - 1 >= 0) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row - 1, col))) {
                int stepValue = chessboard.getChessboard()[row - 1][col].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row - 1, col, stepValue);
                }
            }
        }
        if (row + 1 <= 9) {
            if (check.canMoveTo(chessboard.getChessboard(), new ChessboardPoint(row + 1, col))) {
                int stepValue = chessboard.getChessboard()[row + 1][col].getChessValue();
                if (stepValue > bestMove.getValue()) {
                    bestMove = new MoveCount(row, col, row + 1, col, stepValue);
                }
            }
        }
        return bestMove;
    }

}
