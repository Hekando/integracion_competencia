package Controlador;

import BaseDatos.InventarioPiezaDAO;
import Modelo.InventarioPieza;
import java.sql.Connection;
import java.util.List;

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
}