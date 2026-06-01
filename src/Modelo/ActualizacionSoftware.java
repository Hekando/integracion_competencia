package Modelo;

import java.time.LocalDate;

/**
 * Clase: ActualizacionSoftware
 * Representa actualizaciones realizadas a software
 */
public class ActualizacionSoftware {

    private int idActualizacion;
    private int idEquipoSoftware;
    private int idAdmin;
    private LocalDate fecha;
    private String versionAnterior;
    private String versionNueva;
    private String descripcion;
    private String estado;

    public int getIdActualizacion() { return idActualizacion; }
    public void setIdActualizacion(int idActualizacion) { this.idActualizacion = idActualizacion; }

    public int getIdEquipoSoftware() { return idEquipoSoftware; }
    public void setIdEquipoSoftware(int idEquipoSoftware) { this.idEquipoSoftware = idEquipoSoftware; }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getVersionAnterior() { return versionAnterior; }
    public void setVersionAnterior(String versionAnterior) { this.versionAnterior = versionAnterior; }

    public String getVersionNueva() { return versionNueva; }
    public void setVersionNueva(String versionNueva) { this.versionNueva = versionNueva; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}