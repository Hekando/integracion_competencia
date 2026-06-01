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

        ps.setInt(1, m.getIdEquipo());
        ps.setInt(2, m.getIdAdmin());
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
            m.setIdMantenimiento(rs.getInt("id_mantenimiento"));
            m.setIdEquipo(rs.getInt("id_equipo"));
            m.setIdAdmin(rs.getInt("id_admin"));
            m.setFecha(rs.getDate("fecha").toLocalDate());
            m.setTipo(rs.getString("tipo"));
            m.setDescripcion(rs.getString("descripcion"));
            lista.add(m);
        }
        return lista;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM mantenimiento_equipo WHERE id_mantenimiento=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}