/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Archivos;
/**
 *
 * @author josem
 */


public class ReporteCarga {

    private int estacionesAgregadas;
    private int conexionesAgregadas;
    private int lineasInvalidas;
    private final StringBuilder detalle = new StringBuilder();


    public int getEstacionesAgregadas() { 
        return estacionesAgregadas; 
    }
    public int getConexionesAgregadas() { 
        return conexionesAgregadas; 
    }
    public int getLineasInvalidas() { 
        return lineasInvalidas; 
    }
    public String getDetalle() { 
        return detalle.toString(); 
    }

    void setTotales(int estaciones, int conexiones) {
        this.estacionesAgregadas = estaciones;
        this.conexionesAgregadas = conexiones;
    }

    void registrarInvalida(int numero, String texto, String motivo) {
        lineasInvalidas++;
        if (lineasInvalidas <= 8) {
            String t = texto.length() > 40 ? texto.substring(0, 40) + "..." : texto;
            detalle.append("Línea ").append(numero).append(": ").append(motivo)
                   .append(" (").append(t).append(")\n");
        }
    }
}
