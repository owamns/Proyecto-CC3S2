package produccion;

import java.util.Scanner;

public class Console {
    private SOSGameBoard board;

    public Console(SOSGameBoard board){
        this.board = board;
    }

    public void displayBoard(){
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
        Scanner inputReader =new Scanner(System.in);

        System.out.println("Ingrese el numero de cuadriculas por lado del tablero");
        int squareNumber = inputReader.nextInt();
        board = new SOSGameBoard(squareNumber);

        System.out.println("Ingrese true si desea que el modo de juego sea SIMPLE");
        System.out.println("En el modo SIMPLE el ganador es el que forma el primer SOS");
        System.out.println("O ingrese false si desea que el mode de juego sea GENERAL ");
        System.out.println("En el modo GENERAL, el ganador es el que ha formado mas SOS al final.");

        boolean isGameSimple = inputReader.nextBoolean();

        while(!board.isBoardFull()) {
            displayBoard();
            System.out.println("Es el turno del jugador " + board.getTurn() + ".");

            int row;
            int column;
            do {
                System.out.println("Ingrese la fila de su jugada: ");
                row = inputReader.nextInt();

                System.out.println("Ingrese la columna de su jugada");
                column = inputReader.nextInt();

                if (board.getCell(row, column) == null || board.getCell(row,column)!=SOSGameBoard.contentBoxes.EMPTY){
                    System.out.println("Ingrese numeros validos");
                }
            } while (board.getCell(row, column ) == null || board.getCell(row,column)!=SOSGameBoard.contentBoxes.EMPTY);

            System.out.println("Ingrese el caracter que desea ingresar, sea S o O");
            char chosen = inputReader.next().charAt(0);

            board.makePlay(row, column, (chosen=='S'? SOSGameBoard.contentBoxes.LETTER_S: SOSGameBoard.contentBoxes.LETTER_O));

            if ( isGameSimple && board.howManySOS(row, column, (chosen=='S'? SOSGameBoard.contentBoxes.LETTER_S: SOSGameBoard.contentBoxes.LETTER_O)) > 0 ) {
                board.setTurn((board.getTurn()=='B'?'R':'B'));
                System.out.println("El ganador es " + board.getTurn() + ". Felicidades!");
                return;
            }

            int pointsEarned = board.howManySOS(row, column, (chosen=='S'? SOSGameBoard.contentBoxes.LETTER_S: SOSGameBoard.contentBoxes.LETTER_O));
            char turn = board.getTurn();
            if(turn=='B'){
                board.getPlayers()[0].increaseScore(pointsEarned);
            }
            else {
                board.getPlayers()[1].increaseScore(pointsEarned);
            }
            if(board.getWinner()!=null){
                board.setTurn((turn=='B'?'R':'B'));
                System.out.println("El jugador " + board.getWinner().getName() + " ha ganado " + board.getWinner().getScore() + " puntos!");
            }
        }
    }

    public static void main(String[] args) {
        new Console(new SOSGameBoard()).play();
    }
}