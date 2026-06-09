package BaseDatos;

import Modelo.MantenimientoEquipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoEquipoDAO {

    private Connection conn;

    public MantenimientoEquipoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar(MantenimientoEquipo m) throws SQLException {
        String sql = "INSERT INTO mantenimiento_equipo (id_equipo, id_admin, fecha, tipo, descripcion) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, m.getId_equipo());
        ps.setInt(2, m.getId_admin());
        ps.setDate(3, Date.valueOf(m.getFecha()));
        ps.setString(4, m.getTipo());
        ps.setString(5, m.getDescripcion());

        ps.executeUpdate();
    }

    public List<MantenimientoEquipo> listar() throws SQLException {
        List<MantenimientoEquipo> lista = new ArrayList<>();

        String sql = "SELECT * FROM mantenimiento_equipo";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            MantenimientoEquipo m = new MantenimientoEquipo();
            m.setId_mantenimiento(rs.getInt("id_mantenimiento"));
            m.setId_equipo(rs.getInt("id_equipo"));
            m.setId_admin(rs.getInt("id_admin"));
            m.setFecha(rs.getDate("fecha").toLocalDate());
            m.setTipo(rs.getString("tipo"));
            m.setDescripcion(rs.getString("descripcion"));
            lista.add(m);
        }
        return lista;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM mantenimiento_equipo WHERE id_mantenimiento = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}