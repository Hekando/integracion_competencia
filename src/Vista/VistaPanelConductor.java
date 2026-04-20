package Vista;

import Controlador.ConductorController;
import Modelo.Camion;
import Modelo.Conductor;
import Modelo.RegistroConductorCamion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Panel encargado de registrar y visualizar conductores junto a sus camiones
public class VistaPanelConductor extends JPanel {

    // Controlador que conecta la vista con la lógica de negocio
    private final ConductorController conductorController = new ConductorController();

    // Colores para validación visual de campos
    private final Color COLOR_BORDE_NORMAL = new Color(210, 215, 225);
    private final Color COLOR_BORDE_ERROR = new Color(220, 70, 70);

    public VistaPanelConductor() {

        // Configuración base del panel
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Colores usados en la interfaz
        Color fondoGeneral = new Color(245, 247, 250);
        Color blanco = Color.WHITE;
        Color borde = new Color(220, 220, 220);
        Color azul = new Color(93, 156, 236);
        Color textoOscuro = new Color(45, 45, 45);

        // Título principal
        JLabel titulo = new JLabel("Registrar Conductor");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(textoOscuro);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // Contenedor principal
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(fondoGeneral);

        // Mensaje de estado para el usuario
        JLabel lblEstado = new JLabel("Complete los datos del camión y del conductor.");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 13));
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(235, 248, 245));
        lblEstado.setForeground(new Color(40, 90, 70));
        lblEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 230, 220)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        lblEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        contenido.add(lblEstado);
        contenido.add(Box.createVerticalStrut(12));

        // Panel del formulario de ingreso
        JPanel tarjetaFormulario = new JPanel(new GridBagLayout());
        tarjetaFormulario.setBackground(blanco);
        tarjetaFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjetaFormulario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de entrada
        JTextField txtPatente = crearCampoFormulario();
        JTextField txtMarca = crearCampoFormulario();
        JTextField txtModelo = crearCampoFormulario();
        JTextField txtKilometraje = crearCampoFormulario();
        JTextField txtNombreConductor = crearCampoFormulario();
        JTextField txtUsuarioConductor = crearCampoFormulario();

        // Placeholders para guiar al usuario
        agregarPlaceholder(txtPatente, "Ej: ABCD12");
        agregarPlaceholder(txtMarca, "Ej: Volvo");
        agregarPlaceholder(txtModelo, "Ej: FH16");
        agregarPlaceholder(txtKilometraje, "Ej: 5000");
        agregarPlaceholder(txtNombreConductor, "Ej: Juan Pérez");
        agregarPlaceholder(txtUsuarioConductor, "Ej: juanp");

        // Validaciones de entrada en tiempo real
        permitirSoloLetras(txtMarca);
        permitirSoloLetras(txtNombreConductor);
        permitirSoloNumeros(txtKilometraje);

        // ComboBox para seleccionar licencia
        JComboBox<String> comboLicencia = new JComboBox<>(new String[]{
                "Seleccione licencia",
                "A1",
                "A2",
                "A3",
                "A4",
                "A5",
                "B",
                "C",
                "D",
                "E"
        });
        comboLicencia.setFont(new Font("Arial", Font.PLAIN, 14));
        comboLicencia.setPreferredSize(new Dimension(170, 34));
        comboLicencia.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));

        // Campo de contraseña
        JPasswordField txtPasswordConductor = new JPasswordField(14);
        txtPasswordConductor.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPasswordConductor.setPreferredSize(new Dimension(170, 34));
        txtPasswordConductor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_NORMAL),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        agregarPlaceholderPassword(txtPasswordConductor, "Ej: 12345");

        // Botón guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(azul);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setContentAreaFilled(true);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));

        // Construcción del formulario con GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        tarjetaFormulario.add(new JLabel("Patente del Camión:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtPatente, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Marca del Camión:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtMarca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        tarjetaFormulario.add(new JLabel("Modelo del Camión:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtModelo, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Kilometraje Inicial:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtKilometraje, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        tarjetaFormulario.add(new JLabel("Nombre del Conductor:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtNombreConductor, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Licencia:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(comboLicencia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        tarjetaFormulario.add(new JLabel("Usuario del Camionero:"), gbc);

        gbc.gridx = 1;
        tarjetaFormulario.add(txtUsuarioConductor, gbc);

        gbc.gridx = 2;
        tarjetaFormulario.add(new JLabel("Contraseña del Camionero:"), gbc);

        gbc.gridx = 3;
        tarjetaFormulario.add(txtPasswordConductor, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        tarjetaFormulario.add(btnGuardar, gbc);

        // Panel de tabla de registros
        JPanel tarjetaTabla = new JPanel(new BorderLayout(10, 10));
        tarjetaTabla.setBackground(blanco);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borde),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Subtítulo de la tabla
        JLabel subtitulo = new JLabel("Camiones y Conductores registrados");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 15));
        subtitulo.setForeground(new Color(60, 60, 60));
        tarjetaTabla.add(subtitulo, BorderLayout.NORTH);

        // Modelo de tabla
        String[] columnas = {"ID Camión", "Patente", "Marca", "Modelo", "Kilometraje", "Nombre", "Licencia", "Usuario"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        // Tabla visual
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(220, 230, 250));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(235, 238, 245));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(650, 220));
        tarjetaTabla.add(scrollTabla, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(azul);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setContentAreaFilled(true);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(azul);
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setOpaque(true);
        btnActualizar.setContentAreaFilled(true);

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        tarjetaTabla.add(panelBotones, BorderLayout.SOUTH);

        // Método para recargar la tabla con datos desde BD
        Runnable cargarTabla = () -> {
            modeloTabla.setRowCount(0);
            for (RegistroConductorCamion registro : conductorController.listarRegistros()) {
                modeloTabla.addRow(new Object[]{
                        registro.getIdCamion(),
                        registro.getPatente(),
                        registro.getMarca(),
                        registro.getModelo(),
                        registro.getKilometraje(),
                        registro.getNombreConductor(),
                        registro.getLicencia(),
                        registro.getUsuario()
                });
            }
        };

        // Ejecuta la carga inicial de la tabla con datos desde la BD
        cargarTabla.run();

        // Listener para detectar cuando el usuario selecciona una fila de la tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();

            // Si hay una fila seleccionada, se cargan los datos en el formulario
            if (fila != -1) {
                txtPatente.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtMarca.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtModelo.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtKilometraje.setText(modeloTabla.getValueAt(fila, 4).toString());
                txtNombreConductor.setText(modeloTabla.getValueAt(fila, 5).toString());
                comboLicencia.setSelectedItem(modeloTabla.getValueAt(fila, 6).toString());
                txtUsuarioConductor.setText(modeloTabla.getValueAt(fila, 7).toString());
            }
        });

        // Acción del botón Guardar (registro nuevo)
        btnGuardar.addActionListener(e -> {

            // Limpia bordes de error antes de validar
            limpiarErrores(txtPatente, txtMarca, txtModelo, txtKilometraje, txtNombreConductor,
                    txtUsuarioConductor, txtPasswordConductor, comboLicencia);

            // Obtención de datos desde el formulario
            String patente = txtPatente.getText().trim();
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            String kmTexto = txtKilometraje.getText().trim();
            String nombreConductor = txtNombreConductor.getText().trim();
            String licencia = comboLicencia.getSelectedItem().toString();
            String usuarioConductor = txtUsuarioConductor.getText().trim();
            String passwordConductor = new String(txtPasswordConductor.getPassword()).trim();

            boolean valido = true;

            // Validaciones de cada campo
            if (patente.isEmpty() || patente.equals("Ej: ABCD12")) {
                marcarError(txtPatente);
                valido = false;
            }

            if (marca.isEmpty() || marca.equals("Ej: Volvo") || !soloLetras(marca)) {
                marcarError(txtMarca);
                valido = false;
            }

            if (modelo.isEmpty() || modelo.equals("Ej: FH16")) {
                marcarError(txtModelo);
                valido = false;
            }

            if (kmTexto.isEmpty() || kmTexto.equals("Ej: 5000") || !soloNumeros(kmTexto)) {
                marcarError(txtKilometraje);
                valido = false;
            }

            if (nombreConductor.isEmpty() || nombreConductor.equals("Ej: Juan Pérez") || !soloLetrasConEspacios(nombreConductor)) {
                marcarError(txtNombreConductor);
                valido = false;
            }

            if (licencia.equals("Seleccione licencia")) {
                marcarError(comboLicencia);
                valido = false;
            }

            if (usuarioConductor.isEmpty() || usuarioConductor.equals("Ej: juanp")) {
                marcarError(txtUsuarioConductor);
                valido = false;
            }

            if (passwordConductor.isEmpty() || passwordConductor.equals("Ej: 12345")) {
                marcarError(txtPasswordConductor);
                valido = false;
            }

            // Si hay errores, se muestra mensaje y se detiene
            if (!valido) {
                mostrarError(lblEstado, "Revise los campos obligatorios o inválidos.");
                return;
            }

            try {
                // Creación del objeto Camion con los datos ingresados
                Camion camion = new Camion();
                camion.setPatente(patente);
                camion.setMarca(marca);
                camion.setModelo(modelo);
                camion.setKilometraje(Integer.parseInt(kmTexto));

                // Creación del objeto Conductor
                Conductor conductor = new Conductor();
                conductor.setNombre(nombreConductor);
                conductor.setLicencia(licencia);
                conductor.setUsuario(usuarioConductor);
                conductor.setPassword(passwordConductor);

                // Llamada al controlador para guardar en BD
                conductorController.registrarConductorConCamion(conductor, camion);

                // Mensaje de éxito
                mostrarExito(lblEstado, "Registro guardado exitosamente.");

                // Limpieza de campos
                txtPatente.setText("");
                txtMarca.setText("");
                txtModelo.setText("");
                txtKilometraje.setText("");
                txtNombreConductor.setText("");
                txtUsuarioConductor.setText("");
                txtPasswordConductor.setText("");
                comboLicencia.setSelectedIndex(0);

                // Restauración de placeholders
                agregarPlaceholder(txtPatente, "Ej: ABCD12");
                agregarPlaceholder(txtMarca, "Ej: Volvo");
                agregarPlaceholder(txtModelo, "Ej: FH16");
                agregarPlaceholder(txtKilometraje, "Ej: 5000");
                agregarPlaceholder(txtNombreConductor, "Ej: Juan Pérez");
                agregarPlaceholder(txtUsuarioConductor, "Ej: juanp");
                agregarPlaceholderPassword(txtPasswordConductor, "Ej: 12345");

                // Recarga la tabla con nuevos datos
                cargarTabla.run();

            } catch (Exception ex) {
                // Manejo de errores en el registro
                mostrarError(lblEstado, "Error al guardar: " + ex.getMessage());
            }
        });

        // Acción del botón Eliminar
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            // Verifica que haya una fila seleccionada
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un registro");
                return;
            }

            try {
                // Obtiene el ID del camión seleccionado
                int idCamion = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

                // Llama al controlador para eliminar
                conductorController.eliminarPorIdCamion(idCamion);

                // Mensaje de éxito
                mostrarExito(lblEstado, "Registro eliminado correctamente.");

                // Recarga la tabla
                cargarTabla.run();

            } catch (Exception ex) {
                mostrarError(lblEstado, "Error al eliminar: " + ex.getMessage());
            }
        });

        // Acción del botón Actualizar
        btnActualizar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            // Verifica que haya una fila seleccionada
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un registro");
                return;
            }

            try {
                int idCamion = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

                // Obtiene datos actualizados del formulario
                String patente = txtPatente.getText().trim();
                String marca = txtMarca.getText().trim();
                String modelo = txtModelo.getText().trim();
                String kmTexto = txtKilometraje.getText().trim();

                // Validación de kilometraje
                if (kmTexto.equals("Ej: 5000") || kmTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un kilometraje válido");
                    return;
                }

                int km = Integer.parseInt(kmTexto);
                String nombre = txtNombreConductor.getText().trim();
                String licencia = comboLicencia.getSelectedItem().toString();
                String usuario = txtUsuarioConductor.getText().trim();
                String password = new String(txtPasswordConductor.getPassword()).trim();

                // Creación de objetos actualizados
                Camion camion = new Camion();
                camion.setIdCamion(idCamion);
                camion.setPatente(patente);
                camion.setMarca(marca);
                camion.setModelo(modelo);
                camion.setKilometraje(km);

                Conductor conductor = new Conductor();
                conductor.setNombre(nombre);
                conductor.setLicencia(licencia);
                conductor.setUsuario(usuario);
                conductor.setPassword(password);

                // Llama al controlador para actualizar
                conductorController.actualizarConductorCamion(conductor, camion);

                // Mensaje de éxito
                mostrarExito(lblEstado, "Registro actualizado correctamente.");

                // Recarga tabla
                cargarTabla.run();

            } catch (Exception ex) {
                mostrarError(lblEstado, "Error al actualizar: " + ex.getMessage());
            }
        });

        // Agrega componentes al panel principal
        contenido.add(tarjetaFormulario);
        contenido.add(Box.createVerticalStrut(15));
        contenido.add(tarjetaTabla);

        // Scroll del contenido
        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);

        // Se agrega el panel al contenedor principal
        add(scroll, BorderLayout.CENTER);
    }

    // Método para crear campos de texto con estilo uniforme
    private JTextField crearCampoFormulario() {
        JTextField campo = new JTextField(14);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(170, 34));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_NORMAL),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return campo;
    }

    // Marca un campo con borde rojo para indicar error de validación
    private void marcarError(JComponent campo) {
        campo.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_ERROR, 2));
    }

    // Restaura todos los bordes a su estado normal (sin errores)
    private void limpiarErrores(JTextField txtPatente, JTextField txtMarca, JTextField txtModelo,
                                JTextField txtKilometraje, JTextField txtNombreConductor,
                                JTextField txtUsuarioConductor, JPasswordField txtPasswordConductor,
                                JComboBox<String> comboLicencia) {

        txtPatente.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        txtMarca.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        txtModelo.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        txtKilometraje.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        txtNombreConductor.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        txtUsuarioConductor.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        txtPasswordConductor.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
        comboLicencia.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));
    }

    // Muestra un mensaje de error en la interfaz (color rojo)
    private void mostrarError(JLabel lblEstado, String mensaje) {
        lblEstado.setText(mensaje);
        lblEstado.setBackground(new Color(252, 235, 235));
        lblEstado.setForeground(new Color(150, 50, 50));
    }

    // Muestra un mensaje de éxito (color verde)
    private void mostrarExito(JLabel lblEstado, String mensaje) {
        lblEstado.setText(mensaje);
        lblEstado.setBackground(new Color(235, 248, 245));
        lblEstado.setForeground(new Color(40, 90, 70));
    }

    // Valida que el texto contenga solo números
    private boolean soloNumeros(String texto) {
        return texto.matches("\\d+");
    }

    // Valida que el texto contenga solo letras (incluye tildes y ñ)
    private boolean soloLetras(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    // Valida letras y espacios (para nombres completos)
    private boolean soloLetrasConEspacios(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    // Restringe la entrada del teclado a solo letras en tiempo real
    private void permitirSoloLetras(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Si no es letra, espacio o letra con tilde → bloquea el ingreso
                if (!Character.isLetter(c)
                        && !Character.isWhitespace(c)
                        && "áéíóúÁÉÍÓÚñÑ".indexOf(c) == -1) {
                    e.consume();
                }
            }
        });
    }

    // Restringe la entrada del teclado a solo números
    private void permitirSoloNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Si no es número → bloquea el ingreso
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
    }

    // Agrega texto de ayuda (placeholder) en campos de texto normales
    private void agregarPlaceholder(JTextField campo, String texto) {
        campo.setText(texto);
        campo.setForeground(Color.GRAY);

        campo.addFocusListener(new FocusAdapter() {

            // Cuando el usuario hace clic, limpia el placeholder
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(texto)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            // Si el campo queda vacío, vuelve a mostrar el placeholder
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(texto);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Placeholder especial para contraseña (oculta caracteres reales)
    private void agregarPlaceholderPassword(JPasswordField campo, String texto) {

        // Muestra texto sin ocultar inicialmente
        campo.setEchoChar((char) 0);
        campo.setText(texto);
        campo.setForeground(Color.GRAY);

        campo.addFocusListener(new FocusAdapter() {

            // Al escribir, oculta caracteres con •
            @Override
            public void focusGained(FocusEvent e) {
                String valor = new String(campo.getPassword());
                if (valor.equals(texto)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                    campo.setEchoChar('•');
                }
            }

            // Si queda vacío, vuelve a mostrar el placeholder visible
            @Override
            public void focusLost(FocusEvent e) {
                String valor = new String(campo.getPassword());
                if (valor.trim().isEmpty()) {
                    campo.setEchoChar((char) 0);
                    campo.setText(texto);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }
}