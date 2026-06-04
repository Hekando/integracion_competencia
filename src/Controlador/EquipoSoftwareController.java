package Controlador;

import BaseDatos.EquipoSoftwareDAO;
import Modelo.EquipoSoftware;
import java.sql.Connection;
import java.util.List;

public class EquipoSoftwareController {

    private EquipoSoftwareDAO dao;

    public EquipoSoftwareController(Connection conn) {
        this.dao = new EquipoSoftwareDAO(conn);
    }

    public void instalarSoftware(int idEquipo, int idSoftware, String versionInstalada) throws Exception {
        if (idEquipo <= 0) {
            throw new Exception("Debe seleccionar un equipo");
        }

        if (idSoftware <= 0) {
            throw new Exception("Debe seleccionar un software");
        }

        if (versionInstalada == null || versionInstalada.trim().isEmpty()) {
            throw new Exception("Debe ingresar la versión instalada");
        }

        if (dao.existeInstalacion(idEquipo, idSoftware)) {
            throw new Exception("Este software ya está instalado en el equipo seleccionado");
        }

        dao.instalarSoftware(idEquipo, idSoftware, versionInstalada.trim());
    }

    public List<EquipoSoftware> listarPorEquipo(int idEquipo) throws Exception {
        if (idEquipo <= 0) {
            throw new Exception("Debe seleccionar un equipo");
        }

        return dao.listarPorEquipo(idEquipo);
    }

    public void actualizarVersion(int idEquipoSoftware, String versionInstalada) throws Exception {
        if (idEquipoSoftware <= 0) {
            throw new Exception("Debe seleccionar un registro instalado");
        }

        if (versionInstalada == null || versionInstalada.trim().isEmpty()) {
            throw new Exception("Debe ingresar la versión instalada");
        }

        dao.actualizarVersion(idEquipoSoftware, versionInstalada.trim());
    }

    public void eliminarInstalacion(int idEquipoSoftware) throws Exception {
        if (idEquipoSoftware <= 0) {
            throw new Exception("Debe seleccionar un registro instalado");
        }

        dao.eliminarInstalacion(idEquipoSoftware);
    }
}