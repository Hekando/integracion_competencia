package Vista;

import BaseDatos.ConexionBD;
import Controlador.InventarioController;
import Modelo.InventarioPieza;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class VistaPanelInventario extends JPanel {

    private JPanel panelMovimiento;
    private JTable tabla;

    private JTextField txtNombre, txtCantidad, txtDescripcion;
    private JComboBox<String> comboEstado;
    private JButton btnGuardar;
    private JLabel lblInfo;
    private JComboBox<String> comboPiezaMovimiento;
    private JTextField txtCantidadMovimiento;
    private JTextField txtDescripcionMovimiento;
    private JButton btnEntrada;
    private JButton btnSalida;
    private JTable tablaHistorial;
    private DefaultTableModel modeloHistorial;

    private DefaultTableModel modeloTabla;

    public VistaPanelInventario() {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        comboPiezaMovimiento = new JComboBox<>();

        txtCantidadMovimiento = new JTextField();
        txtDescripcionMovimiento = new JTextField();

        txtCantidadMovimiento.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    @Override
                    public void keyTyped(
                            java.awt.event.KeyEvent e
                    ) {

                        char c = e.getKeyChar();

                        if (!Character.isDigit(c)
                                && c != '\b') {

                            e.consume();
                        }
                    }
                }
        );



        btnEntrada = new JButton("Registrar Entrada");

        btnSalida = new JButton("Registrar Salida");

        JLabel titulo = new JLabel("Inventario de Piezas");
        lblInfo = new JLabel(
                "Administre y lleve el control de las piezas del inventario."
        );

        lblInfo.setOpaque(true);

        lblInfo.setBackground(
                new Color(223, 240, 216)
        );

        lblInfo.setForeground(
                new Color(60, 118, 61)
        );

        lblInfo.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 20, 12, 20
                )
        );


        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));


        JPanel panelRegistro = new JPanel(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        panelMovimiento = new JPanel(new GridBagLayout());


        panelMovimiento.setBackground(Color.WHITE);

        panelMovimiento.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(230, 230, 230)
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 25, 25, 25
                        )
                )
        );

        GridBagConstraints gbcMov =
                new GridBagConstraints();

        gbcMov.insets =
                new Insets(10, 10, 10, 10);

        gbcMov.fill =
                GridBagConstraints.HORIZONTAL;

        gbcMov.gridx = 0;
        gbcMov.gridy = 0;

        panelMovimiento.add(
                new JLabel("Movimientos de Inventario"),
                gbcMov
        );

        gbcMov.gridy = 1;
        panelMovimiento.add(
                new JLabel("Pieza:"),
                gbcMov
        );

        gbcMov.gridx = 1;
        panelMovimiento.add(
                comboPiezaMovimiento,
                gbcMov
        );

        gbcMov.gridx = 0;
        gbcMov.gridy = 2;
        panelMovimiento.add(
                new JLabel("Cantidad:"),
                gbcMov
        );

        gbcMov.gridx = 1;
        panelMovimiento.add(
                txtCantidadMovimiento,
                gbcMov
        );

        gbcMov.gridx = 0;
        gbcMov.gridy = 3;
        panelMovimiento.add(
                new JLabel("Descripción:"),
                gbcMov
        );

        gbcMov.gridx = 1;
        panelMovimiento.add(
                txtDescripcionMovimiento,
                gbcMov
        );

        gbcMov.gridy = 4;
        panelMovimiento.add(
                btnEntrada,
                gbcMov
        );

        gbcMov.gridy = 5;
        panelMovimiento.add(
                btnSalida,
                gbcMov
        );


        JPanel panelSuperior = new JPanel(
                new GridLayout(1, 2, 20, 0)
        );
        panelSuperior.add(panelRegistro);
        panelSuperior.add(panelMovimiento);


        txtNombre = new JTextField();
        txtCantidad = new JTextField();

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        txtDescripcion = new JTextField();

        txtNombre.setPreferredSize(new Dimension(450, 40));
        txtCantidad.setPreferredSize(new Dimension(450, 40));
        txtDescripcion.setPreferredSize(new Dimension(450, 40));


        configurarPlaceholder(
                txtCantidadMovimiento,
                "Ingrese la cantidad"
        );

        configurarPlaceholder(
                txtDescripcionMovimiento,
                "Ingrese la descripción"
        );

        comboEstado = new JComboBox<>(new String[]{
                "Disponible",
                "En reparación",
                "Fuera de servicio"
        });

        btnGuardar = new JButton("Guardar Pieza");
        comboEstado.setPreferredSize(new Dimension(450, 40));

        btnGuardar.setPreferredSize(new Dimension(450, 45));


        btnGuardar.setBackground(
                new Color(66, 133, 244)
        );

        btnGuardar.setForeground(
                Color.WHITE
        );

        btnGuardar.setFocusPainted(false);

        btnGuardar.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelRegistro.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        panelRegistro.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelRegistro.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 1;
        panelRegistro.add(txtCantidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelRegistro.add(new JLabel("Descripción:"), gbc);

        gbc.gridx = 1;
        panelRegistro.add(txtDescripcion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelRegistro.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1;
        panelRegistro.add(comboEstado, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panelRegistro.add(btnGuardar, gbc);

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Cantidad", "Estado", "Descripción"},
                0
        );

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setShowGrid(false);

        tabla.setIntercellSpacing(
                new Dimension(0, 0)
        );

        tabla.getTableHeader().setBackground(
                new Color(245, 247, 250)
        );

        tabla.getTableHeader().setForeground(
                new Color(60, 60, 60)
        );


        tabla.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        tabla.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );
        JPanel panelTabla = new JPanel(new BorderLayout());

        panelTabla.setBackground(Color.WHITE);

        panelTabla.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        panelTabla.add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );
        modeloHistorial = new DefaultTableModel(
                new String[]{
                        "Fecha",
                        "Pieza",
                        "Tipo",
                        "Cantidad",
                        "Descripción"
                },
                0
        );

        tablaHistorial = new JTable(modeloHistorial);

        tablaHistorial.setRowHeight(28);

        tablaHistorial.setShowGrid(false);

        tablaHistorial.setIntercellSpacing(
                new Dimension(0,0)
        );

        tablaHistorial.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        tablaHistorial.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        tablaHistorial.getTableHeader().setBackground(
                new Color(245,247,250)
        );

        tablaHistorial.getTableHeader().setForeground(
                new Color(60,60,60)
        );

        JPanel panelHistorial = new JPanel(new BorderLayout());

        panelHistorial.setBackground(Color.WHITE);

        panelHistorial.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220)
                        ),
                        BorderFactory.createEmptyBorder(
                                15,15,15,15
                        )
                )
        );

        panelHistorial.add(
                new JScrollPane(tablaHistorial),
                BorderLayout.CENTER
        );



        btnGuardar.addActionListener(e -> guardar());
        btnEntrada.addActionListener(e -> {

            try {

                String pieza =
                        comboPiezaMovimiento
                                .getSelectedItem()
                                .toString();

                int cantidad =
                        Integer.parseInt(
                                txtCantidadMovimiento
                                        .getText()
                                        .trim()
                        );

                Connection conn =
                        ConexionBD.getConexion();

                InventarioController controller =
                        new InventarioController(conn);

                if (txtCantidadMovimiento.getText().trim().isEmpty()
                        || txtCantidadMovimiento.getText().equals("Ingrese la cantidad")) {

                    txtCantidadMovimiento.setBorder(
                            BorderFactory.createLineBorder(
                                    Color.RED,
                                    2
                            )
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Debe ingresar una cantidad"
                    );

                    return;
                }

                if (txtDescripcionMovimiento.getText().trim().isEmpty()
                        || txtDescripcionMovimiento.getText().equals("Ingrese la descripción")) {

                    txtDescripcionMovimiento.setBorder(
                            BorderFactory.createLineBorder(
                                    Color.RED,
                                    2
                            )
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Debe ingresar una descripción"
                    );

                    return;
                }

                txtCantidadMovimiento.setBorder(
                        BorderFactory.createLineBorder(
                                new Color(46,204,113),
                                2
                        )
                );

                txtDescripcionMovimiento.setBorder(
                        BorderFactory.createLineBorder(
                                new Color(46,204,113),
                                2
                        )
                );


                controller.registrarEntrada(
                        pieza,
                        cantidad,
                        txtDescripcionMovimiento.getText()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Entrada registrada correctamente"
                );

                cargarTabla();
                cargarHistorial();

                txtCantidadMovimiento.setText("");
                txtDescripcionMovimiento.setText("");


                configurarPlaceholder(
                        txtDescripcionMovimiento,
                        "Ingrese la descripción"
                );
                configurarPlaceholder(
                        txtCantidadMovimiento,
                        "Ingrese la cantidad"
                );



            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }

        });

        btnSalida.addActionListener(e -> {

            try {

                String pieza =
                        comboPiezaMovimiento
                                .getSelectedItem()
                                .toString();

                int cantidad =
                        Integer.parseInt(
                                txtCantidadMovimiento
                                        .getText()
                                        .trim()
                        );

                Connection conn =
                        ConexionBD.getConexion();

                InventarioController controller =
                        new InventarioController(conn);

                if (txtCantidadMovimiento.getText().trim().isEmpty()
                        || txtCantidadMovimiento.getText().equals("Ingrese la cantidad")) {

                    txtCantidadMovimiento.setBorder(
                            BorderFactory.createLineBorder(
                                    Color.RED,
                                    2
                            )
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Debe ingresar una cantidad"
                    );

                    return;
                }

                if (txtDescripcionMovimiento.getText().trim().isEmpty()
                        || txtDescripcionMovimiento.getText().equals("Ingrese la descripción")) {

                    txtDescripcionMovimiento.setBorder(
                            BorderFactory.createLineBorder(
                                    Color.RED,
                                    2
                            )
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Debe ingresar una descripción"
                    );

                    return;
                }


                controller.registrarSalida(
                        pieza,
                        cantidad,
                        txtDescripcionMovimiento.getText()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Salida registrada correctamente"
                );

                cargarTabla();
                cargarHistorial();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });


        JPanel superior = new JPanel(
                new BorderLayout()
        );

        superior.setBackground(
                new Color(245, 247, 250)
        );

        superior.add(
                titulo,
                BorderLayout.WEST
        );

        superior.add(
                lblInfo,
                BorderLayout.EAST
        );

        add(
                superior,
                BorderLayout.NORTH
        );

        panelRegistro.setBackground(Color.WHITE);

        panelRegistro.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(230, 230, 230)
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 25, 25, 25
                        )
                )
        );


        JPanel centro = new JPanel(
                new BorderLayout(0, 20)
        );

        centro.setBackground(
                new Color(245, 247, 250)
        );

        centro.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        centro.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        JPanel panelCentral =
                new JPanel(new GridLayout(2,1,0,15));

        panelCentral.add(panelTabla);
        panelCentral.add(panelHistorial);

        centro.add(
                panelCentral,
                BorderLayout.CENTER
        );

        add(
                centro,
                BorderLayout.CENTER
        );

        cargarTabla();
        cargarHistorial();

        try {

            Connection conn = ConexionBD.getConexion();

            InventarioController controller =
                    new InventarioController(conn);

            List<InventarioPieza> piezas =
                    controller.listar();

            for (InventarioPieza p : piezas) {

                comboPiezaMovimiento.addItem(
                        p.getNombre()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private void guardar() {

        if (!validarCampos()) {

            lblInfo.setText(
                    "Complete todos los campos obligatorios."
            );

            lblInfo.setBackground(
                    new Color(248, 215, 218)
            );

            lblInfo.setForeground(
                    new Color(114, 28, 36)
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos"
            );

            return;
        }

        try {

            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String estado = comboEstado.getSelectedItem().toString();

            int cantidad = Integer.parseInt(
                    txtCantidad.getText().trim()
            );

            InventarioPieza pieza = new InventarioPieza();

            pieza.setNombre(nombre);
            pieza.setCantidad(cantidad);
            pieza.setDescripcion(descripcion);
            pieza.setEstado(estado);

            Connection conn = ConexionBD.getConexion();

            InventarioController controller =
                    new InventarioController(conn);

            controller.registrar(pieza);

            JOptionPane.showMessageDialog(
                    this,
                    "Pieza guardada correctamente"
            );

            lblInfo.setText(
                    "Pieza registrada correctamente."
            );

            lblInfo.setBackground(
                    new Color(223, 240, 216)
            );

            lblInfo.setForeground(
                    new Color(60, 118, 61)
            );

            configurarPlaceholder(
                    txtNombre,
                    "Ingrese el nombre de la pieza"
            );

            configurarPlaceholder(
                    txtCantidad,
                    "Ingrese la cantidad"
            );

            configurarPlaceholder(
                    txtDescripcion,
                    "Ingrese la descripción de la pieza"
            );
            comboEstado.setSelectedIndex(0);

            cargarTabla();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La cantidad debe ser un número válido"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar la pieza: " + e.getMessage()
            );
        }
    }

    private void cargarTabla() {

        try {

            modeloTabla.setRowCount(0);

            Connection conn = ConexionBD.getConexion();

            InventarioController controller =
                    new InventarioController(conn);

            List<InventarioPieza> lista =
                    controller.listar();

            for (InventarioPieza p : lista) {

                modeloTabla.addRow(new Object[]{
                        p.getIdPieza(),
                        p.getNombre(),
                        p.getCantidad(),
                        p.getEstado(),
                        p.getDescripcion()
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar inventario"
            );
        }
    }

    private void cargarHistorial() {

        try {

            modeloHistorial.setRowCount(0);

            Connection conn =
                    ConexionBD.getConexion();

            String sql =
                    "SELECT m.fecha, p.nombre, m.tipo, " +
                            "m.cantidad, m.descripcion " +
                            "FROM movimiento_inventario m " +
                            "INNER JOIN inventario_pieza p " +
                            "ON m.id_pieza = p.id_pieza " +
                            "ORDER BY m.fecha DESC";

            java.sql.Statement st =
                    conn.createStatement();

            java.sql.ResultSet rs =
                    st.executeQuery(sql);

            while(rs.next()) {

                modeloHistorial.addRow(
                        new Object[]{
                                rs.getString("fecha"),
                                rs.getString("nombre"),
                                rs.getString("tipo"),
                                rs.getInt("cantidad"),
                                rs.getString("descripcion")
                        }
                );
            }

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar historial"
            );
        }
    }

    private boolean validarCampos() {

        boolean valido = true;

        if (txtNombre.getText().trim().isEmpty()
                || txtNombre.getText().equals("Ingrese el nombre de la pieza")
        ) {
            txtNombre.setBorder(BorderFactory.createLineBorder(Color.RED, 2)
            );

            valido = false;

        } else {
            txtNombre.setBorder(BorderFactory.createLineBorder(new Color(46, 204, 113), 2)
            );
        }

        if (txtCantidad.getText().trim().isEmpty()
                || txtCantidad.getText().equals("Ingrese la cantidad")
        ) {

            txtCantidad.setBorder(BorderFactory.createLineBorder(Color.RED, 2)
            );

            valido = false;

        } else {
            txtCantidad.setBorder(BorderFactory.createLineBorder(new Color(46, 204, 113), 2)
            );
        }

        if (txtDescripcion.getText().trim().isEmpty() || txtDescripcion.getText().equals("Ingrese la descripción de la pieza")
        ) {

            txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.RED, 2)
            );

            valido = false;

        } else {

            txtDescripcion.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(46, 204, 113),
                            2
                    )
            );
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