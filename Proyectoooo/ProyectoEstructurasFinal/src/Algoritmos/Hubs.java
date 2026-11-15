/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;
//
/**
 *
 * @author Jose Morales
 * @author Jesus Jasso
 * @author Jesus Femminella
 */
import Archivos.Estacion;
import Archivos.Grafo;
import Archivos.Lista;

/**
 * Identificación de hubs de transferencia mediante centralidad de grado:
 * las estaciones con mayor número de conexiones directas.
 */
public class Hubs {

    private Hubs() { }

    /**
     * Calcula el mayor grado presente en la red.
     *
     * @param g grafo a analizar
     * @return grado máximo (0 si no hay estaciones)
     */
    public static int gradoMaximo(Grafo g) {
        int max = 0;
        for (int i = 0; i < g.getLimite(); i++) {
            if (g.estacionEn(i) != null && g.grado(i) > max) {
                max = g.grado(i);
            }
        }
        return max;
    }

    /**
     * Devuelve las estaciones con el grado máximo (puede haber empates).
     *
     * @param g grafo a analizar
     * @return lista de hubs (vacía si la red no tiene conexiones)
     */
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

    /**
     * Genera un ranking de estaciones por grado descendente
     * (ordenamiento por selección; en empate conserva el orden de la red).
     *
     * @param g grafo a analizar
     * @param n cantidad máxima de estaciones del ranking
     * @return lista ordenada de hasta n estaciones
     */
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
