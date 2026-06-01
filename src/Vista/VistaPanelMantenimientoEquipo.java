package Vista;

import javax.swing.*;
import java.awt.*;

public class VistaPanelMantenimientoEquipo extends JPanel {

    private JTextField txtEquipo, txtTipo, txtDescripcion;
    private JButton btnGuardar;

    public VistaPanelMantenimientoEquipo() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Mantenimiento de Equipos");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel form = new JPanel(new GridLayout(4,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));

        txtEquipo = new JTextField();
        txtTipo = new JTextField();
        txtDescripcion = new JTextField();
        btnGuardar = new JButton("Registrar Mantenimiento");

        form.add(new JLabel("Equipo:")); form.add(txtEquipo);
        form.add(new JLabel("Tipo:")); form.add(txtTipo);
        form.add(new JLabel("Descripción:")); form.add(txtDescripcion);
        form.add(new JLabel("")); form.add(btnGuardar);

        btnGuardar.addActionListener(e -> guardar());

        add(titulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    private void guardar() {
        JOptionPane.showMessageDialog(this, "Mantenimiento registrado (simulado)");
    }
}