package BaseDatos;

import Modelo.RegistroKilometraje;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroKilometrajeDAO {

    private Connection conexion;

    public RegistroKilometrajeDAO() {
        try {
            conexion = ConexionBD.getConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // INSERTAR
    public void insertar(RegistroKilometraje r) {
        String sql = "INSERT INTO registro_kilometraje (id_camion, fecha, kilometraje, resultado) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, r.getIdCamion());
            stmt.setDate(2, Date.valueOf(r.getFecha()));
            stmt.setInt(3, r.getKilometraje());
            stmt.setString(4, r.getResultado());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // LISTAR
    public List<RegistroKilometraje> listarTodos() {
        List<RegistroKilometraje> lista = new ArrayList<>();

        String sql = "SELECT * FROM registro_kilometraje ORDER BY fecha DESC";

        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RegistroKilometraje r = new RegistroKilometraje();

                r.setId(rs.getInt("id"));
                r.setIdCamion(rs.getInt("id_camion"));
                r.setFecha(rs.getDate("fecha").toLocalDate());
                r.setKilometraje(rs.getInt("kilometraje"));
                r.setResultado(rs.getString("resultado"));

                lista.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ELIMINAR
    public void eliminar(int id) {
        String sql = "DELETE FROM registro_kilometraje WHERE id = ?";

        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}