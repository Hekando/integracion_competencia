package BaseDatos;

import Modelo.Alerta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    public void insertar(Alerta alerta) {
        String sql = "INSERT INTO alerta (id_camion, kilometraje, fecha, mensaje) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alerta.getIdCamion());
            ps.setInt(2, alerta.getKilometraje());
            ps.setDate(3, Date.valueOf(alerta.getFecha()));
            ps.setString(4, alerta.getMensaje());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("❌ Error al insertar alerta: " + e.getMessage());
        }
    }

    public boolean existeAlertaActiva(int idCamion) {
        String sql = "SELECT * FROM alerta WHERE id_camion = ? AND mensaje = 'Pendiente'";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCamion);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("❌ Error al verificar alerta: " + e.getMessage());
            return false;
        }
    }

    public List<Alerta> listar() {
        List<Alerta> lista = new ArrayList<>();
        String sql = "SELECT * FROM alerta";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Alerta a = new Alerta();
                a.setId(rs.getInt("id"));
                a.setIdCamion(rs.getInt("id_camion"));
                a.setKilometraje(rs.getInt("kilometraje"));
                a.setFecha(rs.getDate("fecha").toLocalDate());
                a.setMensaje(rs.getString("mensaje"));
                lista.add(a);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al listar alertas: " + e.getMessage());
        }

        return lista;
    }

    public void actualizarEstado(int idAlerta, String estado) {
        String sql = "UPDATE alerta SET estado = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idAlerta);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("❌ Error al actualizar alerta: " + e.getMessage());
        }
    }
}