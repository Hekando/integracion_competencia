package BaseDatos;

import Modelo.Mantenimiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {

    public void insertar(Mantenimiento m) {

        String sql = "INSERT INTO mantenimientos (id_camion, fecha, tipo_mantenimiento) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getIdCamion());
            ps.setDate(2, Date.valueOf(m.getFecha()));
            ps.setString(3, m.getTipoMantenimiento());

            ps.executeUpdate();

            System.out.println("✅ Mantenimiento guardado");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public List<Mantenimiento> listar() {

        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimientos";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Mantenimiento m = new Mantenimiento();
                m.setId(rs.getInt("id_mantenimiento"));
                m.setIdCamion(rs.getInt("id_camion"));
                m.setFecha(rs.getDate("fecha").toLocalDate());
                m.setTipoMantenimiento(rs.getString("tipo_mantenimiento"));

                lista.add(m);
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        return lista;
    }
}