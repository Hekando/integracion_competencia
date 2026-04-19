package Controlador;

import BaseDatos.RegistroKilometrajeDAO;
import Modelo.RegistroKilometraje;

import java.util.List;

public class RegistroKilometrajeController {

    private final RegistroKilometrajeDAO dao;

    public RegistroKilometrajeController() {
        dao = new RegistroKilometrajeDAO();
    }

    public void registrar(RegistroKilometraje r) {
        dao.insertar(r);
    }

    public List<RegistroKilometraje> listar() {
        return dao.listarTodos();
    }

    public List<RegistroKilometraje> listarPorCamion(int idCamion) {
        return dao.listarPorCamion(idCamion);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}
