package Prueba;

import Controlador.MantenimientoController;
import Modelo.Mantenimiento;

import java.util.List;

public class PruebaActualizarMantenimiento {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA ACTUALIZAR ===");

        MantenimientoController controller = new MantenimientoController();

        // 1) Obtener un registro existente (tomamos el primero)
        List<Mantenimiento> lista = controller.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("❌ No hay registros para actualizar");
            return;
        }

        Mantenimiento m = lista.get(0);

        System.out.println("Antes:");
        System.out.println("ID: " + m.getId() + " | KM: " + m.getKilometraje());

        // 2) Modificar datos
        m.setKilometraje(m.getKilometraje() + 500);
        m.setTipo("Actualización de prueba");

        // 3) Guardar cambios
        controller.actualizarMantenimiento(m);

        System.out.println("Después (actualizado):");
        System.out.println("ID: " + m.getId() + " | KM: " + m.getKilometraje());

        System.out.println("✅ Actualización ejecutada");
    }
}