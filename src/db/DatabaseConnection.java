package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getenv("DATABASE_USER");
    private static final String PASSWORD = System.getenv("DATABASE_PASS");
    private static Connection connection;

    // Método para obtener la conexión a la base de datos
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión a la base de datos exitosa.");
            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    // Método para cerrar la conexión a la base de datos
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
            }
        }
    }

    // Método para crear las tablas si no existen
    public static void crearTablasSiNoExisten() {
        String sqlListas = "CREATE TABLE IF NOT EXISTS listas_reproduccion (" +
                "id SERIAL PRIMARY KEY," +
                "nombre VARCHAR(255) NOT NULL" +
                ")";

        String sqlCanciones = "CREATE TABLE IF NOT EXISTS canciones (" +
                "id SERIAL PRIMARY KEY," +
                "id_lista INTEGER REFERENCES listas_reproduccion(id) ON DELETE CASCADE," +
                "nombre VARCHAR(255) NOT NULL" +
                ")";

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(sqlListas);
            stmt.execute(sqlCanciones);
            System.out.println("Tablas 'listas_reproduccion' y 'canciones' verificadas/creadas correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
        }
    }

}
