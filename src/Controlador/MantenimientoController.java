package Controlador;

import BaseDatos.MantenimientoDAO;
import BaseDatos.AlertaDAO;
import Modelo.Mantenimiento;


import java.util.List;

public class MantenimientoController {

    private MantenimientoDAO mantenimientoDAO;
    private AlertaDAO alertaDAO;

    public MantenimientoController() {
        mantenimientoDAO = new MantenimientoDAO();
        alertaDAO = new AlertaDAO();
    }

    public void insertarMantenimiento(Mantenimiento m) {
        mantenimientoDAO.insertar(m);

        // Marcar alerta como resuelta al realizar mantenimiento
        alertaDAO.resolverAlertaPorCamion(m.getIdCamion());
    }

    public List<Mantenimiento> listarTodos() {
        return mantenimientoDAO.listarTodos();
    }

    public List<Mantenimiento> listarPorCamion(int idCamion) {
        return mantenimientoDAO.listarPorCamion(idCamion);
    }

    public void actualizarMantenimiento(Mantenimiento m) {
        mantenimientoDAO.actualizar(m);
    }

    public void eliminarMantenimiento(int id) {
        mantenimientoDAO.eliminar(id);
    }
}