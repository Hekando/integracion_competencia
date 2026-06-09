package Controlador;

import BaseDatos.MantenimientoEquipoDAO;
import BaseDatos.EquipoDAO;
import Modelo.MantenimientoEquipo;
import java.sql.Connection;
import java.util.List;

public class MantenimientoEquipoController {

    private MantenimientoEquipoDAO dao;

    private EquipoDAO equipoDAO;

    public MantenimientoEquipoController(Connection conn) {
        this.dao = new MantenimientoEquipoDAO(conn);
        this.equipoDAO = new EquipoDAO(conn);
    }

    public void registrar(MantenimientoEquipo m) throws Exception {
        if (m.getId_equipo() <= 0) {
            throw new Exception("Debe seleccionar un equipo");
        }

        if (m.getId_admin() <= 0) {
            throw new Exception("Debe seleccionar un tecnico responsable");
        }

        if (m.getFecha() == null) {
            throw new Exception("Debe ingresar la fecha");
        }

        if (m.getTipo() == null || m.getTipo().trim().isEmpty()) {
            throw new Exception("Debe seleccionar el tipo de mantenimiento");
        }

        if (m.getDescripcion() == null || m.getDescripcion().trim().isEmpty()) {
            throw new Exception("Debe ingresar la descripcion");
        }

        dao.insertar(m);
        String estadoEquipo;

    }

    public List<MantenimientoEquipo> listar() throws Exception {
        return dao.listar();
    }

    public void eliminar(int id) throws Exception {
        dao.eliminar(id);
    }
}