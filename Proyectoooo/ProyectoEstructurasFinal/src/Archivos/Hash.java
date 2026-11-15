/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

/**
 * Tabla hash propia implementada con encadenamiento separado (listas enlazadas por cubeta)
 * para almacenar y recuperar objetos en tiempo promedio O(1) mediante su ID.
 * <p>
 * Normaliza las claves (sin distinguir mayúsculas ni espacios extremos) y redimensiona
 * dinámicamente su capacidad cuando se supera el factor de carga máximo.
 *
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */


public class Hash {
    
    /**
     * Nodo interno utilizado para resolver colisiones mediante encadenamiento en cada cubeta.
     */
    private static class NodoHash {
        String clave;
        Estacion valor;
        NodoHash sig;

        /**
         * Crea un nuevo nodo para la tabla hash.
         *
         * @param clave identificador normalizado de la estación
         * @param valor instancia de {@link Estacion} a almacenar
         */
        NodoHash(String clave, Estacion valor) {
            this.clave = clave;
            this.valor = valor;
            this.sig = null;
        }
    }

    private NodoHash[] cubetas;
    private int tam;
    // Sustentado por: https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/util/HashMap.html
    private static final double FACTOR_MAX = 0.75;

    @SuppressWarnings("unchecked")
    public Hash() {
        this.cubetas = new NodoHash[16];
        this.tam = 0;
    }
    
    /* La funcionHash esta sustentada por: 
https://algs4.cs.princeton.edu/34hash/
https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/lang/String.html
 */
    private int funcionHash(String clave) {
        int hash = 7;
        for (int i = 0; i < clave.length(); i++) {
            hash = hash * 31 + clave.charAt(i);
        }
        return (hash & 0x7FFFFFFF) % cubetas.length; // máscara: evita el error de Math.abs(Integer.MIN_VALUE)
    }

    /**
     * Elimina espacios laterales y convierte a mayúsculas.
     *
     * @param clave identificador original
     * @return clave limpia en mayúsculas, o cadena vacía si es nula
     */
    private String normalizar(String clave) {
        return clave == null ? "" : clave.trim().toUpperCase();
    }

    /**
     * Inserta una nueva estación en la tabla hash o actualiza su referencia si el ID ya existe.
     * Si al insertar se supera el factor de carga máximo, la tabla se redimensiona automáticamente.
     *
     * @param claveId  identificador único de la estación
     * @param estacion objeto {@link Estacion} a guardar
     */
    public void insertar(String claveId, Estacion estacion) {
        if (claveId == null || estacion == null) return;
        String clave = normalizar(claveId);
        
        if ((double)(tam + 1) / cubetas.length > FACTOR_MAX) {
            redimensionar();
        }

        int idx = funcionHash(clave);
        NodoHash actual = cubetas[idx];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                actual.valor = estacion; // Actualiza si ya existe
                return;
            }
            actual = actual.sig;
        }

        NodoHash nuevo = new NodoHash(clave, estacion);
        nuevo.sig = cubetas[idx];
        cubetas[idx] = nuevo;
        tam++;
    }

    /**
     * Busca y retorna la estación asociada a un identificador dado.
     *
     * @param claveId ID de la estación a consultar
     * @return la instancia de {@link Estacion} correspondiente, o {@code null} si no existe
     */
    public Estacion buscar(String claveId) {
        if (claveId == null) return null;
        String clave = normalizar(claveId);
        int idx = funcionHash(clave);
        NodoHash actual = cubetas[idx];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.sig;
        }
        return null;
    }

    /**
     * Verifica si existe una estación registrada con el identificador indicado.
     *
     * @param claveId ID de la estación a verificar
     * @return {@code true} si la clave se encuentra en la tabla, {@code false} en caso contrario
     */
    public boolean contiene(String claveId) {
        return buscar(claveId) != null;
    }

    /**
     * Elimina de la tabla hash la entrada asociada al identificador indicado.
     *
     * @param claveId ID de la estación a remover
     * @return {@code true} si la estación existía y fue eliminada, {@code false} si no se encontró
     */
    public boolean eliminar(String claveId) {
        if (claveId == null) return false;
        String clave = normalizar(claveId);
        int idx = funcionHash(clave);
        NodoHash actual = cubetas[idx];
        NodoHash previo = null;

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                if (previo == null) {
                    cubetas[idx] = actual.sig;
                } else {
                    previo.sig = actual.sig;
                }
                tam--;
                return true;
            }
            previo = actual;
            actual = actual.sig;
        }
        return false;
    }

    // Sustentado por: https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/util/HashMap.html
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        NodoHash[] antiguas = cubetas;
        cubetas = new NodoHash[antiguas.length * 2];
        tam = 0;

        for (NodoHash cabeza : antiguas) {
            NodoHash actual = cabeza;
            while (actual != null) {
                insertar(actual.clave, actual.valor);
                actual = actual.sig;
            }
        }
    }

    /**
     * Obtiene la cantidad total de estaciones almacenadas en la tabla hash.
     *
     * @return número de elementos registrados
     */
    public int getTamano() {
        return tam;
    }

    /**
     * Devuelve todas las estaciones almacenadas (sin un orden particular).
     *
     * @return lista con las estaciones de la tabla
     */
    public Lista<Estacion> valores() {
        Lista<Estacion> resultado = new Lista<>();
        for (int i = 0; i < cubetas.length; i++) {
            for (NodoHash n = cubetas[i]; n != null; n = n.sig) {
                resultado.agregar(n.valor);
            }
        }
        return resultado;
    }
}
