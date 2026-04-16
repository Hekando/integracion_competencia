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

        // 🔍 Consultar último mantenimiento
        int kmUltimoMantenimiento = camionDAO.obtenerKmUltimoMantenimiento(camion.getIdCamion());

        // 📊 Calcular diferencia
        int diferencia = nuevoKM - kmUltimoMantenimiento;

        // 🔔 Generar alerta si la diferencia es >= 5000 KM
        if (diferencia >= 5000) {

            // Verificar si ya existe una alerta pendiente
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