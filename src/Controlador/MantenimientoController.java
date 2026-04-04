package Controlador;

import BaseDatos.*;
import Modelo.Camion;
import Modelo.Alerta;

import java.time.LocalDate;

public class MantenimientoController {

    private CamionDAO camionDAO = new CamionDAO();
    private MantenimientoDAO mantenimientoDAO = new MantenimientoDAO();
    private AlertaDAO alertaDAO = new AlertaDAO();

    public void registrarKilometraje(int idCamion, int kmActual) {

        Camion camion = camionDAO.buscarPorId(idCamion);

        if (camion == null) {
            System.out.println("❌ Camión no existe");
            return;
        }

        // 🔥 CAMBIO IMPORTANTE
        if (kmActual < camion.getKilometraje()) {
            System.out.println("❌ KM inválido");
            return;
        }

        camionDAO.actualizarKilometraje(idCamion, kmActual);

        int ultimo = mantenimientoDAO.obtenerUltimoKilometraje(idCamion);
        int diferencia = kmActual - ultimo;

        if (diferencia >= 5000) {

            if (!alertaDAO.existeAlertaActiva(idCamion)) {

                Alerta alerta = new Alerta();
                alerta.setIdCamion(idCamion);
                alerta.setKilometraje(kmActual);
                alerta.setFecha(LocalDate.now());
                alerta.setEstado("Pendiente");

                alertaDAO.insertar(alerta);

                System.out.println("⚠ ALERTA GENERADA");
            }
        }

        System.out.println("✅ Registro OK");
    }
}
