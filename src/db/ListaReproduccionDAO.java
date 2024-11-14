package db;

import implementacion.Cancion;
import implementacion.ListaReproduccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ListaReproduccionDAO {

    // Método para guardar una lista de reproducción en la base de datos
    public void guardarListaReproduccion(ListaReproduccion lista) {
        String sqlLista = "INSERT INTO listas_reproduccion (nombre) VALUES (?) RETURNING id";
        String sqlCancion = "INSERT INTO canciones (id_lista, nombre) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Insertar la lista de reproducción y obtener su ID
            PreparedStatement stmtLista = conn.prepareStatement(sqlLista);
            stmtLista.setString(1, lista.getNombreLista());
            ResultSet rsLista = stmtLista.executeQuery();

            if (rsLista.next()) {
                long listaId = rsLista.getLong("id");

                // Insertar cada canción de la lista de reproducción
                PreparedStatement stmtCancion = conn.prepareStatement(sqlCancion);
                for (Cancion cancion : lista.getCanciones()) {
                    stmtCancion.setLong(1, listaId);
                    stmtCancion.setString(2, cancion.getNombreCancion());
                    stmtCancion.addBatch();
                }
                stmtCancion.executeBatch();
                System.out.println("Lista de reproducción '" + lista.getNombreLista() + "' guardada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar la lista de reproducción: " + e.getMessage());
        }
    }

    // Método para mover listas menos usadas a la base de datos para liberar memoria
    public void moverListasMenosUsadasABaseDatos(Map<String, ListaReproduccion> listasEnMemoria, int limiteUso) {
        for (Map.Entry<String, ListaReproduccion> entry : listasEnMemoria.entrySet()) {
            ListaReproduccion lista = entry.getValue();
            if (lista.getContadorUso() < limiteUso) {
                guardarListaReproduccion(lista);
                listasEnMemoria.remove(entry.getKey()); // Eliminar de la memoria
                System.out.println("Lista de reproducción '" + lista.getNombreLista() + "' movida a la base de datos.");
            }
        }
    }

    // Método para cargar una lista de reproducción desde la base de datos por su nombre si no está en memoria
    public ListaReproduccion cargarListaSiNoEstaEnMemoria(Map<String, ListaReproduccion> listasEnMemoria, String nombreLista) {
        // Verificar si la lista ya está en memoria
        if (listasEnMemoria.containsKey(nombreLista)) {
            System.out.println("Lista de reproducción '" + nombreLista + "' ya está en memoria.");
            return listasEnMemoria.get(nombreLista);
        } else {
            // Cargar la lista desde la base de datos si no está en memoria
            ListaReproduccion listaReproduccion = cargarListaReproduccion(nombreLista);

            // Agregar la lista cargada en memoria para facilitar acceso futuro
            if (listaReproduccion != null) {
                listasEnMemoria.put(nombreLista, listaReproduccion);
                System.out.println("Lista de reproducción '" + nombreLista + "' cargada desde la base de datos y agregada a la memoria.");
            } else {
                System.out.println("No se pudo cargar la lista de reproducción '" + nombreLista + "' desde la base de datos.");
            }

            return listaReproduccion;
        }
    }



    // Método para cargar una lista de reproducción desde la base de datos por su nombre
    public ListaReproduccion cargarListaReproduccion(String nombreLista) {
        String sqlLista = "SELECT id FROM listas_reproduccion WHERE nombre = ?";
        String sqlCancion = "SELECT nombre FROM canciones WHERE id_lista = ?";

        ListaReproduccion listaReproduccion = new ListaReproduccion(nombreLista);

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Obtener el ID de la lista de reproducción
            PreparedStatement stmtLista = conn.prepareStatement(sqlLista);
            stmtLista.setString(1, nombreLista);
            ResultSet rsLista = stmtLista.executeQuery();

            if (rsLista.next()) {
                long listaId = rsLista.getLong("id");

                // Obtener todas las canciones asociadas a la lista de reproducción
                PreparedStatement stmtCancion = conn.prepareStatement(sqlCancion);
                stmtCancion.setLong(1, listaId);
                ResultSet rsCancion = stmtCancion.executeQuery();

                while (rsCancion.next()) {
                    String nombreCancion = rsCancion.getString("nombre");
                    listaReproduccion.agregarCancion(nombreCancion);
                }
                System.out.println("Lista de reproducción '" + nombreLista + "' cargada correctamente.");
            } else {
                System.out.println("No se encontró la lista de reproducción con nombre: " + nombreLista);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar la lista de reproducción: " + e.getMessage());
        }

        return listaReproduccion;
    }
}
