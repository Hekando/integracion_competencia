package Modelo;

/**
 * Clase: InventarioPieza
 * Representa piezas de repuesto del sistema
 */
public class InventarioPieza {

    private int idPieza;
    private String nombre;
    private int cantidad;
    private String descripcion;

    public int getIdPieza() { return idPieza; }
    public void setIdPieza(int idPieza) { this.idPieza = idPieza; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}