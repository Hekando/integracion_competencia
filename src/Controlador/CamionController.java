package Controlador;

import BaseDatos.CamionDAO;
import BaseDatos.AlertaDAO;
import Modelo.Camion;
import Modelo.Alerta;

public class CamionController {

    private CamionDAO camionDAO;
    private AlertaDAO alertaDAO;

    public CamionController() {
        camionDAO = new CamionDAO();
        alertaDAO = new AlertaDAO();
    }

    // 🚛 Registrar kilometraje (SUMA y valida alerta)
    public String registrarKilometraje(String modelo, int kilometrajeIngresado) {

        // 🔍 Buscar camión
        Camion camion = camionDAO.buscarPorModelo(modelo);

        if (camion == null) {
            return "Camión no encontrado";
        }

        // 🔄 Actualizar KM (SUMANDO)
        int nuevoKM = camionDAO.actualizarKilometraje(
                camion.getIdCamion(),
                kilometrajeIngresado
        );

        if (nuevoKM == -1) {
            return "Error al actualizar kilometraje";
        }

        // 🔔 Generar alerta si supera 5000 KM (TOTAL)
        if (nuevoKM >= 5000) {

            Alerta alerta = new Alerta();
            alerta.setIdCamion(camion.getIdCamion());
            alerta.setMensaje("Mantención requerida");

            alertaDAO.insertar(alerta);

            return "⚠ Mantención requerida (KM total: " + nuevoKM + ")";
        }

        return "✅ Kilometraje actualizado (KM total: " + nuevoKM + ")";
    }
}