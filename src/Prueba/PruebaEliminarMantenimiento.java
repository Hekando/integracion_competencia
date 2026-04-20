package Prueba;

import Controlador.MantenimientoController;

public class PruebaEliminarMantenimiento {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA ELIMINAR ===");

        MantenimientoController controller = new MantenimientoController();

        int idEliminar = 1; // cambia por un ID real de tu BD

        controller.eliminarMantenimiento(idEliminar);

        System.out.println("✅ Eliminación ejecutada (verifica en BD)");
    }
}