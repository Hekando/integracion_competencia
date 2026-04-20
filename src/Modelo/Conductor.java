package Modelo;

/**
 * Clase: Conductor
 * Descripción: Representa la entidad Conductor dentro del sistema.
 * Contiene la información personal del conductor, su licencia,
 * credenciales de acceso y la relación con un camión asignado.
 */
public class Conductor {

    // Identificador único del conductor en la base de datos
    private int id;

    // Nombre completo del conductor
    private String nombre;

    // Tipo o número de licencia de conducir
    private String licencia;

    // ID del camión asociado al conductor (relación con entidad Camion)
    private int idCamion;

    // Nombre de usuario para autenticación en el sistema
    private String usuario;

    // Contraseña del usuario (credencial de acceso)
    private String password;

    // Retorna el ID del conductor
    public int getId() {
        return id;
    }

    // Asigna el ID del conductor
    public void setId(int id) {
        this.id = id;
    }

    // Retorna el nombre del conductor
    public String getNombre() {
        return nombre;
    }

    // Asigna el nombre del conductor
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Retorna la licencia del conductor
    public String getLicencia() {
        return licencia;
    }

    // Asigna la licencia del conductor
    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    // Retorna el ID del camión asociado
    public int getIdCamion() {
        return idCamion;
    }

    // Asigna el ID del camión al conductor
    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    // Retorna el nombre de usuario
    public String getUsuario() {
        return usuario;
    }

    // Asigna el nombre de usuario
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Retorna la contraseña del usuario
    public String getPassword() {
        return password;
    }

    // Asigna la contraseña del usuario
    public void setPassword(String password) {
        this.password = password;
    }
}