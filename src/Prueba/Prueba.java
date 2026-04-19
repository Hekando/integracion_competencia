package Prueba;

import Controlador.ConductorController;
import Modelo.Camion;
import Modelo.Conductor;

public class Prueba {
    public static void main(String[] args) {
        System.out.println("INICIANDO PRUEBA...");

        Camion camion = new Camion();
        camion.setPatente("PRUEBA-123");
        camion.setMarca("Toyota");
        camion.setModelo("2027");
        camion.setKilometraje(100);

        Conductor conductor = new Conductor();
        conductor.setNombre("Nicol");
        conductor.setLicencia("ABC123");
        conductor.setUsuario("nicol_prueba");
        conductor.setPassword("12345");

        ConductorController controller = new ConductorController();

        try {
            controller.registrarConductorConCamion(conductor, camion);
            System.out.println("PRUEBA FINALIZADA");
        } catch (Exception e) {
            System.out.println("ERROR EN PRUEBA: " + e.getMessage());
        }
    }
}
