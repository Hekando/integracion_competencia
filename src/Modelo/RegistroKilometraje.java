package Modelo;

import java.time.LocalDate;

/**
 * Clase: RegistroKilometraje
 * Descripción: Representa el historial de registros de kilometraje de los camiones.
 * Permite llevar un control de los kilómetros ingresados en el tiempo y el resultado
 * asociado a cada registro (por ejemplo, si generó alerta o fue correcto).
 */
public class RegistroKilometraje {

    // Identificador único del registro en la base de datos
    private int id;

    // ID del camión al que pertenece el registro (relación con entidad Camion)
    private int idCamion;

    // Fecha en que se realizó el registro de kilometraje
    private LocalDate fecha;

    // Cantidad de kilómetros registrados
    private int kilometraje;

    // Resultado del registro (ejemplo: "Correcto", "Alerta generada")
    private String resultado;

    // Retorna el ID del registro
    public int getId() { return id; }

    // Asigna el ID del registro
    public void setId(int id) { this.id = id; }

    // Retorna el ID del camión asociado
    public int getIdCamion() { return idCamion; }

    // Asigna el ID del camión al registro
    public void setIdCamion(int idCamion) { this.idCamion = idCamion; }

    // Retorna la fecha del registro
    public LocalDate getFecha() { return fecha; }

    // Asigna la fecha del registro
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    // Retorna el kilometraje registrado
    public int getKilometraje() { return kilometraje; }

    // Asigna el kilometraje al registro
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }

    // Retorna el resultado del registro
    public String getResultado() { return resultado; }

    // Asigna el resultado del registro
    public void setResultado(String resultado) { this.resultado = resultado; }
}