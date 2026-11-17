# UrbanRoute — Sistema de Gestión y Análisis de Red de Transporte Multimodal

Aplicación de escritorio desarrollada en **Java with Ant** (versión Apache NetBeans IDE 31 y JDK 27) para modelar, visualizar y analizar una red de transporte urbano multimodal (basada en el Metro de Caracas) como un **grafo ponderado no dirigido**. Todas las estructuras de datos en memoria y los algoritmos de grafos están implementados desde cero, sin utilizar colecciones estándar de `java.util`.

## Integrantes

* **[José Morales]** — [@Josemmr28](https://github.com/Josemmr28)
* **[Jesús Jasso]** — [@Jasso-UNI](https://github.com/Jasso-UNI)
* **[Jesús Femminella]** — [@jfemminella-collab](https://github.com/jfemminella-collab)

**Repositorio:** [https://github.com/Jasso-UNI/Proyecto-Estructuras]

---

## Funciones Principales

* **Carga y persistencia de datos (CSV):** Carga automática de `data/metro_caracas.csv` al iniciar, importación de archivos externos mediante `JFileChooser`, validación de registros con reporte detallado de líneas inválidas (`ReporteCarga`) y guardado de cambios en disco (`EscritorCSV`).
* **Gestión dinámica de la red:** Alta y baja de estaciones y conexiones bidireccionales ponderadas por tiempo de traslado (en minutos), manteniendo sincronizados el grafo y la tabla hash.
* **Visualización interactiva del grafo:** Renderizado gráfico de estaciones y conexiones mediante **GraphStream**, con diferenciación visual por líneas y subredes.
* **Detección de subredes y recorrido DFS animado:** Identificación de componentes conexos y estaciones aisladas mediante recorridos **DFS** (iterativo con pila propia) y **BFS** (con cola FIFO propia), incluyendo animación paso a paso sobre el visor.
* **Ruta de menor tiempo:** Cálculo del recorrido óptimo entre una estación de origen y una de destino mediante el **algoritmo de Dijkstra**.
* **Identificación de *Hubs*:** Detección y ranking de las estaciones críticas de transferencia según su grado (cantidad de conexiones directas).

---

## Arquitectura y Estructuras de Datos Propias

La raíz del proyecto contiene la carpeta `data/` (con el archivo maestro `metro_caracas.csv`) y la carpeta `src/`, organizada en tres paquetes principales junto con las librerías de renderizado en `GraphStream`:

* **`Archivos` (Estructuras de datos, modelos y persistencia CSV):**
  * `Lista<T>`: Arreglo dinámico genérico con redimensionamiento automático.
  * `Hash`: Tabla hash con encadenamiento separado y factor de carga máximo de `0.75` para consultar estaciones por ID en tiempo promedio $O(1)$.
  * `Grafo`: Grafo ponderado no dirigido basado en listas de adyacencia (`Lista<Arista>[]`) sincronizadas con la tabla hash.
  * `ColaPrioridad`: Montículo binario mínimo (*Min-Heap*) empleado por Dijkstra para extraer en $O(\log V)$ la estación con menor tiempo acumulado.
  * `Estacion` y `Arista`: Modelos de los vértices y conexiones ponderadas de la red.
  * `LectorCSV`, `EscritorCSV` y `ReporteCarga`: Lectura, escritura y auditoría de archivos `.csv`.

* **`Algoritmos` (Análisis de grafos):**
  * `Conectividad`: Detección de subredes/componentes conexos en $O(V + E)$ mediante DFS iterativo (`dfsTodo`, `componentes`) y BFS (`componentesBFS`).
  * `RecorridoDFS`: Contenedor de resultados del recorrido en profundidad (orden de visita, posición, árbol de padres y componente asignada).
  * `RutaOptima` y `ResultadoRuta`: Implementación del algoritmo de Dijkstra en $O((V + E)\log V)$ y encapsulamiento del trayecto óptimo, tiempo total o motivo cuando no existe ruta.
  * `Hubs`: Cálculo del grado máximo, estaciones *hub* principales y ranking top-$N$ de conectividad.

* **`interfazproyecto` (Interfaz gráfica en Swing):**
  * Pantallas principales (`PrincipalInterface`, `MapInterface`, `ArchivoInterface`, `RedInterface`, `RutasInterface`) y diálogos modales de confirmación y edición (`AgregarEstacion`, `AgregarConexion`, `EliminarEstacion`, `EliminarConexion`, `ConfirmInterface`).
  * `VisorGrafo`: Integración con **GraphStream** para dibujar el grafo y animar recorridos.
  * `Controlador` y `Dialogos`: Gestión centralizada del estado de la red, validaciones y cuadros de diálogo.

---

## Flujo de Pantallas y Uso del Mapa

* **Navegación general:** `Principal` --> `Mapa` --> `{ Archivo (Cargar / Guardar) | Gestión de Red (Agregar / Eliminar estaciones y conexiones) | Rutas (Ruta óptima / Subredes / Hubs) }`.
* **Recorrido DFS animado en el mapa:** Desde la vista del mapa se selecciona una estación inicial (en el selector o haciendo clic sobre el nodo) y se pulsa **Ejecutar DFS**. Se colorean las estaciones ya visitadas en **azul**, la estación activa en **rojo** y resalta las aristas del árbol DFS mientras muestra el orden de visita. La opción **Subredes con DFS** recorre toda la red diferenciando cada componente conexa por color, y el control de velocidad ajusta el ritmo de la animación.

---

## Formato del Archivo CSV

El sistema admite archivos `.csv` estructurados en dos bloques (reconocidos por encabezado o número de columnas):

```csv
ID_Estacion,Nombre,Linea,Zona,Capacidad
E01,Propatria,Línea 1,Oeste,4500
E02,Pérez Bonalde,Línea 1,Oeste,4000

Estacion_Origen,Estacion_Destino,Tiempo
E01,E02,4
```
---

## COMENTARIO SOBRE ENTREGA FINAL

Para la entrega final, se sobreescribió la última carpeta contenedora de los archivos del proyecto que estaba presente en el repositorio. Esto con el motivo de organizar correctamente el repositorio con los archivos finales modificados y ajustados. Además de esto, se eliminó la imagen png del boceto/modelo inicial de las interfaces y su flujo.
