package Controlador;

import BaseDatos.AdministradorDAO;
import Modelo.Administrador;
import java.sql.Connection;
import java.util.List;

public class AdministradorController {

    private AdministradorDAO dao;

    public AdministradorController(Connection conn) {
        this.dao = new AdministradorDAO(conn);
    }

    public List<Administrador> listarTecnicosMantenimientoOficina() throws Exception {
        return dao.listarTecnicosMantenimientoOficina();
    }
}