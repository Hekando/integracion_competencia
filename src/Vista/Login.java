package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import Controlador.LoginController;

/**
 * Clase: Login
 * Descripción: Interfaz gráfica de usuario que permite autenticar usuarios
 * dentro del sistema Hirata Transport.
 *
 * Implementa validación de campos, manejo visual de errores y conexión
 * con la capa de control (LoginController) para verificar credenciales.
 */
public class Login extends JFrame {
    private static final long serialVersionUID = 1L;

    // Campo de texto para ingresar el usuario
    private JTextField txtUsuario;

    // Campo de contraseña
    private JPasswordField txtPassword;

    // Selector de rol del usuario
    private JComboBox<String> comboRol;

    // Botón de acción para iniciar sesión
    private JButton btnIngresar;

    // Etiqueta para mostrar mensajes de estado o error
    private JLabel lblEstado;

    // Controlador encargado de la lógica de autenticación
    private LoginController loginController;

    // Colores utilizados para validación visual
    private final Color COLOR_BORDE_NORMAL = new Color(200, 200, 200);
    private final Color COLOR_BORDE_ERROR = new Color(220, 70, 70);

    public Login() {
        // Inicialización del controlador
        loginController = new LoginController();

        // Configuración básica de la ventana
        setTitle("Hirata Transport - Login");
        setSize(450, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Panel principal contenedor
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 247, 250));

        // Panel superior (header)
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(30, 50, 90));
        panelHeader.setPreferredSize(new Dimension(450, 100));
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));

        JLabel lblLogo = new JLabel("🚛 Hirata Transport");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 28));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Sistema de Gestión");
        lblSubtitulo.setForeground(new Color(200, 210, 230));
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelHeader.add(Box.createVerticalStrut(25));
        panelHeader.add(lblLogo);
        panelHeader.add(Box.createVerticalStrut(5));
        panelHeader.add(lblSubtitulo);

        // Panel del formulario de login
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));

        // Etiqueta de estado inicial
        lblEstado = new JLabel("Complete usuario, contraseña y rol.");
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(235, 248, 245));
        lblEstado.setForeground(new Color(40, 90, 70));
        lblEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 230, 220)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        // Campo usuario con placeholder
        txtUsuario = new JTextField();
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        txtUsuario.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        agregarPlaceholder(txtUsuario, "Ej: testflota");

        // Campo contraseña con placeholder
        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        txtPassword.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        agregarPlaceholderPassword(txtPassword, "Ej: 12345");

        // ComboBox para selección de rol
        String[] roles = {"Administrador de flota", "Administrador de mantencion", "Camionero"};
        comboRol = new JComboBox<>(roles);
        comboRol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        // Botón de login
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(30, 50, 90));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(160, 38));
        btnIngresar.setPreferredSize(new Dimension(160, 38));

        // Evento que ejecuta la autenticación
        btnIngresar.addActionListener(this::autenticar);

        // Etiquetas descriptivas
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Arial", Font.BOLD, 14));
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Construcción del formulario
        panelFormulario.add(lblEstado);
        panelFormulario.add(Box.createVerticalStrut(18));

        panelFormulario.add(lblUsuario);
        panelFormulario.add(Box.createVerticalStrut(6));
        panelFormulario.add(txtUsuario);
        panelFormulario.add(Box.createVerticalStrut(14));

        panelFormulario.add(lblPassword);
        panelFormulario.add(Box.createVerticalStrut(6));
        panelFormulario.add(txtPassword);
        panelFormulario.add(Box.createVerticalStrut(14));

        panelFormulario.add(lblRol);
        panelFormulario.add(Box.createVerticalStrut(6));
        panelFormulario.add(comboRol);
        panelFormulario.add(Box.createVerticalStrut(22));

        panelFormulario.add(btnIngresar);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        add(panelPrincipal);
        setLocationRelativeTo(null);
    }

    /**
     * Método que ejecuta la validación y autenticación del usuario.
     */
    private void autenticar(ActionEvent e) {
        limpiarErrores();

        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String rol = (String) comboRol.getSelectedItem();

        boolean valido = true;

        // Validación de campos obligatorios
        if (usuario.isEmpty() || usuario.equals("Ej: testflota")) {
            marcarError(txtUsuario);
            valido = false;
        }

        if (password.isEmpty() || password.equals("Ej: 12345")) {
            marcarError(txtPassword);
            valido = false;
        }

        if (!valido) {
            mostrarError("Debe completar todos los campos correctamente.");
            return;
        }

        // Llamada al controlador para validar credenciales
        boolean exito = loginController.autenticar(usuario, password, rol);

        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Bienvenido/a\nUsuario: " + usuario + "\nRol: " + rol,
                    "Login exitoso", JOptionPane.INFORMATION_MESSAGE);

            // Cierra ventana actual y abre la principal
            this.dispose();
            new Vista(usuario, rol);

        } else {
            mostrarError("Credenciales incorrectas.");
            txtPassword.setText("");
            txtUsuario.requestFocus();
        }
    }

    // Marca visualmente un campo con error
    private void marcarError(JComponent campo) {
        campo.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_ERROR, 2));
    }

    // Limpia estados de error visual
    private void limpiarErrores() {
        txtUsuario.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        txtPassword.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        lblEstado.setText("Complete usuario, contraseña y rol.");
        lblEstado.setBackground(new Color(235, 248, 245));
        lblEstado.setForeground(new Color(40, 90, 70));
    }

    // Muestra mensaje de error en la interfaz
    private void mostrarError(String mensaje) {
        lblEstado.setText(mensaje);
        lblEstado.setBackground(new Color(252, 235, 235));
        lblEstado.setForeground(new Color(150, 50, 50));
    }

    /**
     * Agrega texto tipo placeholder a un campo de texto.
     */
    private void agregarPlaceholder(JTextField campo, String texto) {
        campo.setText(texto);
        campo.setForeground(Color.GRAY);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(texto)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(texto);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * Agrega placeholder a campos de contraseña manejando visibilidad de caracteres.
     */
    private void agregarPlaceholderPassword(JPasswordField campo, String texto) {
        campo.setEchoChar((char) 0);
        campo.setText(texto);
        campo.setForeground(Color.GRAY);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String valor = new String(campo.getPassword());
                if (valor.equals(texto)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                    campo.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String valor = new String(campo.getPassword());
                if (valor.trim().isEmpty()) {
                    campo.setEchoChar((char) 0);
                    campo.setText(texto);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }
}