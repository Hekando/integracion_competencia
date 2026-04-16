package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import Controlador.LoginController;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JComboBox<String> comboRol;
    private JButton btnIngresar;
    private LoginController loginController;

    public Login() {
        loginController = new LoginController();

        setTitle("Hirata Transport - Login");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 247, 250));

        // Barra azul superior
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

        // Panel central con formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));

        txtUsuario = new JTextField();
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtUsuario.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        String[] roles = {"Administrador", "Camionero"};
        comboRol = new JComboBox<>(roles);
        comboRol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(30, 50, 90));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(this::autenticar);

        panelFormulario.add(new JLabel("Usuario:"));
        panelFormulario.add(txtUsuario);
        panelFormulario.add(Box.createVerticalStrut(10));

        panelFormulario.add(new JLabel("Contraseña:"));
        panelFormulario.add(txtPassword);
        panelFormulario.add(Box.createVerticalStrut(10));

        panelFormulario.add(new JLabel("Rol:"));
        panelFormulario.add(comboRol);
        panelFormulario.add(Box.createVerticalStrut(20));

        panelFormulario.add(btnIngresar);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        add(panelPrincipal);
        setLocationRelativeTo(null); // Centrar ventana
    }

    private void autenticar(ActionEvent e) {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String rol = (String) comboRol.getSelectedItem();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "❌ Por favor complete todos los campos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = loginController.autenticar(usuario, password, rol);

        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "✅ ¡Bienvenido/a!\nUsuario: " + usuario + "\nRol: " + rol,
                    "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new Vista(); // Abrir dashboard principal
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Credenciales incorrectas",
                    "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtUsuario.requestFocus();
        }
    }
}