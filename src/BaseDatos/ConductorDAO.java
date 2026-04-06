package BaseDatos;

import Modelo.Conductor;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConductorDAO {

    public void insertar(Conductor c) {

        String sql = "INSERT INTO conductores (nombre, direccion, contacto) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDireccion());
            ps.setString(3, c.getContacto());

            ps.executeUpdate();

            System.out.println("✅ Conductor guardado");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}