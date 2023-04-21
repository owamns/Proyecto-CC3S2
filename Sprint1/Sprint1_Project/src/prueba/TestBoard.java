package prueba;

import org.junit.jupiter.api.Test;
import produccion.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBoard {

    private static Board board = new Board();

    //Criterio de Aceptacion 1.1
    @Test
    public void testEmptyBoard(){
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                assertEquals(0, board.getCell(row,column));
            }
        }
    }

    //Criterio de Aceptacion 1.2
    @Test
    public void testInvalidRow(){
        assertEquals(-1, board.getCell(4,1));
    }

    //Criterio de Aceptacion 1.3
    @Test
    public void testInvalidColumn(){
        assertEquals(-1, board.getCell(1,4));
    }

    @Test
    public void testIsGameFinishedFalse(){
        assertEquals(false, board.isGameFinished());
    }

    @Test
    public void testIsGameFinishedTrue(){
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                board.setCell(row, column);
            }
        }
        assertEquals(true, board.isGameFinished());
    }
}
