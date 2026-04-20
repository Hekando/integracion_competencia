package Controlador;

import BaseDatos.RegistroKilometrajeDAO;
import Modelo.RegistroKilometraje;

import java.util.List;

public class RegistroKilometrajeController {

    private final RegistroKilometrajeDAO dao;

    // Constructor que inicializa el acceso a datos de registros de kilometraje
    public RegistroKilometrajeController() {
        dao = new RegistroKilometrajeDAO();
    }

    // Registra un nuevo ingreso de kilometraje en el sistema
    public void registrar(RegistroKilometraje r) {
        dao.insertar(r);
    }

    // Obtiene todos los registros de kilometraje
    public List<RegistroKilometraje> listar() {
        return dao.listarTodos();
    }

    // Obtiene los registros asociados a un camión específico
    public List<RegistroKilometraje> listarPorCamion(int idCamion) {
        return dao.listarPorCamion(idCamion);
    }

    // Elimina un registro de kilometraje por su ID
    public void eliminar(int id) {
        dao.eliminar(id);
    }
}