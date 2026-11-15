/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;
//

/**
 *
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */

/**
 * Resultado de un recorrido en profundidad (DFS): el orden en que se visitaron las
 * estaciones, el padre de cada una en el árbol del recorrido y la subred a la que
 * pertenece. Sirve para mostrar el recorrido paso a paso en el mapa.
 */
public class RecorridoDFS {

    /** Índices de las estaciones en el orden de visita. */
    private final int[] orden;
    /** Posición de visita de cada estación (-1 si no se visitó). */
    private final int[] posicion;
    /** Padre de cada estación en el árbol DFS (-1 si es raíz o no se visitó). */
    private final int[] padre;
    /** Subred de cada estación (-1 si no se visitó). */
    private final int[] comp;
    /** Cantidad de estaciones visitadas. */
    private int cantidad = 0;
    /** Cantidad de subredes recorridas. */
    private int numComponentes = 0;
    
    /**
     * Crea un recorrido vacío.
     *
     * @param limite cantidad de índices del grafo
     */
    RecorridoDFS(int limite) {
        orden = new int[limite];
        posicion = new int[limite];
        padre = new int[limite];
        comp = new int[limite];
        for (int i = 0; i < limite; i++) {
            posicion[i] = -1;
            padre[i] = -1;
            comp[i] = -1;
        }
    }

    /**
     * Registra la visita de una estación.
     *
     * @param u índice de la estación visitada
     * @param p índice del padre en el árbol DFS (-1 si es raíz)
     * @param c número de subred
     */
    void registrar(int u, int p, int c) {
        orden[cantidad] = u;
        posicion[u] = cantidad;
        padre[u] = p;
        comp[u] = c;
        cantidad++;
        if (c + 1 > numComponentes) {
            numComponentes = c + 1;
        }
    }

    /** @return cantidad de estaciones visitadas */
    public int getCantidad() {
        return cantidad;
    }

    /** @return cantidad de subredes recorridas */
    public int getNumComponentes() {
        return numComponentes;
    }

    /**
     * Estación visitada en un paso del recorrido.
     *
     * @param k número de paso (desde 0)
     * @return índice de la estación visitada en ese paso
     */
    public int getOrden(int k) {
        return orden[k];
    }

    /**
     * Posición de visita de una estación.
     *
     * @param i índice de la estación
     * @return paso en que se visitó, o -1 si no se visitó
     */
    public int getPosicion(int i) {
        return i < posicion.length ? posicion[i] : -1;
    }

    /**
     * Padre de una estación en el árbol DFS.
     *
     * @param i índice de la estación
     * @return índice del padre o -1
     */
    public int getPadre(int i) {
        return i < padre.length ? padre[i] : -1;
    }

    /**
     * Subred de una estación.
     *
     * @param i índice de la estación
     * @return número de subred o -1 si no se visitó
     */
    public int getComp(int i) {
        return i < comp.length ? comp[i] : -1;
    }
}
