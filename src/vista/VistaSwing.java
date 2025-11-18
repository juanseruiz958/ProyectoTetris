package vista;

import modelo.Juego;
import controlador.ControladorJuego;
import controlador.ControladorTeclado;

import javax.swing.*;
import java.awt.*;

public class VistaSwing extends JFrame {

    private Juego juego;
    private ControladorJuego controlador;
    private PanelTablero panelTablero;
    private PanelInfo panelInfo;

    private Timer timer;

    public VistaSwing() {

        // MODELO
        juego = new Juego(20, 10);
        controlador = new ControladorJuego(juego);

        // VENTANA
        setTitle("Tetris — Proyecto Estructuras de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // PANELES
        panelTablero = new PanelTablero(juego);
        panelInfo = new PanelInfo(juego);

        setLayout(new BorderLayout());
        add(panelTablero, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.EAST);
        pack();

        // CONTROLADOR TECLADO
        ControladorTeclado teclado = new ControladorTeclado(controlador, panelTablero, panelInfo);
        addKeyListener(teclado);

        setFocusable(true);
        requestFocus();

        // TIMER
        iniciarTimer();

        setVisible(true);
    }

    // --------------------------------------------------------------------
    // TIMER — baja la pieza automáticamente
    // --------------------------------------------------------------------
    private void iniciarTimer() {

        int delay = Math.max(80, 550 - (juego.getNivel() * 40));
        // mínimo 80 ms (rápido pero jugable)

        timer = new Timer(delay, e -> {

            if (!juego.isGameOver()) {

                juego.moverAbajo();
                panelTablero.repaint();
                panelInfo.repaint();

                // SOLO actualiza velocidad cuando el nivel cambia
                actualizarVelocidadSiEsNecesario();

            } else {

                timer.stop();
                panelTablero.repaint();
                panelInfo.repaint();
            }
        });

        timer.start();
    }

    // --------------------------------------------------------------------
    // SOLO cuando cambia el nivel, se ajusta el timer
    // --------------------------------------------------------------------
    private int nivelAnterior = 1;

    private void actualizarVelocidadSiEsNecesario() {
        int nivelActual = juego.getNivel();

        if (nivelActual != nivelAnterior) {
            nivelAnterior = nivelActual;

            timer.stop();
            iniciarTimer();  // cambia velocidad sin lag
        }
    }
}
