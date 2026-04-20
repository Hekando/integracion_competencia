package Prueba;

import Controlador.MantenimientoController;
import Modelo.Mantenimiento;

public class PruebaMantenimiento {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA MANTENIMIENTO ===");

        MantenimientoController controller = new MantenimientoController();

        Mantenimiento m = new Mantenimiento();
        m.setIdCamion(8);
        m.setFecha(java.time.LocalDate.now());
        m.setKilometraje(15000);
        m.setTipo("Cambio de aceite");

        controller.insertarMantenimiento(m);

        System.out.println("✅ Mantenimiento registrado correctamente");
    }
}