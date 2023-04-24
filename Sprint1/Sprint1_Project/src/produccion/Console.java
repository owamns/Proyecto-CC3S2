package produccion;

import java.util.Scanner;

public class Console {
    private Board board;

    public Console(Board board) {
        this.board = board;
    }

    public void displayBoard() {
        for (int row = 0; row < board.getSquaresPerSide(); row++) {
            System.out.println("-------");
            for (int column = 0; column < board.getSquaresPerSide(); column++){
                System.out.print("|" + board.getCell(row,column));
            }
            System.out.println("|");
        }
        System.out.println("-------");
    }

    public void play(){
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Ingrese el numero de columnas del tablero:");
        int numeroDeCuadriculas = inputReader.nextInt();

        board = new Board(numeroDeCuadriculas);

        while(true) {
            displayBoard();

            System.out.println("Ingrese la columna de su jugada:");
            int fila = inputReader.nextInt();

            System.out.println("Ingrese la fila de su jugada:");
            int columna = inputReader.nextInt();

            board.setCell(columna - 1, fila - 1);
        }
    }

    public static void main(String[] args) {
        new Console(new Board()).play();
    }
}
