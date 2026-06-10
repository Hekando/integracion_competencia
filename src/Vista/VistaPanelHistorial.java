package Vista;


import Controlador.EquipoController;
import Modelo.Equipo;
import java.util.List;
import BaseDatos.ConexionBD;
import Controlador.MantenimientoEquipoController;
import Modelo.MantenimientoEquipo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;

public class VistaPanelHistorial extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    public VistaPanelHistorial() {

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        JLabel titulo = new JLabel("Historial de Mantenimiento de Equipos");

        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        titulo.setBorder(
                BorderFactory.createEmptyBorder(
                        20,20,20,20
                )
        );
        JLabel lblInfo = new JLabel(
                "Consulte el historial completo de mantenimientos realizados."
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
                        10,15,10,15
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

        JPanel panelMensaje = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        panelMensaje.setBackground(
                new Color(245,247,250)
        );

        panelMensaje.add(lblInfo);

        superior.add(
                panelMensaje,
                BorderLayout.EAST
        );

        add(superior, BorderLayout.NORTH);

        JPanel panelFiltros = new JPanel(new GridBagLayout());

        panelFiltros.setBackground(Color.WHITE);

        panelFiltros.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220)
                        ),
                        BorderFactory.createEmptyBorder(
                                20,20,20,20
                        )
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<Object> comboEquipo =
                new JComboBox<>();

        comboEquipo.addItem("Todos los equipos");



        JComboBox<String> comboTipo =
                new JComboBox<>(new String[]{
                        "Todos",
                        "Preventivo",
                        "Correctivo"
                });

        JTextField txtDesde =
                new JTextField("2026-01-01",10);

        JTextField txtHasta =
                new JTextField("2026-12-31",10);

        JButton btnBuscar =
                new JButton("Buscar");



        JButton btnLimpiar =
                new JButton("Limpiar");

        btnBuscar.setBackground(
                new Color(72,133,237)
        );

        btnBuscar.setForeground(
                Color.WHITE
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFiltros.add(
                new JLabel("Equipo:"),
                gbc
        );

        gbc.gridx = 1;
        panelFiltros.add(
                comboEquipo,
                gbc
        );

        gbc.gridx = 2;
        panelFiltros.add(
                new JLabel("Tipo de Mantenimiento:"),
                gbc
        );

        gbc.gridx = 3;
        panelFiltros.add(
                comboTipo,
                gbc
        );

        gbc.gridx = 4;
        panelFiltros.add(
                new JLabel("Desde:"),
                gbc
        );

        gbc.gridx = 5;
        panelFiltros.add(
                txtDesde,
                gbc
        );

        gbc.gridx = 6;
        panelFiltros.add(
                new JLabel("Hasta:"),
                gbc
        );

        gbc.gridx = 7;
        panelFiltros.add(
                txtHasta,
                gbc
        );

        gbc.gridx = 8;
        panelFiltros.add(
                btnBuscar,
                gbc
        );

        gbc.gridx = 9;
        panelFiltros.add(
                btnLimpiar,
                gbc
        );



        modelo = new DefaultTableModel(new String[]{
                "ID",
                "Equipo",
                "Tipo",
                "Fecha",
                "Técnico",
                "Descripción",
                "Estado"
        },0);

        tabla = new JTable(modelo);

        tabla.setRowHeight(28);

        tabla.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        tabla.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );


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
                panelFiltros,
                BorderLayout.NORTH
        );

        centro.add(
                panelTabla,
                BorderLayout.CENTER
        );

        add(
                centro,
                BorderLayout.CENTER
        );

        try {

            Connection conn = ConexionBD.getConexion();

            MantenimientoEquipoController controller =
                    new MantenimientoEquipoController(conn);

            EquipoController equipoController =
                    new EquipoController(conn);

            List<Equipo> equipos =
                    equipoController.listar();

            for (Equipo equipo : equipos) {
                comboEquipo.addItem(equipo);
            }

            for (MantenimientoEquipo m : controller.listar()) {

                modelo.addRow(new Object[]{
                        m.getId_mantenimiento(),
                        m.getId_equipo(),
                        m.getTipo(),
                        m.getFecha(),
                        m.getId_admin(),
                        m.getDescripcion(),
                        "Registrado"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar historial");
        }

        btnBuscar.addActionListener(e -> {
            Object equipoSeleccionado =
                    comboEquipo.getSelectedItem();
            try {

                modelo.setRowCount(0);

                Connection conn = ConexionBD.getConexion();

                MantenimientoEquipoController controller =
                        new MantenimientoEquipoController(conn);

                String tipoSeleccionado =
                        comboTipo.getSelectedItem().toString();

                for (MantenimientoEquipo m : controller.listar()) {

                    boolean coincideTipo =
                            tipoSeleccionado.equals("Todos")
                                    || m.getTipo().equalsIgnoreCase(tipoSeleccionado);

                    boolean coincideEquipo = true;

                    if (equipoSeleccionado instanceof Equipo equipo) {

                        coincideEquipo =
                                m.getId_equipo() == equipo.getIdEquipo();
                    }
                    if (coincideTipo && coincideEquipo) {

                        modelo.addRow(new Object[]{
                                m.getId_mantenimiento(),
                                m.getId_equipo(),
                                m.getTipo(),
                                m.getFecha(),
                                m.getId_admin(),
                                m.getDescripcion(),
                                "Registrado"
                        });
                    }
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al buscar"
                );

            }

        });

        btnLimpiar.addActionListener(e -> {

            comboEquipo.setSelectedIndex(0); // Todos los equipos

            comboTipo.setSelectedIndex(0); // Todos

            txtDesde.setText("2026-01-01");

            txtHasta.setText("2026-12-31");

            modelo.setRowCount(0);

            try {

                Connection conn = ConexionBD.getConexion();

                MantenimientoEquipoController controller =
                        new MantenimientoEquipoController(conn);

                for (MantenimientoEquipo m : controller.listar()) {

                    modelo.addRow(new Object[]{
                            m.getId_mantenimiento(),
                            m.getId_equipo(),
                            m.getTipo(),
                            m.getFecha(),
                            m.getId_admin(),
                            m.getDescripcion(),
                            "Registrado"
                    });
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al recargar historial"
                );
            }

        });

    }
}