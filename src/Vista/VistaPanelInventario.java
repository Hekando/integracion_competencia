package Vista;

import BaseDatos.ConexionBD;
import Controlador.InventarioController;
import Modelo.InventarioPieza;
import java.sql.Connection;

import javax.swing.*;
import java.awt.*;

public class VistaPanelInventario extends JPanel {

    private JTextField txtNombre, txtCantidad, txtDescripcion;
    private JButton btnGuardar;

    public VistaPanelInventario() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Inventario de Piezas");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel form = new JPanel(new GridLayout(4,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        txtNombre = new JTextField();
        txtCantidad = new JTextField();
        txtDescripcion = new JTextField();
        btnGuardar = new JButton("Guardar Pieza");

        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Cantidad:")); form.add(txtCantidad);
        form.add(new JLabel("Descripción:")); form.add(txtDescripcion);
        form.add(new JLabel("")); form.add(btnGuardar);

        btnGuardar.addActionListener(e -> guardar());

        add(titulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            InventarioPieza pieza = new InventarioPieza();
            pieza.setNombre(nombre);
            pieza.setCantidad(cantidad);
            pieza.setDescripcion(descripcion);

            Connection conn = ConexionBD.getConexion();
            InventarioController controller = new InventarioController(conn);

            controller.registrar(pieza);

            JOptionPane.showMessageDialog(this, "Pieza guardada correctamente");

            txtNombre.setText("");
            txtCantidad.setText("");
            txtDescripcion.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la pieza: " + e.getMessage());
        }
    }
}