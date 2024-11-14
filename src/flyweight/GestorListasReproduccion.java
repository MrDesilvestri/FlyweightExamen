package flyweight;

import db.ListaReproduccionDAO;
import implementacion.ListaReproduccion;

import java.util.HashMap;
import java.util.Map;

public class GestorListasReproduccion {
    private static final Map<String, ListaReproduccion> listasEnMemoria = new HashMap<>();
    private static final ListaReproduccionDAO listaDAO = new ListaReproduccionDAO();
    private static final long LIMITE_MEMORIA_MB = 100; // Límite de memoria en MB

    // Método para verificar la memoria y mover listas menos usadas si es necesario
    public static void verificarMemoriaYExpulsar() {
        long memoriaUsadaMB = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);

        if (memoriaUsadaMB > LIMITE_MEMORIA_MB) {
            System.out.println("Memoria excede el límite. Moviendo listas menos usadas a la base de datos...");
            listaDAO.moverListasMenosUsadasABaseDatos(listasEnMemoria, 5); // Expulsar listas con menos de 5 usos
        }
    }

    // Método para obtener una lista de reproducción
    public static ListaReproduccion obtenerLista(String nombreLista) {
        verificarMemoriaYExpulsar(); // Verificar memoria antes de acceder a la lista

        // Intentar cargar la lista desde memoria o desde la base de datos si no está en memoria
        return listaDAO.cargarListaSiNoEstaEnMemoria(listasEnMemoria, nombreLista);
    }

    // Método para agregar una lista de reproducción a memoria
    public static void agregarListaEnMemoria(ListaReproduccion lista) {
        listasEnMemoria.put(lista.getNombreLista(), lista);
    }
}
