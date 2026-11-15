/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

/**
 * Representa una conexión directa hacia una estación vecina
 * dentro de las listas de adyacencia del grafo, almacenando el índice del nodo
 * destino y el tiempo de traslado en minutos.
 *
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */
public class Arista {

    /** Índice de la estación vecina en el grafo. */
    private final int destino;

    /** Tiempo de traslado en minutos. */
    private int tiempo;

    /**
     * Crea una nueva arista hacia una estación vecina con su respectivo tiempo de viaje.
     *
     * @param destino Índice de la estación vecina en el grafo.
     * @param tiempo  Tiempo de traslado hacia el destino en minutos.
     */
    public Arista(int destino, int tiempo) {
        this.destino = destino;
        this.tiempo = tiempo;
    }

    /**
     * Obtiene el índice de la estación destino.
     *
     * @return Índice del nodo vecino en el grafo.
     */
    public int getDestino() {
        return destino;
    }

    /**
     * Obtiene el tiempo de traslado de la conexión.
     *
     * @return Tiempo de viaje en minutos.
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * Actualiza el tiempo de traslado de la conexión.
     *
     * @param tiempo Nuevo tiempo de viaje en minutos.
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
}