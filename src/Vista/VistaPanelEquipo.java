package Vista;

import BaseDatos.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VistaPanelEquipo extends JPanel {

    private JTextField txtSerie, txtNombre, txtTipo, txtMarca, txtModelo;
    private JComboBox<String> comboEstado;
    private JButton btnGuardar;
    private JLabel lblInfo;

    public VistaPanelEquipo() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Gestión de Equipos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        lblInfo = new JLabel(
                "Administre el inventario de equipos."
        );

        lblInfo.setOpaque(true);

        lblInfo.setBackground(new Color(223, 240, 216)
        );

        lblInfo.setForeground(new Color(60, 118, 61)
        );

        lblInfo.setBorder(BorderFactory.createEmptyBorder(
                12, 20, 12, 20)
        );

        JPanel card = new JPanel(new GridBagLayout());

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                30, 35, 30, 35
                        )
                )
        );

        txtSerie = new JTextField();

        txtSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        txtNombre = new JTextField();
        txtTipo = new JTextField();
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        configurarPlaceholder(txtSerie, "Ingrese número de serie");

        configurarPlaceholder(txtNombre, "Ingrese nombre del equipo");

        configurarPlaceholder(txtTipo, "Ingrese tipo de equipo");

        configurarPlaceholder(txtMarca, "Ingrese marca");

        configurarPlaceholder(txtModelo, "Ingrese modelo");

        comboEstado = new JComboBox<>(new String[]{"Activo", "En reparación", "Inactivo"});

        btnGuardar = new JButton("Guardar Equipo");

        btnGuardar.setBackground(
                new Color(59,130,246)
        );

        btnGuardar.setForeground(Color.WHITE);

        btnGuardar.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        btnGuardar.setFocusPainted(false);

        btnGuardar.setBorderPainted(false);

        btnGuardar.setPreferredSize(
                new Dimension(500,55)
        );

        Font fuenteCampo =
                new Font("Segoe UI", Font.PLAIN, 14);

        txtSerie.setFont(fuenteCampo);
        txtNombre.setFont(fuenteCampo);
        txtTipo.setFont(fuenteCampo);
        txtMarca.setFont(fuenteCampo);
        txtModelo.setFont(fuenteCampo);

        txtSerie.setPreferredSize(
                new Dimension(500,45)
        );

        txtNombre.setPreferredSize(
                new Dimension(500,45)
        );

        txtTipo.setPreferredSize(
                new Dimension(500,45)
        );

        txtMarca.setPreferredSize(
                new Dimension(500,45)
        );

        txtModelo.setPreferredSize(
                new Dimension(500,45)
        );

        comboEstado.setPreferredSize(
                new Dimension(500,45)
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(15,15,15,15);

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(new JLabel("N° Serie:"), gbc);

        gbc.gridx = 1;
        card.add(txtSerie, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        card.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        card.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        card.add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 1;
        card.add(txtTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        card.add(new JLabel("Marca:"), gbc);

        gbc.gridx = 1;
        card.add(txtMarca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        card.add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 1;
        card.add(txtModelo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        card.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1;
        card.add(comboEstado, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        card.add(btnGuardar, gbc);

        // BOTONN FUNCIONAL
        btnGuardar.addActionListener(e -> {

            if (!validarCampos()) {lblInfo.setText("Complete todos los campos obligatorios.");

                lblInfo.setBackground(new Color(248,215,218));

                lblInfo.setForeground(new Color(114,28,36));

                return;
            }


            guardarEquipo(
                    txtSerie.getText(),
                    txtNombre.getText(),
                    txtTipo.getText(),
                    txtMarca.getText(),
                    txtModelo.getText(),
                    comboEstado.getSelectedItem().toString()
            );

            // limpiar campos
            txtSerie.setText("");
            txtNombre.setText("");
            txtTipo.setText("");
            txtMarca.setText("");
            txtModelo.setText("");
            comboEstado.setSelectedIndex(0);
        });

        JPanel superior = new JPanel(new BorderLayout()
        );

        superior.setBackground(new Color(245,247,250));

        superior.add(titulo, BorderLayout.WEST);

        superior.add(lblInfo, BorderLayout.EAST);

        add(superior, BorderLayout.NORTH);
        JPanel contenedor =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                20
                        )
                );

        contenedor.setBackground(
                new Color(245,247,250)
        );

        contenedor.add(card);

        add(contenedor, BorderLayout.CENTER);
    }

    // MÉTODO QUE GUARDA EN MYSQL
    private void guardarEquipo(String serie, String nombre, String tipo, String marca, String modelo, String estado) {
        try {
            Connection conn = ConexionBD.getConexion();

            String sql = "INSERT INTO equipo (numero_serie, nombre, tipo, marca, modelo, estado) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, serie);
            ps.setString(2, nombre);
            ps.setString(3, tipo);
            ps.setString(4, marca);
            ps.setString(5, modelo);
            ps.setString(6, estado);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Equipo guardado en BD ✅");
            lblInfo.setText(
                    "Equipo registrado correctamente."
            );

            lblInfo.setBackground(new Color(223,240,216));

            lblInfo.setForeground(new Color(60,118,61));


            conn.close();

        } catch (Exception e) {e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar ❌");
        }
    }
    private boolean validarCampos() {

        boolean valido = true;

        JTextField[] campos = {
                txtSerie,
                txtNombre,
                txtTipo,
                txtMarca,
                txtModelo
        };

        for (JTextField campo : campos) {

            if (campo.getText().trim().isEmpty()
                    || campo.getForeground().equals(Color.GRAY)) {

                campo.setBorder(BorderFactory.createLineBorder(Color.RED, 2)
                );

                valido = false;

            } else {campo.setBorder(BorderFactory.createLineBorder(
                            new Color(46,204,113), 2
                    )
            );
            }
        }

        return valido;
    }
    private void configurarPlaceholder(
            JTextField campo,
            String texto
    ) {

        campo.setText(texto);
        campo.setForeground(Color.GRAY);

        campo.addFocusListener(
                new java.awt.event.FocusAdapter() {

                    @Override
                    public void focusGained(
                            java.awt.event.FocusEvent e
                    ) {

                        if (campo.getText().equals(texto)) {

                            campo.setText("");
                            campo.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(
                            java.awt.event.FocusEvent e
                    ) {

                        if (campo.getText().trim().isEmpty()) {

                            campo.setText(texto);
                            campo.setForeground(Color.GRAY);
                        }
                    }
                }
        );
    }

}