package Controlador;

import BaseDatos.ConductorDAO;
import Modelo.Conductor;

import java.util.List;

public class ConductorController {

    private ConductorDAO conductorDAO = new ConductorDAO();

    // Crear conductor
    public void crearConductor(String nombre, String licencia, int idCamion) {

        if (nombre == null || nombre.isEmpty()) {
            System.out.println("❌ El nombre es obligatorio");
            return;
        }

        Conductor conductor = new Conductor();
        conductor.setNombre(nombre);
        conductor.setLicencia(licencia);
        conductor.setIdCamion(idCamion);

        conductorDAO.insertar(conductor);

        System.out.println("✅ Conductor registrado correctamente");
    }

    // Listar conductores
    public void listarConductores() {
        List<Conductor> lista = conductorDAO.listar();

        for (Conductor c : lista) {
            System.out.println("ID: " + c.getId() +
                    " | Nombre: " + c.getNombre() +
                    " | Licencia: " + c.getLicencia() +
                    " | Camión ID: " + c.getIdCamion());
        }
    }
}
