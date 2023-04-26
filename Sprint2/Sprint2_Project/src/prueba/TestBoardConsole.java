package prueba;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import produccion.Board;
import produccion.Console;

import java.util.Random;

public class TestBoardConsole {

    static Random random = new Random();
    static int rand = random.nextInt(8) + 3;
    public static int sideLength = rand;

    public static Board board;

    @BeforeAll
    public static void setUp() {
        board = new Board(sideLength);
    }

    @Test
    public void testEmptyBoardOnConsole(){
        new Console(board).displayBoard();
    }
}
