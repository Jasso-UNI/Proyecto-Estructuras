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
 */

/**
 * Cola de prioridad mínima (min-heap binario) implementada desde cero.
 * Almacena pares (prioridad, valor) enteros y se usa en el algoritmo de Dijkstra.
 */
public class ColaPrioridad {

    /** Prioridades de los elementos del heap. */
    private int[] prio = new int[16];
    /** Valores asociados a cada prioridad. */
    private int[] val = new int[16];
    /** Cantidad de elementos almacenados. */
    private int tam = 0;

    /**
     * Indica si la cola está vacía.
     *
     * @return true si no hay elementos
     */
    public boolean estaVacia() {
        return tam == 0;
    }

    /** Duplica la capacidad de los arreglos internos. */
    private void crecer() {
        int[] np = new int[prio.length * 2];
        int[] nv = new int[val.length * 2];
        System.arraycopy(prio, 0, np, 0, tam);
        System.arraycopy(val, 0, nv, 0, tam);
        prio = np;
        val = nv;
    }

    /**
     * Inserta un par prioridad-valor.
     *
     * @param p prioridad (menor = sale primero)
     * @param v valor asociado
     */
    public void insertar(int p, int v) {
        if (tam == prio.length) {
            crecer();
        }
        int i = tam++;
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (prio[padre] <= p) {
                break;
            }
            prio[i] = prio[padre];
            val[i] = val[padre];
            i = padre;
        }
        prio[i] = p;
        val[i] = v;
    }

    /**
     * Extrae el valor con la menor prioridad. La cola no debe estar vacía.
     *
     * @return el valor asociado a la menor prioridad
     */
    public int extraerMinimo() {
        int resultado = val[0];
        tam--;
        if (tam > 0) {
            int p = prio[tam];
            int v = val[tam];
            int i = 0;
            while (true) {
                int hijo = 2 * i + 1;
                if (hijo >= tam) {
                    break;
                }
                if (hijo + 1 < tam && prio[hijo + 1] < prio[hijo]) {
                    hijo++;
                }
                if (prio[hijo] >= p) {
                    break;
                }
                prio[i] = prio[hijo];
                val[i] = val[hijo];
                i = hijo;
            }
            prio[i] = p;
            val[i] = v;
        }
        return resultado;
    }

}
