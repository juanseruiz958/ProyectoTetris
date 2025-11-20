package modelo;

public class Tablero {

    private int[][] grid;
    private int filas;
    private int columnas;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grid = new int[filas][columnas];
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                grid[i][j] = 0;
    }


     // Devuelve true si la pieza colisiona con paredes, suelo o bloques fijos.

    public boolean hayColision(Pieza pieza) {
        int[][] forma = pieza.getForma();
        int x = pieza.getX();
        int y = pieza.getY();

        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j] != 0) {
                    int posX = x + j;
                    int posY = y + i;

                    // colisión lateral
                    if (posX < 0 || posX >= columnas) return true;

                    // colisión con suelo
                    if (posY >= filas) return true;

                    // colisión con bloque fijo (solo si está dentro del tablero en Y)
                    if (posY >= 0 && posY < filas && grid[posY][posX] != 0) return true;
                }
            }
        }
        return false;
    }


      //Conveniencia: devuelve true si la pieza EN SU POSICIÓN ACTUAL no colisiona.

    public boolean posicionValida(Pieza pieza) {
        return !hayColision(pieza);
    }

    /**
     * Fija la pieza en el grid. Se ignoran celdas fuera del tablero (y<0)
     */
    public void fijarPieza(Pieza pieza) {
        int[][] forma = pieza.getForma();
        int x = pieza.getX();
        int y = pieza.getY();

        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j] != 0) {
                    int posY = y + i;
                    int posX = x + j;
                    if (posY >= 0 && posY < filas && posX >= 0 && posX < columnas) {
                        grid[posY][posX] = forma[i][j];
                    }
                }
            }
        }
    }


      // Elimina líneas completas y devuelve cuántas se eliminaron.

    public int eliminarLineasCompletas() {
        int lineasCompletas = 0;

        // Recorremos desde abajo hacia arriba
        for (int fila = filas - 1; fila >= 0; fila--) {
            if (esLineaCompleta(fila)) {
                eliminarLinea(fila);
                lineasCompletas++;
                // después de eliminar, la fila actual tiene nueva información (fila-- en for)
                // como se copio desde arriba, se debe volver a revisar la misma fila
                fila++; // esto compensa el decremento del for y provoca re-evaluar la fila
            }
        }
        return lineasCompletas;
    }

    private boolean esLineaCompleta(int fila) {
        for (int col = 0; col < columnas; col++) {
            if (grid[fila][col] == 0) return false;
        }
        return true;
    }

    private void eliminarLinea(int filaEliminar) {
        // mover todas las filas superiores una posición hacia abajo
        for (int fila = filaEliminar; fila > 0; fila--) {
            System.arraycopy(grid[fila - 1], 0, grid[fila], 0, columnas);
        }
        // limpiar la fila superior
        for (int col = 0; col < columnas; col++) grid[0][col] = 0;
    }

    public void limpiar() {
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                grid[i][j] = 0;
    }


     // Devuelve el valor de la celda; si está fuera de límites devuelve 0 (vacío).
     // Esto evita que la vista o cualquier chequeo interprete -1 como "bloque".

    public int getCelda(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return grid[fila][columna];
        }
        return 0; // fuera de rango -> tratar como vacío
    }

    // GETTERS
    public int[][] getGrid() { return grid; }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }

    @Override
    public String toString() { return "Tablero[" + filas + "x" + columnas + "]"; }
}
