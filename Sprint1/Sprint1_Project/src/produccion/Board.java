package produccion;

public class Board {
    private int[][] grid;

    public Board() { grid  = new int[3][3]; }

    public void setCell(int row, int column){
        if (row >= 0 && row < 3 && column >= 0 && column < 3) {
            grid[row][column] = 1;
        }
        else {
            throw new IllegalArgumentException("El argumento debe estar dentro de los limites de la cuadricula");
        }
    }

    public int getCell(int row, int column){
        if (row >= 0 && row < 3 && column >= 0 && column < 3) {
            return grid[row][column];
        }
        else
            return -1;
    }

    public boolean isGameFinished(){
        //boolean isBoardFull = true;
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                if (getCell(row, column) == 0)
                    return false;
            }
        }
        return true;
    }

    public void makePlay(int row, int column){

    }

}
