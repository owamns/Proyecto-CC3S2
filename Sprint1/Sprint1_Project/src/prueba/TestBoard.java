package prueba;

import org.junit.jupiter.api.Test;
import produccion.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoard {

    public static int sideLength = 5;
    private static Board board = new Board(sideLength);

    //Criterio de Aceptacion 1.1
    @Test
    public void testEmptyBoard(){
        for (int row = 0; row < sideLength; row++){
            for (int column = 0; column < sideLength; column++){
                assertEquals(0, board.getCell(row,column));
            }
        }
    }

    //Criterio de Aceptacion 1.2
    @Test
    public void testInvalidRow(){
        assertEquals(-1, board.getCell(sideLength,1));
    }

    //Criterio de Aceptacion 1.3
    @Test
    public void testInvalidColumn(){
        assertEquals(-1, board.getCell(1,sideLength));
    }

    @Test
    public void testIsGameFinishedTrue(){
        for (int row = 0; row < sideLength; row++){
            for (int column = 0; column < sideLength; column++){
                board.setCell(row, column);
            }
        }
        assertEquals(true, board.isGameFinished());
    }
}
