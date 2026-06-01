package Modelo;

/**
 * Clase: Software
 * Representa un software instalado en equipos
 */
public class Software {

    private int idSoftware;
    private String nombre;
    private String descripcion;

    public int getIdSoftware() { return idSoftware; }
    public void setIdSoftware(int idSoftware) { this.idSoftware = idSoftware; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}