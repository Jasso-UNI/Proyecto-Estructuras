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
import Archivos.Estacion;
import Archivos.Lista;

/**
 * Resultado de una búsqueda de ruta de menor tiempo.
 */
public class ResultadoRuta {

    private boolean existe;
    private int tiempoTotal;
    private final Lista<Estacion> camino = new Lista<>();
    private String motivo = "";

    /** @return true si existe ruta entre origen y destino */
    public boolean isExiste() {
        return existe;
    }

    /** @return tiempo total en minutos */
    public int getTiempoTotal() {
        return tiempoTotal;
    }

    /** @return estaciones del recorrido en orden */
    public Lista<Estacion> getCamino() {
        return camino;
    }

    /** @return motivo por el cual no hay ruta (vacío si existe) */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Establece si se encontró una ruta válida entre el origen y el destino.
     *
     * @param existe true si existe un camino posible, false si no
     */
    void setExiste(boolean existe) {
        this.existe = existe;
    }

    /**
     * Asigna el tiempo total acumulado del recorrido óptimo.
     *
     * @param t tiempo total del trayecto en minutos
     */
    void setTiempoTotal(int t) {
        this.tiempoTotal = t;
    }

    /**
     * Define el mensaje descriptivo cuando no es posible calcular la ruta.
     *
     * @param motivo razón por la cual no existe ruta
     */
    void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
