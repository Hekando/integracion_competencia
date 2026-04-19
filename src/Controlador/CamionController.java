package Controlador;

import BaseDatos.AlertaDAO;
import BaseDatos.CamionDAO;
import Modelo.Alerta;
import Modelo.Camion;

public class CamionController {

    private CamionDAO camionDAO;
    private AlertaDAO alertaDAO;

    public CamionController() {
        camionDAO = new CamionDAO();
        alertaDAO = new AlertaDAO();
    }

    public String registrarKilometraje(String modelo, int kilometrajeIngresado) {

        Camion camion = camionDAO.buscarPorModelo(modelo);

        if (camion == null) {
            return "Camión no encontrado";
        }

        int nuevoKM = camionDAO.actualizarKilometraje(
                camion.getIdCamion(),
                kilometrajeIngresado
        );

        if (nuevoKM == -1) {
            return "Error al actualizar kilometraje";
        }

        int kmUltimoMantenimiento = camionDAO.obtenerKmUltimoMantenimiento(camion.getIdCamion());
        int diferencia = nuevoKM - kmUltimoMantenimiento;

        if (diferencia >= 5000) {

            if (!alertaDAO.existeAlertaActiva(camion.getIdCamion())) {

                Alerta alerta = new Alerta();
                alerta.setIdCamion(camion.getIdCamion());
                alerta.setKilometraje(nuevoKM);
                alerta.setFecha(java.time.LocalDate.now());
                alerta.setEstado("Pendiente");

                alertaDAO.insertar(alerta);

                return "⚠ ¡ALERTA GENERADA! Mantención requerida.\n" +
                        "KM actual: " + nuevoKM + "\n" +
                        "KM último mantenimiento: " + kmUltimoMantenimiento + "\n" +
                        "Diferencia: " + diferencia + " km";
            } else {
                return "⚠ Kilometraje actualizado (KM: " + nuevoKM + ")\n" +
                        "Ya existe una alerta pendiente para este camión.";
            }
        }

        return "✅ Kilometraje actualizado correctamente (KM total: " + nuevoKM + ")";
    }
}