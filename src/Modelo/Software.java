package Modelo;

/**
 * Clase: Software
 * Representa un software instalado en equipos
 */
public class Software {

    private int idSoftware;
    private String nombre;
    private String tipo;

    public int getIdSoftware() { return idSoftware; }
    public void setIdSoftware(int idSoftware) { this.idSoftware = idSoftware; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return nombre;
    }
}