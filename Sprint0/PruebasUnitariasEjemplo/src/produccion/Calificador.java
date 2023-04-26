package produccion;

public class Calificador {
    public char determinarNotaEnLetras( int notaNumerica  ){
        if ( notaNumerica < 0) throw new IllegalArgumentException("Nota no puede ser menor que 0");

        else if (notaNumerica < 60) return 'F';
        else if (notaNumerica < 70) return 'D';
        else if (notaNumerica < 80) return 'C';
        else if (notaNumerica < 90) return 'B';
        else return 'A';
    }
}
