package BaseDatos;

import Modelo.Software;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoftwareDAO {

    private Connection conn;

    public SoftwareDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar(Software s) throws SQLException {
        String sql = "INSERT INTO software (nombre, descripcion) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, s.getNombre());
        ps.setString(2, s.getDescripcion());

        ps.executeUpdate();
    }

    public List<Software> listar() throws SQLException {
        List<Software> lista = new ArrayList<>();
        String sql = "SELECT * FROM software";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Software s = new Software();
            s.setIdSoftware(rs.getInt("id_software"));
            s.setNombre(rs.getString("nombre"));
            s.setDescripcion(rs.getString("descripcion"));
            lista.add(s);
        }
        return lista;
    }
}