package Controlador;

import BaseDatos.EquipoDAO;
import Modelo.Equipo;
import java.sql.Connection;
import java.util.List;

public class EquipoController {

    private EquipoDAO dao;

    public EquipoController(Connection conn) {
        this.dao = new EquipoDAO(conn);
    }

    public void registrarEquipo(Equipo e) throws Exception {
        if (e.getNombre().isEmpty()) {
            throw new Exception("Nombre vacío");
        }
        dao.insertar(e);
    }

    public List<Equipo> listar() throws Exception {
        return dao.listar();
    }

    public void eliminar(int id) throws Exception {
        dao.eliminar(id);
    }

    public void actualizarEstado(int idEquipo, String estado) throws Exception {
        dao.actualizarEstado(idEquipo, estado);
    }
}