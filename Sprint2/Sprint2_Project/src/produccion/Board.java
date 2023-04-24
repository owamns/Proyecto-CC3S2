package produccion;

public class Board {

    private int squaresPerSide;

    private int turn;

    private char[][] grid;

    private int[] score;

    public Board(){
        squaresPerSide = 3;
        grid = new char[squaresPerSide][squaresPerSide];
        for ( int row = 0; row < squaresPerSide; row++ ){
            for ( int column = 0; column < squaresPerSide; column++ ){
                grid[row][column] = '-';
            }
        }
        turn = 1;
        score = new int[2];
    }

    public Board(int squaresPerSide){
        this.squaresPerSide = squaresPerSide;
        grid = new char[squaresPerSide][squaresPerSide];
        for ( int row = 0; row < squaresPerSide; row++ ){
            for ( int column = 0; column < squaresPerSide; column++ ){
                grid[row][column] = '-';
            }
        }
        turn = 1;
        score = new int[2];
    }

    public int getSquaresPerSide() { return squaresPerSide; };

    public char getCell(int row, int column){
        if ( row >= 0 && row < squaresPerSide && column >= 0 && column < squaresPerSide ){
            return grid[row][column];
        }
        else return 'X';
    }

    public int getTurn(){
        return turn;
    }

    public void changeTurn(){
        turn = 3 - turn;
    }

    public void makePlay(int row, int column, char chosen){
        if ( row >= 0 && row < squaresPerSide && column >= 0 && column < squaresPerSide ){
            grid[row][column] = chosen;
        }
        else {
            throw new IllegalArgumentException("El argumento debe estar dentro de los limites del tablero");
        }
    }

    public void increaseScore(int player, int points){
        int index = player - 1;
        score[index] = score[index] + points;
    }

    public void getWinner(){
        if ( score[0] > score[1] ) System.out.println("EL ganador " + 1 + " ha ganado con " + score[0] + " puntos. Felicidades!");
        else if ( score[0] < score[1] ) System.out.println("El ganador " + 2 + " ha ganado con " + score[1] + " puntos. Felicidades!");
        else System.out.println("Es un empate de " + score[1] + "puntos!");
    }

    public boolean isBoardFull(){
        for (int row = 0; row < squaresPerSide; row++){
            for (int column = 0; column < squaresPerSide; column++){
                if (getCell(row, column) == '-') return false;
            }
        }
        return true;
    }

    public int howManySOS(int row, int column, char chosen){
        int[][] around = new int[][]{ {-1,-1} , {0,-1} , {1,-1} , {-1,0} , {1,0} , {-1,1} , {0,1} , {1,1} } ;

        int points = 0;

        if ( chosen == 'S' ){
            for( int i = 0; i < 8; i++ ){
                if( getCell(row + around[i][0],column + around[i][1]) == 'O' && getCell(row + 2*around[i][0],column + 2*around[i][1]) == 'S' ) points++;
            }
        }

        else if ( chosen == 'O' ){
            for( int i = 0; i < 4; i++ ){
                if( getCell(row + around[i][0],column + around[i][1]) == 'S' && getCell(row - around[i][0],column - around[i][1]) == 'S' ) points++;
            }

        }
        return points;
    }

}
