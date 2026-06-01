package Modelo;

import java.time.LocalDate;

/**
 * Clase: MantenimientoEquipo
 * Representa el mantenimiento de equipos de oficina
 */
public class MantenimientoEquipo {

    private int idMantenimiento;
    private int idEquipo;
    private int idAdmin;
    private LocalDate fecha;
    private String tipo;
    private String descripcion;

    public int getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(int idMantenimiento) { this.idMantenimiento = idMantenimiento; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}