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
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel panelSuperior = new JPanel(new GridLayout(4, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        comboEquipos = new JComboBox<>();
        comboSoftware = new JComboBox<>();
        txtVersion = new JTextField();

        btnAgregar = new JButton("Agregar software al equipo");
        btnNuevoSoftware = new JButton("Nuevo software");

        panelSuperior.add(new JLabel("Equipo:"));
        panelSuperior.add(comboEquipos);

        panelSuperior.add(new JLabel("Software:"));
        panelSuperior.add(comboSoftware);

        panelSuperior.add(new JLabel("Versión instalada:"));
        panelSuperior.add(txtVersion);

        panelSuperior.add(btnNuevoSoftware);
        panelSuperior.add(btnAgregar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Equipo");
        modeloTabla.addColumn("Software");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Versión instalada");

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        add(titulo, BorderLayout.NORTH);
        add(panelSuperior, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

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