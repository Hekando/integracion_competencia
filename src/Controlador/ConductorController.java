package Controlador;

import BaseDatos.ConductorDAO;
import Modelo.Camion;
import Modelo.Conductor;
import Modelo.RegistroConductorCamion;

import java.util.List;

public class ConductorController {

    private final ConductorDAO conductorDAO;

    // Constructor que inicializa el acceso a datos de conductor
    public ConductorController() {
        conductorDAO = new ConductorDAO();
    }

    // Registra un nuevo conductor junto con su camión asociado
    public void registrarConductorConCamion(Conductor conductor, Camion camion) throws Exception {
        conductorDAO.insertarConductorConCamion(conductor, camion);
    }

    // Obtiene la lista de registros combinados de conductor y camión
    public List<RegistroConductorCamion> listarRegistros() {
        return conductorDAO.listarConCamion();
    }

    // Elimina un conductor y su camión asociado utilizando el ID del camión
    public void eliminarPorIdCamion(int idCamion) throws Exception {
        conductorDAO.eliminarPorIdCamion(idCamion);
    }

    // Actualiza los datos del conductor y del camión asociado
    public void actualizarConductorCamion(Conductor conductor, Camion camion) throws Exception {
        conductorDAO.actualizarConductorCamion(conductor, camion);
    }
}