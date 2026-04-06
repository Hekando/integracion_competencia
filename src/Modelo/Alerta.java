package Modelo;

public class Alerta {

    private int id;
    private int idCamion;
    private String mensaje;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCamion() { return idCamion; }
    public void setIdCamion(int idCamion) { this.idCamion = idCamion; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}