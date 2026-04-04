package Controlador;

import BaseDatos.*;
import Modelo.*;

public class CamionController {

    CamionDAO dao = new CamionDAO();
    AlertaDAO alertaDAO = new AlertaDAO();

    public void agregarCamion(String patente, String marca, String modelo, int anio, int km) {

        Camion c = new Camion();
        c.setPatente(patente);
        c.setMarca(marca);
        c.setModelo(modelo);
        c.setAnio(anio);
        c.setKilometraje(km);

        dao.insertar(c);
    }

    public Camion buscarPorPatente(String patente) {
        return dao.buscarPorPatente(patente);
    }

    public void actualizarKmPorPatente(String patente, int nuevoKm) {

        Camion c = dao.buscarPorPatente(patente);

        if (c == null) {
            System.out.println("Camión no existe");
            return;
        }

        int actual = c.getKilometraje();

        if (nuevoKm <= actual) {
            System.out.println("KM debe ser mayor");
            return;
        }

        int diferencia = nuevoKm - actual;

        dao.actualizarKilometraje(c.getId(), nuevoKm);

        if (diferencia >= 10000) {
            Alerta a = new Alerta();
            a.setCamionId(c.getId());
            a.setMensaje("Camión " + patente + " requiere mantenimiento");

            alertaDAO.insertar(a);
        }
    }
}
