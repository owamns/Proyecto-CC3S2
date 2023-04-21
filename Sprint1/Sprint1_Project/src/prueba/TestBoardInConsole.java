package prueba;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import produccion.Board;
import produccion.Console;

public class TestBoardInConsole {
    private static Board board;

    @BeforeAll
    public static void setUp() {
        board = new Board();
    }

    @Test
    public void testEmptyBoardOnConsole(){
        new Console(board).displayBoard();
    }
}
