package produccion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalificadorTest {

    public static Calificador profesor = new Calificador();

    @Test
    void MenorQueSesentaDebeDevolverF(){
        assertEquals('F',profesor.determinarNotaEnLetras(55));
    }

    @Test
    void MenorQueSetentaDebeDevolverD(){
        assertEquals('D',profesor.determinarNotaEnLetras(65));
    }

    @Test
    void MenorQueOchentaDebeDevolverC(){
        assertEquals('C',profesor.determinarNotaEnLetras(75));
    }

    @Test
    void MenorQueNoventaDebeDevolverB(){
        assertEquals('B',profesor.determinarNotaEnLetras(85));
    }

    @Test
    void MenorQueCienDebeDevolverA(){
        assertEquals('A',profesor.determinarNotaEnLetras(95));
    }

    @Test
    void NegativoDebeLanzarExcepcion(){
        assertThrows(IllegalArgumentException.class, () -> {
            profesor.determinarNotaEnLetras(-5);
        });
    }
}
