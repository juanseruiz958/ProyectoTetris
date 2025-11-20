package modelo;

import java.util.ArrayList;
import java.util.List;

public class Puntuacion {

    private int puntuacionActual;
    private int nivel;
    private int lineasCompletadas;

    private List<RegistroPuntos> mejoresPuntuaciones;

    private static final int PUNTOS_LINEA = 100;

    public void agregarPuntosExtra(int puntos) {
        // Los puntos extra también se multiplican por el nivel
        puntuacionActual += puntos * nivel;
    }

    public Puntuacion() {
        this.puntuacionActual = 0;
        this.nivel = 1;
        this.lineasCompletadas = 0;
        this.mejoresPuntuaciones = new ArrayList<>();
    }

    // Actualiza al eliminar líneas

    public void actualizarPorLineas(int lineas) {
        if (lineas <= 0) return;

        lineasCompletadas += lineas;

        // Puntos base
        puntuacionActual += lineas * PUNTOS_LINEA * nivel;

        // Bonus por combos
        switch (lineas) {
            case 2: puntuacionActual += 300; break;
            case 3: puntuacionActual += 500; break;
            case 4: puntuacionActual += 800; break;
        }

        // Subir nivel cada 10 líneas
        nivel = (lineasCompletadas / 10) + 1;
    }

    // Guardar en ranking

    public void guardarPuntuacion(String jugador) {
        if (jugador == null || jugador.trim().isEmpty()) return;

        mejoresPuntuaciones.add(new RegistroPuntos(jugador, puntuacionActual));

        // Mantener solo top 10
        mejoresPuntuaciones.sort((a, b) -> b.puntos - a.puntos);
        if (mejoresPuntuaciones.size() > 10) {
            mejoresPuntuaciones.remove(mejoresPuntuaciones.size() - 1);
        }
    }

    // Reiniciar

    public void reiniciar() {
        puntuacionActual = 0;
        nivel = 1;
        lineasCompletadas = 0;
    }

    // Getters

    public int getPuntuacionActual() { return puntuacionActual; }
    public int getNivel() { return nivel; }
    public int getLineasCompletadas() { return lineasCompletadas; }

    public List<RegistroPuntos> getMejoresPuntuaciones() {
        return mejoresPuntuaciones;
    }

    // Registro interno

    public static class RegistroPuntos {
        public String jugador;
        public int puntos;

        public RegistroPuntos(String jugador, int puntos) {
            this.jugador = jugador;
            this.puntos = puntos;
        }

        @Override
        public String toString() {
            return jugador + " - " + puntos;
        }
    }

    @Override
    public String toString() {
        return "Puntuación: " + puntuacionActual +
                " | Nivel: " + nivel +
                " | Líneas: " + lineasCompletadas;
    }
}

