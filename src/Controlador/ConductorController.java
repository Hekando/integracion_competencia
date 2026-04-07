package Controlador;

import BaseDatos.ConexionBD;
import Modelo.Conductor;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConductorController {

    public void insertarConductor(Conductor conductor) {

        String sql = "INSERT INTO conductores (nombre, direccion, contacto) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, conductor.getNombre());
            ps.setString(2, conductor.getDireccion());
            ps.setString(3, conductor.getContacto());

            ps.executeUpdate();

            System.out.println("✅ Conductor guardado");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}