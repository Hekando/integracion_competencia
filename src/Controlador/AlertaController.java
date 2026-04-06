package Controlador;

import BaseDatos.ConexionBD;
import Modelo.Alerta;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AlertaController {

    public void insertarAlerta(Alerta alerta) {

        String sql = "INSERT INTO alertas (id_camion, mensaje) VALUES (?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alerta.getIdCamion());
            ps.setString(2, alerta.getMensaje());

            ps.executeUpdate();

            System.out.println("⚠️ Alerta guardada");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}