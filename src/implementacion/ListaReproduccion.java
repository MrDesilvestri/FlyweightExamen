package implementacion;

import java.util.ArrayList;
import java.util.List;

public class ListaReproduccion {
    private String nombreLista;
    private List<Cancion> canciones = new ArrayList<>();
    private int contadorUso; // Nuevo campo para el contador de uso

    // Constructor para inicializar la lista con un nombre
    public ListaReproduccion(String nombreLista) {
        this.nombreLista = nombreLista;
        this.contadorUso = 0; // Inicializar el contador en 0
    }

    // Método para incrementar el contador de uso cada vez que se accede a la lista
    public void incrementarUso() {
        this.contadorUso++;
    }

    public int getContadorUso() {
        return contadorUso;
    }

    // Método para agregar una canción usando la fábrica de canciones
    public void agregarCancion(String nombreCancion) {
        Cancion cancion = FabricaCanciones.obtenerCancion(nombreCancion);
        canciones.add(cancion);
    }

    // Método para imprimir la lista de reproducción
    public void imprimirLista() {
        incrementarUso(); // Incrementar el uso al imprimir la lista
        System.out.println("Lista de reproducción: " + nombreLista);
        for (Cancion cancion : canciones) {
            System.out.println("\t" + cancion);
        }
    }

    // Getters y Setters
    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }
}
