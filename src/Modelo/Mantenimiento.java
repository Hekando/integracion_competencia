package Modelo;

import java.time.LocalDate;

/**
 * Clase: Mantenimiento
 * Descripción: Representa la entidad Mantenimiento dentro del sistema.
 * Almacena la información de los mantenimientos realizados a los camiones,
 * incluyendo fecha, kilometraje y tipo de mantenimiento.
 */
public class Mantenimiento {

    // Identificador único del mantenimiento en la base de datos
    private int id;

    // ID del camión asociado al mantenimiento (relación con entidad Camion)
    private int idCamion;

    // Fecha en que se realizó el mantenimiento
    private LocalDate fecha;

    // Kilometraje del camión al momento del mantenimiento
    private int kilometraje;

    // Tipo de mantenimiento (ejemplo: Preventivo, Correctivo)
    private String tipo;

    // Retorna el ID del mantenimiento
    public int getId() { return id; }

    // Asigna el ID del mantenimiento
    public void setId(int id) { this.id = id; }

    // Retorna el ID del camión asociado
    public int getIdCamion() { return idCamion; }

    // Asigna el ID del camión al mantenimiento
    public void setIdCamion(int idCamion) { this.idCamion = idCamion; }

    // Retorna la fecha del mantenimiento
    public LocalDate getFecha() { return fecha; }

    // Asigna la fecha del mantenimiento
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    // Retorna el kilometraje registrado en el mantenimiento
    public int getKilometraje() { return kilometraje; }

    // Asigna el kilometraje al mantenimiento
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }

    // Retorna el tipo de mantenimiento
    public String getTipo() { return tipo; }

    // Asigna el tipo de mantenimiento
    public void setTipo(String tipo) { this.tipo = tipo; }
}