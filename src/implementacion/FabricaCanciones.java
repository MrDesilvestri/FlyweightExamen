package implementacion;

import java.util.HashMap;
import java.util.Map;

public class FabricaCanciones {
    // Mapa para almacenar las canciones compartidas, usando el nombre como clave
    private static final Map<String, Cancion> canciones = new HashMap<>();
    private static Long secuencia = 0L; // Secuencia para generar IDs únicos para las canciones

    // Método para obtener una canción (reutiliza si ya existe, crea si no)
    public static Cancion obtenerCancion(String nombreCancion) {
        // Si la canción ya existe en el mapa, la retorna
        if (canciones.containsKey(nombreCancion)) {
            return canciones.get(nombreCancion);
        } else {
            // Si no existe, crea una nueva instancia, la almacena y luego la retorna
            Cancion nuevaCancion = new Cancion(++secuencia, nombreCancion);
            canciones.put(nombreCancion, nuevaCancion);
            return nuevaCancion;
        }
    }
}
