package Controlador;

import BaseDatos.AlertaDAO;
import Modelo.Alerta;

import java.time.LocalDate;
import java.util.List;

public class AlertaController {

    private AlertaDAO alertaDAO = new AlertaDAO();

    // Crear alerta manual (opcional)
    public void crearAlerta(int idCamion, int kilometraje) {

        if (kilometraje <= 0) {
            System.out.println("❌ Kilometraje inválido");
            return;
        }

        // Evitar duplicados
        if (alertaDAO.existeAlertaActiva(idCamion)) {
            System.out.println("ℹ Ya existe una alerta activa");
            return;
        }

        Alerta alerta = new Alerta();
        alerta.setIdCamion(idCamion);
        alerta.setKilometraje(kilometraje);
        alerta.setFecha(LocalDate.now());
        alerta.setEstado("Pendiente");

        alertaDAO.insertar(alerta);

        System.out.println("⚠ Alerta creada correctamente");
    }

    // Listar alertas
    public void listarAlertas() {
        List<Alerta> lista = alertaDAO.listar();

        for (Alerta a : lista) {
            System.out.println("ID: " + a.getId() +
                    " | Camión: " + a.getIdCamion() +
                    " | KM: " + a.getKilometraje() +
                    " | Fecha: " + a.getFecha() +
                    " | Estado: " + a.getEstado());
        }
    }

    // Marcar alerta como realizada
    public void cerrarAlerta(int idAlerta) {

        alertaDAO.actualizarEstado(idAlerta, "Realizada");

        System.out.println("✅ Alerta cerrada");
    }
}
