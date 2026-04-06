package Modelo;

import java.time.LocalDate;

public class Mantenimiento {

    private int id;
    private int idCamion;
    private LocalDate fecha;
    private String tipoMantenimiento;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCamion() { return idCamion; }
    public void setIdCamion(int idCamion) { this.idCamion = idCamion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipoMantenimiento() { return tipoMantenimiento; }
    public void setTipoMantenimiento(String tipoMantenimiento) { this.tipoMantenimiento = tipoMantenimiento; }
}