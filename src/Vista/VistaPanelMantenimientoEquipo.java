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

    private EquipoController equipoController;
    private AdministradorController administradorController;
    private MantenimientoEquipoController mantenimientoController;

    public VistaPanelMantenimientoEquipo() {
        try {
            Connection conn = ConexionBD.getConexion();
            equipoController = new EquipoController(conn);
            administradorController = new AdministradorController(conn);
            mantenimientoController = new MantenimientoEquipoController(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos");
        }

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Mantenimiento de Equipos");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        comboEquipo = new JComboBox<>();
        comboTecnico = new JComboBox<>();
        txtFecha = new JTextField();
        txtFecha.setToolTipText("Formato: yyyy-MM-dd");

        comboTipo = new JComboBox<>(new String[]{"Preventivo", "Correctivo"});
        txtDescripcion = new JTextArea(3, 20);
        comboEstado = new JComboBox<>(new String[]{"Pendiente MantEquipo", "En proceso mantEquipo", "Finalizado"});
        btnRegistrar = new JButton("Registrar Mantenimiento");

        form.add(new JLabel("Equipo de oficina:"));
        form.add(comboEquipo);

        form.add(new JLabel("Tecnico responsable:"));
        form.add(comboTecnico);

        form.add(new JLabel("Fecha (yyyy-MM-dd):"));
        form.add(txtFecha);

        form.add(new JLabel("Tipo de mantenimiento:"));
        form.add(comboTipo);

        form.add(new JLabel("Descripcion:"));
        form.add(new JScrollPane(txtDescripcion));

        form.add(new JLabel("Estado:"));
        form.add(comboEstado);

        form.add(new JLabel(""));
        form.add(btnRegistrar);

        add(titulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);

        cargarEquipos();
        cargarTecnicos();

        btnRegistrar.addActionListener(e -> registrarMantenimiento());
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

            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());

            MantenimientoEquipo mantenimiento = new MantenimientoEquipo();
            mantenimiento.setId_equipo(equipo.getIdEquipo());
            mantenimiento.setId_admin(tecnico.getId_admin());
            mantenimiento.setFecha(fecha);
            mantenimiento.setTipo(comboTipo.getSelectedItem().toString());
            mantenimiento.setDescripcion(txtDescripcion.getText().trim());

            // Registrar mantenimiento
            mantenimientoController.registrar(mantenimiento);

            // Actualizar estado del equipo
            String nuevoEstadoEquipo = comboEstado.getSelectedItem().toString();
            equipoController.actualizarEstado(equipo.getIdEquipo(), nuevoEstadoEquipo);

            equipoController.actualizarEstado(
                    equipo.getIdEquipo(),
                    nuevoEstadoEquipo
            );

            JOptionPane.showMessageDialog(this, "Mantenimiento registrado correctamente");

            txtFecha.setText("");
            txtDescripcion.setText("");
            comboTipo.setSelectedIndex(0);
            comboEstado.setSelectedIndex(0);

        } catch (java.time.format.DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener formato yyyy-MM-dd");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}