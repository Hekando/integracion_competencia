package Modelo;

import java.time.LocalDate;

public class RegistroKilometraje {

    private int id;
    private int idCamion;
    private LocalDate fecha;
    private int kilometraje;
    private String resultado;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCamion() { return idCamion; }
    public void setIdCamion(int idCamion) { this.idCamion = idCamion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public int getKilometraje() { return kilometraje; }
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}