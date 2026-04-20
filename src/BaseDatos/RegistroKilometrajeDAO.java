package BaseDatos;

import Modelo.RegistroKilometraje;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroKilometrajeDAO {

    private final Connection conexion;

    // Constructor que inicializa la conexión a la base de datos
    public RegistroKilometrajeDAO() {
        try {
            conexion = ConexionBD.getConexion();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al conectar con la base de datos", e);
        }
    }

    // Inserta un nuevo registro de kilometraje en la base de datos
    public void insertar(RegistroKilometraje r) {
        String sql = "INSERT INTO registro_kilometraje (id_camion, fecha, kilometraje, resultado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, r.getIdCamion());
            stmt.setDate(2, Date.valueOf(r.getFecha()));
            stmt.setInt(3, r.getKilometraje());
            stmt.setString(4, r.getResultado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lista todos los registros ordenados por fecha descendente
    public List<RegistroKilometraje> listarTodos() {
        return listar("SELECT * FROM registro_kilometraje ORDER BY fecha DESC, id DESC", null);
    }

    // Lista los registros de un camión específico
    public List<RegistroKilometraje> listarPorCamion(int idCamion) {
        return listar("SELECT * FROM registro_kilometraje WHERE id_camion = ? ORDER BY fecha DESC, id DESC", idCamion);
    }

    // Elimina un registro por su ID
    public void eliminar(int id) {
        String sql = "DELETE FROM registro_kilometraje WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método genérico para listar registros, con o sin filtro por camión
    private List<RegistroKilometraje> listar(String sql, Integer idCamion) {
        List<RegistroKilometraje> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Si se recibe un idCamion, se agrega como parámetro a la consulta
            if (idCamion != null) {
                stmt.setInt(1, idCamion);
            }

            try (ResultSet rs = stmt.executeQuery()) {

                // Se recorren los resultados y se mapean a objetos
                while (rs.next()) {
                    RegistroKilometraje r = new RegistroKilometraje();
                    r.setId(rs.getInt("id"));
                    r.setIdCamion(rs.getInt("id_camion"));
                    r.setFecha(rs.getDate("fecha").toLocalDate());
                    r.setKilometraje(rs.getInt("kilometraje"));
                    r.setResultado(rs.getString("resultado"));
                    lista.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}