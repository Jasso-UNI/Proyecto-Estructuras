/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

/**
 *
 * @author josem
 */

public class Grafo {
    private final Hash tablaHash;
    private java.util.List<Arista>[] adyacencia;
    private Estacion[] porIndice;
    private int siguienteIndice;

    @SuppressWarnings("unchecked")
    public Grafo() {
        this.tablaHash = new Hash();
        this.adyacencia = (java.util.List<Arista>[]) new java.util.List[16];
        this.porIndice = new Estacion[16];
        this.siguienteIndice = 0;
    }

    public boolean agregarEstacion(Estacion estacion) {
        if (estacion == null || tablaHash.contiene(estacion.getId())) {
            return false;
        }

        if (siguienteIndice >= adyacencia.length) {
            crecerArreglos();
        }

        estacion.indice = siguienteIndice;
        porIndice[siguienteIndice] = estacion;
        adyacencia[siguienteIndice] = new java.util.ArrayList<>();
        
        tablaHash.insertar(estacion.getId(), estacion);
        siguienteIndice++;
        return true;
    }

    public boolean agregarConexion(String idA, String idB, int tiempo) {
        Estacion estA = tablaHash.buscar(idA);
        Estacion estB = tablaHash.buscar(idB);

        if (estA == null || estB == null || estA.equals(estB) || tiempo <= 0) {
            return false;
        }

        if (existeConexion(estA.indice, estB.indice)) {
            return false;
        }

        adyacencia[estA.indice].add(new Arista(estB.indice, tiempo));
        adyacencia[estB.indice].add(new Arista(estA.indice, tiempo)); // Bidireccional
        return true;
    }

    private boolean existeConexion(int idxA, int idxB) {
        for (Arista arista : adyacencia[idxA]) {
            if (arista.getDestino() == idxB) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEstacion(String id) {
        Estacion est = tablaHash.buscar(id);
        if (est == null) return false;

        int idx = est.indice;

        // Eliminar todas las aristas entrantes de los vecinos
        for (Arista arista : adyacencia[idx]) {
            removerAristaDirecta(arista.getDestino(), idx);
        }

        adyacencia[idx] = null;
        porIndice[idx] = null;
        tablaHash.eliminar(id);
        return true;
    }

    private void removerAristaDirecta(int desdeIdx, int haciaIdx) {
        java.util.List<Arista> lista = adyacencia[desdeIdx];
        if (lista != null) {
            lista.removeIf(arista -> arista.getDestino() == haciaIdx);
        }
    }

    @SuppressWarnings("unchecked")
    private void crecerArreglos() {
        int nuevoTamano = adyacencia.length * 2;
        java.util.List<Arista>[] nuevoAdy = (java.util.List<Arista>[]) new java.util.List[nuevoTamano];
        Estacion[] nuevoPorIndice = new Estacion[nuevoTamano];

        System.arraycopy(adyacencia, 0, nuevoAdy, 0, adyacencia.length);
        System.arraycopy(porIndice, 0, nuevoPorIndice, 0, porIndice.length);

        adyacencia = nuevoAdy;
        porIndice = nuevoPorIndice;
    }

    public Estacion buscarEstacion(String id) {
        return tablaHash.buscar(id);
    }

    public java.util.List<Arista> obtenerVecinos(String id) {
        Estacion est = tablaHash.buscar(id);
        if (est != null && adyacencia[est.indice] != null) {
            return adyacencia[est.indice];
        }
        return new java.util.ArrayList<>();
    }

    public Estacion[] getEstaciones() {
        return porIndice;
    }
    
    public int getLimite() {
        return siguienteIndice;
    }
}