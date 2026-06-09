package Modelo;

public class Administrador {

    private int id_admin;
    private String nombre;
    private String usuario;
    private String password;
    private String rol;

    public int getId_admin() { return id_admin; }
    public void setId_admin(int id_admin) { this.id_admin = id_admin; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return nombre + " - " + rol;
    }
}