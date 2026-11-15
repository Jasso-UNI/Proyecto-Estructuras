/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

/**
 * Red de transporte modelada como grafo ponderado no dirigido con lista de adyacencia.
 * <p>
 * Cada estación recibe un índice entero. Un arreglo de listas de adyacencia guarda las
 * conexiones de cada índice y la búsqueda de estaciones por ID se delega en la
 * {@link Hash}. Toda modificación mantiene sincronizadas ambas estructuras.
 * Al eliminar una estación su posición queda en null y los algoritmos deben omitirla.
 *
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */
public class Grafo {
    private final Hash tablaHash;
    private Lista<Arista>[] adyacencia;
    private Estacion[] porIndice;
    private int siguienteIndice;

    /** Crea un grafo vacío. */
    @SuppressWarnings("unchecked")
    public Grafo() {
        this.tablaHash = new Hash();
        this.adyacencia = (Lista<Arista>[]) new Lista[16];
        this.porIndice = new Estacion[16];
        this.siguienteIndice = 0;
    }

    /**
     * Agrega una estación al grafo y a la tabla hash.
     *
     * @param estacion estación a agregar
     * @return false si es nula, no tiene ID o el ID ya existe
     */
    public boolean agregarEstacion(Estacion estacion) {
        if (estacion == null || estacion.getId().isEmpty() || tablaHash.contiene(estacion.getId())) {
            return false;
        }

        if (siguienteIndice >= adyacencia.length) {
            crecerArreglos();
        }

        estacion.indice = siguienteIndice;
        porIndice[siguienteIndice] = estacion;
        adyacencia[siguienteIndice] = new Lista<>();

        tablaHash.insertar(estacion.getId(), estacion);
        siguienteIndice++;
        return true;
    }
    
    /**
     * Agrega una conexión bidireccional entre dos estaciones.
     *
     * @param idA ID de la primera estación
     * @param idB ID de la segunda estación
     * @param tiempo minutos de traslado (mayor que 0)
     * @return true si se agregó; false si falta una estación, son la misma,
     *         el tiempo no es positivo o la conexión ya existía
     */
    public boolean agregarConexion(String idA, String idB, int tiempo) {
        Estacion estA = tablaHash.buscar(idA);
        Estacion estB = tablaHash.buscar(idB);

        if (estA == null || estB == null || estA == estB || tiempo <= 0) {
            return false;
        }

        if (existeConexion(estA.indice, estB.indice)) {
            return false;
        }

        adyacencia[estA.indice].agregar(new Arista(estB.indice, tiempo));
        adyacencia[estB.indice].agregar(new Arista(estA.indice, tiempo)); // Bidireccional
        return true;
    }

