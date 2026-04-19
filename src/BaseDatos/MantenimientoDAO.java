package BaseDatos;

import Modelo.Mantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {

    public void insertar(Mantenimiento mantenimiento) {
        String sql = "INSERT INTO mantenimiento (id_camion, fecha, kilometraje, tipo) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mantenimiento.getIdCamion());

            if (mantenimiento.getFecha() != null) {
                ps.setDate(2, Date.valueOf(mantenimiento.getFecha()));
            } else {
                ps.setDate(2, new Date(System.currentTimeMillis()));
            }

            ps.setInt(3, mantenimiento.getKilometraje());
            ps.setString(4, mantenimiento.getTipo());

            ps.executeUpdate();

            System.out.println("✅ Mantenimiento insertado correctamente");

        } catch (Exception e) {
            System.out.println("❌ Error al insertar mantenimiento: " + e.getMessage());
        }
    }

    public List<Mantenimiento> listarTodos() {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimiento";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Mantenimiento m = new Mantenimiento();
                m.setId(rs.getInt("id_mantenimiento"));
                m.setIdCamion(rs.getInt("id_camion"));
                m.setFecha(rs.getDate("fecha").toLocalDate());
                m.setKilometraje(rs.getInt("kilometraje"));
                m.setTipo(rs.getString("tipo"));
                lista.add(m);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al listar mantenimientos: " + e.getMessage());
        }

        return lista;
    }

    public List<Mantenimiento> listarPorCamion(int idCamion) {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimiento WHERE id_camion = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCamion);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mantenimiento m = new Mantenimiento();
                m.setId(rs.getInt("id_mantenimiento"));
                m.setIdCamion(rs.getInt("id_camion"));
                m.setFecha(rs.getDate("fecha").toLocalDate());
                m.setKilometraje(rs.getInt("kilometraje"));
                m.setTipo(rs.getString("tipo"));
                lista.add(m);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al listar mantenimientos por camión: " + e.getMessage());
        }

        return lista;
    }

    public void actualizar(Mantenimiento mantenimiento) {
        String sql = "UPDATE mantenimiento SET id_camion = ?, fecha = ?, kilometraje = ?, tipo = ? WHERE id_mantenimiento = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mantenimiento.getIdCamion());
            ps.setDate(2, Date.valueOf(mantenimiento.getFecha()));
            ps.setInt(3, mantenimiento.getKilometraje());
            ps.setString(4, mantenimiento.getTipo());
            ps.setInt(5, mantenimiento.getId());

            ps.executeUpdate();

            System.out.println("✅ Mantenimiento actualizado");

        } catch (Exception e) {
            System.out.println("❌ Error al actualizar mantenimiento: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM mantenimiento WHERE id_mantenimiento = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("✅ Mantenimiento eliminado");

        } catch (Exception e) {
            System.out.println("❌ Error al eliminar mantenimiento: " + e.getMessage());
        }
    }

    public int obtenerUltimoKilometraje(int idCamion) {
        String sql = "SELECT MAX(kilometraje) AS ultimo FROM mantenimiento WHERE id_camion = ?";
        int ultimo = 0;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCamion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ultimo = rs.getInt("ultimo");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al obtener último mantenimiento: " + e.getMessage());
        }

        return ultimo;
    }

    public void registrarMantenimiento(int idCamion, int kilometraje) {
        Mantenimiento m = new Mantenimiento();
        m.setIdCamion(idCamion);
        m.setFecha(java.time.LocalDate.now());
        m.setKilometraje(kilometraje);
        m.setTipo("Preventivo");

        insertar(m);
    }
}