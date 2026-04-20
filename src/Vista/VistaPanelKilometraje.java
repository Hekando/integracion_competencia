package Vista;

import Controlador.CamionController;
import Controlador.RegistroKilometrajeController;
import Modelo.Camion;
import Modelo.RegistroKilometraje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VistaPanelKilometraje extends JPanel {

    private final CamionController camionController = new CamionController();
    private final RegistroKilometrajeController registroController = new RegistroKilometrajeController();

    public VistaPanelKilometraje(String usuario) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Camion> camionesAsignados = camionController.listarCamionesPorUsuario(usuario);

        Color fondoGeneral = new Color(245, 247, 250);
        Color blanco = Color.WHITE;
        Color borde = new Color(220, 220, 220);
        Color azul = new Color(93, 156, 236);
        Color textoOscuro = new Color(45, 45, 45);

        JLabel titulo = new JLabel("Registrar Kilometraje");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(textoOscuro);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(fondoGeneral);

        JPanel tarjetaFormulario = new JPanel(new GridBagLayout());
        tarjetaFormulario.setBackground(blanco);
        tarjetaFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjetaFormulario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCamion = new JLabel("Seleccionar Camión:");
        JLabel lblKm = new JLabel("Kilometraje Actual (km):");

        JComboBox<Camion> comboCamion = new JComboBox<>();
        for (Camion camion : camionesAsignados) {
            comboCamion.addItem(camion);
        }

        comboCamion.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Camion camion) {
                    String patente = camion.getPatente() == null || camion.getPatente().isBlank()
                            ? "Sin patente"
                            : camion.getPatente();

                    setText("ID " + camion.getIdCamion() + " | " + patente + " | " +
                            camion.getMarca() + " " + camion.getModelo());
                }
                return this;
            }
        });

        JTextField campoKM = new JTextField(12);
        agregarPlaceholder(campoKM, "Ej: 5000");
        permitirSoloNumeros(campoKM);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(azul);
        btnGuardar.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0;
        tarjetaFormulario.add(lblCamion, gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(comboCamion, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        tarjetaFormulario.add(lblKm, gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(campoKM, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        tarjetaFormulario.add(btnGuardar, gbc);

        JLabel lblAlerta = new JLabel("ℹ️ Estado: esperando registro.");

        JPanel tarjetaTabla = new JPanel(new BorderLayout(10, 10));
        tarjetaTabla.setBackground(blanco);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel subtitulo = new JLabel("Registros del camión seleccionado");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 15));
        tarjetaTabla.add(subtitulo, BorderLayout.NORTH);

        String[] columnas = {"ID", "ID Camión", "Fecha", "Kilometraje", "Resultado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);

        // COLORES AUTOMÁTICOS
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, s, f, row, col);

                String resultado = t.getValueAt(row, 4).toString().toLowerCase();

                if (resultado.contains("alerta")) {
                    c.setBackground(new Color(255, 220, 220));
                } else if (resultado.contains("correctamente")) {
                    c.setBackground(new Color(220, 255, 220));
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tabla);
        tarjetaTabla.add(scrollTabla, BorderLayout.CENTER);

        // BOTÓN ELIMINAR
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(azul);
        btnEliminar.setForeground(Color.WHITE);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnEliminar);
        tarjetaTabla.add(panelBotones, BorderLayout.SOUTH);

        Runnable cargarTabla = () -> {
            modeloTabla.setRowCount(0);
            Camion camionSeleccionado = (Camion) comboCamion.getSelectedItem();
            if (camionSeleccionado == null) return;

            for (RegistroKilometraje r : registroController.listarPorCamion(camionSeleccionado.getIdCamion())) {
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

        btnGuardar.addActionListener(e -> {
            try {
                Camion camionSeleccionado = (Camion) comboCamion.getSelectedItem();
                String kmTexto = campoKM.getText().trim();

                if (kmTexto.equals("Ej: 5000") || kmTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un kilometraje válido");
                    return;
                }

                int km = Integer.parseInt(kmTexto);

                String resultado = camionController.registrarKilometraje(camionSeleccionado.getIdCamion(), km);

                RegistroKilometraje r = new RegistroKilometraje();
                r.setIdCamion(camionSeleccionado.getIdCamion());
                r.setFecha(java.time.LocalDate.now());
                r.setKilometraje(km);
                r.setResultado(resultado);

                registroController.registrar(r);

                lblAlerta.setText("✅ " + resultado);
                campoKM.setText("Ej: 5000");
                campoKM.setForeground(Color.GRAY);

                cargarTabla.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Error: " + ex.getMessage());
            }
        });

        // ELIMINAR CON BLOQUEO
        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un registro");
                return;
            }

            String resultado = modeloTabla.getValueAt(fila, 4).toString().toLowerCase();

            if (resultado.contains("alerta")) {
                JOptionPane.showMessageDialog(null, "❌ No puedes eliminar un registro con alerta activa");
                return;
            }

            try {
                int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                registroController.eliminar(id);

                JOptionPane.showMessageDialog(null, "Eliminado correctamente");
                cargarTabla.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        contenido.add(tarjetaFormulario);
        contenido.add(lblAlerta);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(tarjetaTabla);

        add(new JScrollPane(contenido), BorderLayout.CENTER);
    }

    private void permitirSoloNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    private void agregarPlaceholder(JTextField campo, String texto) {
        campo.setText(texto);
        campo.setForeground(Color.GRAY);

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(texto)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(texto);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }
}