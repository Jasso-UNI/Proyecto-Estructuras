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
import Archivos.Arista;
import Archivos.Grafo;
import Archivos.Lista;


public class Conectividad {

    private Conectividad() { }

    
    public static int[] componentes(Grafo g) {
        RecorridoDFS r = dfsTodo(g);
        int n = g.getLimite();
        int[] comp = new int[n];
        for (int i = 0; i < n; i++) {
            comp[i] = r.getComp(i);
        }
        return comp;
    }

    
    public static RecorridoDFS dfs(Grafo g, int inicio) {
        RecorridoDFS r = new RecorridoDFS(g.getLimite());
        boolean[] visitado = new boolean[g.getLimite()];
        if (inicio >= 0 && inicio < g.getLimite() && g.estacionEn(inicio) != null) {
            dfsDesde(g, inicio, r, visitado, 0, capacidadPila(g));
        }
        return r;
    }

    
    public static RecorridoDFS dfsTodo(Grafo g) {
        int n = g.getLimite();
        RecorridoDFS r = new RecorridoDFS(n);
        boolean[] visitado = new boolean[n];
        int cap = capacidadPila(g);
        int numero = 0;
        for (int i = 0; i < n; i++) {
            if (g.estacionEn(i) != null && !visitado[i]) {
                dfsDesde(g, i, r, visitado, numero, cap);
                numero++;
            }
        }
        return r;
    }

   
    private static int capacidadPila(Grafo g) {
        int cap = 1;
        for (int i = 0; i < g.getLimite(); i++) {
            if (g.estacionEn(i) != null) {
                cap += g.grado(i);
            }
        }
        return cap;
    }

    private static void dfsDesde(Grafo g, int inicio, RecorridoDFS r, boolean[] visitado, int numero, int cap) {
        int[] pilaNodo = new int[cap];
        int[] pilaPadre = new int[cap];
        int tope = 0;
        pilaNodo[tope] = inicio;
        pilaPadre[tope] = -1;
        tope++;
        while (tope > 0) {
            tope--;
            int u = pilaNodo[tope];
            int p = pilaPadre[tope];
            if (visitado[u]) {
                continue;
            }
            visitado[u] = true;
            r.registrar(u, p, numero);
            Lista<Arista> vec = g.vecinos(u);
            for (int k = vec.tamano() - 1; k >= 0; k--) { // en orden inverso para visitar en el orden de la lista
                int v = vec.obtener(k).getDestino();
                if (!visitado[v]) {
                    pilaNodo[tope] = v;
                    pilaPadre[tope] = u;
                    tope++;
                }
            }
        }
    }

    public static int[] componentesBFS(Grafo g) {
        int n = g.getLimite();
        int[] comp = new int[n];
        for (int i = 0; i < n; i++) {
            comp[i] = -1;
        }
        int[] cola = new int[n];
        int numero = 0;
        for (int inicio = 0; inicio < n; inicio++) {
            if (g.estacionEn(inicio) == null || comp[inicio] != -1) {
                continue;
            }
            int ini = 0;
            int fin = 0;
            cola[fin++] = inicio;
            comp[inicio] = numero;
            while (ini < fin) {
                int u = cola[ini++];
                Lista<Arista> vec = g.vecinos(u);
                for (int k = 0; k < vec.tamano(); k++) {
                    int v = vec.obtener(k).getDestino();
                    if (comp[v] == -1) {
                        comp[v] = numero;
                        cola[fin++] = v;
                    }
                }
            }
            numero++;
        }
        return comp;
    }


    public static int contar(int[] comp) {
        int max = -1;
        for (int i = 0; i < comp.length; i++) {
            if (comp[i] > max) {
                max = comp[i];
            }
        }
        return max + 1;
    }

    public static int[] tamanos(int[] comp) {
        int[] t = new int[contar(comp)];
        for (int i = 0; i < comp.length; i++) {
            if (comp[i] >= 0) {
                t[comp[i]]++;
            }
        }
        return t;
    }

    public static int[] ordenPorTamano(int[] tam) {
        int[] orden = new int[tam.length];
        for (int i = 0; i < orden.length; i++) {
            orden[i] = i;
        }
        for (int i = 0; i < orden.length - 1; i++) {
            int mejor = i;
            for (int j = i + 1; j < orden.length; j++) {
                if (tam[orden[j]] > tam[orden[mejor]]) {
                    mejor = j;
                }
            }
            int tmp = orden[i];
            orden[i] = orden[mejor];
            orden[mejor] = tmp;
        }
        return orden;
    }
}
