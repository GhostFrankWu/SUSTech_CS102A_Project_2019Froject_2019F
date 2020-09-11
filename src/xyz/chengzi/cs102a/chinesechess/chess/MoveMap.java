package xyz.chengzi.cs102a.chinesechess.chess;

public class MoveMap {
    private int fromRow, fromCol, toRow, toCol;
    private ChessComponent fromChess, toChess;
    private ChessColor moveColor;

    public MoveMap(ChessComponent fromChess, ChessComponent toChess, ChessColor moveColor) {
        if (moveColor == ChessColor.BLACK) {
            this.fromRow = fromChess.getChessboardPoint().getX() + 1;
            this.fromCol = fromChess.getChessboardPoint().getY() + 1;
            this.toRow = toChess.getChessboardPoint().getX() + 1;
            this.toCol = toChess.getChessboardPoint().getY() + 1;
        } else {
            this.fromRow = 10 - fromChess.getChessboardPoint().getX();
            this.fromCol = 9 - fromChess.getChessboardPoint().getY();
            this.toRow = 10 - toChess.getChessboardPoint().getX();
            this.toCol = 9 - toChess.getChessboardPoint().getY();
        }
        this.fromChess = fromChess;
        this.toChess = toChess;
        this.moveColor = moveColor;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public ChessComponent getFromChess() {
        return fromChess;
    }

    public ChessComponent getToChess() {
        return toChess;
    }

    public ChessColor getMoveColor() {
        return moveColor;
    }
}
