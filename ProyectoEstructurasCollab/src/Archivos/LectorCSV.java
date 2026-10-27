/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

/**
 *
 * @author josem
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorCSV {

    /**
     * Carga las estaciones 
     * @param rutaArchivo
     * @param grafo
     */
    public static void cargarEstaciones(String rutaArchivo, Grafo grafo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean esEncabezado = true;
            
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                
                // Detectar y omitir la línea de cabecera
                if (esEncabezado && (linea.toLowerCase().contains("id_estacion") || linea.toLowerCase().contains("nombre"))) {
                    esEncabezado = false;
                    continue;
                }
                
                String[] partes = linea.split(",");
                if (partes.length >= 5) {
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    String lineaTransporte = partes[2].trim();
                    String zona = partes[3].trim();
                    
                    try {
                        int capacidad = Integer.parseInt(partes[4].trim());
                        Estacion est = new Estacion(id, nombre, lineaTransporte, zona, capacidad);
                        grafo.agregarEstacion(est);
                    } catch (NumberFormatException e) {
                        System.err.println("Advertencia: No se pudo parsear la capacidad numérica en la línea: " + linea);
                    }
                }
            }
            System.out.println("¡Estaciones cargadas exitosamente desde: " + rutaArchivo + "!");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de estaciones: " + e.getMessage());
        }
    }

    /**
     * Carga las conexiones 
     * @param rutaArchivo
     * @param grafo
     */
    public static void cargarConexiones(String rutaArchivo, Grafo grafo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean esEncabezado = true;
            
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }
                
                // Detectar y omitir la línea de cabecera
                if (esEncabezado && (linea.toLowerCase().contains("estacion_origen") || linea.toLowerCase().contains("origen"))) {
                    esEncabezado = false;
                    continue;
                }
                
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    
                    try {
                        int tiempo = Integer.parseInt(partes[2].trim());
                        grafo.agregarConexion(origen, destino, tiempo);
                    } catch (NumberFormatException e) {
                        System.err.println("Advertencia: No se pudo parsear el tiempo numérico en la línea: " + linea);
                    }
                }
            }
            System.out.println("¡Conexiones cargadas exitosamente desde: " + rutaArchivo + "!");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de conexiones: " + e.getMessage());
        }
    }
}
