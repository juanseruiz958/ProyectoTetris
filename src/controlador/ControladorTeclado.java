package controlador;

import vista.VistaSwing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControladorTeclado implements KeyListener {

    private ControladorJuego controlador;
    private VistaSwing vista;

    // Ahora recibe la vista directamente
    public ControladorTeclado(ControladorJuego controlador, VistaSwing vista) {
        this.controlador = controlador;
        this.vista = vista;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_LEFT:
                controlador.moverIzquierda();
                break;

            case KeyEvent.VK_RIGHT:
                controlador.moverDerecha();
                break;

            case KeyEvent.VK_UP:
                controlador.rotarPieza();
                break;

            case KeyEvent.VK_DOWN:
                controlador.moverAbajo();
                break;

            case KeyEvent.VK_SPACE:
                controlador.caidaRapida();
                break;

            case KeyEvent.VK_R:
                // Reinicio completo y correcto
                vista.reiniciarJuegoCompleto();
                return; // evitar repaints extra
        }

        // Repintado tras cada acción
        vista.repaint();
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}