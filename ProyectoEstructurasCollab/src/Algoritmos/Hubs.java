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
import Archivos.Estacion;
import Archivos.Grafo;
import Archivos.Lista;


public class Hubs {

    private Hubs() { }

    public static int gradoMaximo(Grafo g) {
        int max = 0;
        for (int i = 0; i < g.getLimite(); i++) {
            if (g.estacionEn(i) != null && g.grado(i) > max) {
                max = g.grado(i);
            }
        }
        return max;
    }

    public static Lista<Estacion> hubs(Grafo g) {
        Lista<Estacion> r = new Lista<>();
        int max = gradoMaximo(g);
        if (max == 0) {
            return r;
        }
        for (int i = 0; i < g.getLimite(); i++) {
            if (g.estacionEn(i) != null && g.grado(i) == max) {
                r.agregar(g.estacionEn(i));
            }
        }
        return r;
    }

    public static Lista<Estacion> ranking(Grafo g, int n) {
        Lista<Estacion> todas = g.listaEstaciones();
        int m = todas.tamano();
        int[] grados = new int[m];
        for (int i = 0; i < m; i++) {
            grados[i] = g.grado(todas.obtener(i).getIndice());
        }
        Lista<Estacion> r = new Lista<>();
        for (int i = 0; i < m && i < n; i++) {
            int mejor = i;
            for (int j = i + 1; j < m; j++) {
                if (grados[j] > grados[mejor]) {
                    mejor = j;
                }
            }
            int tg = grados[i];
            grados[i] = grados[mejor];
            grados[mejor] = tg;
            Estacion te = todas.obtener(i);
            todas.establecer(i, todas.obtener(mejor));
            todas.establecer(mejor, te);
            r.agregar(todas.obtener(i));
        }
        return r;
    }
}
