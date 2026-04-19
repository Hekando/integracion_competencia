package BaseDatos;

import Modelo.Camion;
import Modelo.Conductor;
import Modelo.RegistroConductorCamion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    public void insertarConductorConCamion(Conductor conductor, Camion camion) throws Exception {
        String sqlCamion = "INSERT INTO camion (patente, marca, modelo, kilometraje) VALUES (?, ?, ?, ?)";
        String sqlConductor = "INSERT INTO conductor (nombre, licencia, id_camion, usuario, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);

            try (PreparedStatement psCamion = con.prepareStatement(sqlCamion, Statement.RETURN_GENERATED_KEYS)) {
                psCamion.setString(1, camion.getPatente());
                psCamion.setString(2, camion.getMarca());
                psCamion.setString(3, camion.getModelo());
                psCamion.setInt(4, camion.getKilometraje());
                psCamion.executeUpdate();

                int idCamion;
                try (ResultSet generatedKeys = psCamion.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        throw new IllegalStateException("No se pudo obtener el id del camion");
                    }
                    idCamion = generatedKeys.getInt(1);
                }

                try (PreparedStatement psConductor = con.prepareStatement(sqlConductor)) {
                    psConductor.setString(1, conductor.getNombre());
                    psConductor.setString(2, conductor.getLicencia());
                    psConductor.setInt(3, idCamion);
                    psConductor.setString(4, conductor.getUsuario());
                    psConductor.setString(5, conductor.getPassword());
                    psConductor.executeUpdate();
                }
            } catch (Exception e) {
                con.rollback();
                throw e;
            }

            con.commit();
        }
    }

    public List<RegistroConductorCamion> listarConCamion() {
        List<RegistroConductorCamion> lista = new ArrayList<>();
        String sql = "SELECT c.id_camion, c.patente, c.marca, c.modelo, c.kilometraje, " +
                "co.nombre, co.licencia, co.usuario " +
                "FROM camion c " +
                "INNER JOIN conductor co ON co.id_camion = c.id_camion " +
                "ORDER BY c.id_camion DESC";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroConductorCamion registro = new RegistroConductorCamion();
                registro.setIdCamion(rs.getInt("id_camion"));
                registro.setPatente(rs.getString("patente"));
                registro.setMarca(rs.getString("marca"));
                registro.setModelo(rs.getString("modelo"));
                registro.setKilometraje(rs.getInt("kilometraje"));
                registro.setNombreConductor(rs.getString("nombre"));
                registro.setLicencia(rs.getString("licencia"));
                registro.setUsuario(rs.getString("usuario"));
                lista.add(registro);
            }
        } catch (Exception e) {
            System.out.println("Error al listar conductores y camiones: " + e.getMessage());
        }

        return lista;
    }

    public void eliminarPorIdCamion(int idCamion) throws Exception {
        String sqlConductor = "DELETE FROM conductor WHERE id_camion = ?";
        String sqlCamion = "DELETE FROM camion WHERE id_camion = ?";

        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);

            try (PreparedStatement psConductor = con.prepareStatement(sqlConductor);
                 PreparedStatement psCamion = con.prepareStatement(sqlCamion)) {

                psConductor.setInt(1, idCamion);
                psConductor.executeUpdate();

                psCamion.setInt(1, idCamion);
                psCamion.executeUpdate();
            } catch (Exception e) {
                con.rollback();
                throw e;
            }

            con.commit();
        }
    }
}
