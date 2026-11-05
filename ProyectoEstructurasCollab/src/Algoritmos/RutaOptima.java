package Algoritmos;

import Archivos.Arista;
import Archivos.ColaPrioridad;
import Archivos.Estacion;
import Archivos.Grafo;
import Archivos.Lista;

public class RutaOptima {

    private RutaOptima() { }

    public static ResultadoRuta calcular(Grafo g, String idOrigen, String idDestino) {
        ResultadoRuta res = new ResultadoRuta();
        Estacion a = g.buscarEstacion(idOrigen);
        Estacion b = g.buscarEstacion(idDestino);
        if (a == null || b == null) {
            res.setMotivo("La estación de origen o de destino no existe en la red.");
            return res;
        }
        int n = g.getLimite();
        final int INF = Integer.MAX_VALUE;
        int[] dist = new int[n];
        int[] previo = new int[n];
        boolean[] listo = new boolean[n];
        for (int i = 0; i < n; i++) {
            dist[i] = INF;
            previo[i] = -1;
        }
        int s = a.getIndice();
        int t = b.getIndice();
        dist[s] = 0;
        ColaPrioridad cola = new ColaPrioridad();
        cola.insertar(0, s);
        while (!cola.estaVacia()) {
            int u = cola.extraerMinimo();
            if (listo[u]) {
                continue;
            }
            listo[u] = true;
            if (u == t) {
                break;
            }
            Lista<Arista> vec = g.vecinos(u);
            for (int k = 0; k < vec.tamano(); k++) {
                Arista ar = vec.obtener(k);
                int v = ar.getDestino();
                int nuevo = dist[u] + ar.getTiempo();
                if (!listo[v] && nuevo < dist[v]) {
                    dist[v] = nuevo;
                    previo[v] = u;
                    cola.insertar(nuevo, v);
                }
            }
        }
        if (dist[t] == INF) {
            res.setMotivo("No existe ruta: las estaciones están en subredes distintas (desconectadas).");
            return res;
        }
        Lista<Estacion> inverso = new Lista<>();
        for (int x = t; x != -1; x = previo[x]) {
            inverso.agregar(g.estacionEn(x));
        }
        for (int i = inverso.tamano() - 1; i >= 0; i--) {
            res.getCamino().agregar(inverso.obtener(i));
        }
        res.setExiste(true);
        res.setTiempoTotal(dist[t]);
        return res;
    }
}
