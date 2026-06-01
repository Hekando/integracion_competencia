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
        String sql = "INSERT INTO inventario_pieza (nombre, cantidad, descripcion) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, p.getNombre());
        ps.setInt(2, p.getCantidad());
        ps.setString(3, p.getDescripcion());

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
            lista.add(p);
        }
        return lista;
    }
}