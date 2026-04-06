package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/transporte_hirata";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    // Método para obtener la conexión con la base de datos
    public static Connection getConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Método para probar la conexión
    public static void testConexion() {
        try (Connection conexion = getConexion()) {
            if (conexion != null) {
                System.out.println("Conexión a la base de datos exitosa.");
            } else {
                System.out.println("La conexión a la base de datos falló.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
}
