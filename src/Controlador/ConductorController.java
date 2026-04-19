package Controlador;

import BaseDatos.ConductorDAO;
import Modelo.Camion;
import Modelo.Conductor;
import Modelo.RegistroConductorCamion;

import java.util.List;

public class ConductorController {

    private final ConductorDAO conductorDAO;

    public ConductorController() {
        conductorDAO = new ConductorDAO();
    }

    public void registrarConductorConCamion(Conductor conductor, Camion camion) throws Exception {
        conductorDAO.insertarConductorConCamion(conductor, camion);
    }

    public List<RegistroConductorCamion> listarRegistros() {
        return conductorDAO.listarConCamion();
    }

    public void eliminarPorIdCamion(int idCamion) throws Exception {
        conductorDAO.eliminarPorIdCamion(idCamion);
    }
}
