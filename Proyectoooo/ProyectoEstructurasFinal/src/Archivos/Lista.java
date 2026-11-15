/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Archivos;
/**
 * Lista dinámica genérica implementada desde cero sobre un arreglo.
 * Reemplaza a la lista dinámica de la librería estándar, cuyo uso no está permitido
 * en el proyecto.
 *
 * @param <T> tipo de los elementos almacenados
 * 
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */


public class Lista<T> {

    /** Arreglo interno donde se guardan los elementos. */
    private Object[] datos;
    /** Cantidad de posiciones ocupadas. */
    private int tam;

    /** Crea una lista vacía con capacidad inicial de 8 elementos. */
    public Lista() {
        datos = new Object[8];
        tam = 0;
    }

    /**
     * Agrega un elemento al final de la lista.
     *
     * @param x elemento a agregar
     */
    public void agregar(T x) {
        if (tam == datos.length) {
            crecer();
        }
        datos[tam++] = x;
    }

    /**
     * Obtiene el elemento de una posición.
     *
     * @param i posición (desde 0)
     * @return el elemento almacenado en la posición i
     * @throws IndexOutOfBoundsException si la posición no es válida
     */
    @SuppressWarnings("unchecked")
    public T obtener(int i) {
        if (i < 0 || i >= tam) {
            throw new IndexOutOfBoundsException("Posición inválida: " + i);
        }
        return (T) datos[i];
    }

    /**
     * Reemplaza el elemento de una posición.
     *
     * @param i posición (desde 0)
     * @param x nuevo elemento
     */
    public void establecer(int i, T x) {
        if (i < 0 || i >= tam) {
            throw new IndexOutOfBoundsException("Posición inválida: " + i);
        }
        datos[i] = x;
    }

    /**
     * Elimina el elemento de la posición i desplazando los siguientes.
     *
     * @param i posición a eliminar
     */
    public void eliminarEn(int i) {
        if (i < 0 || i >= tam) {
            throw new IndexOutOfBoundsException("Posición inválida: " + i);
        }
        for (int k = i; k < tam - 1; k++) {
            datos[k] = datos[k + 1];
        }
        datos[--tam] = null;
    }

    /**
     * Indica la cantidad de elementos almacenados.
     *
     * @return número de elementos
     */
    public int tamano() {
        return tam;
    }

    /**
     * Indica si la lista no tiene elementos.
     *
     * @return true si está vacía
     */
    public boolean estaVacia() {
        return tam == 0;
    }

    /** Elimina todos los elementos de la lista. */
    public void limpiar() {
        for (int i = 0; i < tam; i++) {
            datos[i] = null;
        }
        tam = 0;
    }

    /** Duplica la capacidad del arreglo interno. */
    private void crecer() {
        Object[] nuevo = new Object[datos.length * 2];
        System.arraycopy(datos, 0, nuevo, 0, tam);
        datos = nuevo;
    }
}
