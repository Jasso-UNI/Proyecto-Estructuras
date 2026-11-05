/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Archivos;
/**
 *
 * @author josem
 */


public class Lista<T> {

    private Object[] datos;
    private int tam;
    
    public Lista() {
        datos = new Object[8];
        tam = 0;
    }

    public void agregar(T x) {
        if (tam == datos.length) {
            crecer();
        }
        datos[tam++] = x;
    }

    @SuppressWarnings("unchecked")
    public T obtener(int i) {
        if (i < 0 || i >= tam) {
            throw new IndexOutOfBoundsException("Posición inválida: " + i);
        }
        return (T) datos[i];
    }

    public void establecer(int i, T x) {
        if (i < 0 || i >= tam) {
            throw new IndexOutOfBoundsException("Posición inválida: " + i);
        }
        datos[i] = x;
    }

    public void eliminarEn(int i) {
        if (i < 0 || i >= tam) {
            throw new IndexOutOfBoundsException("Posición inválida: " + i);
        }
        for (int k = i; k < tam - 1; k++) {
            datos[k] = datos[k + 1];
        }
        datos[--tam] = null;
    }

    public int tamano() {
        return tam;
    }

    public boolean estaVacia() {
        return tam == 0;
    }

    public void limpiar() {
        for (int i = 0; i < tam; i++) {
            datos[i] = null;
        }
        tam = 0;
    }

    private void crecer() {
        Object[] nuevo = new Object[datos.length * 2];
        System.arraycopy(datos, 0, nuevo, 0, tam);
        datos = nuevo;
    }
}
