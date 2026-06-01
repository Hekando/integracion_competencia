package Vista;

import BaseDatos.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VistaPanelEquipo extends JPanel {

    private JTextField txtSerie, txtNombre, txtTipo, txtMarca, txtModelo;
    private JComboBox<String> comboEstado;
    private JButton btnGuardar;

    public VistaPanelEquipo() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Gestión de Equipos");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel form = new JPanel();
        form.setLayout(new GridLayout(7, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        txtSerie = new JTextField();
        txtNombre = new JTextField();
        txtTipo = new JTextField();
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        comboEstado = new JComboBox<>(new String[]{"Activo", "En reparación", "Inactivo"});

        btnGuardar = new JButton("Guardar Equipo");

        form.add(new JLabel("N° Serie:")); form.add(txtSerie);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Tipo:")); form.add(txtTipo);
        form.add(new JLabel("Marca:")); form.add(txtMarca);
        form.add(new JLabel("Modelo:")); form.add(txtModelo);
        form.add(new JLabel("Estado:")); form.add(comboEstado);
        form.add(new JLabel("")); form.add(btnGuardar);

        // BOTONN FUNCIONAL
        btnGuardar.addActionListener(e -> {
            guardarEquipo(
                    txtSerie.getText(),
                    txtNombre.getText(),
                    txtTipo.getText(),
                    txtMarca.getText(),
                    txtModelo.getText(),
                    comboEstado.getSelectedItem().toString()
            );

            // limpiar campos
            txtSerie.setText("");
            txtNombre.setText("");
            txtTipo.setText("");
            txtMarca.setText("");
            txtModelo.setText("");
            comboEstado.setSelectedIndex(0);
        });

        add(titulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    // MÉTODO QUE GUARDA EN MYSQL
    private void guardarEquipo(String serie, String nombre, String tipo, String marca, String modelo, String estado) {
        try {
            Connection conn = ConexionBD.getConexion();

            String sql = "INSERT INTO equipo (numero_serie, nombre, tipo, marca, modelo, estado) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, serie);
            ps.setString(2, nombre);
            ps.setString(3, tipo);
            ps.setString(4, marca);
            ps.setString(5, modelo);
            ps.setString(6, estado);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Equipo guardado en BD ✅");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar ❌");
        }
    }
}