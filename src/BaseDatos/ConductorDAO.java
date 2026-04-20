package BaseDatos;

import Modelo.Camion;
import Modelo.Conductor;
import Modelo.RegistroConductorCamion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    // Inserta un conductor junto con su camión en una transacción
    public void insertarConductorConCamion(Conductor conductor, Camion camion) throws Exception {
        String sqlCamion = "INSERT INTO camion (patente, marca, modelo, kilometraje) VALUES (?, ?, ?, ?)";
        String sqlConductor = "INSERT INTO conductor (nombre, licencia, id_camion, usuario, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion()) {
            // Se desactiva el autocommit para manejar la transacción manualmente
            con.setAutoCommit(false);

            try (PreparedStatement psCamion = con.prepareStatement(sqlCamion, Statement.RETURN_GENERATED_KEYS)) {

                // Inserta el camión
                psCamion.setString(1, camion.getPatente());
                psCamion.setString(2, camion.getMarca());
                psCamion.setString(3, camion.getModelo());
                psCamion.setInt(4, camion.getKilometraje());
                psCamion.executeUpdate();

                // Obtiene el ID generado automáticamente del camión
                int idCamion;
                try (ResultSet rs = psCamion.getGeneratedKeys()) {
                    if (!rs.next()) throw new Exception("No se obtuvo ID");
                    idCamion = rs.getInt(1);
                }

                // Inserta el conductor asociado al camión recién creado
                try (PreparedStatement psConductor = con.prepareStatement(sqlConductor)) {
                    psConductor.setString(1, conductor.getNombre());
                    psConductor.setString(2, conductor.getLicencia());
                    psConductor.setInt(3, idCamion);
                    psConductor.setString(4, conductor.getUsuario());
                    psConductor.setString(5, conductor.getPassword());
                    psConductor.executeUpdate();
                }

            } catch (Exception e) {
                // Si ocurre un error, se revierte toda la transacción
                con.rollback();
                throw e;
            }

            // Si todo sale bien, se confirma la transacción
            con.commit();
        }
    }

    // Lista los registros combinados de conductor y camión
    public List<RegistroConductorCamion> listarConCamion() {
        List<RegistroConductorCamion> lista = new ArrayList<>();

        String sql = "SELECT c.id_camion, c.patente, c.marca, c.modelo, c.kilometraje, " +
                "co.nombre, co.licencia, co.usuario " +
                "FROM camion c INNER JOIN conductor co ON co.id_camion = c.id_camion " +
                "ORDER BY c.id_camion DESC";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorre los resultados y los convierte en objetos
            while (rs.next()) {
                RegistroConductorCamion r = new RegistroConductorCamion();
                r.setIdCamion(rs.getInt("id_camion"));
                r.setPatente(rs.getString("patente"));
                r.setMarca(rs.getString("marca"));
                r.setModelo(rs.getString("modelo"));
                r.setKilometraje(rs.getInt("kilometraje"));
                r.setNombreConductor(rs.getString("nombre"));
                r.setLicencia(rs.getString("licencia"));
                r.setUsuario(rs.getString("usuario"));
                lista.add(r);
            }

        } catch (Exception e) {
            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }

    // Elimina un conductor y su camión asociado usando transacción
    public void eliminarPorIdCamion(int idCamion) throws Exception {
        String sql1 = "DELETE FROM conductor WHERE id_camion=?";
        String sql2 = "DELETE FROM camion WHERE id_camion=?";

        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);

            try (
                    PreparedStatement ps1 = con.prepareStatement(sql1);
                    PreparedStatement ps2 = con.prepareStatement(sql2)
            ) {
                // Primero elimina el conductor (por clave foránea)
                ps1.setInt(1, idCamion);
                ps1.executeUpdate();

                // Luego elimina el camión
                ps2.setInt(1, idCamion);
                ps2.executeUpdate();

            } catch (Exception e) {
                // Revierte cambios si ocurre error
                con.rollback();
                throw e;
            }

            // Confirma la transacción
            con.commit();
        }
    }

    // Actualiza los datos del conductor y del camión en una sola transacción
    public void actualizarConductorCamion(Conductor conductor, Camion camion) throws Exception {

        String sqlCamion = "UPDATE camion SET patente=?, marca=?, modelo=?, kilometraje=? WHERE id_camion=?";
        String sqlConductor = "UPDATE conductor SET nombre=?, licencia=?, usuario=?, password=? WHERE id_camion=?";

        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);

            try (
                    PreparedStatement psCamion = con.prepareStatement(sqlCamion);
                    PreparedStatement psConductor = con.prepareStatement(sqlConductor)
            ) {

                // Actualiza los datos del camión
                psCamion.setString(1, camion.getPatente());
                psCamion.setString(2, camion.getMarca());
                psCamion.setString(3, camion.getModelo());
                psCamion.setInt(4, camion.getKilometraje());
                psCamion.setInt(5, camion.getIdCamion());
                psCamion.executeUpdate();

                // Actualiza los datos del conductor
                psConductor.setString(1, conductor.getNombre());
                psConductor.setString(2, conductor.getLicencia());
                psConductor.setString(3, conductor.getUsuario());
                psConductor.setString(4, conductor.getPassword());
                psConductor.setInt(5, camion.getIdCamion());
                psConductor.executeUpdate();

            } catch (Exception e) {
                // Revierte cambios si falla alguna actualización
                con.rollback();
                throw e;
            }

            // Confirma la transacción
            con.commit();
        }
    }
}