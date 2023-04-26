package prueba;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import produccion.SOSGameBoard;
import produccion.Console;

import java.util.Random;

public class TestConsole {

    static Random random = new Random();
    static int rand = random.nextInt(8) + 3;
    public static int sideLength = rand;

    public static SOSGameBoard board;

    @BeforeAll
    public static void setUp() {
        board = new SOSGameBoard(sideLength);
    }

    @Test
    public void testEmptyBoardOnConsole(){
        new Console(board).displayBoard();
    }
}
