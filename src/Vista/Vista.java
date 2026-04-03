package Vista;

import javax.swing.*;
import java.awt.*;

public class Vista {

    public Vista() {
        // Crear la ventana principal
        JFrame ventanaPrincipal = new JFrame("Sistema de Registro");
        JButton botonRegistrarKilometraje = new JButton("Registrar Kilometraje");

        // Configurar la ventana principal
        ventanaPrincipal.setLayout(new FlowLayout());
        ventanaPrincipal.add(botonRegistrarKilometraje);

        // Acción al hacer clic en el botón
        botonRegistrarKilometraje.addActionListener(e -> mostrarVentanaKilometraje());

        // Configuración de la ventana principal
        ventanaPrincipal.setSize(300, 150);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setVisible(true);
    }

    private void mostrarVentanaKilometraje() {
        // Crear ventana de kilometraje
        JFrame ventanaKilometraje = new JFrame("Registrar Kilometraje");
        JTextField campoCamion = new JTextField(10);
        JTextField campoKilometraje = new JTextField(10);
        JButton botonRegistrar = new JButton("Guardar");

        // Configurar la ventana de kilometraje
        ventanaKilometraje.setLayout(new FlowLayout());
        ventanaKilometraje.add(new JLabel("Modelo de Camión:"));
        ventanaKilometraje.add(campoCamion);
        ventanaKilometraje.add(new JLabel("Kilometraje:"));
        ventanaKilometraje.add(campoKilometraje);
        ventanaKilometraje.add(botonRegistrar);

        // Acción al hacer clic en "Registrar"
        botonRegistrar.addActionListener(e -> {
            String camion = campoCamion.getText();
            String kilometraje = campoKilometraje.getText();

            // Validar si alguno de los campos está vacío
            if (camion.isEmpty() || kilometraje.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaKilometraje,
                        "Debe completar todos los campos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // Verificar que el kilometraje sea un número
                    double kilometrajeValor = Double.parseDouble(kilometraje);

                    // Si el kilometraje es mayor o igual a 5000, mostrar alerta
                    if (kilometrajeValor >= 5000) {
                        JOptionPane.showMessageDialog(ventanaKilometraje,
                                "El camión requiere mantenimiento preventivo.",
                                "Alerta de Mantenimiento",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    // Mostrar mensaje de registro exitoso
                    JOptionPane.showMessageDialog(ventanaKilometraje,
                            "Registro exitoso.",
                            "Confirmación",
                            JOptionPane.INFORMATION_MESSAGE);

                    ventanaKilometraje.dispose();  // Cerrar la ventana

                } catch (NumberFormatException ex) {
                    // Si no es un número, mostrar mensaje de error
                    JOptionPane.showMessageDialog(ventanaKilometraje,
                            "El kilometraje debe ser un valor numérico válido.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Configurar la ventana de kilometraje
        ventanaKilometraje.setSize(250, 200);
        ventanaKilometraje.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaKilometraje.setVisible(true);
    }

    // Método para mostrar mensajes en la Vista
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}