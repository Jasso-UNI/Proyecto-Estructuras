/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interfazproyecto;

import Algoritmos.RecorridoDFS;
import Archivos.Estacion;
import Archivos.Grafo;
import Archivos.LectorCSV;
import Archivos.Lista;
import java.io.File;
import java.io.IOException;

/**
 * Clase principal. Además de arrancar el programa, guarda el estado que comparten todas
 * las pantallas (el grafo en memoria, el archivo actual, los cambios sin guardar y los
 * resaltados del mapa), porque cada ventana se cierra y se vuelve a crear al navegar.
 */
public class InterfazProyecto {

    /** Ruta relativa del archivo maestro que se carga al iniciar el programa. */
    public static final String RUTA_MAESTRO = "data/metro_caracas.csv";

    /** Red de transporte en memoria (grafo + tabla hash). */
    public static Grafo grafo = new Grafo();
    /** Archivo fuente actual (destino de "Guardar"), o null si no hay. */
    public static File archivoActual = null;
    /** Indica si hay cambios sin guardar. */
    public static boolean cambios = false;
    /** Estaciones de la ruta de menor tiempo que se resalta en el mapa (o null). */
    public static Lista<Estacion> rutaResaltada = null;
    /** Estaciones hub que se resaltan en el mapa (o null). */
    public static Lista<Estacion> hubsResaltados = null;
    /** Recorrido DFS de las subredes que se muestra en el mapa (o null). */
    public static RecorridoDFS dfsResaltado = null;

    /** Quita los resaltados de ruta, hubs y DFS del mapa. */
    public static void limpiarResaltados() {
        rutaResaltada = null;
        hubsResaltados = null;
        dfsResaltado = null;
    }

    /**
     * Carga el archivo maestro en memoria.
     *
     * @return null si se cargó bien, o un mensaje explicando por qué no se pudo
     */
    public static String cargarMaestro() {
        File[] candidatos = {new File(RUTA_MAESTRO), new File("../" + RUTA_MAESTRO)};
        for (int i = 0; i < candidatos.length; i++) {
            if (candidatos[i].isFile()) {
                try {
                    Grafo nuevo = new Grafo();
                    LectorCSV.cargarArchivo(candidatos[i], nuevo);
                    if (nuevo.numEstaciones() == 0) {
                        return "El archivo maestro no contiene estaciones válidas.";
                    }
                    grafo = nuevo;
                    archivoActual = candidatos[i];
                    cambios = false;
                    limpiarResaltados();
                    return null;
                } catch (IOException ex) {
                    return "No se pudo leer el archivo maestro: " + ex.getMessage();
                }
            }
        }
        return "No se encontró el archivo maestro '" + RUTA_MAESTRO + "'.\n"
             + "Use Archivo > Cargar para elegir un archivo CSV.";
    }

    /**
     * Etiquetas "ID - Nombre" de las estaciones en memoria, para llenar las listas
     * desplegables (usa Grafo.estaciones()).
     *
     * @return arreglo de etiquetas (vacío si no hay estaciones)
     */
    public static String[] etiquetasEstaciones() {
        String[] partes = grafo.estaciones().split(";");
        int n = 0;
        for (int i = 0; i < partes.length; i++) {
            if (!partes[i].trim().isEmpty()) {
                n++;
            }
        }
        String[] etiquetas = new String[n];
        int k = 0;
        for (int i = 0; i < partes.length; i++) {
            if (!partes[i].trim().isEmpty()) {
                etiquetas[k++] = partes[i].trim();
            }
        }
        return etiquetas;
    }

    /**
     * Extrae el ID de una etiqueta "ID - Nombre" (el ID es todo lo anterior a " - ").
     *
     * @param etiqueta elemento seleccionado en una lista desplegable
     * @return el ID, o null si no hay selección
     */
    public static String idDeEtiqueta(Object etiqueta) {
        if (etiqueta == null) {
            return null;
        }
        String s = etiqueta.toString();
        int k = s.indexOf(" - ");
        String id = k >= 0 ? s.substring(0, k) : s;
        return id.trim().isEmpty() ? null : id.trim();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // 0. Cargar el archivo maestro por defecto en memoria
            String errorCarga = cargarMaestro();

            // 1. Crear la instancia de tu ventana principal
            PrincipalInterface inicio = new PrincipalInterface();

            // 2. Centrar la ventana en la pantalla
            inicio.setLocationRelativeTo(null);

            // 3. Hacerla visible
            inicio.setVisible(true);

            if (errorCarga != null) {
                javax.swing.JOptionPane.showMessageDialog(inicio, errorCarga, "Archivo maestro",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
