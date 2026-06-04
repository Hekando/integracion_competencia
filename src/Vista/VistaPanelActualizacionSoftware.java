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

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        comboEquipos = new JComboBox<>();
        comboSoftwareInstalado = new JComboBox<>();
        txtVersionActual = new JTextField();
        txtVersionActual.setEditable(false);

        txtVersionNueva = new JTextField();

        comboEstado = new JComboBox<>(new String[]{
                "Pendiente",
                "En proceso",
                "Completada",
                "Fallida"
        });

        btnRegistrar = new JButton("Registrar actualización");

        form.add(new JLabel("Equipo:"));
        form.add(comboEquipos);

        form.add(new JLabel("Software instalado:"));
        form.add(comboSoftwareInstalado);

        form.add(new JLabel("Versión actual:"));
        form.add(txtVersionActual);

        form.add(new JLabel("Versión nueva:"));
        form.add(txtVersionNueva);

        form.add(new JLabel("Estado:"));
        form.add(comboEstado);

        form.add(new JLabel(""));
        form.add(btnRegistrar);

        add(titulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);

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