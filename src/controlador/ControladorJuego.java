package controlador;

import modelo.Juego;

public class ControladorJuego {

    private Juego juego;

    public ControladorJuego(Juego juego) {
        this.juego = juego;
    }


    // ACCIONES DEL USUARIO


    public void moverIzquierda() {
        if (!juego.isGameOver()) {
            juego.moverIzquierda();
        }
    }

    public void moverDerecha() {
        if (!juego.isGameOver()) {
            juego.moverDerecha();
        }
    }

    public void rotarPieza() {
        if (!juego.isGameOver()) {
            juego.rotarPieza();
        }
    }

    public void moverAbajo() {
        if (!juego.isGameOver()) {
            juego.moverAbajo();
        }
    }

    public void caidaRapida() {
        if (!juego.isGameOver()) {
            juego.caidaRapida();
        }
    }

    public void reiniciarJuego() {
        juego.reiniciar();
    }


    // GETTERS


    public Juego getJuego() {
        return juego;
    }
}
