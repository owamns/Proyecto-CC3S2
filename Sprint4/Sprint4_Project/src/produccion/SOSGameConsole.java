package produccion;
import java.util.Scanner;

public class SOSGameConsole {
    private SOSGameBoard board;

    public SOSGameConsole(SOSGameBoard board){
        this.board = board;
    }

    public void displayBoard(){
        for( int row = 0; row < board.getSquaresPerSide(); row++ ){
            System.out.println("-------");
            for( int column = 0; column < board.getSquaresPerSide(); column++ ){
                System.out.print("|" + toLetter(board.getBox(row, column)));
            }
            System.out.println("|");
        }
        System.out.println("-------");

    }

    private char toLetter(SOSGameBoard.Box box){
        if (box == SOSGameBoard.Box.LETTER_S) return 'S';
        else if(box == SOSGameBoard.Box.LETTER_O) return 'O';
        else return ' ';
    }

    public void play(){
        Scanner inRead = new Scanner(System.in);

        System.out.println("Ingrese el numero de cuadriculas por lado del tablero");
        int squareNumber = inRead.nextInt();

        System.out.println("Ingrese S si desea que el modo de juego sea SIMPLE");
        System.out.println("En el modo SIMPLE el ganador es el que forma el primer SOS");
        System.out.println("O ingrese G si desea que el mode de juego sea GENERAL ");
        System.out.println("En el modo GENERAL, el ganador es el que ha formado mas SOS al final.");
        char gameType = inRead.next().charAt(0);

        board = new SOSGameBoard(squareNumber, gameType);

        while(!board.isBoardFull()){
            displayBoard();
            System.out.println("Es el turno del jugador " + board.getActivePlayer().getName() + ".");
            int row, column;
            do{
                System.out.println("Ingrese la fila de su jugada: ");
                row = inRead.nextInt();

                System.out.println("Ingrese la columna de su jugada");
                column = inRead.nextInt();

                if (board.getBox(row, column) == null || board.getBox(row,column)!=SOSGameBoard.Box.EMPTY){
                    System.out.println("Ingrese numeros validos");
                }
            } while(board.getBox(row, column) == null || board.getBox(row, column) != SOSGameBoard.Box.EMPTY);

            System.out.println("Ingrese el caracter que desea ingresar, sea S o O");
            char chosenChar = inRead.next().charAt(0);
            SOSGameBoard.Box chosen = chosenChar == 'S' ? SOSGameBoard.Box.LETTER_S: SOSGameBoard.Box.LETTER_O;
            board.makePlay(row, column, chosen);

            if( board.getGameType() == 'S' && board.atLeastOneSOS(row, column, chosen) ){
                System.out.println("El ganador es " + board.getActivePlayer().getName() + ". Felicidades!");
                return;
            }

            if( board.atLeastOneSOS(row, column, chosen) ){
                int pointsEarned = board.howManySOS(row, column, chosen);
                board.getActivePlayer().increaseScore(pointsEarned);
            } else board.changeActivePlayer();
        }
        if ( board.getWinner() != null) System.out.println("El ganador es " + board.getWinner().getName() + " con " + board.getActivePlayer().getScore() + " puntos.");
        else System.out.println("El juego termina en empate!");

    }

    public static void main(String[] args) {
        new SOSGameConsole(new SOSGameBoard()).play();
    }
}
