package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    // Valida las credenciales del usuario según su rol
    public boolean validarCredenciales(String usuario, String password, String rol) {
        String sql;
        String rolBD = mapearRolABaseDatos(rol);

        // Si el rol es camionero, se valida contra la tabla conductor
        if ("Camionero".equals(rol)) {
            sql = "SELECT * FROM conductor WHERE usuario = ? AND password = ?";
        }
        // Si es administrador, se valida contra la tabla administrador con rol específico
        else if (rolBD != null) {
            sql = "SELECT * FROM administrador WHERE usuario = ? AND password = ? AND rol = ?";
        }
        // Si el rol no es válido, se retorna falso
        else {
            return false;
        }

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Se asignan los parámetros de la consulta
            ps.setString(1, usuario);
            ps.setString(2, password);

            // Solo para administradores se agrega el rol en la consulta
            if (rolBD != null) {
                ps.setString(3, rolBD);
            }

            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            // Si existe al menos un resultado, las credenciales son válidas
            return rs.next();

        } catch (Exception e) {
            System.out.println("Error al validar credenciales: " + e.getMessage());
            return false;
        }
    }

    // Convierte el rol de la interfaz al formato almacenado en la base de datos
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