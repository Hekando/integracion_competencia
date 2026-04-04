package BaseDatos;

import Modelo.Camion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamionDAO {

    public void insertar(Camion camion) {
        String sql = "INSERT INTO camion (patente, modelo, kilometraje_actual) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, camion.getPatente());
            ps.setString(2, camion.getModelo());
            ps.setInt(3, camion.getKilometrajeActual());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("❌ Error al insertar camión: " + e.getMessage());
        }
    }

    public Camion buscarPorId(int id) {
        String sql = "SELECT * FROM camion WHERE id = ?";
        Camion camion = null;

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                camion = new Camion();
                camion.setId(rs.getInt("id"));
                camion.setPatente(rs.getString("patente"));
                camion.setModelo(rs.getString("modelo"));
                camion.setKilometrajeActual(rs.getInt("kilometraje_actual"));
            }

        } catch (Exception e) {
            System.out.println("❌ Error al buscar camión: " + e.getMessage());
        }

        return camion;
    }

    public List<Camion> listar() {
        List<Camion> lista = new ArrayList<>();
        String sql = "SELECT * FROM camion";

        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Camion c = new Camion();
                c.setId(rs.getInt("id"));
                c.setPatente(rs.getString("patente"));
                c.setModelo(rs.getString("modelo"));
                c.setKilometrajeActual(rs.getInt("kilometraje_actual"));
                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al listar camiones: " + e.getMessage());
        }

        return lista;
    }
