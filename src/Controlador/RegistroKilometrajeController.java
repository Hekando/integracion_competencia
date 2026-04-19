package Controlador;

import BaseDatos.RegistroKilometrajeDAO;
import Modelo.RegistroKilometraje;

import java.util.List;

public class RegistroKilometrajeController {

    private RegistroKilometrajeDAO dao;

    public RegistroKilometrajeController() {
        dao = new RegistroKilometrajeDAO();
    }

    // INSERTAR
    public void registrar(RegistroKilometraje r) {
        dao.insertar(r);
    }

    // LISTAR
    public List<RegistroKilometraje> listar() {
        return dao.listarTodos();
    }

    // ELIMINAR
    public void eliminar(int id) {
        dao.eliminar(id);
    }
}