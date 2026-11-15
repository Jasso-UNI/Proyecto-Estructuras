/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Lectura de archivos CSV de la red de transporte.
 * <p>
 * Acepta dos bloques, identificados por su encabezado o por el número de columnas:
 * estaciones (ID_Estacion, Nombre, Linea, Zona, Capacidad) y conexiones
 * (Estacion_Origen, Estacion_Destino, Tiempo). Las líneas con errores se ignoran, se
 * cuentan y se reportan en un {@link ReporteCarga}; nunca se escribe en consola.
 *
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */
public class LectorCSV {
    
    /**
     * Carga un archivo CSV (con estaciones, conexiones o ambos bloques) en el grafo.
     * Si una conexión menciona una estación no declarada se crea una estación genérica.
     *
     * @param archivo archivo a leer
     * @param grafo grafo donde se agregan los datos
     * @return reporte con totales agregados y líneas inválidas
     * @throws IOException si el archivo no se puede leer
     */
    public static ReporteCarga cargarArchivo(File archivo, Grafo grafo) throws IOException {
        ReporteCarga rep = new ReporteCarga();
        int estAntes = grafo.numEstaciones();
        int conAntes = grafo.numConexiones();

        byte[] bytes = Files.readAllBytes(archivo.toPath());
        String texto = new String(bytes, StandardCharsets.UTF_8);
        if (texto.indexOf('\uFFFD') >= 0) {
            texto = new String(bytes, StandardCharsets.ISO_8859_1); // archivo guardado en ANSI
        }
        String[] lineas = texto.split("\\r\\n|\\n|\\r");

        int modo = 0; // 0 = desconocido, 1 = estaciones, 2 = conexiones
        for (int n = 0; n < lineas.length; n++) {
            String linea = lineas[n].replace("\uFEFF", "").trim();
            if (linea.isEmpty()) {
                continue;
            }
            String low = linea.toLowerCase();
            if (low.startsWith("id_estacion")) {
                modo = 1;
                continue;
            }
            if (low.startsWith("estacion_origen")) {
                modo = 2;
                continue;
            }
            String sep = linea.indexOf(',') >= 0 ? "," : (linea.indexOf(';') >= 0 ? ";" : ",");
            String[] partes = linea.split(sep, -1);
            for (int k = 0; k < partes.length; k++) {
                partes[k] = partes[k].trim();
            }
            int tipo = modo;
            if (tipo == 0) {
                tipo = partes.length >= 5 ? 1 : (partes.length == 3 ? 2 : 0);
            }
            if (tipo == 1) {
                procesarEstacion(partes, grafo, rep, n + 1, linea);
            } else if (tipo == 2) {
                procesarConexion(partes, grafo, rep, n + 1, linea);
            } else {
                rep.registrarInvalida(n + 1, linea, "formato no reconocido");
            }
        }
        rep.setTotales(grafo.numEstaciones() - estAntes, grafo.numConexiones() - conAntes);
        return rep;
    }
    
    /**
     * Carga las estaciones de un archivo (se mantiene por compatibilidad).
     *
     * @param rutaArchivo ruta del archivo
     * @param grafo grafo destino
     * @return reporte de la carga
     * @throws IOException si el archivo no se puede leer
     */
    public static ReporteCarga cargarEstaciones(String rutaArchivo, Grafo grafo) throws IOException {
        return cargarArchivo(new File(rutaArchivo), grafo);
    }

    /**
     * Carga las conexiones de un archivo (se mantiene por compatibilidad).
     *
     * @param rutaArchivo ruta del archivo
     * @param grafo grafo destino
     * @return reporte de la carga
     * @throws IOException si el archivo no se puede leer
     */
    public static ReporteCarga cargarConexiones(String rutaArchivo, Grafo grafo) throws IOException {
        return cargarArchivo(new File(rutaArchivo), grafo);
    }

    /**
     * Valida y procesa una fila correspondiente a una estación.
     * Si la estación ya existía en el grafo (por ejemplo, creada previamente de forma genérica
     * al leer una conexión), actualiza sus metadatos con los valores del archivo.
     *
     * @param c     arreglo con los campos separados de la línea ({@code ID, Nombre, Linea, Zona, Capacidad})
     * @param grafo grafo donde se registrará o actualizará la estación
     * @param rep   reporte de carga donde se registran las líneas con errores
     * @param num   número de línea dentro del archivo CSV (base 1)
     * @param linea contenido textual original de la línea procesada
     */
    private static void procesarEstacion(String[] c, Grafo grafo, ReporteCarga rep, int num, String linea) {
        if (c.length < 5 || c[0].trim().isEmpty()) {
            rep.registrarInvalida(num, linea, "estación incompleta");
            return;
        }
        int capacidad;
        try {
            capacidad = Integer.parseInt(c[4]);
        } catch (NumberFormatException e) {
            rep.registrarInvalida(num, linea, "capacidad no numérica");
            return;
        }
        String nombre = c[1].isEmpty() ? c[0] : c[1];
        String lin = c[2].isEmpty() ? "Sin línea" : c[2];
        String zona = c[3].isEmpty() ? "Sin zona" : c[3];
        Estacion existente = grafo.buscarEstacion(c[0]);
        if (existente != null) {
            existente.setNombre(nombre);
            existente.setLinea(lin);
            existente.setZona(zona);
            existente.setCapacidad(capacidad);
        } else {
            grafo.agregarEstacion(new Estacion(c[0], nombre, lin, zona, capacidad));
        }
    }

    /**
     * Valida y procesa una fila correspondiente a una conexión entre dos estaciones.
     * Garantiza que ambas estaciones existan en el grafo antes de crear la arista bidireccional.
     *
     * @param c     arreglo con los campos separados de la línea ({@code Origen, Destino, Tiempo})
     * @param grafo grafo donde se agregará la conexión
     * @param rep   reporte de carga donde se registran las líneas con errores
     * @param num   número de línea dentro del archivo CSV (base 1)
     * @param linea contenido textual original de la línea procesada
     */
    private static void procesarConexion(String[] c, Grafo grafo, ReporteCarga rep, int num, String linea) {
        if (c.length < 3) {
            rep.registrarInvalida(num, linea, "conexión incompleta");
            return;
        }
        String a = c[0].trim().toUpperCase();
        String b = c[1].trim().toUpperCase();
        if (a.isEmpty() || b.isEmpty()) {
            rep.registrarInvalida(num, linea, "falta una estación");
            return;
        }
        if (a.equals(b)) {
            rep.registrarInvalida(num, linea, "conexión de una estación consigo misma");
            return;
        }
        int tiempo;
        try {
            tiempo = Integer.parseInt(c[2]);
        } catch (NumberFormatException e) {
            rep.registrarInvalida(num, linea, "tiempo no numérico");
            return;
        }
        if (tiempo <= 0) {
            rep.registrarInvalida(num, linea, "el tiempo debe ser mayor que 0");
            return;
        }
        asegurar(grafo, a);
        asegurar(grafo, b);
        grafo.agregarConexion(a, b, tiempo); // si ya existía se ignora sin error
    }

    /**
     * Verifica si una estación existe en el grafo y, de no ser así, la crea con valores
     * genéricos por defecto para evitar referencias faltantes al cargar conexiones.
     *
     * @param grafo grafo de la red de transporte
     * @param id    identificador único de la estación a verificar
     */
    private static void asegurar(Grafo grafo, String id) {
        if (grafo.buscarEstacion(id) == null) {
            grafo.agregarEstacion(new Estacion(id, id, "Sin línea", "Sin zona", 0));
        }
    }
}
