package Prueba;
import Modelo.Conductor;
import Controlador.ConductorController;
public class Prueba {
    public static void main(String[] args) {

        System.out.println("INICIANDO PRUEBA...");

        Conductor c = new Conductor();
        c.setNombre("Nicol");
        c.setLicencia("ABC123");

        ConductorController controller = new ConductorController();
        controller.insertarConductor(c);

        System.out.println("✅ FIN PRUEBA");
    }
}