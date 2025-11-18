package modelo;

import java.util.Random;

public class Pieza {

    private int[][] forma;
    private int x, y;

    // FORMAS CLÁSICAS BASADAS EN IDS 1..7
    private static final int[][][] FORMAS = {

            // I (1)
            {
                    {0,0,0,0},
                    {1,1,1,1},
                    {0,0,0,0},
                    {0,0,0,0}
            },

            // J (2)
            {
                    {2,0,0},
                    {2,2,2},
                    {0,0,0}
            },

            // L (3)
            {
                    {0,0,3},
                    {3,3,3},
                    {0,0,0}
            },

            // O (4)
            {
                    {4,4},
                    {4,4}
            },

            // S (5)
            {
                    {0,5,5},
                    {5,5,0},
                    {0,0,0}
            },

            // T (6)
            {
                    {0,6,0},
                    {6,6,6},
                    {0,0,0}
            },

            // Z (7)
            {
                    {7,7,0},
                    {0,7,7},
                    {0,0,0}
            }
    };

    // Constructor aleatorio
    public Pieza() {
        Random r = new Random();
        int tipo = r.nextInt(FORMAS.length);

        this.forma = copiarForma(FORMAS[tipo]);

        // Posición inicial centrada
        this.x = 3;
        this.y = -1; // empieza un poco más arriba para evitar recorte
    }

    // Constructor copia (útil para Next Piece)
    public Pieza(Pieza otra) {
        this.forma = copiarForma(otra.forma);
        this.x = otra.x;
        this.y = otra.y;
    }

    // Copia profunda de matriz
    private int[][] copiarForma(int[][] original) {
        int[][] copia = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copia[i], 0, original[i].length);
        }
        return copia;
    }

    // Movimiento
    public void moverAbajo() { y++; }
    public void moverArriba() { y--; }
    public void moverIzquierda() { x--; }
    public void moverDerecha() { x++; }

    // Rotación segura
    public void rotar() {
        int filas = forma.length;
        int columnas = forma[0].length;

        int[][] nueva = new int[columnas][filas];

        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                nueva[j][filas - 1 - i] = forma[i][j];

        forma = nueva;
    }

    public void rotarInverso() {
        rotar();
        rotar();
        rotar();
    }

    // Getters
    public int[][] getForma() { return forma; }
    public int getX() { return x; }
    public int getY() { return y; }

    public int getAncho() { return forma[0].length; }
    public int getAltura() { return forma.length; }
}
