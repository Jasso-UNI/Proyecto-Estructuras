/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

/**
 *
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */

/**
 * Estación o parada de la red de transporte multimodal.
 * Guarda sus datos descriptivos y su posición interna dentro del {@link Grafo}.
 */
public class Estacion {
    private final String id;
    private String nombre;
    private String linea;
    private String zona;
    private int capacidad;
    int indice = -1; // Posición interna en el grafo

    /**
     * Crea una estación; el ID se normaliza (sin espacios en los extremos y en mayúsculas).
     *
     * @param id identificador único
     * @param nombre nombre de la estación
     * @param linea línea de transporte
     * @param zona zona de la ciudad
     * @param capacidad pasajeros por hora
     */
    public Estacion(String id, String nombre, String linea, String zona, int capacidad) {
        this.id = id != null ? id.trim().toUpperCase() : "";
        this.nombre = nombre;
        this.linea = linea;
        this.zona = zona;
        this.capacidad = capacidad;
    }
    
    /** @return identificador único */
    public String getId() { 
        return id; 
    }
    /** @return nombre de la estación */
    public String getNombre() { 
        return nombre; 
    }
    /** @return línea de transporte */
    public String getLinea() { 
        return linea; 
    }
    /** @return zona de la ciudad */
    public String getZona() { 
        return zona; 
    }
    /** @return capacidad en pasajeros por hora */
    public int getCapacidad() { 
        return capacidad; 
    }
    /** @return posición interna en el grafo (-1 si no pertenece a uno) */
    public int getIndice() { 
        return indice; 
    }
    /** @param nombre nuevo nombre */
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    /** @param linea nueva línea */
    public void setLinea(String linea) { 
        this.linea = linea; 
    }
    /** @param zona nueva zona */
    public void setZona(String zona) { 
        this.zona = zona; 
    }
    /** @param capacidad nueva capacidad */
    public void setCapacidad(int capacidad) { 
        this.capacidad = capacidad; 
    }
    
    /**
     * Texto "ID - Nombre" que se muestra en las listas desplegables.
     *
     * @return representación en texto de la estación
     */
    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
