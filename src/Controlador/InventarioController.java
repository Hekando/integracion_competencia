package Controlador;

import BaseDatos.InventarioPiezaDAO;
import Modelo.InventarioPieza;
import java.sql.Connection;
import java.util.List;
import java.sql.ResultSet;

public class InventarioController {

    private InventarioPiezaDAO dao;

    public InventarioController(Connection conn) {
        this.dao = new InventarioPiezaDAO(conn);
    }

    public void registrar(InventarioPieza p) throws Exception {
        if (p.getCantidad() < 0) {
            throw new Exception("Cantidad inválida");
        }
        dao.insertar(p);
    }

    public List<InventarioPieza> listar() throws Exception {
        return dao.listar();
    }
    public void actualizarStock(
            String nombre,
            int cantidad
    ) throws Exception {

        dao.actualizarStock(
                nombre,
                cantidad
        );

    }

    public void registrarEntrada(
            String nombre,
            int cantidad,
            String descripcion
    ) throws Exception {

        dao.actualizarStock(nombre,cantidad);

        dao.registrarMovimiento(
                nombre,
                "Entrada",
                cantidad,
                descripcion
        );
    }

    public void registrarSalida(
            String nombre,
            int cantidad,
            String descripcion
    ) throws Exception {

        dao.actualizarStock(
                nombre,
                -cantidad
        );

        dao.registrarMovimiento(
                nombre,
                "Salida",
                cantidad,
                descripcion
        );
    }

    public int contarEntradas() throws Exception {
        return dao.contarEntradas();
    }

    public int contarSalidas() throws Exception {
        return dao.contarSalidas();
    }

    public int contarPiezas() throws Exception {
        return dao.contarPiezas();
    }

    public int contarReparacion() throws Exception {
        return dao.contarReparacion();
    }
    public ResultSet listarMovimientos() throws Exception {
        return dao.listarMovimientos();
    }

}