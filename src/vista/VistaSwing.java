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
    private int nivelAnterior = 1;

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
        ControladorTeclado teclado = new ControladorTeclado(controlador, this);
        addKeyListener(teclado);

        setFocusable(true);
        requestFocusInWindow();

        // INICIAR TIMER DEL JUEGO
        iniciarTimer();

        setVisible(true);
    }


    // TIMER PRINCIPAL — BAJA LA PIEZA AUTOMÁTICAMENTE

    private void iniciarTimer() {

        int delay = Math.max(80, 550 - (juego.getNivel() * 40));
        nivelAnterior = juego.getNivel();

        timer = new Timer(delay, e -> {

            if (!juego.isGameOver()) {

                juego.moverAbajo();
                panelTablero.repaint();
                panelInfo.repaint();

                actualizarVelocidadSiEsNecesario();

            } else {

                timer.stop();
                panelTablero.repaint();
                panelInfo.repaint();
            }
        });

        timer.start();
    }


    // ACTUALIZAR VELOCIDAD SI SUBE EL NIVEL

    private void actualizarVelocidadSiEsNecesario() {
        int nivelActual = juego.getNivel();

        if (nivelActual != nivelAnterior) {

            nivelAnterior = nivelActual;

            timer.stop();
            iniciarTimer(); // recrea timer con nueva velocidad
        }
    }


    // REINICIAR JUEGO — CORRECCIÓN CRÍTICA

    public void reiniciarJuegoCompleto() {

        // Detener timer viejo
        if (timer != null) {
            timer.stop();
            timer = null;
        }

        // Reiniciar modelo
        controlador.reiniciarJuego();

        // Reset nivel para que timer arranque con velocidad correcta
        nivelAnterior = juego.getNivel();

        // Reiniciar timer nuevo
        iniciarTimer();

        // Repintar pantalla
        panelTablero.repaint();
        panelInfo.repaint();

        // Recuperar control del teclado
        requestFocusInWindow();
    }
}