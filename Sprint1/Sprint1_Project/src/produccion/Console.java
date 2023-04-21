package produccion;

import java.sql.SQLOutput;

public class Console {
    private Board board;

    public Console(Board board) { this.board = board; }

    public void displayBoard() {
        for (int row = 0; row < 3; row++) {
            System.out.println("-------");
            for (int column = 0; column < 3; column++){
                System.out.print("|" + board.getCell(row,column));
            }
            System.out.println("|");
        }
        System.out.println("-------");
    }

    public static void main(String[] args) {
        new Console(new Board()).displayBoard();
    }
}
