package Prueba;

import Controlador.CamionController;

public class PruebaCasosBorde {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA CASOS BORDE ===");

        CamionController controller = new CamionController();

        // Caso 1: Camión no existe
        System.out.println("\n--- Camión inexistente ---");
        String r1 = controller.registrarKilometraje(9999, 1000);
        System.out.println(r1);

        // Caso 2: KM negativo
        System.out.println("\n--- Kilometraje negativo ---");
        String r2 = controller.registrarKilometraje(8, -500);
        System.out.println(r2);

        // Caso 3: KM muy alto (prueba lógica)
        System.out.println("\n--- Kilometraje extremo ---");
        String r3 = controller.registrarKilometraje(8, 999999);
        System.out.println(r3);

        System.out.println("\n✅ Pruebas de borde ejecutadas");
    }
}