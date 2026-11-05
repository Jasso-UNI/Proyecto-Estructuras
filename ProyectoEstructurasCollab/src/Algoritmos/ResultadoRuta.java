/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;
//

/**
 *
 * @author josem
 */
import Archivos.Estacion;
import Archivos.Lista;

public class ResultadoRuta {

    private boolean existe;
    private int tiempoTotal;
    private final Lista<Estacion> camino = new Lista<>();
    private String motivo = "";

    public boolean isExiste() {
        return existe;
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public Lista<Estacion> getCamino() {
        return camino;
    }

    public String getMotivo() {
        return motivo;
    }

    void setExiste(boolean existe) {
        this.existe = existe;
    }

    void setTiempoTotal(int t) {
        this.tiempoTotal = t;
    }

    void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
