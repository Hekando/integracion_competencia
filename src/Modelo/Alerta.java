package Modelo;

import java.time.LocalDate;

public class Alerta {

    private int id;
    private int idCamion;
    private int kilometraje;
    private LocalDate fecha;
    private String mensaje;
    private String estado;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCamion() { return idCamion; }
    public void setIdCamion(int idCamion) { this.idCamion = idCamion; }

    public int getKilometraje() { return kilometraje; }
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}