/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Archivos_TableHash_Incluido;

/**
 *
 * @author josem
 */
public class TableHash {

    private static class NodoHash {

        String clave;
        Estacion valor;
        NodoHash sig;

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
    public TableHash() {
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

    private String normalizar(String clave) {
        return clave == null ? "" : clave.trim().toUpperCase();
    }

    public void insertar(String claveId, Estacion estacion) {
        if (claveId == null || estacion == null) {
            return;
        }
        String clave = normalizar(claveId);

        if ((double) (tam + 1) / cubetas.length > FACTOR_MAX) {
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

    public Estacion buscar(String claveId) {
        if (claveId == null) {
            return null;
        }
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

    public boolean contiene(String claveId) {
        return buscar(claveId) != null;
    }

    public boolean eliminar(String claveId) {
        if (claveId == null) {
            return false;
        }
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

    public int getTamano() {
        return tam;
    }

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
