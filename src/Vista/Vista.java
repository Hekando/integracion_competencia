package Vista;

import javax.swing.*;
import java.awt.*;

public class Vista {

    private JPanel mainPanel;

    public Vista() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

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

        JButton btnKM = crearBotonMenu("Registrar KM");

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(20));
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

        // EVENTO
        btnKM.addActionListener(e -> mostrarPanelKilometraje());

        ventana.add(header, BorderLayout.NORTH);
        ventana.add(sidebar, BorderLayout.WEST);
        ventana.add(mainPanel, BorderLayout.CENTER);

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    // 🔹 BOTÓN SIDEBAR
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

    // 🔥 PANEL KILOMETRAJE
    private void mostrarPanelKilometraje() {

        mainPanel.removeAll();

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(new Color(245, 247, 250));

        // 🔹 TÍTULO
        JLabel titulo = new JLabel("Registrar Kilometraje");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        contenedor.add(titulo, BorderLayout.NORTH);

        // 🔹 CARD (FORMULARIO)
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🔽 COMBO CAMIÓN
        JComboBox<String> comboCamion = new JComboBox<>();
        comboCamion.addItem("CAM-001 - Toyota Hilux 2020");
        comboCamion.addItem("CAM-002 - Toyota Hilux 2023");

        // 🔢 INPUT KM
        JTextField campoKM = new JTextField();
        campoKM.setFont(new Font("Arial", Font.PLAIN, 14));
        campoKM.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));

        // 🏷 LABELS
        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(new JLabel("Seleccionar Camión:"), gbc);

        gbc.gridx = 1;
        card.add(comboCamion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        card.add(new JLabel("Kilometraje:"), gbc);

        gbc.gridx = 1;
        card.add(campoKM, gbc);

        // 🔘 BOTÓN
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnGuardar.setOpaque(true);
        btnGuardar.setBorderPainted(false);

        gbc.gridx = 1;
        gbc.gridy = 2;
        card.add(btnGuardar, gbc);

        // 🔔 MENSAJE
        JLabel info = new JLabel("⚠ Se generará alerta al llegar a 5000 km");
        info.setForeground(new Color(120,120,120));
        info.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // EVENTO BOTÓN
        btnGuardar.addActionListener(e -> {

            String km = campoKM.getText();

            if (km.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Debe ingresar kilometraje",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int valor = Integer.parseInt(km);

                if (valor >= 5000) {
                    JOptionPane.showMessageDialog(null,
                            "⚠ El camión requiere mantenimiento",
                            "Alerta",
                            JOptionPane.WARNING_MESSAGE);
                }

                JOptionPane.showMessageDialog(null,
                        "✅ Registro guardado",
                        "OK",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Kilometraje inválido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // CONTENEDOR CENTRAL
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(new Color(245, 247, 250));
        centro.add(card);

        contenedor.add(centro, BorderLayout.CENTER);
        contenedor.add(info, BorderLayout.SOUTH);

        mainPanel.add(contenedor, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}



