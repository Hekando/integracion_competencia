package Controlador;

import BaseDatos.ConexionBD;
import Modelo.Camion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CamionController {

    public void insertarCamion(Camion camion) {

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
}