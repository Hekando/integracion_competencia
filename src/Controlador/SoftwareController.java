package Controlador;

import BaseDatos.SoftwareDAO;
import BaseDatos.ActualizacionSoftwareDAO;
import Modelo.Software;
import Modelo.ActualizacionSoftware;
import java.sql.Connection;
import java.util.List;

public class SoftwareController {

    private SoftwareDAO dao;
    private ActualizacionSoftwareDAO daoActualizacion;

    public SoftwareController(Connection conn) {
        this.dao = new SoftwareDAO(conn);
        this.daoActualizacion = new ActualizacionSoftwareDAO(conn);
    }

    public void registrarSoftware(Software s) throws Exception {
        if (s.getNombre().isEmpty()) {
            throw new Exception("Nombre vacío");
        }
        dao.insertar(s);
    }

    public List<Software> listarSoftware() throws Exception {
        return dao.listar();
    }

    public void registrarActualizacion(ActualizacionSoftware a) throws Exception {
        daoActualizacion.insertar(a);
    }

    public List<ActualizacionSoftware> listarActualizaciones() throws Exception {
        return daoActualizacion.listar();
    }
}