package Modelo;

public class Alerta {

    private int id;
    private int camionId;
    private String mensaje;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCamionId() { return camionId; }
    public void setCamionId(int camionId) { this.camionId = camionId; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
