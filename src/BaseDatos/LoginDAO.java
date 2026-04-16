package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public boolean validarCredenciales(String usuario, String password, String rol) {
        String sql;

        if (rol.equals("Administrador")) {
            sql = "SELECT * FROM administrador WHERE usuario = ? AND password = ?";
        } else if (rol.equals("Camionero")) {
            sql = "SELECT * FROM conductor WHERE usuario = ? AND password = ?";
        } else {
            return false;
        }

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("❌ Error al validar credenciales: " + e.getMessage());
            return false;
        }
    }
}
