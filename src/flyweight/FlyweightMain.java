package flyweight;

import db.DatabaseConnection;
import db.ListaReproduccionDAO;
import implementacion.ListaReproduccion;

public class FlyweightMain {
    public static void main(String[] args) {
        // Crear las tablas si no existen
        DatabaseConnection.crearTablasSiNoExisten();

        // Crear una lista de reproducción y agregar canciones
        ListaReproduccion lista = new ListaReproduccion("Mis Favoritas");
        lista.agregarCancion("Cancion A");
        lista.agregarCancion("Cancion B");
        lista.agregarCancion("Cancion C");

        // Guardar la lista de reproducción y sus canciones en la base de datos
        ListaReproduccionDAO listaDAO = new ListaReproduccionDAO();
        listaDAO.guardarListaReproduccion(lista);

        // Cerrar la conexión al finalizar
        DatabaseConnection.closeConnection();
    }
}
