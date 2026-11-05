/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos;

/**
 *
 *
 * @author josem
 */
public class Grafo {
    private final Hash tablaHash;
    private Lista<Arista>[] adyacencia;
    private Estacion[] porIndice;
    private int siguienteIndice;

    @SuppressWarnings("unchecked")
    public Grafo() {
        this.tablaHash = new Hash();
        this.adyacencia = (Lista<Arista>[]) new Lista[16];
        this.porIndice = new Estacion[16];
        this.siguienteIndice = 0;
    }

    public boolean agregarEstacion(Estacion estacion) {
        if (estacion == null || estacion.getId().isEmpty() || tablaHash.contiene(estacion.getId())) {
            return false;
        }

        if (siguienteIndice >= adyacencia.length) {
            crecerArreglos();
        }

        estacion.indice = siguienteIndice;
        porIndice[siguienteIndice] = estacion;
        adyacencia[siguienteIndice] = new Lista<Arista>();

        tablaHash.insertar(estacion.getId(), estacion);
        siguienteIndice++;
        return true;
    }

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

    public boolean existeConexion(String idA, String idB) {
        Estacion a = tablaHash.buscar(idA);
        Estacion b = tablaHash.buscar(idB);
        return a != null && b != null && existeConexion(a.indice, b.indice);
    }

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

    public Estacion buscarEstacion(String id) {
        return tablaHash.buscar(id);
    }

    public Lista<Arista> obtenerVecinos(String id) {
        Estacion est = tablaHash.buscar(id);
        if (est != null && adyacencia[est.indice] != null) {
            return adyacencia[est.indice];
        }
        return new Lista<>();
    }
    
    public Estacion[] getEstaciones() {
        return porIndice;
    }

    public int getLimite() {
        return siguienteIndice;
    }

    public Estacion estacionEn(int i) {
        return porIndice[i];
    }

    public Lista<Arista> vecinos(int i) {
        return adyacencia[i];
    }

    public int grado(int i) {
        return adyacencia[i].tamano();
    }

    public int numEstaciones() {
        return tablaHash.getTamano();
    }

    public int numConexiones() {
        int total = 0;
        for (int i = 0; i < siguienteIndice; i++) {
            if (adyacencia[i] != null) {
                total += adyacencia[i].tamano();
            }
        }
        return total / 2;
    }

    public Lista<Estacion> listaEstaciones() {
        Lista<Estacion> r = new Lista<>();
        for (int i = 0; i < siguienteIndice; i++) {
            if (porIndice[i] != null) {
                r.agregar(porIndice[i]);
            }
        }
        return r;
    }
}
