package Modelo;

/**
 * Clase: Camion
 * Descripción: Representa la entidad Camión dentro del sistema.
 * Contiene la información básica del vehículo, incluyendo su identificación,
 * características principales y el kilometraje acumulado.
 */
public class Camion {

    // Identificador único del camión en la base de datos
    private int idCamion;

    // Patente del camión (identificación vehicular)
    private String patente;

    // Marca del camión
    private String marca;

    // Modelo del camión
    private String modelo;

    // Kilometraje total acumulado del camión
    private int kilometraje;

    // Retorna el ID del camión
    public int getIdCamion() {
        return idCamion;
    }

    // Asigna el ID del camión
    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    // Retorna la patente del camión
    public String getPatente() {
        return patente;
    }

    // Asigna la patente del camión
    public void setPatente(String patente) {
        this.patente = patente;
    }

    // Retorna la marca del camión
    public String getMarca() {
        return marca;
    }

    // Asigna la marca del camión
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // Retorna el modelo del camión
    public String getModelo() {
        return modelo;
    }

    // Asigna el modelo del camión
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    // Retorna el kilometraje total del camión
    public int getKilometraje() {
        return kilometraje;
    }

    // Asigna el kilometraje del camión
    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }
}