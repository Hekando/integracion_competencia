package Vista;

import BaseDatos.ConexionBD;
import Controlador.AdministradorController;
import Controlador.EquipoController;
import Controlador.MantenimientoEquipoController;
import Modelo.Administrador;
import Modelo.Equipo;
import Modelo.MantenimientoEquipo;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class VistaPanelMantenimientoEquipo extends JPanel {

    private JComboBox<Equipo> comboEquipo;
    private JComboBox<Administrador> comboTecnico;
    private JTextField txtFecha;
    private JComboBox<String> comboTipo;
    private JTextArea txtDescripcion;
    private JComboBox<String> comboEstado;
    private JButton btnRegistrar;
    private JTable tabla;
    private javax.swing.table.DefaultTableModel modeloTabla;


    private EquipoController equipoController;
    private AdministradorController administradorController;
    private MantenimientoEquipoController mantenimientoController;
    private JLabel lblMensaje;

    public VistaPanelMantenimientoEquipo() {

        try {
            Connection conn = ConexionBD.getConexion();
            equipoController = new EquipoController(conn);
            administradorController = new AdministradorController(conn);
            mantenimientoController = new MantenimientoEquipoController(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al conectar con la base de datos");
        }

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        JLabel titulo = new JLabel(
                "Registrar Mantenimiento de Equipo"
        );

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        lblMensaje = new JLabel(
                "Complete la información del mantenimiento realizado."
        );

        lblMensaje.setOpaque(true);
        lblMensaje.setBackground(
                new Color(223,240,216)
        );

        lblMensaje.setForeground(
                new Color(60,118,61)
        );

        lblMensaje.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        lblMensaje.setBorder(
                BorderFactory.createEmptyBorder(
                        12,20,12,20
                )
        );

        JPanel panelSuperior = new JPanel(
                new BorderLayout()
        );

        panelSuperior.setBackground(
                new Color(245,247,250)
        );

        panelSuperior.setBorder(
                BorderFactory.createEmptyBorder(
                        20,20,10,20
                )
        );

        panelSuperior.add(
                titulo,
                BorderLayout.WEST
        );

        panelSuperior.add(
                lblMensaje,
                BorderLayout.EAST
        );

        add(panelSuperior, BorderLayout.NORTH);

        JPanel form = new JPanel(
                new GridBagLayout()
        );

        form.setBackground(Color.WHITE);

        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220)
                        ),
                        BorderFactory.createEmptyBorder(
                                25,25,25,25
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(
                10,10,10,10
        );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        comboEquipo = new JComboBox<>();
        comboTecnico = new JComboBox<>();

        txtFecha = new JTextField();

        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isDigit(c)
                        && c != '-'
                        && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {

                    e.consume();
                }
            }
        });

        txtFecha.setText("Ej: 2026-06-10");
        txtFecha.setForeground(Color.GRAY);

        comboTipo = new JComboBox<>(
                new String[]{
                        "Preventivo",
                        "Correctivo"
                }
        );

        txtDescripcion =
                new JTextArea(5,20);

        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        txtDescripcion.setText(
                "Detalle las acciones realizadas, cambios de piezas, limpieza, observaciones, etc."
        );

        txtDescripcion.setForeground(
                Color.GRAY
        );

        comboEstado = new JComboBox<>(
                new String[]{
                        "Pendiente MantEquipo",
                        "En proceso mantEquipo",
                        "Finalizado"
                }
        );

        btnRegistrar =
                new JButton(
                        "Guardar Registro"
                );

        btnRegistrar.setBackground(
                new Color(72,133,237)
        );

        btnRegistrar.setForeground(
                Color.WHITE
        );

        btnRegistrar.setFocusPainted(false);

        btnRegistrar.setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        txtFecha.addFocusListener(
                new java.awt.event.FocusAdapter() {

                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {

                        if (txtFecha.getText().equals("Ej: 2026-06-10")) {

                            txtFecha.setText("");
                            txtFecha.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e
                    ) {

                        if (txtFecha.getText().trim().isEmpty()) {

                            txtFecha.setText("Ej: 2026-06-10");

                            txtFecha.setForeground(
                                    Color.GRAY
                            );
                        }
                    }
                }
        );

        txtDescripcion.addFocusListener(new java.awt.event.FocusAdapter() {

                                            @Override
                                            public void focusGained(java.awt.event.FocusEvent e) {

                                                if (txtDescripcion.getText().equals("Detalle las acciones realizadas, cambios de piezas, limpieza, observaciones, etc."
                                                )) {

                                                    txtDescripcion.setText("");

                                                    txtDescripcion.setForeground(
                                                            Color.BLACK
                                                    );
                                                }
                                            }

                                            @Override
                                            public void focusLost(java.awt.event.FocusEvent e
                                            ) {

                                                if (txtDescripcion.getText().trim().isEmpty()) {

                                                    txtDescripcion.setText("Detalle las acciones realizadas, cambios de piezas, limpieza, observaciones, etc.");

                                                    txtDescripcion.setForeground(Color.GRAY);
                                                }
                                            }
                                        }
        );

        // COLUMNA IZQUIERDA

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Equipo:"), gbc);

        gbc.gridx = 1;
        form.add(comboEquipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(new JLabel("Tipo de Mantenimiento:"), gbc);

        gbc.gridx = 1;
        form.add(comboTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 1;
        form.add(txtFecha, gbc);

        // COLUMNA DERECHA

        gbc.gridx = 2;
        gbc.gridy = 0;
        form.add(new JLabel("Técnico:"), gbc);

        gbc.gridx = 3;
        form.add(comboTecnico, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        form.add(new JLabel("Descripción / Acciones realizadas:"), gbc);

        gbc.gridx = 3;

        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

        scrollDescripcion.setPreferredSize(
                new Dimension(350, 100
                )
        );

        form.add(scrollDescripcion, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        form.add(new JLabel("Estado del Equipo:"), gbc);

        gbc.gridx = 3;
        form.add(comboEstado, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;

        form.add(btnRegistrar, gbc);

        JPanel centro = new JPanel(new BorderLayout());

        centro.setBackground(
                new Color(245,247,250)
        );

        centro.setBorder(BorderFactory.createEmptyBorder(
                        10,
                        20,
                        20,
                        20
                )
        );

        centro.add(form, BorderLayout.NORTH);



        JPanel panelTabla = new JPanel(new BorderLayout());

        panelTabla.setBackground(Color.WHITE);

        panelTabla.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220)
                        ),
                        BorderFactory.createEmptyBorder(
                                10,10,10,10
                        )
                )
        );

        JLabel lblHistorial = new JLabel("Mantenimientos Registrados");

        lblHistorial.setFont(new Font("Segoe UI", Font.BOLD, 20)
        );

        String[] columnas = {
                "ID",
                "Equipo",
                "Tipo",
                "Fecha",
                "Técnico",
                "Descripción",
                "Estado"
        };

        modeloTabla =
                new javax.swing.table.DefaultTableModel(columnas, 0);

        tabla = new JTable(modeloTabla);

        JScrollPane scrollTabla = new JScrollPane(tabla);

        panelTabla.add(lblHistorial, BorderLayout.NORTH);

        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnEliminar);

        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un registro"
                );
                return;
            }

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea eliminar el registro?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar != JOptionPane.YES_OPTION) {
                return;
            }

            try {

                int id = Integer.parseInt(
                        modeloTabla.getValueAt(fila, 0).toString()
                );

                mantenimientoController.eliminar(id);

                cargarTabla();

                JOptionPane.showMessageDialog(
                        this,
                        "Registro eliminado correctamente"
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });

        panelTabla.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        centro.add(
                panelTabla,
                BorderLayout.CENTER
        );

        add(
                centro,
                BorderLayout.CENTER
        );

        cargarEquipos();
        cargarTecnicos();
        cargarTabla();

        btnRegistrar.addActionListener(
                e -> registrarMantenimiento()
        );
    }

    private void cargarEquipos() {
        try {
            comboEquipo.removeAllItems();

            List<Equipo> equipos = equipoController.listar();

            for (Equipo equipo : equipos) {
                comboEquipo.addItem(equipo);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar equipos");
        }
    }

    private void cargarTecnicos() {
        try {
            comboTecnico.removeAllItems();

            List<Administrador> tecnicos = administradorController.listarTecnicosMantenimientoOficina();

            for (Administrador tecnico : tecnicos) {
                comboTecnico.addItem(tecnico);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tecnicos");
        }
    }

    private void cargarTabla() {

        try {

            modeloTabla.setRowCount(0);

            List<MantenimientoEquipo> lista =
                    mantenimientoController.listar();

            for (MantenimientoEquipo m : lista) {

                modeloTabla.addRow(
                        new Object[]{
                                m.getId_mantenimiento(),
                                m.getId_equipo(),
                                m.getTipo(),
                                m.getFecha(),
                                m.getId_admin(),
                                m.getDescripcion(),
                                "Registrado"
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar mantenimientos"
            );
        }
    }

    private void registrarMantenimiento() {
        try {
            Equipo equipo = (Equipo) comboEquipo.getSelectedItem();
            Administrador tecnico = (Administrador) comboTecnico.getSelectedItem();

            if (equipo == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un equipo");
                return;
            }

            if (tecnico == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un tecnico");
                return;
            }

            boolean error = false;

            txtFecha.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(200,200,200)
                    )
            );

            txtDescripcion.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(200,200,200)
                    )
            );

            if (txtFecha.getText().equals("Ej: 2026-06-10")
                    || txtFecha.getText().trim().isEmpty()) {

                txtFecha.setBorder(
                        BorderFactory.createLineBorder(
                                Color.RED,
                                2
                        )
                );

                error = true;
            }

            if (txtDescripcion.getText().contains("Detalle las acciones")
                    || txtDescripcion.getText().trim().isEmpty()) {

                txtDescripcion.setBorder(
                        BorderFactory.createLineBorder(
                                Color.RED,
                                2
                        )
                );

                error = true;
            }

            if (error) {

                lblMensaje.setText(
                        "Revise los campos obligatorios."
                );

                lblMensaje.setBackground(
                        new Color(255,220,220)
                );

                lblMensaje.setForeground(
                        new Color(180,0,0)
                );

                return;
            }

            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());

            MantenimientoEquipo mantenimiento = new MantenimientoEquipo();
            mantenimiento.setId_equipo(equipo.getIdEquipo());
            mantenimiento.setId_admin(tecnico.getId_admin());
            mantenimiento.setFecha(fecha);
            mantenimiento.setTipo(comboTipo.getSelectedItem().toString());
            mantenimiento.setDescripcion(txtDescripcion.getText().trim());

            // Registrar mantenimiento
            mantenimientoController.registrar(mantenimiento);

            cargarTabla();

            lblMensaje.setText(
                    "Registro guardado exitosamente."
            );

            lblMensaje.setBackground(
                    new Color(223,240,216)
            );

            lblMensaje.setForeground(
                    new Color(60,118,61)
            );

            // Actualizar estado del equipo
            String nuevoEstadoEquipo = comboEstado.getSelectedItem().toString();
            equipoController.actualizarEstado(equipo.getIdEquipo(), nuevoEstadoEquipo);

            equipoController.actualizarEstado(
                    equipo.getIdEquipo(),
                    nuevoEstadoEquipo
            );

            JOptionPane.showMessageDialog(this, "Mantenimiento registrado correctamente");

            txtFecha.setText("Ej: 2026-06-10");
            txtFecha.setForeground(Color.GRAY);

            txtDescripcion.setText(
                    "Detalle las acciones realizadas, cambios de piezas, limpieza, observaciones, etc."
            );

            txtDescripcion.setForeground(
                    Color.GRAY
            );
            comboTipo.setSelectedIndex(0);
            comboEstado.setSelectedIndex(0);

        } catch (java.time.format.DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener formato yyyy-MM-dd");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}