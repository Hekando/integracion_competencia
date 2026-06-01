package Vista;

import javax.swing.*;
import java.awt.*;

public class VistaPanelSoftware extends JPanel {

    private JTextField txtNombre, txtDescripcion;
    private JButton btnGuardar;

    public VistaPanelSoftware() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Gestión de Software");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel form = new JPanel(new GridLayout(3,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        txtNombre = new JTextField();
        txtDescripcion = new JTextField();
        btnGuardar = new JButton("Registrar Software");

        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Descripción:")); form.add(txtDescripcion);
        form.add(new JLabel("")); form.add(btnGuardar);

        btnGuardar.addActionListener(e -> guardar());

        add(titulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    private void guardar() {
        JOptionPane.showMessageDialog(this, "Software registrado (simulado)");
    }
}