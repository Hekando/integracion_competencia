package BaseDatos;

import Modelo.Conductor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    public void insertar(Conductor conductor) {
        String sql = "INSERT INTO conductor (nombre, licencia) VALUES (?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, conductor.getNombre());
            ps.setString(2, conductor.getLicencia());
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("❌ Error al insertar conductor: " + e.getMessage());
        }
    }

    public Conductor buscarPorId(int id) {
        String sql = "SELECT * FROM conductor WHERE id = ?";
        Conductor c = null;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Conductor();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setLicencia(rs.getString("licencia"));
            }

        } catch (Exception e) {
            System.out.println("❌ Error al buscar conductor: " + e.getMessage());
        }
        return c;
    }

    public List<Conductor> listar() {
        List<Conductor> lista = new ArrayList<>();
        String sql = "SELECT * FROM conductor";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Conductor c = new Conductor();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setLicencia(rs.getString("licencia"));
                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al listar conductores: " + e.getMessage());
        }
        return lista;
    }
}