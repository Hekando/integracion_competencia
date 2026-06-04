package BaseDatos;

import Modelo.EquipoSoftware;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoSoftwareDAO {

    private Connection conn;

    public EquipoSoftwareDAO(Connection conn) {
        this.conn = conn;
    }

    public void instalarSoftware(int idEquipo, int idSoftware, String versionInstalada) throws SQLException {
        String sql = "INSERT INTO equipo_software (id_equipo, id_software, version_instalada) VALUES (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEquipo);
        ps.setInt(2, idSoftware);
        ps.setString(3, versionInstalada);

        ps.executeUpdate();
    }

    public List<EquipoSoftware> listarPorEquipo(int idEquipo) throws SQLException {
        List<EquipoSoftware> lista = new ArrayList<>();

        String sql = "SELECT " +
                "es.id_equipo_software, " +
                "es.id_equipo, " +
                "es.id_software, " +
                "e.nombre AS nombre_equipo, " +
                "e.numero_serie, " +
                "s.nombre AS nombre_software, " +
                "s.tipo AS tipo_software, " +
                "es.version_instalada " +
                "FROM equipo_software es " +
                "INNER JOIN equipo e ON es.id_equipo = e.id_equipo " +
                "INNER JOIN software s ON es.id_software = s.id_software " +
                "WHERE es.id_equipo = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEquipo);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            EquipoSoftware es = new EquipoSoftware();

            es.setIdEquipoSoftware(rs.getInt("id_equipo_software"));
            es.setIdEquipo(rs.getInt("id_equipo"));
            es.setIdSoftware(rs.getInt("id_software"));
            es.setNombreEquipo(rs.getString("nombre_equipo"));
            es.setNumeroSerieEquipo(rs.getString("numero_serie"));
            es.setNombreSoftware(rs.getString("nombre_software"));
            es.setTipoSoftware(rs.getString("tipo_software"));
            es.setVersionInstalada(rs.getString("version_instalada"));

            lista.add(es);
        }

        return lista;
    }

    public boolean existeInstalacion(int idEquipo, int idSoftware) throws SQLException {
        String sql = "SELECT id_equipo_software FROM equipo_software WHERE id_equipo = ? AND id_software = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEquipo);
        ps.setInt(2, idSoftware);

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public void actualizarVersion(int idEquipoSoftware, String versionInstalada) throws SQLException {
        String sql = "UPDATE equipo_software SET version_instalada = ? WHERE id_equipo_software = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, versionInstalada);
        ps.setInt(2, idEquipoSoftware);

        ps.executeUpdate();
    }

    public void eliminarInstalacion(int idEquipoSoftware) throws SQLException {
        String sql = "DELETE FROM equipo_software WHERE id_equipo_software = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idEquipoSoftware);

        ps.executeUpdate();
    }
}