    private boolean existeConexion(int idxA, int idxB) {
        Lista<Arista> lista = adyacencia[idxA];
        for (int i = 0; i < lista.tamano(); i++) {
            if (lista.obtener(i).getDestino() == idxB) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indica si dos estaciones están conectadas directamente.
     *
     * @param idA ID de la primera estación
     * @param idB ID de la segunda estación
     * @return true si existe la conexión
     */
    public boolean existeConexion(String idA, String idB) {
        Estacion a = tablaHash.buscar(idA);
        Estacion b = tablaHash.buscar(idB);
        return a != null && b != null && existeConexion(a.indice, b.indice);
    }

    /**
     * Elimina la conexión entre dos estaciones.
     *
     * @param idA ID de la primera estación
     * @param idB ID de la segunda estación
     * @return true si la conexión existía y fue eliminada
     */
    public boolean eliminarConexion(String idA, String idB) {
        Estacion a = tablaHash.buscar(idA);
        Estacion b = tablaHash.buscar(idB);
        if (a == null || b == null || !existeConexion(a.indice, b.indice)) {
            return false;
        }
        removerAristaDirecta(a.indice, b.indice);
        removerAristaDirecta(b.indice, a.indice);
        return true;
    }

    /**
     * Elimina una estación con todas sus conexiones, tanto del grafo como de la tabla hash.
     *
     * @param id ID de la estación
     * @return true si existía y fue eliminada
     */
    public boolean eliminarEstacion(String id) {
        Estacion est = tablaHash.buscar(id);
        if (est == null) return false;

        int idx = est.indice;

        // Eliminar todas las aristas entrantes de los vecinos
        Lista<Arista> vecinos = adyacencia[idx];
        for (int i = 0; i < vecinos.tamano(); i++) {
            removerAristaDirecta(vecinos.obtener(i).getDestino(), idx);
        }

        adyacencia[idx] = null;
        porIndice[idx] = null;
        tablaHash.eliminar(est.getId());
        est.indice = -1;
        return true;
    }

    /**
     * Elimina la arista dirigida desde {@code desdeIdx} hacia {@code haciaIdx} en su lista de adyacencia.
     *
     * @param desdeIdx índice de la estación cuya lista se va a modificar
     * @param haciaIdx índice de la estación destino a desconectar
     */
    private void removerAristaDirecta(int desdeIdx, int haciaIdx) {
        Lista<Arista> lista = adyacencia[desdeIdx];
        if (lista != null) {
            for (int i = 0; i < lista.tamano(); i++) {
                if (lista.obtener(i).getDestino() == haciaIdx) {
                    lista.eliminarEn(i);
                    return;
                }
            }
        }
    }

    /**
     * Duplica la capacidad de los arreglos internos {@code adyacencia} y {@code porIndice}
     * cuando se alcanza el límite actual de almacenamiento.
     */
    @SuppressWarnings("unchecked")
    private void crecerArreglos() {
        int nuevoTamano = adyacencia.length * 2;
        Lista<Arista>[] nuevoAdy = (Lista<Arista>[]) new Lista[nuevoTamano];
        Estacion[] nuevoPorIndice = new Estacion[nuevoTamano];

        System.arraycopy(adyacencia, 0, nuevoAdy, 0, adyacencia.length);
        System.arraycopy(porIndice, 0, nuevoPorIndice, 0, porIndice.length);

        adyacencia = nuevoAdy;
        porIndice = nuevoPorIndice;
    }

    /**
     * Busca una estación por ID delegando en la tabla hash.
     *
     * @param id ID de la estación
     * @return la estación o null si no existe
     */
    public Estacion buscarEstacion(String id) {
        return tablaHash.buscar(id);
    }

    /**
     * Conexiones de una estación identificada por su ID.
     *
     * @param id ID de la estación
     * @return lista de aristas (vacía si la estación no existe)
     */
    public Lista<Arista> obtenerVecinos(String id) {
        Estacion est = tablaHash.buscar(id);
        if (est != null && adyacencia[est.indice] != null) {
            return adyacencia[est.indice];
        }
        return new Lista<>();
    }
    
    /**
     * Arreglo interno de estaciones por índice (las eliminadas son null).
     * Se debe recorrer solo hasta {@link #getLimite()}.
     *
     * @return arreglo de estaciones
     */
    public Estacion[] getEstaciones() {
        return porIndice;
    }

    /** @return límite (exclusivo) de índices a recorrer */
    public int getLimite() {
        return siguienteIndice;
    }

    /**
     * Estación ubicada en un índice.
     *
     * @param i índice
     * @return la estación, o null si fue eliminada
     */
    public Estacion estacionEn(int i) {
        return porIndice[i];
    }

    /**
     * Conexiones de la estación ubicada en un índice.
     *
     * @param i índice de la estación
     * @return lista de aristas (null si fue eliminada)
     */
    public Lista<Arista> vecinos(int i) {
        return adyacencia[i];
    }

    /**
     * Grado de una estación (cantidad de conexiones directas).
     *
     * @param i índice de la estación
     * @return grado
     */
    public int grado(int i) {
        return adyacencia[i].tamano();
    }

    /** @return cantidad de estaciones existentes */
    public int numEstaciones() {
        return tablaHash.getTamano();
    }

    /** @return cantidad de conexiones (cada una cuenta una sola vez) */
    public int numConexiones() {
        int total = 0;
        for (int i = 0; i < siguienteIndice; i++) {
            if (adyacencia[i] != null) {
                total += adyacencia[i].tamano();
            }
        }
        return total / 2;
    }

    /**
     * Lista las estaciones existentes en orden de creación.
     *
     * @return lista de estaciones
     */
    public Lista<Estacion> listaEstaciones() {
        Lista<Estacion> r = new Lista<>();
        for (int i = 0; i < siguienteIndice; i++) {
            if (porIndice[i] != null) {
                r.agregar(porIndice[i]);
            }
        }
        return r;
    }
    
    /**
     * Construye una cadena de texto con todas las estaciones activas en formato {@code "ID - Nombre;"}.
     *
     * @return cadena con el identificador y nombre de cada estación existente
     */
    public String estaciones(){
        String IdNombre = "";
        for(int i = 0; i < siguienteIndice; i++){
            if (porIndice[i] != null){
                IdNombre += porIndice[i].getId() + " - " + porIndice[i].getNombre() + ";";
            }
        }
        return IdNombre;
    }
}
