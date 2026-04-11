package Vista;

import BaseDatos.ConexionBD;


public class Main {
    public static void main(String[] args) {
        // Probar la conexión a la base de datos
        ConexionBD.testConexion();
        new Vista();
    }
}