package controlador;

import vista.PanelTablero;
import vista.PanelInfo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControladorTeclado implements KeyListener {

    private ControladorJuego controlador;
    private PanelTablero panelTablero;
    private PanelInfo panelInfo;

    // Ahora recibe la vista también
    public ControladorTeclado(ControladorJuego controlador,
                              PanelTablero panelTablero,
                              PanelInfo panelInfo) {

        this.controlador = controlador;
        this.panelTablero = panelTablero;
        this.panelInfo = panelInfo;
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
                controlador.reiniciarJuego();
                break;
        }

        // 🔥 IMPORTANTÍSIMO: repintar inmediatamente
        panelTablero.repaint();
        panelInfo.repaint();
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
