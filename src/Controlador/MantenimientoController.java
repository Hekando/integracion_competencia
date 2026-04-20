package Controlador;

import BaseDatos.MantenimientoDAO;
import BaseDatos.AlertaDAO;
import Modelo.Mantenimiento;

import java.util.List;

public class MantenimientoController {

    private MantenimientoDAO mantenimientoDAO;
    private AlertaDAO alertaDAO;

    // Constructor que inicializa los DAO necesarios
    public MantenimientoController() {
        mantenimientoDAO = new MantenimientoDAO();
        alertaDAO = new AlertaDAO();
    }

    // Inserta un mantenimiento y resuelve automáticamente la alerta asociada
    public void insertarMantenimiento(Mantenimiento m) {

        // Se guarda el mantenimiento en la base de datos
        mantenimientoDAO.insertar(m);

        // Regla de negocio: al realizar mantenimiento, se marca la alerta como resuelta
        alertaDAO.resolverAlertaPorCamion(m.getIdCamion());
    }

    // Obtiene todos los registros de mantenimiento
    public List<Mantenimiento> listarTodos() {
        return mantenimientoDAO.listarTodos();
    }

    // Obtiene los mantenimientos de un camión específico
    public List<Mantenimiento> listarPorCamion(int idCamion) {
        return mantenimientoDAO.listarPorCamion(idCamion);
    }

    // Actualiza un registro de mantenimiento existente
    public void actualizarMantenimiento(Mantenimiento m) {
        mantenimientoDAO.actualizar(m);
    }

    // Elimina un registro de mantenimiento por su ID
    public void eliminarMantenimiento(int id) {
        mantenimientoDAO.eliminar(id);
    }
}