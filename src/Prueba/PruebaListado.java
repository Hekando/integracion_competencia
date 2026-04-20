package Prueba;

import Controlador.MantenimientoController;
import Modelo.Mantenimiento;

import java.util.List;

public class PruebaListado {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA LISTADO ===");

        MantenimientoController controller = new MantenimientoController();

        List<Mantenimiento> lista = controller.listarTodos();

        for (Mantenimiento m : lista) {
            System.out.println(
                    "ID: " + m.getId() +
                            " | Camión: " + m.getIdCamion() +
                            " | Fecha: " + m.getFecha() +
                            " | KM: " + m.getKilometraje() +
                            " | Tipo: " + m.getTipo()
            );
        }

        System.out.println("✅ Listado completado");
    }
}