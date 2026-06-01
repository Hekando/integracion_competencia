package Controlador;

import BaseDatos.MantenimientoEquipoDAO;
import Modelo.MantenimientoEquipo;
import java.sql.Connection;
import java.util.List;

public class MantenimientoEquipoController {

    private MantenimientoEquipoDAO dao;

    public MantenimientoEquipoController(Connection conn) {
        this.dao = new MantenimientoEquipoDAO(conn);
    }

    public void registrar(MantenimientoEquipo m) throws Exception {
        if (m.getTipo().isEmpty()) {
            throw new Exception("Tipo vacío");
        }
        dao.insertar(m);
    }

    public List<MantenimientoEquipo> listar() throws Exception {
        return dao.listar();
    }

    public void eliminar(int id) throws Exception {
        dao.eliminar(id);
    }
}