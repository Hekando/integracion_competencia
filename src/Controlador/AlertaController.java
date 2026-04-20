package Controlador;

import BaseDatos.AlertaDAO;
import Modelo.Alerta;

public class AlertaController {

    private AlertaDAO alertaDAO;

    // Constructor que inicializa el acceso a datos de alertas
    public AlertaController() {
        alertaDAO = new AlertaDAO();
    }

    // Crea una alerta manual asociada a un camión
    public void crearAlerta(int idCamion, String mensaje) {

        // Se construye el objeto alerta con los datos recibidos
        Alerta alerta = new Alerta();
        alerta.setIdCamion(idCamion);
        alerta.setMensaje(mensaje);

        // Se guarda la alerta en la base de datos
        alertaDAO.insertar(alerta);
    }
}