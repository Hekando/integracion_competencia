package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public boolean validarCredenciales(String usuario, String password, String rol) {
        String sql;
        String rolBD = mapearRolABaseDatos(rol);

        if ("Camionero".equals(rol)) {
            sql = "SELECT * FROM conductor WHERE usuario = ? AND password = ?";
        } else if (rolBD != null) {
            sql = "SELECT * FROM administrador WHERE usuario = ? AND password = ? AND rol = ?";
        } else {
            return false;
        }

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);
            if (rolBD != null) {
                ps.setString(3, rolBD);
            }

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("❌ Error al validar credenciales: " + e.getMessage());
            return false;
        }
    }

    private String mapearRolABaseDatos(String rol) {
        if ("Administrador de flota".equals(rol)) {
            return "AdmFlota";
        }
        if ("Administrador de mantencion".equals(rol)) {
            return "AdmMantenimiento";
        }
        return null;
    }
}
