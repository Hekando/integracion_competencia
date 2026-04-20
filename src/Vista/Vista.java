package Vista;

import javax.swing.*;
import java.awt.*;

/**
 * Clase: Vista
 * Descripción: Interfaz principal del sistema Hirata Transport.
 *
 * Esta clase representa el dashboard de la aplicación una vez autenticado el usuario.
 * Se encarga de:
 * - Mostrar el menú lateral (sidebar)
 * - Gestionar los paneles dinámicos según opción seleccionada
 * - Aplicar permisos según el rol del usuario
 * - Administrar la sesión (perfil y cierre de sesión)
 */
public class Vista {

    // Panel principal donde se cargan dinámicamente las vistas
    private JPanel mainPanel;

    // Ventana principal de la aplicación
    private final JFrame ventana;

    // Usuario autenticado
    private final String usuario;

    // Rol del usuario autenticado
    private final String rol;

    public Vista(String usuario, String rol) {
        this.usuario = usuario;
        this.rol = rol;

        // Configura el estilo visual de la aplicación
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configuración de la ventana principal
        ventana = new JFrame("Hirata Transport");
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        // Sidebar lateral de navegación
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 50, 90));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("🚛 Hirata");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 18));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones del menú lateral
        JButton btnCamion = crearBotonMenu("Camiones y Conductores");
        JButton btnConductor = crearBotonMenu("Registrar Conductor");
        JButton btnKM = crearBotonMenu("Registrar KM");
        JButton btnMantenimiento = crearBotonMenu("Mantenimiento");

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(btnCamion);
        sidebar.add(btnConductor);
        sidebar.add(btnKM);
        sidebar.add(btnMantenimiento);

        // Panel central donde se cargan las vistas
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Header superior
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Dashboard");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblRol = new JLabel("Rol: " + rol);
        lblRol.setFont(new Font("Arial", Font.PLAIN, 13));

        // Botón de perfil con menú desplegable
        JButton btnPerfil = new JButton(usuario + " ▼");
        btnPerfil.setFocusPainted(false);
        btnPerfil.setBackground(Color.WHITE);
        btnPerfil.setForeground(new Color(45, 45, 45));
        btnPerfil.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        btnPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPopupMenu menuPerfil = new JPopupMenu();

        JMenuItem itemPerfil = new JMenuItem("Ver mi perfil");
        JMenuItem itemCuenta = new JMenuItem("Detalles de la cuenta");
        JMenuItem itemCerrar = new JMenuItem("Cerrar sesión");

        menuPerfil.add(itemPerfil);
        menuPerfil.add(itemCuenta);
        menuPerfil.addSeparator();
        menuPerfil.add(itemCerrar);

        // Acción para desplegar el menú de perfil
        btnPerfil.addActionListener(e -> menuPerfil.show(btnPerfil, 0, btnPerfil.getHeight()));

        // Acción: ver perfil
        itemPerfil.addActionListener(e -> JOptionPane.showMessageDialog(
                ventana,
                "Perfil de usuario\n\nUsuario: " + usuario + "\nRol: " + rol,
                "Mi perfil",
                JOptionPane.INFORMATION_MESSAGE
        ));

        // Acción: ver detalles de cuenta
        itemCuenta.addActionListener(e -> JOptionPane.showMessageDialog(
                ventana,
                "Detalles de la cuenta\n\nUsuario: " + usuario + "\nRol asignado: " + rol,
                "Detalles de la cuenta",
                JOptionPane.INFORMATION_MESSAGE
        ));

        // Acción: cerrar sesión
        itemCerrar.addActionListener(e -> cerrarSesion());

        JPanel panelSesion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelSesion.setOpaque(false);
        panelSesion.add(lblRol);
        panelSesion.add(btnPerfil);

        header.add(titulo, BorderLayout.WEST);
        header.add(panelSesion, BorderLayout.EAST);

        // Eventos de navegación entre paneles
        btnCamion.addActionListener(e -> mostrarPanel(new VistaPanelConductor()));
        btnConductor.addActionListener(e -> mostrarPanel(new VistaPanelConductor()));
        btnKM.addActionListener(e -> mostrarPanel(new VistaPanelKilometraje(usuario)));
        btnMantenimiento.addActionListener(e -> mostrarPanel(new VistaPanelMantenimiento()));

        // Aplicación de permisos según rol
        aplicarPermisos(btnCamion, btnConductor, btnKM, btnMantenimiento, titulo);

        ventana.add(header, BorderLayout.NORTH);
        ventana.add(sidebar, BorderLayout.WEST);
        ventana.add(mainPanel, BorderLayout.CENTER);

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    /**
     * Controla la visibilidad de opciones según el rol del usuario.
     */
    private void aplicarPermisos(JButton btnCamion, JButton btnConductor, JButton btnKM,
                                 JButton btnMantenimiento, JLabel titulo) {

        switch (rol) {
            case "Administrador de flota":
                btnCamion.setVisible(false);
                btnKM.setVisible(false);
                btnMantenimiento.setVisible(false);
                titulo.setText("Panel de Flota");
                mostrarPanel(new VistaPanelConductor());
                break;

            case "Administrador de mantencion":
                btnCamion.setVisible(false);
                btnConductor.setVisible(false);
                btnKM.setVisible(false);
                titulo.setText("Panel de Mantenimiento");
                mostrarPanel(new VistaPanelMantenimiento());
                break;

            case "Camionero":
                btnCamion.setVisible(false);
                btnConductor.setVisible(false);
                btnMantenimiento.setVisible(false);
                titulo.setText("Panel de Camionero");
                mostrarPanel(new VistaPanelKilometraje(usuario));
                break;

            default:
                mostrarPanel(new VistaPanelConductor());
                break;
        }
    }

    /**
     * Cambia dinámicamente el panel principal.
     */
    private void mostrarPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Cierra la sesión actual y vuelve al login.
     */
    private void cerrarSesion() {
        ventana.dispose();
        Login login = new Login();
        login.setVisible(true);
    }

    /**
     * Crea botones estilizados para el menú lateral.
     */
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(30, 50, 90));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(50, 80, 130));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 50, 90));
            }
        });

        return btn;
    }
}