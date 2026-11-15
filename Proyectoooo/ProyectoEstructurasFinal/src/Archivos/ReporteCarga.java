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

/**
 * Resumen del resultado de cargar un archivo CSV: cuántas estaciones y conexiones
 * se agregaron y qué líneas fueron ignoradas por tener errores.
 */
public class ReporteCarga {

    private int estacionesAgregadas;
    private int conexionesAgregadas;
    private int lineasInvalidas;
    private final StringBuilder detalle = new StringBuilder();

    /** @return estaciones nuevas agregadas */
    public int getEstacionesAgregadas() { 
        return estacionesAgregadas; 
    }
    /** @return conexiones nuevas agregadas */
    public int getConexionesAgregadas() { 
        return conexionesAgregadas; 
    }
    /** @return líneas ignoradas por errores */
    public int getLineasInvalidas() { 
        return lineasInvalidas; 
    }
    /** @return descripción de las primeras líneas inválidas */
    public String getDetalle() { 
        return detalle.toString(); 
    }

    /**
     * Fija los totales agregados.
     *
     * @param estaciones estaciones nuevas
     * @param conexiones conexiones nuevas
     */
    void setTotales(int estaciones, int conexiones) {
        this.estacionesAgregadas = estaciones;
        this.conexionesAgregadas = conexiones;
    }

    /**
     * Registra una línea inválida (solo se detallan las primeras 8).
     *
     * @param numero número de línea en el archivo
     * @param texto contenido de la línea
     * @param motivo causa del error
     */
    void registrarInvalida(int numero, String texto, String motivo) {
        lineasInvalidas++;
        if (lineasInvalidas <= 8) {
            String t = texto.length() > 40 ? texto.substring(0, 40) + "..." : texto;
            detalle.append("Línea ").append(numero).append(": ").append(motivo)
                   .append(" (").append(t).append(")\n");
        }
    }
}
