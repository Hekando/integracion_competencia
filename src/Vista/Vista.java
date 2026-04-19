package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import Controlador.RegistroKilometrajeController;
import Modelo.RegistroKilometraje;
import BaseDatos.ConexionBD;
import Controlador.CamionController;
import Controlador.ConductorController;
import Controlador.MantenimientoController;
import Modelo.Camion;
import Modelo.Conductor;
import Modelo.Mantenimiento;
import Modelo.RegistroConductorCamion;

public class Vista {

    private JPanel mainPanel;
    private final JFrame ventana;
    private final String usuario;
    private final String rol;

    // CONTROLLERS
    private CamionController camionController = new CamionController();
    private ConductorController conductorController = new ConductorController();
    private MantenimientoController mantenimientoController = new MantenimientoController();
    private RegistroKilometrajeController registroController = new RegistroKilometrajeController();

    public Vista(String usuario, String rol) {
        this.usuario = usuario;
        this.rol = rol;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        ventana = new JFrame("Hirata Transport");
        ventana.setSize(900, 550);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 50, 90));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("🚛 Hirata");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 18));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

        // PANEL PRINCIPAL
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Dashboard");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel lblSesion = new JLabel("Usuario: " + usuario + " | Rol: " + rol);
        lblSesion.setFont(new Font("Arial", Font.PLAIN, 13));
        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.setFocusPainted(false);

        header.add(titulo, BorderLayout.WEST);
        JPanel panelSesion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelSesion.setOpaque(false);
        panelSesion.add(lblSesion);
        panelSesion.add(btnCerrarSesion);
        header.add(panelSesion, BorderLayout.EAST);

        // EVENTOS
        btnCamion.addActionListener(e -> mostrarPanelConductor());
        btnConductor.addActionListener(e -> mostrarPanelConductor());
        btnKM.addActionListener(e -> mostrarPanelKilometraje());
        btnMantenimiento.addActionListener(e -> mostrarPanelMantenimiento());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        aplicarPermisos(btnCamion, btnConductor, btnKM, btnMantenimiento, titulo);

        ventana.add(header, BorderLayout.NORTH);
        ventana.add(sidebar, BorderLayout.WEST);
        ventana.add(mainPanel, BorderLayout.CENTER);

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void aplicarPermisos(JButton btnCamion, JButton btnConductor, JButton btnKM,
                                 JButton btnMantenimiento, JLabel titulo) {
        switch (rol) {
            case "Administrador de flota":
                btnCamion.setVisible(false);
                btnKM.setVisible(false);
                btnMantenimiento.setVisible(false);
                titulo.setText("Panel de Flota");
                mostrarPanelConductor();
                break;
            case "Administrador de mantencion":
                btnCamion.setVisible(false);
                btnConductor.setVisible(false);
                btnKM.setVisible(false);
                titulo.setText("Panel de Mantenimiento");
                mostrarPanelMantenimiento();
                break;
            case "Camionero":
                btnCamion.setVisible(false);
                btnConductor.setVisible(false);
                btnMantenimiento.setVisible(false);
                titulo.setText("Panel de Camionero");
                mostrarPanelKilometraje();
                break;
            default:
                mostrarPanelConductor();
                break;
        }
    }

    private void cerrarSesion() {
        ventana.dispose();
        Login login = new Login();
        login.setVisible(true);
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(30, 50, 90));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    private JTextField crearCampoFormulario() {
        JTextField campo = new JTextField(14);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(170, 34));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return campo;
    }

    // =========================================================
    // CAMIONES Y CONDUCTORES
    // =========================================================
    private void mostrarPanelCamion() {

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        Color fondoGeneral = new Color(245, 247, 250);
        Color blanco = Color.WHITE;
        Color borde = new Color(220, 220, 220);
        Color azul = new Color(93, 156, 236);
        Color textoOscuro = new Color(45, 45, 45);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(fondoGeneral);
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Gestión de Camiones y Conductores");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(textoOscuro);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        fondo.add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(fondoGeneral);

        // MENSAJE
        JLabel lblEstado = new JLabel("ℹ️ Complete los datos del camión y del conductor.");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 13));
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(235, 248, 245));
        lblEstado.setForeground(new Color(40, 90, 70));
        lblEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 230, 220)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        lblEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        contenido.add(lblEstado);
        contenido.add(Box.createVerticalStrut(12));

        // FORMULARIO
        JPanel tarjetaFormulario = new JPanel(new GridBagLayout());
        tarjetaFormulario.setBackground(blanco);
        tarjetaFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjetaFormulario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtPatente = new JTextField(14);
        JTextField txtMarca = new JTextField(14);
        JTextField txtModelo = new JTextField(14);
        JTextField txtKilometraje = new JTextField(14);
        JTextField txtNombreConductor = new JTextField(14);
        JTextField txtLicencia = new JTextField(14);

        Font fuenteCampo = new Font("Arial", Font.PLAIN, 14);
        txtPatente.setFont(fuenteCampo);
        txtMarca.setFont(fuenteCampo);
        txtModelo.setFont(fuenteCampo);
        txtKilometraje.setFont(fuenteCampo);
        txtNombreConductor.setFont(fuenteCampo);
        txtLicencia.setFont(fuenteCampo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(azul);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setContentAreaFilled(true);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));

        // fila 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        tarjetaFormulario.add(new JLabel("Patente del Camión:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtPatente, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Marca del Camión:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtMarca, gbc);

        // fila 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        tarjetaFormulario.add(new JLabel("Modelo del Camión:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtModelo, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Kilometraje Inicial:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtKilometraje, gbc);

        // fila 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        tarjetaFormulario.add(new JLabel("Nombre del Conductor:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtNombreConductor, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        tarjetaFormulario.add(new JLabel("Licencia:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtLicencia, gbc);

        // botón
        gbc.gridx = 3;
        gbc.gridy = 3;
        tarjetaFormulario.add(btnGuardar, gbc);

        // TABLA
        JPanel tarjetaTabla = new JPanel(new BorderLayout(10, 10));
        tarjetaTabla.setBackground(blanco);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel subtitulo = new JLabel("Camiones y Conductores registrados");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 15));
        subtitulo.setForeground(new Color(60, 60, 60));
        tarjetaTabla.add(subtitulo, BorderLayout.NORTH);

        String[] columnas = {"Patente", "Marca", "Modelo", "Kilometraje", "Conductor", "Licencia"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(220, 230, 250));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(235, 238, 245));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(650, 220));
        tarjetaTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(azul);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setContentAreaFilled(true);

        panelBotones.add(btnEliminar);
        tarjetaTabla.add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> {
            try {
                String patente = txtPatente.getText().trim();
                String marca = txtMarca.getText().trim();
                String modelo = txtModelo.getText().trim();
                String kmTexto = txtKilometraje.getText().trim();
                String nombreConductor = txtNombreConductor.getText().trim();
                String licencia = txtLicencia.getText().trim();

                if (patente.isEmpty() || marca.isEmpty() || modelo.isEmpty() || kmTexto.isEmpty()
                        || nombreConductor.isEmpty() || licencia.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                    return;
                }

                Connection con = ConexionBD.getConexion();

                String sqlCamion = "INSERT INTO camion (patente, marca, modelo, kilometraje) VALUES (?, ?, ?, ?)";
                PreparedStatement psCamion = con.prepareStatement(sqlCamion);
                psCamion.setString(1, patente);
                psCamion.setString(2, marca);
                psCamion.setString(3, modelo);
                psCamion.setInt(4, Integer.parseInt(kmTexto));
                psCamion.executeUpdate();

                Conductor c = new Conductor();
                c.setNombre(nombreConductor);
                c.setLicencia(licencia);
                lblEstado.setText("Panel antiguo deshabilitado");

                modeloTabla.addRow(new Object[]{
                        patente, marca, modelo, kmTexto, nombreConductor, licencia
                });

                lblEstado.setText("✅ Registro guardado exitosamente");
                lblEstado.setBackground(new Color(235, 248, 245));
                lblEstado.setForeground(new Color(40, 90, 70));

                txtPatente.setText("");
                txtMarca.setText("");
                txtModelo.setText("");
                txtKilometraje.setText("");
                txtNombreConductor.setText("");
                txtLicencia.setText("");

            } catch (Exception ex) {
                lblEstado.setText("❌ Error al guardar: " + ex.getMessage());
                lblEstado.setBackground(new Color(252, 235, 235));
                lblEstado.setForeground(new Color(150, 50, 50));
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un registro");
                return;
            }

            modeloTabla.removeRow(fila);
            lblEstado.setText("✅ Registro quitado de la vista");
            lblEstado.setBackground(new Color(235, 248, 245));
            lblEstado.setForeground(new Color(40, 90, 70));
        });

        contenido.add(tarjetaFormulario);
        contenido.add(Box.createVerticalStrut(15));
        contenido.add(tarjetaTabla);

        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);

        fondo.add(scroll, BorderLayout.CENTER);

        mainPanel.add(fondo, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void mostrarPanelConductor() {

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        Color fondoGeneral = new Color(245, 247, 250);
        Color blanco = Color.WHITE;
        Color borde = new Color(220, 220, 220);
        Color azul = new Color(93, 156, 236);
        Color textoOscuro = new Color(45, 45, 45);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(fondoGeneral);
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Registrar Conductor");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(textoOscuro);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        fondo.add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(fondoGeneral);

        JLabel lblEstado = new JLabel("Complete los datos del camion y del conductor.");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 13));
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(235, 248, 245));
        lblEstado.setForeground(new Color(40, 90, 70));
        lblEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 230, 220)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        lblEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        contenido.add(lblEstado);
        contenido.add(Box.createVerticalStrut(12));

        JPanel tarjetaFormulario = new JPanel(new GridBagLayout());
        tarjetaFormulario.setBackground(blanco);
        tarjetaFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjetaFormulario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtPatente = crearCampoFormulario();
        JTextField txtMarca = crearCampoFormulario();
        JTextField txtModelo = crearCampoFormulario();
        JTextField txtKilometraje = crearCampoFormulario();
        JTextField txtNombreConductor = crearCampoFormulario();
        JTextField txtLicencia = crearCampoFormulario();
        JTextField txtUsuarioConductor = crearCampoFormulario();
        JPasswordField txtPasswordConductor = new JPasswordField(14);
        txtPasswordConductor.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPasswordConductor.setPreferredSize(new Dimension(170, 34));
        txtPasswordConductor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(azul);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setContentAreaFilled(true);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        tarjetaFormulario.add(new JLabel("Patente del Camion:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtPatente, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Marca del Camion:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtMarca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        tarjetaFormulario.add(new JLabel("Modelo del Camion:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtModelo, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Kilometraje Inicial:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtKilometraje, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        tarjetaFormulario.add(new JLabel("Nombre del Conductor:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtNombreConductor, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Licencia:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtLicencia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        tarjetaFormulario.add(new JLabel("Usuario del Camionero:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtUsuarioConductor, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Contrasena del Camionero:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtPasswordConductor, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        tarjetaFormulario.add(btnGuardar, gbc);

        JPanel tarjetaTabla = new JPanel(new BorderLayout(10, 10));
        tarjetaTabla.setBackground(blanco);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel subtitulo = new JLabel("Camiones y Conductores registrados");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 15));
        subtitulo.setForeground(new Color(60, 60, 60));
        tarjetaTabla.add(subtitulo, BorderLayout.NORTH);

        String[] columnas = {"ID Camion", "Patente", "Marca", "Modelo", "Kilometraje", "Nombre", "Licencia", "Usuario"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(220, 230, 250));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(235, 238, 245));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(650, 220));
        tarjetaTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(azul);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setContentAreaFilled(true);
        panelBotones.add(btnEliminar);
        tarjetaTabla.add(panelBotones, BorderLayout.SOUTH);

        Runnable cargarTabla = () -> {
            modeloTabla.setRowCount(0);
            for (RegistroConductorCamion registro : conductorController.listarRegistros()) {
                modeloTabla.addRow(new Object[]{
                        registro.getIdCamion(),
                        registro.getPatente(),
                        registro.getMarca(),
                        registro.getModelo(),
                        registro.getKilometraje(),
                        registro.getNombreConductor(),
                        registro.getLicencia(),
                        registro.getUsuario()
                });
            }
        };

        cargarTabla.run();

        btnGuardar.addActionListener(e -> {
            try {
                String patente = txtPatente.getText().trim();
                String marca = txtMarca.getText().trim();
                String modelo = txtModelo.getText().trim();
                String kmTexto = txtKilometraje.getText().trim();
                String nombreConductor = txtNombreConductor.getText().trim();
                String licencia = txtLicencia.getText().trim();
                String usuarioConductor = txtUsuarioConductor.getText().trim();
                String passwordConductor = new String(txtPasswordConductor.getPassword()).trim();

                if (patente.isEmpty() || marca.isEmpty() || modelo.isEmpty() || kmTexto.isEmpty()
                        || nombreConductor.isEmpty() || licencia.isEmpty()
                        || usuarioConductor.isEmpty() || passwordConductor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                    return;
                }

                Camion camion = new Camion();
                camion.setPatente(patente);
                camion.setMarca(marca);
                camion.setModelo(modelo);
                camion.setKilometraje(Integer.parseInt(kmTexto));

                Conductor conductor = new Conductor();
                conductor.setNombre(nombreConductor);
                conductor.setLicencia(licencia);
                conductor.setUsuario(usuarioConductor);
                conductor.setPassword(passwordConductor);

                conductorController.registrarConductorConCamion(conductor, camion);

                lblEstado.setText("Registro guardado exitosamente");
                lblEstado.setBackground(new Color(235, 248, 245));
                lblEstado.setForeground(new Color(40, 90, 70));

                txtPatente.setText("");
                txtMarca.setText("");
                txtModelo.setText("");
                txtKilometraje.setText("");
                txtNombreConductor.setText("");
                txtLicencia.setText("");
                txtUsuarioConductor.setText("");
                txtPasswordConductor.setText("");

                cargarTabla.run();
            } catch (NumberFormatException ex) {
                lblEstado.setText("Error: el kilometraje debe ser numerico");
                lblEstado.setBackground(new Color(252, 235, 235));
                lblEstado.setForeground(new Color(150, 50, 50));
            } catch (Exception ex) {
                lblEstado.setText("Error al guardar: " + ex.getMessage());
                lblEstado.setBackground(new Color(252, 235, 235));
                lblEstado.setForeground(new Color(150, 50, 50));
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un registro");
                return;
            }

            try {
                int idCamion = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                conductorController.eliminarPorIdCamion(idCamion);
                lblEstado.setText("Registro eliminado correctamente");
                lblEstado.setBackground(new Color(235, 248, 245));
                lblEstado.setForeground(new Color(40, 90, 70));
                cargarTabla.run();
            } catch (Exception ex) {
                lblEstado.setText("Error al eliminar: " + ex.getMessage());
                lblEstado.setBackground(new Color(252, 235, 235));
                lblEstado.setForeground(new Color(150, 50, 50));
            }
        });

        contenido.add(tarjetaFormulario);
        contenido.add(Box.createVerticalStrut(15));
        contenido.add(tarjetaTabla);

        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);
        fondo.add(scroll, BorderLayout.CENTER);

        mainPanel.add(fondo, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // =========================================================
    // REGISTRAR KILOMETRAJE
    // =========================================================
    private void mostrarPanelKilometraje() {

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        Color fondoGeneral = new Color(245, 247, 250);
        Color blanco = Color.WHITE;
        Color borde = new Color(220, 220, 220);
        Color azul = new Color(93, 156, 236);
        Color textoOscuro = new Color(45, 45, 45);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(fondoGeneral);
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Registrar Kilometraje");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(textoOscuro);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        fondo.add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(fondoGeneral);

        JPanel tarjetaFormulario = new JPanel(new GridBagLayout());
        tarjetaFormulario.setBackground(blanco);
        tarjetaFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        tarjetaFormulario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCamion = new JLabel("Seleccionar Camión:");
        JLabel lblKm = new JLabel("Kilometraje Actual (km):");

        JComboBox<String> comboCamion = new JComboBox<>(new String[]{
                "2026 - Camión ID 8",
                "2025 - Camión ID 17",
                "2025 - Camión ID 18"
        });

        JTextField campoKM = new JTextField(12);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(azul);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        tarjetaFormulario.add(lblCamion, gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(comboCamion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        tarjetaFormulario.add(lblKm, gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(campoKM, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        tarjetaFormulario.add(btnGuardar, gbc);

        JLabel lblAlerta = new JLabel("ℹ️ Estado: esperando registro.");
        lblAlerta.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel tarjetaTabla = new JPanel(new BorderLayout(10, 10));
        tarjetaTabla.setBackground(blanco);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel subtitulo = new JLabel("Recientes registrados");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 15));
        tarjetaTabla.add(subtitulo, BorderLayout.NORTH);

        String[] columnas = {"ID", "ID Camión", "Fecha", "Kilometraje", "Resultado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);

        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setWidth(0);

        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(220, 230, 250));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(650, 180));

        tarjetaTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(azul);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);

        panelBotones.add(btnEliminar);

        tarjetaTabla.add(panelBotones, BorderLayout.SOUTH);

        Runnable cargarTabla = () -> {
            modeloTabla.setRowCount(0);

            for (RegistroKilometraje r : registroController.listar()) {
                modeloTabla.addRow(new Object[]{
                        r.getId(),
                        r.getIdCamion(),
                        r.getFecha(),
                        r.getKilometraje(),
                        r.getResultado()
                });
            }
        };

        cargarTabla.run();

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona un registro");
                return;
            }

            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

            registroController.eliminar(id);

            JOptionPane.showMessageDialog(null, "✅ Registro eliminado");

            cargarTabla.run();
        });

        btnGuardar.addActionListener(e -> {
            try {
                String seleccionado = comboCamion.getSelectedItem().toString();
                String modelo = seleccionado.split(" - ")[0].trim();
                int km = Integer.parseInt(campoKM.getText().trim());

                String resultado = camionController.registrarKilometraje(modelo, km);

                int idCamion = Integer.parseInt(seleccionado.split("ID ")[1]);

                RegistroKilometraje r = new RegistroKilometraje();
                r.setIdCamion(idCamion);
                r.setFecha(java.time.LocalDate.now());
                r.setKilometraje(km);
                r.setResultado(resultado);

                registroController.registrar(r);

                if (resultado.toLowerCase().contains("alerta")) {
                    lblAlerta.setText("⚠️ " + resultado);
                    lblAlerta.setForeground(new Color(180, 80, 60));
                } else {
                    lblAlerta.setText("✅ " + resultado);
                    lblAlerta.setForeground(new Color(60, 120, 70));
                }

                campoKM.setText("");
                cargarTabla.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Error: " + ex.getMessage());
            }
        });

        contenido.add(tarjetaFormulario);
        contenido.add(lblAlerta);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(tarjetaTabla);

        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);

        fondo.add(scroll, BorderLayout.CENTER);

        mainPanel.add(fondo, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // =========================================================
    // MANTENIMIENTO
    // =========================================================
    private void mostrarPanelMantenimiento() {

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(245, 247, 250));
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Registros de Mantenimientos");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(45, 45, 45));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        fondo.add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(new Color(245, 247, 250));

        BaseDatos.AlertaDAO alertaDAO = new BaseDatos.AlertaDAO();

        JPanel panelPendientes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPendientes.setBackground(Color.WHITE);
        panelPendientes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblPendientes = new JLabel("Pendientes:");
        lblPendientes.setFont(new Font("Arial", Font.BOLD, 14));

        JComboBox<String> comboPendientes = new JComboBox<>();
        comboPendientes.setPreferredSize(new Dimension(280, 34));
        comboPendientes.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnRealizarPendiente = new JButton("Realizar mantenimiento");
        btnRealizarPendiente.setBackground(new Color(93, 156, 236));
        btnRealizarPendiente.setForeground(Color.WHITE);
        btnRealizarPendiente.setFocusPainted(false);
        btnRealizarPendiente.setBorderPainted(false);
        btnRealizarPendiente.setOpaque(true);
        btnRealizarPendiente.setContentAreaFilled(true);
        btnRealizarPendiente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRealizarPendiente.setFont(new Font("Arial", Font.BOLD, 13));

        panelPendientes.add(lblPendientes);
        panelPendientes.add(comboPendientes);
        panelPendientes.add(btnRealizarPendiente);

        contenido.add(panelPendientes);
        contenido.add(Box.createVerticalStrut(10));

        JPanel tarjetaFormulario = new JPanel(new GridBagLayout());
        tarjetaFormulario.setBackground(Color.WHITE);
        tarjetaFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjetaFormulario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> comboIdCamion = new JComboBox<>(new String[]{"8", "9", "10", "11", "16", "17", "18", "19"});
        JTextField txtFecha = new JTextField(12);
        JTextField txtKilometraje = new JTextField(12);
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{
                "Cambio de aceite",
                "Revisión técnica",
                "Alineación",
                "Cambio de neumáticos",
                "Cambio de frenos"
        });

        comboIdCamion.setFont(new Font("Arial", Font.PLAIN, 14));
        txtFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        txtKilometraje.setFont(new Font("Arial", Font.PLAIN, 14));
        comboTipo.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnGuardar = new JButton("Guardar manual");
        btnGuardar.setBackground(new Color(93, 156, 236));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setContentAreaFilled(true);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        tarjetaFormulario.add(new JLabel("ID Camión:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(comboIdCamion, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Fecha (AAAA-MM-DD):"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        tarjetaFormulario.add(new JLabel("Kilometraje:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtKilometraje, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(comboTipo, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        tarjetaFormulario.add(btnGuardar, gbc);

        JPanel tarjetaTabla = new JPanel(new BorderLayout(10, 10));
        tarjetaTabla.setBackground(Color.WHITE);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel subtituloTabla = new JLabel("Historial de Mantenimientos");
        subtituloTabla.setFont(new Font("Arial", Font.BOLD, 15));
        subtituloTabla.setForeground(new Color(60, 60, 60));
        tarjetaTabla.add(subtituloTabla, BorderLayout.NORTH);

        String[] columnas = {"ID", "ID Camión", "Fecha", "Kilometraje", "Tipo"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(220, 230, 250));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(235, 238, 245));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(650, 220));
        tarjetaTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);

        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        btnEditar.setBackground(new Color(93, 156, 236));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setOpaque(true);
        btnEditar.setContentAreaFilled(true);

        btnEliminar.setBackground(new Color(93, 156, 236));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setContentAreaFilled(true);

        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        tarjetaTabla.add(panelBotones, BorderLayout.SOUTH);

        Runnable cargarPendientes = () -> {
            comboPendientes.removeAllItems();
            List<Modelo.Alerta> pendientes = alertaDAO.listarPendientes();

            for (Modelo.Alerta a : pendientes) {
                comboPendientes.addItem("Camión " + a.getIdCamion() + " - " + a.getKilometraje() + " km");
            }
        };

        Runnable cargarTabla = () -> {
            modeloTabla.setRowCount(0);
            List<Mantenimiento> lista = mantenimientoController.listarTodos();

            for (Mantenimiento m : lista) {
                modeloTabla.addRow(new Object[]{
                        m.getId(),
                        m.getIdCamion(),
                        m.getFecha(),
                        m.getKilometraje(),
                        m.getTipo()
                });
            }
        };

        cargarPendientes.run();
        cargarTabla.run();

        comboPendientes.addActionListener(e -> {
            if (comboPendientes.getSelectedItem() == null) return;

            try {
                String texto = comboPendientes.getSelectedItem().toString();
                String[] partes = texto.split(" - ");
                int idCamion = Integer.parseInt(partes[0].replace("Camión ", "").trim());
                int kilometraje = Integer.parseInt(partes[1].replace(" km", "").trim());

                comboIdCamion.setSelectedItem(String.valueOf(idCamion));
                txtKilometraje.setText(String.valueOf(kilometraje));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Error al leer pendiente");
            }
        });

        btnRealizarPendiente.addActionListener(e -> {
            if (comboPendientes.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "No hay alertas pendientes");
                return;
            }

            try {
                String texto = comboPendientes.getSelectedItem().toString();
                String[] partes = texto.split(" - ");
                int idCamion = Integer.parseInt(partes[0].replace("Camión ", "").trim());
                int kilometraje = Integer.parseInt(partes[1].replace(" km", "").trim());

                String fechaTexto = txtFecha.getText().trim();
                java.time.LocalDate fecha;

                if (fechaTexto.isEmpty()) {
                    fecha = java.time.LocalDate.now();
                } else {
                    fecha = java.time.LocalDate.parse(fechaTexto);
                }

                Mantenimiento m = new Mantenimiento();
                m.setIdCamion(idCamion);
                m.setFecha(fecha);
                m.setKilometraje(kilometraje);
                m.setTipo(comboTipo.getSelectedItem().toString());

                mantenimientoController.insertarMantenimiento(m);

                JOptionPane.showMessageDialog(null, "✅ Mantenimiento realizado correctamente");

                txtFecha.setText("");
                txtKilometraje.setText("");
                comboTipo.setSelectedIndex(0);

                cargarPendientes.run();
                cargarTabla.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Error al realizar mantenimiento: " + ex.getMessage());
            }
        });

        btnGuardar.addActionListener(e -> {
            try {
                Mantenimiento m = new Mantenimiento();
                m.setIdCamion(Integer.parseInt(comboIdCamion.getSelectedItem().toString()));

                String fechaTexto = txtFecha.getText().trim();
                if (fechaTexto.isEmpty()) {
                    m.setFecha(java.time.LocalDate.now());
                } else {
                    m.setFecha(java.time.LocalDate.parse(fechaTexto));
                }

                m.setKilometraje(Integer.parseInt(txtKilometraje.getText().trim()));
                m.setTipo(comboTipo.getSelectedItem().toString());

                mantenimientoController.insertarMantenimiento(m);

                JOptionPane.showMessageDialog(null, "✅ Mantenimiento guardado");

                comboIdCamion.setSelectedIndex(0);
                txtFecha.setText("");
                txtKilometraje.setText("");
                comboTipo.setSelectedIndex(0);

                cargarPendientes.run();
                cargarTabla.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Error: " + ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un mantenimiento para eliminar");
                return;
            }

            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            mantenimientoController.eliminarMantenimiento(id);
            JOptionPane.showMessageDialog(null, "✅ Mantenimiento eliminado");
            cargarTabla.run();
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un mantenimiento para editar");
                return;
            }

            try {
                Mantenimiento m = new Mantenimiento();
                m.setId(Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString()));
                m.setIdCamion(Integer.parseInt(comboIdCamion.getSelectedItem().toString()));

                String fechaTexto = txtFecha.getText().trim();
                if (fechaTexto.isEmpty()) {
                    m.setFecha(java.time.LocalDate.now());
                } else {
                    m.setFecha(java.time.LocalDate.parse(fechaTexto));
                }

                m.setKilometraje(Integer.parseInt(txtKilometraje.getText().trim()));
                m.setTipo(comboTipo.getSelectedItem().toString());

                mantenimientoController.actualizarMantenimiento(m);
                JOptionPane.showMessageDialog(null, "✅ Mantenimiento actualizado");
                cargarTabla.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Error al editar: " + ex.getMessage());
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                comboIdCamion.setSelectedItem(modeloTabla.getValueAt(fila, 1).toString());
                txtFecha.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtKilometraje.setText(modeloTabla.getValueAt(fila, 3).toString());
                comboTipo.setSelectedItem(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        contenido.add(tarjetaFormulario);
        contenido.add(Box.createVerticalStrut(15));
        contenido.add(tarjetaTabla);

        fondo.add(contenido, BorderLayout.CENTER);

        mainPanel.add(fondo, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
