package Vista;

import Controlador.CamionController;
import Controlador.RegistroKilometrajeController;
import Modelo.Camion;
import Modelo.RegistroKilometraje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaCamionero extends JFrame {
    private static final long serialVersionUID = 1L;

    private final List<Camion> camionesAsignados;
    private final CamionController camionController;
    private final RegistroKilometrajeController registroController;
    private final DefaultTableModel modeloTabla;
    private final JTextField campoKM;
    private final JLabel lblEstado;
    private final JComboBox<Camion> comboCamion;

    public VistaCamionero(String usuario, String rol) {
        camionController = new CamionController();
        registroController = new RegistroKilometrajeController();
        camionesAsignados = camionController.listarCamionesPorUsuario(usuario);

        setTitle("Hirata Transport - Camionero");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Panel de Camionero");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel panelSesion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelSesion.setOpaque(false);
        panelSesion.add(new JLabel("Usuario: " + usuario + " | Rol: " + rol));

        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelSesion.add(btnCerrarSesion);

        header.add(titulo, BorderLayout.WEST);
        header.add(panelSesion, BorderLayout.EAST);

        JPanel contenido = new JPanel();
        contenido.setBackground(new Color(245, 247, 250));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JPanel tarjetaFormulario = new JPanel(new GridBagLayout());
        tarjetaFormulario.setBackground(Color.WHITE);
        tarjetaFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjetaFormulario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboCamion = new JComboBox<>();
        for (Camion camion : camionesAsignados) {
            comboCamion.addItem(camion);
        }
        comboCamion.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Camion camion) {
                    setText(describirCamion(camion));
                }
                return this;
            }
        });
        comboCamion.addActionListener(e -> cargarTabla());

        campoKM = new JTextField(18);

        JButton btnGuardar = new JButton("Guardar kilometraje");
        btnGuardar.setBackground(new Color(93, 156, 236));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> registrarKilometraje());

        gbc.gridx = 0;
        gbc.gridy = 0;
        tarjetaFormulario.add(new JLabel("Camion asociado:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(comboCamion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        tarjetaFormulario.add(new JLabel("Kilometraje a registrar:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(campoKM, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        tarjetaFormulario.add(btnGuardar, gbc);

        lblEstado = new JLabel("Estado: esperando registro.");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel tarjetaTabla = new JPanel(new BorderLayout(10, 10));
        tarjetaTabla.setBackground(Color.WHITE);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel subtitulo = new JLabel("Registros del camion seleccionado");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 15));
        tarjetaTabla.add(subtitulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Camion", "Fecha", "Kilometraje", "Resultado"}, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setWidth(0);
        tabla.setRowHeight(28);
        tarjetaTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(93, 156, 236));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarRegistro(tabla));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnEliminar);
        tarjetaTabla.add(panelBotones, BorderLayout.SOUTH);

        if (camionesAsignados.isEmpty()) {
            btnGuardar.setEnabled(false);
            btnEliminar.setEnabled(false);
            comboCamion.setEnabled(false);
            lblEstado.setText("No tienes camiones asociados. Contacta al administrador.");
            lblEstado.setForeground(new Color(150, 50, 50));
        } else {
            cargarTabla();
        }

        contenido.add(tarjetaFormulario);
        contenido.add(lblEstado);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(tarjetaTabla);

        add(header, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
        setVisible(true);
    }

    private void registrarKilometraje() {
        Camion camionSeleccionado = (Camion) comboCamion.getSelectedItem();
        if (camionSeleccionado == null) {
            return;
        }

        try {
            int km = Integer.parseInt(campoKM.getText().trim());
            if (km <= 0) {
                JOptionPane.showMessageDialog(this, "Ingresa un kilometraje mayor a 0");
                return;
            }

            String resultado = camionController.registrarKilometraje(camionSeleccionado.getIdCamion(), km);

            RegistroKilometraje registro = new RegistroKilometraje();
            registro.setIdCamion(camionSeleccionado.getIdCamion());
            registro.setFecha(java.time.LocalDate.now());
            registro.setKilometraje(km);
            registro.setResultado(resultado);
            registroController.registrar(registro);

            lblEstado.setText("Estado: " + resultado);
            lblEstado.setForeground(resultado.toLowerCase().contains("alerta")
                    ? new Color(180, 80, 60)
                    : new Color(60, 120, 70));

            campoKM.setText("");
            cargarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un kilometraje valido");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarRegistro(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro");
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        registroController.eliminar(id);
        cargarTabla();
        lblEstado.setText("Estado: registro eliminado.");
        lblEstado.setForeground(new Color(60, 120, 70));
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        Camion camionSeleccionado = (Camion) comboCamion.getSelectedItem();
        if (camionSeleccionado == null) {
            return;
        }

        for (RegistroKilometraje r : registroController.listarPorCamion(camionSeleccionado.getIdCamion())) {
            modeloTabla.addRow(new Object[]{
                    r.getId(),
                    r.getIdCamion(),
                    r.getFecha(),
                    r.getKilometraje(),
                    r.getResultado()
            });
        }
    }

    private String describirCamion(Camion camion) {
        String patente = camion.getPatente() == null || camion.getPatente().isBlank()
                ? "Sin patente"
                : camion.getPatente();
        return "ID " + camion.getIdCamion() + " | " + patente + " | " +
                camion.getMarca() + " " + camion.getModelo();
    }

    private void cerrarSesion() {
        dispose();
        Login login = new Login();
        login.setVisible(true);
    }
}
