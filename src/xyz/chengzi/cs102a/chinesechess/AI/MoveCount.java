package xyz.chengzi.cs102a.chinesechess.AI;

public class MoveCount {
    //这里是初始位置和目标位置
    int fromRow, fromCol, toRow, toCol;
    int value;

    public MoveCount(int fromRow, int fromCol, int toRow, int toCol, int value) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.value = value;
    }

    public MoveCount(int value) {
        this.value = value;
    }

    public MoveCount() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToCol() {
        return toCol;
    }

    public int getToRow() {
        return toRow;
    }
}
