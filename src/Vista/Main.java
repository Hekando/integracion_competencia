package Vista;

import BaseDatos.ConexionBD;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Probar la conexión a la base de datos
        ConexionBD.testConexion();

        // Iniciar la aplicación con la ventana de Login
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login login = new Login();
                login.setVisible(true);
            }
        });
    }
}

