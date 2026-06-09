package Modelo;

import java.time.LocalDate;

public class MantenimientoEquipo {

    private int id_mantenimiento;
    private int id_equipo;
    private int id_admin;
    private LocalDate fecha;
    private String tipo;
    private String descripcion;

    public int getId_mantenimiento() { return id_mantenimiento; }
    public void setId_mantenimiento(int id_mantenimiento) { this.id_mantenimiento = id_mantenimiento; }

    public int getId_equipo() { return id_equipo; }
    public void setId_equipo(int id_equipo) { this.id_equipo = id_equipo; }

    public int getId_admin() { return id_admin; }
    public void setId_admin(int id_admin) { this.id_admin = id_admin; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

}