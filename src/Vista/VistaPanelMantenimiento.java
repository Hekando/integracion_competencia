package Vista;

import Controlador.MantenimientoController;
import Modelo.Mantenimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaPanelMantenimiento extends JPanel {

    private final MantenimientoController mantenimientoController = new MantenimientoController();

    public VistaPanelMantenimiento() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(245, 247, 250));

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

        // CARGAR ALERTAS PENDIENTES EN EL COMBO (AQUÍ VA)
        for (Modelo.Alerta a : alertaDAO.listarPendientes()) {
            comboPendientes.addItem(
                    "Camión " + a.getIdCamion() + " - KM: " + a.getKilometraje()
            );
        }

        JButton btnRealizarPendiente = new JButton("Realizar mantenimiento");

        btnRealizarPendiente.addActionListener(e -> {
            try {
                String seleccionado = (String) comboPendientes.getSelectedItem();

                if (seleccionado == null) {
                    JOptionPane.showMessageDialog(null, "No hay pendientes");
                    return;
                }

                // Obtener ID del camión desde el texto
                int idCamion = Integer.parseInt(seleccionado.split(" ")[1]);

                //  EXTRAER KM REAL DESDE EL TEXTO
                int kmActual = Integer.parseInt(
                        seleccionado.split("KM: ")[1]
                );

                // CREAR MANTENIMIENTO CON KM REAL
                Mantenimiento m = new Mantenimiento();
                m.setIdCamion(idCamion);
                m.setFecha(java.time.LocalDate.now());
                m.setKilometraje(kmActual);
                m.setTipo("Mantenimiento por alerta");

                mantenimientoController.insertarMantenimiento(m);

                // MARCAR ALERTA COMO RESUELTA
                alertaDAO.resolverAlertaPorCamion(idCamion);

                // REINICIAR BASE DE KM (ESTO ARREGLA TU PROBLEMA)
                new Controlador.CamionController().actualizarKilometrajeBase(idCamion, kmActual);

                JOptionPane.showMessageDialog(null, "Mantenimiento realizado correctamente");
                // Refrescar combo
                comboPendientes.removeAllItems();
                for (Modelo.Alerta a : alertaDAO.listarPendientes()) {
                    comboPendientes.addItem(
                            "Camión " + a.getIdCamion() + " - KM: " + a.getKilometraje()
                    );
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

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

        // FECHA AUTOMÁTICA POR DÍA + EDITABLE
        SpinnerDateModel modeloFecha = new SpinnerDateModel(
                new java.util.Date(),
                null,
                null,
                java.util.Calendar.DAY_OF_MONTH // avanza por días
        );

        JSpinner spinnerFecha = new JSpinner(modeloFecha);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd");
        spinnerFecha.setEditor(editor);
        spinnerFecha.setPreferredSize(new Dimension(140, 30));

        // permite escribir manualmente también
        ((JSpinner.DefaultEditor) spinnerFecha.getEditor()).getTextField().setEditable(true);

        JTextField txtKilometraje = new JTextField(12);
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{
                "Cambio de aceite",
                "Revisión técnica",
                "Alineación",
                "Cambio de neumáticos",
                "Cambio de frenos"
        });

        permitirSoloNumeros(txtKilometraje);

        JButton btnGuardar = new JButton("Guardar manual");
        btnGuardar.setBackground(new Color(93, 156, 236));
        btnGuardar.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0;
        tarjetaFormulario.add(new JLabel("ID Camión:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(comboIdCamion, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Fecha (AAAA-MM-DD):"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(spinnerFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        tarjetaFormulario.add(new JLabel("Kilometraje:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtKilometraje, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(comboTipo, gbc);

        gbc.gridx = 3; gbc.gridy = 2;
        tarjetaFormulario.add(btnGuardar, gbc);

        JPanel tarjetaTabla = new JPanel(new BorderLayout(10, 10));
        tarjetaTabla.setBackground(Color.WHITE);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel subtituloTabla = new JLabel("Historial de Mantenimientos");
        tarjetaTabla.add(subtituloTabla, BorderLayout.NORTH);

        String[] columnas = {"ID", "ID Camión", "Fecha", "Kilometraje", "Tipo"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);

        // COLORES
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, s, f, row, col);
                try {
                    int km = Integer.parseInt(t.getValueAt(row, 3).toString());
                    if (km >= 10000) {
                        c.setBackground(new Color(220, 235, 255));
                    } else {
                        c.setBackground(new Color(220, 255, 220));
                    }
                } catch (Exception e) {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tabla);
        tarjetaTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEliminar = new JButton("Eliminar");


        panelBotones.add(btnEliminar);
        tarjetaTabla.add(panelBotones, BorderLayout.SOUTH);

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

        cargarTabla.run();

        btnGuardar.addActionListener(e -> {
            try {
                String kmTexto = txtKilometraje.getText().trim();
                if (kmTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese kilometraje válido");
                    return;
                }

                java.util.Date fechaUtil = (java.util.Date) spinnerFecha.getValue();

                java.time.LocalDate fecha = fechaUtil.toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();

                Mantenimiento m = new Mantenimiento();
                m.setIdCamion(Integer.parseInt(comboIdCamion.getSelectedItem().toString()));
                m.setFecha(fecha);
                m.setKilometraje(Integer.parseInt(kmTexto));
                m.setTipo(comboTipo.getSelectedItem().toString());

                mantenimientoController.insertarMantenimiento(m);

                JOptionPane.showMessageDialog(null, "✅ Guardado");

                txtKilometraje.setText("");
                spinnerFecha.setValue(new java.util.Date());

                cargarTabla.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un mantenimiento");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar mantenimiento?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            mantenimientoController.eliminarMantenimiento(id);

            JOptionPane.showMessageDialog(null, "Eliminado");
            cargarTabla.run();
        });

        contenido.add(tarjetaFormulario);
        contenido.add(Box.createVerticalStrut(15));
        contenido.add(tarjetaTabla);

        fondo.add(new JScrollPane(contenido), BorderLayout.CENTER);
        add(fondo, BorderLayout.CENTER);
    }

    private void permitirSoloNumeros(JTextField campo) {
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }
}