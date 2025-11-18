package modelo;

import java.util.LinkedList;
import java.util.Queue;

public class Juego {

    private Tablero tablero;
    private Pieza piezaActual;
    private Queue<Pieza> siguientesPiezas;

    private boolean gameOver;

    // SISTEMA DE PUNTUACIÓN COMPLETO
    private Puntuacion puntuacion;

    // Constructor
    public Juego(int filas, int columnas) {
        this.tablero = new Tablero(filas, columnas);

        this.gameOver = false;
        this.puntuacion = new Puntuacion();

        inicializarColaPiezas();
        this.piezaActual = siguientesPiezas.poll();
    }

    // Inicializa la cola con piezas futuras
    private void inicializarColaPiezas() {
        siguientesPiezas = new LinkedList<>();

        // Llenamos con 3 piezas iniciales (Next pieces)
        for (int i = 0; i < 3; i++) {
            siguientesPiezas.offer(new Pieza());
        }
    }

    // --------------------------------------------------------------------
    // MOVIMIENTOS
    // --------------------------------------------------------------------

    public void moverIzquierda() {
        if (gameOver) return;

        piezaActual.moverIzquierda();
        if (tablero.hayColision(piezaActual)) {
            piezaActual.moverDerecha();
        }
    }

    public void moverDerecha() {
        if (gameOver) return;

        piezaActual.moverDerecha();
        if (tablero.hayColision(piezaActual)) {
            piezaActual.moverIzquierda();
        }
    }

    public void rotarPieza() {
        if (gameOver) return;

        piezaActual.rotar();
        if (tablero.hayColision(piezaActual)) {
            piezaActual.rotarInverso();
        }
    }

    // Caída rápida
    public void caidaRapida() {
        if (gameOver) return;

        int bonus = 0;

        while (!gameOver && moverAbajo()) {
            bonus += 2;
        }

        puntuacion.agregarPuntosExtra(bonus);
    }

    // Baja la pieza un bloque.
    public boolean moverAbajo() {
        if (gameOver) return false;

        piezaActual.moverAbajo();

        if (tablero.hayColision(piezaActual)) {
            // Revertir movimiento
            piezaActual.moverArriba();

            // Fijar pieza en tablero
            tablero.fijarPieza(piezaActual);

            // Revisar líneas completas
            int lineas = tablero.eliminarLineasCompletas();
            if (lineas > 0) {
                puntuacion.actualizarPorLineas(lineas);
            }

            generarNuevaPieza();
            return false;
        }

        return true;
    }

    // --------------------------------------------------------------------
    // LÓGICA DEL JUEGO
    // --------------------------------------------------------------------

    private void generarNuevaPieza() {
        piezaActual = siguientesPiezas.poll();
        siguientesPiezas.offer(new Pieza()); // Nueva pieza aleatoria

        // Verificar fin del juego
        if (tablero.hayColision(piezaActual)) {
            gameOver = true;
            puntuacion.guardarPuntuacion("PLAYER");
        }
    }

    // --------------------------------------------------------------------
    // REINICIO
    // --------------------------------------------------------------------

    public void reiniciar() {
        tablero.limpiar();
        gameOver = false;

        puntuacion.reiniciar();

        inicializarColaPiezas();
        piezaActual = siguientesPiezas.poll();
    }

    // --------------------------------------------------------------------
    // GETTERS
    // --------------------------------------------------------------------

    public Tablero getTablero() { return tablero; }
    public Pieza getPiezaActual() { return piezaActual; }
    public Queue<Pieza> getSiguientesPiezas() { return siguientesPiezas; }
    public boolean isGameOver() { return gameOver; }

    public int getPuntuacion() { return puntuacion.getPuntuacionActual(); }
    public int getNivel() { return puntuacion.getNivel(); }
    public int getLineasCompletadas() { return puntuacion.getLineasCompletadas(); }
    public Puntuacion getPuntuacionSistema() { return puntuacion; }

    @Override
    public String toString() {
        return "Juego[Puntos=" + getPuntuacion() +
                ", Nivel=" + getNivel() +
                ", Lineas=" + getLineasCompletadas() + "]";
    }
}
