package xyz.chengzi.cs102a.chinesechess.AI;

import xyz.chengzi.cs102a.chinesechess.chess.ChessColor;
import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

public class ComputerMove {//每次使用请重新new一个
    ChessComponent[] blackChess = new ChessComponent[20];
    ChessComponent[] redChess = new ChessComponent[20];
    int black = 0;
    int red = 0;

    public ComputerMove(ChessboardComponent chessboard) { //初始化，请new一个对象并使用它
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (chessboard.getChessboard()[i][j].getChessColor() == ChessColor.BLACK) {
                    black++;
                    blackChess[black] = chessboard.getChessboard()[i][j];
                    if (chessboard.getChessboard()[i][j].getChessName() == 'G') {
                        chessboard.getChessboard()[i][j].setChessValue(900);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'A') {
                        chessboard.getChessboard()[i][j].setChessValue(20);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'E') {
                        chessboard.getChessboard()[i][j].setChessValue(30);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'H') {
                        chessboard.getChessboard()[i][j].setChessValue(80);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'C') {
                        chessboard.getChessboard()[i][j].setChessValue(90);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'N') {
                        chessboard.getChessboard()[i][j].setChessValue(70);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'S') {
                        chessboard.getChessboard()[i][j].setChessValue(10);
                    }
                }
                if (chessboard.getChessboard()[i][j].getChessColor() == ChessColor.RED) {
                    red++;
                    redChess[red] = chessboard.getChessboard()[i][j];
                    if (chessboard.getChessboard()[i][j].getChessName() == 'g') {
                        chessboard.getChessboard()[i][j].setChessValue(900);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'a') {
                        chessboard.getChessboard()[i][j].setChessValue(20);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'e') {
                        chessboard.getChessboard()[i][j].setChessValue(30);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'h') {
                        chessboard.getChessboard()[i][j].setChessValue(80);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'c') {
                        chessboard.getChessboard()[i][j].setChessValue(90);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 'n') {
                        chessboard.getChessboard()[i][j].setChessValue(70);
                    }
                    if (chessboard.getChessboard()[i][j].getChessName() == 's') {
                        chessboard.getChessboard()[i][j].setChessValue(10);
                    }
                }
            }
        }
    }

    public MoveCount BlackCalculateMove(ComputerMove computer, ChessboardComponent chessboard) {
        //黑方计算行动
        MoveCount bestMove = new MoveCount(-1);
        ChessMoveBFS bfs = new ChessMoveBFS();
        for (int i = 1; i < computer.black; i++) {
            if (blackChess[i].getChessName() == 'G') {
                MoveCount bestMoveTemp = bfs.moveG(chessboard, blackChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (blackChess[i].getChessName() == 'A') {
                MoveCount bestMoveTemp = bfs.moveA(chessboard, blackChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (blackChess[i].getChessName() == 'E') {
                MoveCount bestMoveTemp = bfs.moveE(chessboard, blackChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (blackChess[i].getChessName() == 'H') {
                MoveCount bestMoveTemp = bfs.moveH(chessboard, blackChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (blackChess[i].getChessName() == 'C') {
                MoveCount bestMoveTemp = bfs.moveC(chessboard, blackChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (blackChess[i].getChessName() == 'N') {
                MoveCount bestMoveTemp = bfs.moveN(chessboard, blackChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (blackChess[i].getChessName() == 'S') {
                MoveCount bestMoveTemp = bfs.moveS(chessboard, blackChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
        }
        return bestMove;
    }

    public MoveCount RedCalculateMove(ComputerMove computer, ChessboardComponent chessboard) {
        //红方计算行动
        MoveCount bestMove = new MoveCount(-1);
        ChessMoveBFS bfs = new ChessMoveBFS();
        for (int i = 1; i < computer.red; i++) {
            if (redChess[i].getChessName() == 'g') {
                MoveCount bestMoveTemp = bfs.moveG(chessboard, redChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (redChess[i].getChessName() == 'a') {
                MoveCount bestMoveTemp = bfs.moveA(chessboard, redChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (redChess[i].getChessName() == 'e') {
                MoveCount bestMoveTemp = bfs.moveE(chessboard, redChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (redChess[i].getChessName() == 'h') {
                MoveCount bestMoveTemp = bfs.moveH(chessboard, redChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (redChess[i].getChessName() == 'c') {
                MoveCount bestMoveTemp = bfs.moveC(chessboard, redChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (redChess[i].getChessName() == 'n') {
                MoveCount bestMoveTemp = bfs.moveN(chessboard, redChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
            if (redChess[i].getChessName() == 's') {
                MoveCount bestMoveTemp = bfs.moveS(chessboard, redChess[i]);
                if (bestMoveTemp.getValue() > bestMove.getValue()) {
                    bestMove = bestMoveTemp;
                }
            }
        }
        return bestMove;
    }
}
