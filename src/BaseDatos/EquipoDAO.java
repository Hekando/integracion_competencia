package BaseDatos;

import Modelo.Equipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    private Connection conn;

    public EquipoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar(Equipo e) throws SQLException {
        String sql = "INSERT INTO equipo (numero_serie, nombre, tipo, marca, modelo, estado) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, e.getNumeroSerie());
        ps.setString(2, e.getNombre());
        ps.setString(3, e.getTipo());
        ps.setString(4, e.getMarca());
        ps.setString(5, e.getModelo());
        ps.setString(6, e.getEstado());

        ps.executeUpdate();
    }

    public List<Equipo> listar() throws SQLException {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Equipo e = new Equipo();
            e.setIdEquipo(rs.getInt("id_equipo"));
            e.setNumeroSerie(rs.getString("numero_serie"));
            e.setNombre(rs.getString("nombre"));
            e.setTipo(rs.getString("tipo"));
            e.setMarca(rs.getString("marca"));
            e.setModelo(rs.getString("modelo"));
            e.setEstado(rs.getString("estado"));
            lista.add(e);
        }
        return lista;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM equipo WHERE id_equipo=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void actualizarEstado(int idEquipo, String estado) throws SQLException {
        String sql = "UPDATE equipo SET estado = ? WHERE id_equipo = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, estado);
        ps.setInt(2, idEquipo);

        ps.executeUpdate();
    }
}