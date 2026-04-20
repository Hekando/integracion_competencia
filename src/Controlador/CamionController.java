package Controlador;

import BaseDatos.AlertaDAO;
import BaseDatos.CamionDAO;
import Modelo.Alerta;
import Modelo.Camion;

import java.util.List;

public class CamionController {

    private final CamionDAO camionDAO;
    private final AlertaDAO alertaDAO;

    // Constructor que inicializa los DAO necesarios
    public CamionController() {
        camionDAO = new CamionDAO();
        alertaDAO = new AlertaDAO();
    }

    // Obtiene los camiones asociados a un usuario (conductor)
    public List<Camion> listarCamionesPorUsuario(String usuario) {

        // Se obtiene el nombre del conductor a partir del usuario
        String nombreConductor = camionDAO.buscarNombreConductorPorUsuario(usuario);

        // Validación: si no existe o está vacío, se retorna lista vacía
        if (nombreConductor == null || nombreConductor.isBlank()) {
            return List.of();
        }

        // Retorna los camiones asociados al conductor
        return camionDAO.listarPorNombreConductor(nombreConductor);
    }

    // Sobrecarga que permite registrar kilometraje usando el modelo del camión
    public String registrarKilometraje(String modelo, int kilometrajeIngresado) {

        // Busca el camión por su modelo
        Camion camion = camionDAO.buscarPorModelo(modelo);

        // Validación si no existe
        if (camion == null) {
            return "Camion no encontrado";
        }

        // Llama al método principal usando el ID
        return registrarKilometraje(camion.getIdCamion(), kilometrajeIngresado);
    }

    // Método principal para registrar kilometraje y generar alertas
    public String registrarKilometraje(int idCamion, int kilometrajeIngresado) {

        // Obtiene el camión desde la base de datos
        Camion camion = camionDAO.buscarPorId(idCamion);

        // Validación de existencia
        if (camion == null) {
            return "Camion no encontrado";
        }

        // Actualiza el kilometraje acumulado
        int nuevoKM = camionDAO.actualizarKilometraje(camion.getIdCamion(), kilometrajeIngresado);

        // Validación de error en actualización
        if (nuevoKM == -1) {
            return "Error al actualizar kilometraje";
        }

        // Obtiene el kilometraje del último mantenimiento
        int kmUltimoMantenimiento = camionDAO.obtenerKmUltimoMantenimiento(camion.getIdCamion());

        // Calcula la diferencia desde el último mantenimiento
        int diferencia = nuevoKM - kmUltimoMantenimiento;

        // Regla de negocio: si supera los 5000 km se debe generar alerta
        if (diferencia >= 5000) {

            // Verifica si ya existe una alerta pendiente para evitar duplicados
            if (!alertaDAO.existeAlertaActiva(camion.getIdCamion())) {

                // Se crea una nueva alerta preventiva
                Alerta alerta = new Alerta();
                alerta.setIdCamion(camion.getIdCamion());
                alerta.setKilometraje(nuevoKM);
                alerta.setFecha(java.time.LocalDate.now());
                alerta.setEstado("Pendiente");

                // Se guarda la alerta en la base de datos
                alertaDAO.insertar(alerta);

                // Mensaje detallado de alerta generada
                return "ALERTA GENERADA. Mantencion requerida.\n" +
                        "KM actual: " + nuevoKM + "\n" +
                        "KM ultimo mantenimiento: " + kmUltimoMantenimiento + "\n" +
                        "Diferencia: " + diferencia + " km";
            }

            // Caso en que ya existe alerta pendiente
            return "Kilometraje actualizado (KM: " + nuevoKM + ")\n" +
                    "Ya existe una alerta pendiente para este camion.";
        }

        // Caso normal: no se requiere mantenimiento
        return "Kilometraje actualizado correctamente (KM total: " + nuevoKM + ")";
    }

    public void actualizarKilometrajeBase(int idCamion, int km) {
        camionDAO.reiniciarKilometraje(idCamion, km);
    }
}
