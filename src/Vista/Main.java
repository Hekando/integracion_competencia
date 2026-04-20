package Vista;

import BaseDatos.ConexionBD;
import javax.swing.*;

/**
 * Clase: Main
 * Descripción: Punto de entrada principal del sistema Hirata Transport.
 *
 * Se encarga de:
 * - Verificar la conexión a la base de datos
 * - Inicializar la interfaz gráfica (Login)
 * - Ejecutar la aplicación dentro del hilo de eventos de Swing
 */
public class Main {

    public static void main(String[] args) {

        // Verifica que la conexión a la base de datos funcione correctamente
        ConexionBD.testConexion();

        // Ejecuta la interfaz gráfica en el hilo de eventos de Swing (recomendado para UI)
        SwingUtilities.invokeLater(() -> {

            // Se crea la ventana de login
            Login login = new Login();

            // Se hace visible la ventana
            login.setVisible(true);
        });
    }
}