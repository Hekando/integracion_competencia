package Controlador;

import BaseDatos.AlertaDAO;
import BaseDatos.CamionDAO;
import Modelo.Alerta;
import Modelo.Camion;

import java.util.List;

public class CamionController {

    private final CamionDAO camionDAO;
    private final AlertaDAO alertaDAO;

    public CamionController() {
        camionDAO = new CamionDAO();
        alertaDAO = new AlertaDAO();
    }

    public List<Camion> listarCamionesPorUsuario(String usuario) {
        String nombreConductor = camionDAO.buscarNombreConductorPorUsuario(usuario);
        if (nombreConductor == null || nombreConductor.isBlank()) {
            return List.of();
        }
        return camionDAO.listarPorNombreConductor(nombreConductor);
    }

    public String registrarKilometraje(String modelo, int kilometrajeIngresado) {
        Camion camion = camionDAO.buscarPorModelo(modelo);
        if (camion == null) {
            return "Camion no encontrado";
        }
        return registrarKilometraje(camion.getIdCamion(), kilometrajeIngresado);
    }

    public String registrarKilometraje(int idCamion, int kilometrajeIngresado) {
        Camion camion = camionDAO.buscarPorId(idCamion);

        if (camion == null) {
            return "Camion no encontrado";
        }

        int nuevoKM = camionDAO.actualizarKilometraje(camion.getIdCamion(), kilometrajeIngresado);

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

                return "ALERTA GENERADA. Mantencion requerida.\n" +
                        "KM actual: " + nuevoKM + "\n" +
                        "KM ultimo mantenimiento: " + kmUltimoMantenimiento + "\n" +
                        "Diferencia: " + diferencia + " km";
            }

            return "Kilometraje actualizado (KM: " + nuevoKM + ")\n" +
                    "Ya existe una alerta pendiente para este camion.";
        }

        return "Kilometraje actualizado correctamente (KM total: " + nuevoKM + ")";
    }
}
