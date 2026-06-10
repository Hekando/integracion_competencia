package Vista;

import BaseDatos.ConexionBD;
import Controlador.EquipoController;
import Controlador.EquipoSoftwareController;
import Controlador.SoftwareController;
import Modelo.ActualizacionSoftware;
import Modelo.Equipo;
import Modelo.EquipoSoftware;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class VistaPanelActualizacionSoftware extends JPanel {

    private JComboBox<Equipo> comboEquipos;
    private JComboBox<EquipoSoftware> comboSoftwareInstalado;
    private JTextField txtVersionActual;
    private JTextField txtVersionNueva;
    private JComboBox<String> comboEstado;
    private JButton btnRegistrar;

    private Connection conn;
    private EquipoController equipoController;
    private EquipoSoftwareController equipoSoftwareController;
    private SoftwareController softwareController;

    public VistaPanelActualizacionSoftware() {
        try {
            conn = ConexionBD.getConexion();
            equipoController = new EquipoController(conn);
            equipoSoftwareController = new EquipoSoftwareController(conn);
            softwareController = new SoftwareController(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
        }

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Actualización de Software");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel form = new JPanel(new GridBagLayout());

        form.setBackground(Color.WHITE);

        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(230,230,230)
                        ),
                        BorderFactory.createEmptyBorder(
                                25,25,25,25
                        )
                )
        );

        comboEquipos = new JComboBox<>();
        comboSoftwareInstalado = new JComboBox<>();
        txtVersionActual = new JTextField();
        txtVersionActual.setEditable(false);

        txtVersionNueva = new JTextField();

        Dimension campo = new Dimension(450,40);

        comboEquipos.setPreferredSize(campo);
        comboSoftwareInstalado.setPreferredSize(campo);
        txtVersionActual.setPreferredSize(campo);
        txtVersionNueva.setPreferredSize(campo);

        txtVersionActual.setEditable(false);

        comboEstado = new JComboBox<>(new String[]{
                "Pendiente",
                "En proceso",
                "Completada",
                "Fallida"
        });

        comboEstado.setPreferredSize(campo);

        btnRegistrar = new JButton("Registrar actualización");
        btnRegistrar.setBackground(
                new Color(66,133,244)
        );

        btnRegistrar.setForeground(
                Color.WHITE
        );

        btnRegistrar.setFocusPainted(false);

        btnRegistrar.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        btnRegistrar.setPreferredSize(
                new Dimension(450,45)
        );

        JLabel lblInfo = new JLabel(
                "Complete la información del mantenimiento realizado."
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
        add(
                superior,
                BorderLayout.NORTH
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Equipo:"), gbc);

        gbc.gridx = 1;
        form.add(comboEquipos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(new JLabel("Software instalado:"), gbc);

        gbc.gridx = 1;
        form.add(comboSoftwareInstalado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("Versión actual:"), gbc);

        gbc.gridx = 1;
        form.add(txtVersionActual, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        form.add(new JLabel("Versión nueva:"), gbc);

        gbc.gridx = 1;
        form.add(txtVersionNueva, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        form.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1;
        form.add(comboEstado, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        form.add(btnRegistrar, gbc);

        JPanel centro = new JPanel(
                new BorderLayout()
        );

        centro.setBackground(
                new Color(245,247,250)
        );

        centro.setBorder(
                BorderFactory.createEmptyBorder(
                        20,20,20,20
                )
        );

        centro.add(
                form,
                BorderLayout.NORTH
        );

        add(
                centro,
                BorderLayout.CENTER
        );

        cargarEquipos();

        comboEquipos.addActionListener(e -> cargarSoftwareInstalado());
        comboSoftwareInstalado.addActionListener(e -> mostrarVersionActual());
        btnRegistrar.addActionListener(e -> registrarActualizacion());
    }

    private void cargarEquipos() {
        try {
            comboEquipos.removeAllItems();

            List<Equipo> equipos = equipoController.listar();

            for (Equipo equipo : equipos) {
                comboEquipos.addItem(equipo);
            }

            cargarSoftwareInstalado();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar equipos");
        }
    }

    private void cargarSoftwareInstalado() {
        try {
            comboSoftwareInstalado.removeAllItems();
            txtVersionActual.setText("");

            Equipo equipo = (Equipo) comboEquipos.getSelectedItem();

            if (equipo == null) {
                return;
            }

            List<EquipoSoftware> lista = equipoSoftwareController.listarPorEquipo(equipo.getIdEquipo());

            for (EquipoSoftware es : lista) {
                comboSoftwareInstalado.addItem(es);
            }

            mostrarVersionActual();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar software instalado");
        }
    }

    private void mostrarVersionActual() {
        EquipoSoftware es = (EquipoSoftware) comboSoftwareInstalado.getSelectedItem();

        if (es == null) {
            txtVersionActual.setText("");
            return;
        }

        txtVersionActual.setText(es.getVersionInstalada());
    }

    private void registrarActualizacion() {
        try {
            EquipoSoftware es = (EquipoSoftware) comboSoftwareInstalado.getSelectedItem();
            String versionNueva = txtVersionNueva.getText().trim();
            String estado = comboEstado.getSelectedItem().toString();

            if (es == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un software instalado");
                return;
            }

            if (versionNueva.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la versión nueva");
                return;
            }

            ActualizacionSoftware actualizacion = new ActualizacionSoftware();
            actualizacion.setIdEquipoSoftware(es.getIdEquipoSoftware());
            actualizacion.setIdAdmin(7); // Aquí deberías poner el admin real logueado
            actualizacion.setFecha(LocalDate.now());
            actualizacion.setVersionAnterior(es.getVersionInstalada());
            actualizacion.setVersionNueva(versionNueva);
            actualizacion.setDescripcion("Actualización registrada desde el sistema");
            actualizacion.setEstado(estado);

            softwareController.registrarActualizacion(actualizacion);

            equipoSoftwareController.actualizarVersion(
                    es.getIdEquipoSoftware(),
                    versionNueva
            );

            JOptionPane.showMessageDialog(this, "Actualización registrada");

            txtVersionNueva.setText("");
            cargarSoftwareInstalado();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}