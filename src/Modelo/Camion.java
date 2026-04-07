package Modelo;

import java.util.List;

public class Camion {

    private int id;
    private String patente;
    private String marca;
    private String modelo;
    private int anio;
    private int kilometraje;
    private List<Mantenimiento> mantenimientos;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public int getKilometraje() { return kilometraje; }
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }

    public int getKilometrajeActual() { return 0; }
    public void setKilometrajeActual(int kilometrajeActual) { }

    public List<Mantenimiento> getMantenimientos() { return mantenimientos; }
    public void setMantenimientos(List<Mantenimiento> mantenimientos) { this.mantenimientos = mantenimientos; }

}
