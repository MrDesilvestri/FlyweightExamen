package implementacion;

public class Cancion {
    private Long id;
    private String nombreCancion;
    private byte[] contenidoCancion = new byte[1000000]; // Simulación de contenido pesado

    // Constructor con parámetros
    public Cancion(Long id, String nombreCancion) {
        this.id = id;
        this.nombreCancion = nombreCancion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    // Método toString para representación en consola
    @Override
    public String toString() {
        return "Cancion{" + "id=" + id + ", nombre='" + nombreCancion + '\'' + '}';
    }
}
