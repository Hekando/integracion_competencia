package Modelo;

import java.time.LocalDate;

/**
 * Clase: Alerta
 * Descripción: Representa una alerta generada por el sistema cuando un camión
 * alcanza un kilometraje que requiere mantenimiento preventivo.
 * Contiene información como el camión asociado, kilometraje, fecha y estado.
 */
public class Alerta {

    // Identificador único de la alerta
    private int id;

    // ID del camión asociado a la alerta
    private int idCamion;

    // Kilometraje en el que se generó la alerta
    private int kilometraje;

    // Fecha en la que se generó la alerta
    private LocalDate fecha;

    // Mensaje descriptivo de la alerta
    private String mensaje;

    // Estado de la alerta (Pendiente / Realizada)
    private String estado;

    // Retorna el ID de la alerta
    public int getId() { return id; }

    // Asigna el ID de la alerta
    public void setId(int id) { this.id = id; }

    // Retorna el ID del camión
    public int getIdCamion() { return idCamion; }

    // Asigna el ID del camión
    public void setIdCamion(int idCamion) { this.idCamion = idCamion; }

    // Retorna el kilometraje asociado a la alerta
    public int getKilometraje() { return kilometraje; }

    // Asigna el kilometraje de la alerta
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }

    // Retorna la fecha de la alerta
    public LocalDate getFecha() { return fecha; }

    // Asigna la fecha de la alerta
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    // Retorna el mensaje de la alerta
    public String getMensaje() { return mensaje; }

    // Asigna el mensaje de la alerta
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    // Retorna el estado de la alerta
    public String getEstado() { return estado; }

    // Asigna el estado de la alerta
    public void setEstado(String estado) { this.estado = estado; }
}