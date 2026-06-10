package BaseDatos;

import Modelo.InventarioPieza;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioPiezaDAO {

    private Connection conn;

    public InventarioPiezaDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar(InventarioPieza p) throws SQLException {

        String sql =
                "INSERT INTO inventario_pieza " +
                        "(nombre, cantidad, descripcion, estado, fecha_ingreso) " +
                        "VALUES (?, ?, ?, ?, CURDATE())";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, p.getNombre());
        ps.setInt(2, p.getCantidad());
        ps.setString(3, p.getDescripcion());
        ps.setString(4, p.getEstado());

        ps.executeUpdate();
    }

    public List<InventarioPieza> listar() throws SQLException {
        List<InventarioPieza> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario_pieza";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            InventarioPieza p = new InventarioPieza();
            p.setIdPieza(rs.getInt("id_pieza"));
            p.setNombre(rs.getString("nombre"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setEstado(rs.getString("estado"));
            lista.add(p);
        }
        return lista;
    }

    public void actualizarStock(
            String nombre,
            int cantidad
    ) throws SQLException {

        String sql =
                "UPDATE inventario_pieza " +
                        "SET cantidad = cantidad + ? " +
                        "WHERE nombre = ?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, cantidad);

        ps.setString(2, nombre);

        ps.executeUpdate();
    }

    public int contarEntradas() throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM movimiento_inventario " +
                        "WHERE tipo='Entrada'";

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(sql);

        if(rs.next()){
            return rs.getInt(1);
        }

        return 0;
    }

    public int contarSalidas() throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM movimiento_inventario " +
                        "WHERE tipo='Salida'";

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(sql);

        if(rs.next()){
            return rs.getInt(1);
        }

        return 0;
    }
    public int contarPiezas() throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM inventario_pieza";

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(sql);

        if(rs.next()){
            return rs.getInt(1);
        }

        return 0;
    }

    public int contarReparacion() throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM inventario_pieza " +
                        "WHERE estado='En reparación'";

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(sql);

        if(rs.next()){
            return rs.getInt(1);
        }

        return 0;
    }
    public void registrarMovimiento(
            String nombrePieza,
            String tipo,
            int cantidad,
            String descripcion
    ) throws SQLException {

        String sql =
                "INSERT INTO movimiento_inventario " +
                        "(id_pieza,tipo,cantidad,descripcion) " +
                        "VALUES ((SELECT id_pieza FROM inventario_pieza WHERE nombre=?), ?, ?, ?)";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setString(1, nombrePieza);
        ps.setString(2, tipo);
        ps.setInt(3, cantidad);
        ps.setString(4, descripcion);

        ps.executeUpdate();
    }
    public ResultSet listarMovimientos() throws SQLException {

        String sql =
                "SELECT m.fecha, p.nombre, m.tipo, m.cantidad, m.descripcion " +
                        "FROM movimiento_inventario m " +
                        "INNER JOIN inventario_pieza p ON m.id_pieza = p.id_pieza " +
                        "ORDER BY m.fecha DESC";

        Statement st = conn.createStatement();

        return st.executeQuery(sql);
    }
}