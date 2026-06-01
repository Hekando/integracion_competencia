package BaseDatos;

import Modelo.ActualizacionSoftware;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActualizacionSoftwareDAO {

    private Connection conn;

    public ActualizacionSoftwareDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar(ActualizacionSoftware a) throws SQLException {
        String sql = "INSERT INTO actualizacion_software (id_equipo_software, id_admin, fecha, version_anterior, version_nueva, descripcion, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, a.getIdEquipoSoftware());
        ps.setInt(2, a.getIdAdmin());
        ps.setDate(3, Date.valueOf(a.getFecha()));
        ps.setString(4, a.getVersionAnterior());
        ps.setString(5, a.getVersionNueva());
        ps.setString(6, a.getDescripcion());
        ps.setString(7, a.getEstado());

        ps.executeUpdate();
    }

    public List<ActualizacionSoftware> listar() throws SQLException {
        List<ActualizacionSoftware> lista = new ArrayList<>();
        String sql = "SELECT * FROM actualizacion_software";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            ActualizacionSoftware a = new ActualizacionSoftware();
            a.setIdActualizacion(rs.getInt("id_actualizacion"));
            a.setIdEquipoSoftware(rs.getInt("id_equipo_software"));
            a.setIdAdmin(rs.getInt("id_admin"));
            a.setFecha(rs.getDate("fecha").toLocalDate());
            a.setVersionAnterior(rs.getString("version_anterior"));
            a.setVersionNueva(rs.getString("version_nueva"));
            a.setDescripcion(rs.getString("descripcion"));
            a.setEstado(rs.getString("estado"));
            lista.add(a);
        }
        return lista;
    }
}