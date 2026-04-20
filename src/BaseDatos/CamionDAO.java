package BaseDatos;

import Modelo.Camion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CamionDAO {

    private final Connection conexion;

    // Constructor que establece la conexión con la base de datos
    public CamionDAO() {
        try {
            this.conexion = ConexionBD.getConexion();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al conectar con la base de datos", e);
        }
    }

    // Busca un camión por su ID
    public Camion buscarPorId(int idCamion) {
        String sql = "SELECT * FROM camion WHERE id_camion = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCamion);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCamion(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar camion por id");
            e.printStackTrace();
        }

        return null;
    }

    // Busca un camión por su modelo
    public Camion buscarPorModelo(String modelo) {
        String sql = "SELECT * FROM camion WHERE modelo = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, modelo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCamion(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar camion por modelo");
            e.printStackTrace();
        }

        return null;
    }

    // Obtiene el nombre del conductor asociado a un usuario
    public String buscarNombreConductorPorUsuario(String usuario) {
        String sql = "SELECT nombre FROM conductor WHERE usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar nombre del conductor por usuario");
            e.printStackTrace();
        }

        return null;
    }

    // Lista todos los camiones asociados a un conductor específico
    public List<Camion> listarPorNombreConductor(String nombreConductor) {
        List<Camion> camiones = new ArrayList<>();
        String sql = "SELECT c.* " +
                "FROM camion c " +
                "INNER JOIN conductor co ON co.id_camion = c.id_camion " +
                "WHERE co.nombre = ? " +
                "ORDER BY c.id_camion";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreConductor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    camiones.add(mapearCamion(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar camiones por nombre de conductor");
            e.printStackTrace();
        }

        return camiones;
    }

    // Actualiza el kilometraje de un camión sumando el valor ingresado
    public int actualizarKilometraje(int idCamion, int kmIngresado) {
        String sqlSelect = "SELECT kilometraje FROM camion WHERE id_camion = ?";
        String sqlUpdate = "UPDATE camion SET kilometraje = ? WHERE id_camion = ?";

        try (PreparedStatement stmtSelect = conexion.prepareStatement(sqlSelect)) {
            stmtSelect.setInt(1, idCamion);

            try (ResultSet rs = stmtSelect.executeQuery()) {
                if (rs.next()) {
                    int kmActual = rs.getInt("kilometraje");

                    // Calcula el nuevo kilometraje acumulado
                    int nuevoKM = kmActual + kmIngresado;

                    try (PreparedStatement stmtUpdate = conexion.prepareStatement(sqlUpdate)) {
                        stmtUpdate.setInt(1, nuevoKM);
                        stmtUpdate.setInt(2, idCamion);
                        stmtUpdate.executeUpdate();
                    }

                    return nuevoKM;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar kilometraje");
            e.printStackTrace();
        }

        return -1;
    }

    // Obtiene el kilometraje del último mantenimiento registrado de un camión
    public int obtenerKmUltimoMantenimiento(int idCamion) {
        String sql = "SELECT kilometraje FROM mantenimiento WHERE id_camion = ? ORDER BY fecha DESC LIMIT 1";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCamion);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("kilometraje");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ultimo mantenimiento");
            e.printStackTrace();
        }

        return 0;
    }

    // Convierte un ResultSet en un objeto Camion
    private Camion mapearCamion(ResultSet rs) throws SQLException {
        Camion camion = new Camion();
        camion.setIdCamion(rs.getInt("id_camion"));
        camion.setPatente(rs.getString("patente"));
        camion.setMarca(rs.getString("marca"));
        camion.setModelo(rs.getString("modelo"));
        camion.setKilometraje(rs.getInt("kilometraje"));
        return camion;
    }

    public void reiniciarKilometraje(int idCamion, int km) {
        String sql = "UPDATE camion SET kilometraje = ? WHERE id_camion = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, km);
            stmt.setInt(2, idCamion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}