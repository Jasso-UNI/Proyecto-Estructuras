/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Archivos;
/**
 *
 * @author josem
 */


public class Arista {
    private final int destino; // Índice de la estación vecina en el grafo
    private int tiempo;        // Tiempo de traslado en minutos

    public Arista(int destino, int tiempo) {
        this.destino = destino;
        this.tiempo = tiempo;
    }

    public int getDestino() { return destino; }
    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }
}
