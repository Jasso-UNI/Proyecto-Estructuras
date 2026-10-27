/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

/**
 *
 * @author josem
 */


public class Estacion {
    private final String id;
    private String nombre;
    private String linea;
    private String zona;
    private int capacidad;
    int indice = -1; // Posición interna en el grafo

    public Estacion(String id, String nombre, String linea, String zona, int capacidad) {
        this.id = id != null ? id.trim().toUpperCase() : "";
        this.nombre = nombre;
        this.linea = linea;
        this.zona = zona;
        this.capacidad = capacidad;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getLinea() { return linea; }
    public String getZona() { return zona; }
    public int getCapacidad() { return capacidad; }
    public int getIndice() { return indice; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setLinea(String linea) { this.linea = linea; }
    public void setZona(String zona) { this.zona = zona; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
}
