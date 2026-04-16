package Controlador;

import BaseDatos.LoginDAO;

public class LoginController {

    private LoginDAO loginDAO;

    public LoginController() {
        loginDAO = new LoginDAO();
    }

    /**
     * Autentica al usuario consultando la base de datos según el rol.
     */
    public boolean autenticar(String usuario, String password, String rol) {
        return loginDAO.validarCredenciales(usuario, password, rol);
    }
}
