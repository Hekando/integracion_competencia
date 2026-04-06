package BaseDatos;

import Modelo.Camion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamionDAO {

    public void insertar(Camion camion) {

        String sql = "INSERT INTO camiones (marca, modelo, anio, kilometraje, estado_mantenimiento) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, camion.getMarca());
            ps.setString(2, camion.getModelo());
            ps.setInt(3, camion.getAnio());
            ps.setInt(4, camion.getKilometraje());
            ps.setString(5, camion.getEstadoMantenimiento());

            ps.executeUpdate();

            System.out.println("✅ Camión guardado");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public List<Camion> listar() {

        List<Camion> lista = new ArrayList<>();
        String sql = "SELECT * FROM camiones";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Camion c = new Camion();
                c.setIdCamion(rs.getInt("id_camion"));
                c.setMarca(rs.getString("marca"));
                c.setModelo(rs.getString("modelo"));
                c.setAnio(rs.getInt("anio"));
                c.setKilometraje(rs.getInt("kilometraje"));
                c.setEstadoMantenimiento(rs.getString("estado_mantenimiento"));

                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al listar: " + e.getMessage());
        }

        return lista;
    }
}