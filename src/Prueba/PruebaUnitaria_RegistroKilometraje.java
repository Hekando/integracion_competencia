package Prueba;

import Controlador.CamionController;
import Modelo.Camion;

public class PruebaUnitaria_RegistroKilometraje {

    public static void main(String[] args) {

        System.out.println("=== INICIO PRUEBAS UNITARIAS KM ===");

        CamionController controller = new CamionController();

        // PRUEBA 1: Registro normal
        System.out.println("\n--- PRUEBA 1: Registro normal ---");
        String resultado1 = controller.registrarKilometraje(8, 1000);
        System.out.println(resultado1);

        // PRUEBA 2: Generar alerta (supera 5000 km)
        System.out.println("\n--- PRUEBA 2: Generar alerta ---");
        String resultado2 = controller.registrarKilometraje(8, 6000);
        System.out.println(resultado2);

        // PRUEBA 3: Alerta duplicada
        System.out.println("\n--- PRUEBA 3: Alerta duplicada ---");
        String resultado3 = controller.registrarKilometraje(8, 100);
        System.out.println(resultado3);

        // PRUEBA 4: Camión inexistente
        System.out.println("\n--- PRUEBA 4: Camión no existe ---");
        String resultado4 = controller.registrarKilometraje(999, 1000);
        System.out.println(resultado4);

        System.out.println("\n=== FIN PRUEBAS ===");
    }
}