package produccion;

public class Board {

    private int squaresPerSide;

    private boolean turn;

    private int[][] grid;

    public Board() {
        squaresPerSide = 3;
        grid  = new int[3][3];
        turn = true;
    }

    public Board(int squaresPerSide){
        this.squaresPerSide = squaresPerSide;
        grid = new int[squaresPerSide][squaresPerSide];
        turn = true;
    }

    public int getSquaresPerSide() {
        return squaresPerSide;
    }

    public void setCell(int row, int column){
        if (row >= 0 && row < squaresPerSide && column >= 0 && column < squaresPerSide) {
            if (turn){
                grid[row][column] = 1;
                turn = false;
            } else{
                grid[row][column] = 2;
                turn = true;
            }
        }
        else {
            throw new IllegalArgumentException("El argumento debe estar dentro de los limites de la cuadricula");
        }
    }

    public int getCell(int row, int column){
        if (row >= 0 && row < squaresPerSide && column >= 0 && column < squaresPerSide) {
            return grid[row][column];
        }
        else
            return -1;
    }

    public boolean isGameFinished(){
        //boolean isBoardFull = true;
        for (int row = 0; row < squaresPerSide; row++){
            for (int column = 0; column < squaresPerSide; column++){
                System.out.println("test" + squaresPerSide + " " + getCell(row,column));
                if (getCell(row, column) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
