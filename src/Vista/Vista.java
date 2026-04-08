package Vista;

import javax.swing.*;
import java.awt.*;

// IMPORTS AGREAO
import Controlador.CamionController;
import Controlador.ConductorController;
import Modelo.Conductor;
import BaseDatos.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Vista {

    private JPanel mainPanel;

    // CONTROLLERS
    private CamionController camionController = new CamionController();
    private ConductorController conductorController = new ConductorController();

    public Vista() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        JFrame ventana = new JFrame("Hirata Transport");
        ventana.setSize(900, 550);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        // 🔵 SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 50, 90));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("🚛 Hirata");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 18));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //BOTONES
        JButton btnCamion = crearBotonMenu("Registrar Camión");
        JButton btnConductor = crearBotonMenu("Registrar Conductor");
        JButton btnKM = crearBotonMenu("Registrar KM");

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(btnCamion);
        sidebar.add(btnConductor);
        sidebar.add(btnKM);

        // ⚪ PANEL PRINCIPAL
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // 🔷 HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Dashboard");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        header.add(titulo, BorderLayout.WEST);

        // EVENTOS
        btnCamion.addActionListener(e -> mostrarPanelCamion());
        btnConductor.addActionListener(e -> mostrarPanelConductor());
        btnKM.addActionListener(e -> mostrarPanelKilometraje());

        ventana.add(header, BorderLayout.NORTH);
        ventana.add(sidebar, BorderLayout.WEST);
        ventana.add(mainPanel, BorderLayout.CENTER);

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    // BOTÓN SIDEBAR
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

    // 🚛 REGISTRAR CAMIÓN
    private void mostrarPanelCamion() {

        mainPanel.removeAll();

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JTextField txtPatente = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtKM = new JTextField();

        panel.add(new JLabel("Patente:"));
        panel.add(txtPatente);

        panel.add(new JLabel("Marca:"));
        panel.add(txtMarca);

        panel.add(new JLabel("Modelo:"));
        panel.add(txtModelo);

        panel.add(new JLabel("Kilometraje:"));
        panel.add(txtKM);

        JButton btnGuardar = new JButton("Guardar");

        panel.add(new JLabel(""));
        panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                Connection con = ConexionBD.getConexion();

                String sql = "INSERT INTO camion (patente, marca, modelo, kilometraje) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtPatente.getText());
                ps.setString(2, txtMarca.getText());
                ps.setString(3, txtModelo.getText());
                ps.setInt(4, Integer.parseInt(txtKM.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "✅ Camión guardado");

                // LIMPIAR
                txtPatente.setText("");
                txtMarca.setText("");
                txtModelo.setText("");
                txtKM.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "❌ Error real: " + ex.getMessage());
            }
        });

        mainPanel.add(panel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // 👤 REGISTRAR CONDUCTOR
    private void mostrarPanelConductor() {

        mainPanel.removeAll();

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JTextField txtNombre = new JTextField();
        JTextField txtLicencia = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Licencia:"));
        panel.add(txtLicencia);

        JButton btnGuardar = new JButton("Guardar");

        panel.add(new JLabel(""));
        panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {

            Conductor c = new Conductor();
            c.setNombre(txtNombre.getText());
            c.setLicencia(txtLicencia.getText());

            conductorController.insertarConductor(c);

            JOptionPane.showMessageDialog(null, "✅ Conductor guardado");
        });

        mainPanel.add(panel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // PANEL KILOMETRAJE (ESCRIBIENDO MODELO)
    private void mostrarPanelKilometraje() {

        mainPanel.removeAll();

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Registrar Kilometraje");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        contenedor.add(titulo, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CAMBIO: ahora escribes el modelo
        JTextField campoModelo = new JTextField();
        JTextField campoKM = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 1;
        card.add(campoModelo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        card.add(new JLabel("KM:"), gbc);

        gbc.gridx = 1;
        card.add(campoKM, gbc);

        JButton btnGuardar = new JButton("Guardar");

        gbc.gridx = 1;
        gbc.gridy = 2;
        card.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> {

            try {
                String modelo = campoModelo.getText(); // scribes tú
                int km = Integer.parseInt(campoKM.getText());

                String resultado = camionController.registrarKilometraje(modelo, km);

                JOptionPane.showMessageDialog(null, resultado);

                campoModelo.setText("");
                campoKM.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Error: " + ex.getMessage());
            }
        });

        JPanel centro = new JPanel(new GridBagLayout());
        centro.add(card);

        contenedor.add(centro, BorderLayout.CENTER);

        mainPanel.add(contenedor);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}