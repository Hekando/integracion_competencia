package Modelo;

/**
 * Clase: RegistroConductorCamion
 * Descripción: Representa una vista combinada de datos entre las entidades
 * Camion y Conductor.
 *
 * Se utiliza principalmente para mostrar información unificada en interfaces
 * o consultas, evitando múltiples llamadas a la base de datos.
 */
public class RegistroConductorCamion {

    // ID del camión asociado al conductor
    private int idCamion;

    // Patente del camión
    private String patente;

    // Marca del camión
    private String marca;

    // Modelo del camión
    private String modelo;

    // Kilometraje actual del camión
    private int kilometraje;

    // Nombre del conductor asociado
    private String nombreConductor;

    // Licencia del conductor
    private String licencia;

    // Usuario del conductor (para autenticación en el sistema)
    private String usuario;

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

    // Retorna el kilometraje del camión
    public int getKilometraje() {
        return kilometraje;
    }

    // Asigna el kilometraje del camión
    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    // Retorna el nombre del conductor
    public String getNombreConductor() {
        return nombreConductor;
    }

    // Asigna el nombre del conductor
    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    // Retorna la licencia del conductor
    public String getLicencia() {
        return licencia;
    }

    // Asigna la licencia del conductor
    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    // Retorna el usuario del conductor
    public String getUsuario() {
        return usuario;
    }

    // Asigna el usuario del conductor
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}