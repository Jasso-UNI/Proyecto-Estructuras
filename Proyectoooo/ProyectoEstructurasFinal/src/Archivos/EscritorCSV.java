package Archivos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */

/**
 * Escritura de la red en un archivo CSV (UTF-8) para actualizar el repositorio.
 * Escribe el bloque de estaciones y el de conexiones; cada conexión una sola vez,
 * de modo que al volver a cargar el archivo la red quede idéntica.
 */
public class EscritorCSV {

    private static final String ENC_ESTACIONES = "ID_Estacion,Nombre,Linea,Zona,Capacidad_Pasajeros_Hora";
    private static final String ENC_CONEXIONES = "Estacion_Origen,Estacion_Destino,Tiempo_Minutos";

    /**
     * Guarda el grafo en un archivo CSV.
     *
     * @param archivo archivo de destino
     * @param grafo grafo a guardar
     * @throws IOException si no se puede escribir el archivo
     */
    public static void guardar(File archivo, Grafo grafo) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(archivo), StandardCharsets.UTF_8));
        try {
            pw.println(ENC_ESTACIONES);
            for (int i = 0; i < grafo.getLimite(); i++) {
                Estacion e = grafo.estacionEn(i);
                if (e != null) {
                    pw.println(e.getId() + "," + limpiar(e.getNombre()) + "," + limpiar(e.getLinea())
                            + "," + limpiar(e.getZona()) + "," + e.getCapacidad());
                }
            }
            pw.println();
            pw.println(ENC_CONEXIONES);
            for (int i = 0; i < grafo.getLimite(); i++) {
                if (grafo.estacionEn(i) == null) {
                    continue;
                }
                Lista<Arista> v = grafo.vecinos(i);
                for (int k = 0; k < v.tamano(); k++) {
                    Arista a = v.obtener(k);
                    if (i < a.getDestino()) { // evita escribir la arista duplicada
                        pw.println(grafo.estacionEn(i).getId() + ","
                                + grafo.estacionEn(a.getDestino()).getId() + "," + a.getTiempo());
                    }
                }
            }
            pw.flush();
            if (pw.checkError()) {
                throw new IOException("Error al escribir el archivo.");
            }
        } finally {
            pw.close();
        }
    }

    private static String limpiar(String s) {
        return s == null ? "" : s.replace(',', ' ').trim();
    }
}
