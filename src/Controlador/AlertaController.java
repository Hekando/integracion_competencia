package Controlador;

import BaseDatos.AlertaDAO;
import Modelo.Alerta;

public class AlertaController {

    private AlertaDAO alertaDAO;

    public AlertaController() {
        alertaDAO = new AlertaDAO();
    }

    // 🔔 Crear alerta manual
    public void crearAlerta(int idCamion, String mensaje) {

        Alerta alerta = new Alerta();
        alerta.setIdCamion(idCamion);
        alerta.setMensaje(mensaje);

        alertaDAO.insertar(alerta);
    }
}