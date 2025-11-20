package vista;

import modelo.Juego;
import modelo.Tablero;
import modelo.Pieza;

import javax.swing.*;
import java.awt.*;

public class PanelTablero extends JPanel {

    private Juego juego;

    // Tamaño visual de cada celda
    private static final int TAM_CELDA = 30;

    // Colores clásicos del Tetris
    private static final Color[] COLORES = {
            Color.BLACK,            // 0 - vacío
            new Color(0, 255, 255), // 1 - I
            new Color(0, 0, 255),   // 2 - J
            new Color(255, 165, 0), // 3 - L
            new Color(255, 255, 0), // 4 - O
            new Color(0, 255, 0),   // 5 - S
            new Color(128, 0, 128), // 6 - T
            new Color(255, 0, 0)    // 7 - Z
    };

    public PanelTablero(Juego juego) {
        this.juego = juego;
        setBackground(new Color(20, 20, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Tablero tablero = juego.getTablero();

        dibujarFondo(g);
        dibujarBloquesFijos(g, tablero);
        dibujarPiezaActual(g);
    }

    // FONDO

    private void dibujarFondo(Graphics g) {
        g.setColor(new Color(35, 35, 35));

        int filas = juego.getTablero().getFilas();
        int columnas = juego.getTablero().getColumnas();

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {

                int x = col * TAM_CELDA;
                int y = fila * TAM_CELDA;

                g.drawRect(x, y, TAM_CELDA, TAM_CELDA);
            }
        }
    }

    // BLOQUES FIJOS

    private void dibujarBloquesFijos(Graphics g, Tablero tablero) {

        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {

                int valor = tablero.getCelda(fila, col);
                if (valor != 0) {
                    dibujarCelda(g, col, fila, COLORES[valor]);
                }
            }
        }
    }

    //  BLOQUE

    private void dibujarCelda(Graphics g, int col, int fila, Color color) {

        int x = col * TAM_CELDA;
        int y = fila * TAM_CELDA;

        // sombra
        g.setColor(color.darker());
        g.fillRoundRect(x + 3, y + 3, TAM_CELDA - 3, TAM_CELDA - 3, 6, 6);

        // base
        g.setColor(color);
        g.fillRoundRect(x, y, TAM_CELDA - 4, TAM_CELDA - 4, 6, 6);

        // brillo
        g.setColor(color.brighter());
        g.fillRoundRect(x, y, TAM_CELDA - 6, TAM_CELDA - 6, 6, 6);

        // borde
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, TAM_CELDA - 4, TAM_CELDA - 4, 6, 6);
    }

    // PIEZA ACTUAL

    private void dibujarPiezaActual(Graphics g) {
        Pieza pieza = juego.getPiezaActual();
        int[][] forma = pieza.getForma();

        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {

                if (forma[i][j] != 0) {

                    int colorID = forma[i][j];
                    int col = pieza.getX() + j;
                    int fila = pieza.getY() + i;

                    if (fila >= 0) {
                        dibujarCelda(g, col, fila, COLORES[colorID]);
                    }
                }
            }
        }
    }

    //TAMAÑO PARA QUE NO SE CORTEN LAS PIEZAS

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                juego.getTablero().getColumnas() * TAM_CELDA,
                juego.getTablero().getFilas() * TAM_CELDA
        );
    }
}
