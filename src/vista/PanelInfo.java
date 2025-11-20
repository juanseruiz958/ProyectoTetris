package vista;

import modelo.Juego;
import modelo.Pieza;

import javax.swing.*;
import java.awt.*;

public class PanelInfo extends JPanel {

    private Juego juego;

    public PanelInfo(Juego juego) {
        this.juego = juego;

        setPreferredSize(new Dimension(200, 500));
        setBackground(new Color(25, 25, 25));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int y = 20;

        // TÍTULO
        g.setFont(new Font("Arial Black", Font.BOLD, 28));
        g.setColor(new Color(255, 200, 0));
        g.drawString("TETRIS", 40, y + 20);

        y += 60;

        // DATOS
        y = dibujarCajaTexto(g, "Puntuación", String.valueOf(juego.getPuntuacion()), y);
        y = dibujarCajaTexto(g, "Nivel", String.valueOf(juego.getNivel()), y);
        y = dibujarCajaTexto(g, "Líneas", String.valueOf(juego.getLineasCompletadas()), y);

        // PIEZA SIGUIENTE
        dibujarSiguientePieza(g, y + 20);
    }

    private int dibujarCajaTexto(Graphics g, String titulo, String valor, int y) {

        int alto = 60;
        int ancho = getWidth() - 40;

        // Sombra
        g.setColor(new Color(0, 0, 0, 80));
        g.fillRoundRect(23, y + 3, ancho, alto, 15, 15);

        // Fondo
        g.setColor(new Color(40, 40, 40));
        g.fillRoundRect(20, y, ancho, alto, 15, 15);

        // Título
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(new Color(200, 200, 200));
        g.drawString(titulo, 30, y + 20);

        // Valor
        g.setFont(new Font("Arial Black", Font.BOLD, 20));
        g.setColor(new Color(0, 255, 255));
        g.drawString(valor, 30, y + 45);

        return y + alto + 15;
    }

    private void dibujarSiguientePieza(Graphics g, int y) {

        Pieza siguiente = new Pieza(juego.getSiguientesPiezas().peek());
        if (siguiente == null) return;

        int[][] forma = siguiente.getForma();

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(new Color(255, 140, 0));
        g.drawString("SIGUIENTE:", 40, y);

        int cuadroX = 40;
        int cuadroY = y + 20;
        int tamCelda = 22;

        g.setColor(new Color(50, 50, 50));
        g.fillRoundRect(cuadroX - 10, cuadroY - 10, 120, 120, 15, 15);

        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j] != 0) {
                    g.setColor(colorPieza(forma[i][j]));
                    g.fill3DRect(
                            cuadroX + j * tamCelda,
                            cuadroY + i * tamCelda,
                            tamCelda - 2,
                            tamCelda - 2,
                            true
                    );
                }
            }
        }
    }

    private Color colorPieza(int id) {
        switch (id) {
            case 1: return new Color(0, 255, 255);
            case 2: return new Color(0, 0, 255);
            case 3: return new Color(255, 165, 0);
            case 4: return new Color(255, 255, 0);
            case 5: return new Color(0, 255, 0);
            case 6: return new Color(128, 0, 128);
            case 7: return new Color(255, 0, 0);
        }
        return Color.WHITE;
    }
}
