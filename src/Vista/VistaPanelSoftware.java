package Vista;

import BaseDatos.ConexionBD;
import Controlador.EquipoController;
import Controlador.EquipoSoftwareController;
import Controlador.SoftwareController;
import Modelo.Equipo;
import Modelo.EquipoSoftware;
import Modelo.Software;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class VistaPanelSoftware extends JPanel {

    private JComboBox<Equipo> comboEquipos;
    private JComboBox<Software> comboSoftware;
    private JTextField txtVersion;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JButton btnAgregar;
    private JButton btnNuevoSoftware;

    private Connection conn;
    private EquipoController equipoController;
    private SoftwareController softwareController;
    private EquipoSoftwareController equipoSoftwareController;

    public VistaPanelSoftware() {
        try {
            conn = ConexionBD.getConexion();
            equipoController = new EquipoController(conn);
            softwareController = new SoftwareController(conn);
            equipoSoftwareController = new EquipoSoftwareController(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
        }

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Gestión de Software por Equipo");

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 24)
        );

        titulo.setBorder(
                BorderFactory.createEmptyBorder(
                        20,20,20,20
                )
        );

        JLabel lblInfo = new JLabel(
                "Administre el software instalado y sus actualizaciones."
        );

        lblInfo.setOpaque(true);

        lblInfo.setBackground(
                new Color(223,240,216)
        );

        lblInfo.setForeground(
                new Color(60,118,61)
        );

        lblInfo.setBorder(
                BorderFactory.createEmptyBorder(
                        12,20,12,20
                )
        );

        JPanel superior = new JPanel(
                new BorderLayout()
        );

        superior.setBackground(
                new Color(245,247,250)
        );

        superior.add(
                titulo,
                BorderLayout.WEST
        );

        superior.add(
                lblInfo,
                BorderLayout.EAST
        );


        JPanel panelFormulario = new JPanel(new GridBagLayout());

        panelFormulario.setBackground(Color.WHITE);

        panelFormulario.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220)
                        ),
                        BorderFactory.createEmptyBorder(
                                25,25,25,25
                        )
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        comboEquipos = new JComboBox<>();
        comboSoftware = new JComboBox<>();
        txtVersion = new JTextField();

        btnAgregar = new JButton("Agregar software al equipo");
        btnNuevoSoftware = new JButton("Nuevo software");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Equipo:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(comboEquipos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Software:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(comboSoftware, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Versión instalada:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtVersion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(btnNuevoSoftware, gbc);

        gbc.gridx = 1;
        panelFormulario.add(btnAgregar, gbc);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Equipo");
        modeloTabla.addColumn("Software");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Versión instalada");

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelTabla = new JPanel(
                new BorderLayout()
        );

        panelTabla.setBackground(Color.WHITE);

        panelTabla.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220)
                        ),
                        BorderFactory.createEmptyBorder(
                                15,15,15,15
                        )
                )
        );

        panelTabla.add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        JPanel centro = new JPanel(
                new BorderLayout()
        );

        centro.setBackground(
                new Color(245,247,250)
        );

        centro.add(
                panelFormulario,
                BorderLayout.NORTH
        );

        centro.add(
                panelTabla,
                BorderLayout.CENTER
        );

        add(
                superior,
                BorderLayout.NORTH
        );

        add(
                centro,
                BorderLayout.CENTER
        );

        cargarEquipos();
        cargarSoftware();

        comboEquipos.addActionListener(e -> cargarSoftwareInstalado());

        btnAgregar.addActionListener(e -> agregarSoftwareAlEquipo());

        btnNuevoSoftware.addActionListener(e -> registrarNuevoSoftware());
    }

    private void cargarEquipos() {
        try {
            comboEquipos.removeAllItems();

            List<Equipo> equipos = equipoController.listar();

            for (Equipo e : equipos) {
                comboEquipos.addItem(e);
            }

            cargarSoftwareInstalado();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar equipos");
        }
    }

    private void cargarSoftware() {
        try {
            comboSoftware.removeAllItems();

            List<Software> softwares = softwareController.listarSoftware();

            for (Software s : softwares) {
                comboSoftware.addItem(s);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar software");
        }
    }

    private void cargarSoftwareInstalado() {
        try {
            modeloTabla.setRowCount(0);

            Equipo equipo = (Equipo) comboEquipos.getSelectedItem();

            if (equipo == null) {
                return;
            }

            List<EquipoSoftware> lista = equipoSoftwareController.listarPorEquipo(equipo.getIdEquipo());

            for (EquipoSoftware es : lista) {
                modeloTabla.addRow(new Object[]{
                        es.getNombreEquipo(),
                        es.getNombreSoftware(),
                        es.getTipoSoftware(),
                        es.getVersionInstalada()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar software instalado");
        }
    }

    private void agregarSoftwareAlEquipo() {
        try {
            Equipo equipo = (Equipo) comboEquipos.getSelectedItem();
            Software software = (Software) comboSoftware.getSelectedItem();
            String version = txtVersion.getText().trim();

            if (equipo == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un equipo");
                return;
            }

            if (software == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un software");
                return;
            }

            equipoSoftwareController.instalarSoftware(
                    equipo.getIdEquipo(),
                    software.getIdSoftware(),
                    version
            );

            JOptionPane.showMessageDialog(this, "Software agregado al equipo");

            txtVersion.setText("");
            cargarSoftwareInstalado();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void registrarNuevoSoftware() {
        try {
            JTextField txtNombre = new JTextField();
            JTextField txtTipo = new JTextField();

            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Tipo:"));
            panel.add(txtTipo);

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Nuevo software",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (opcion != JOptionPane.OK_OPTION) {
                return;
            }

            String nombre = txtNombre.getText().trim();
            String tipo = txtTipo.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el nombre del software");
                return;
            }

            if (tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el tipo del software");
                return;
            }

            Software software = new Software();
            software.setNombre(nombre.trim());
            software.setTipo(tipo);

            softwareController.registrarSoftware(software);

            JOptionPane.showMessageDialog(this, "Software registrado");

            cargarSoftware();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}