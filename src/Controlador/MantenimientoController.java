package Controlador;

import BaseDatos.ConexionBD;
import Modelo.Mantenimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class MantenimientoController {

    public void insertarMantenimiento(Mantenimiento m) {

        String sql = "INSERT INTO mantenimientos (id_camion, fecha, tipo_mantenimiento) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getIdCamion());
            ps.setDate(2, Date.valueOf(m.getFecha()));
            ps.setString(3, m.getTipoMantenimiento());

            ps.executeUpdate();

            System.out.println("🔧 Mantenimiento guardado");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}