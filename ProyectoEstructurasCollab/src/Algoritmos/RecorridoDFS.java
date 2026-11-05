/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;
//

/**
 *
 * @author josem
 */
public class RecorridoDFS {

    private final int[] orden;
    private final int[] posicion;
    private final int[] padre;
    private final int[] comp;
    private int cantidad = 0;
    private int numComponentes = 0;

    RecorridoDFS(int limite) {
        orden = new int[limite];
        posicion = new int[limite];
        padre = new int[limite];
        comp = new int[limite];
        for (int i = 0; i < limite; i++) {
            posicion[i] = -1;
            padre[i] = -1;
            comp[i] = -1;
        }
    }

    void registrar(int u, int p, int c) {
        orden[cantidad] = u;
        posicion[u] = cantidad;
        padre[u] = p;
        comp[u] = c;
        cantidad++;
        if (c + 1 > numComponentes) {
            numComponentes = c + 1;
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getNumComponentes() {
        return numComponentes;
    }

    public int getOrden(int k) {
        return orden[k];
    }

    public int getPosicion(int i) {
        return i < posicion.length ? posicion[i] : -1;
    }

    public int getPadre(int i) {
        return i < padre.length ? padre[i] : -1;
    }

    public int getComp(int i) {
        return i < comp.length ? comp[i] : -1;
    }
}
