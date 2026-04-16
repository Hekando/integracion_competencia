package BaseDatos;

import Modelo.Camion;
import java.sql.*;

public class CamionDAO {

    private Connection conexion;

    public CamionDAO() {
        try {
            this.conexion = ConexionBD.getConexion();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BD");
            e.printStackTrace();
        }
    }

    // 🔍 Buscar camión por modelo
    public Camion buscarPorModelo(String modelo) {

        Camion camion = null;
        String sql = "SELECT * FROM camion WHERE modelo = ?";

        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, modelo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                camion = new Camion();
                camion.setIdCamion(rs.getInt("id_camion"));
                camion.setMarca(rs.getString("marca"));
                camion.setModelo(rs.getString("modelo"));
                camion.setAnio(rs.getInt("anio"));
                camion.setKilometraje(rs.getInt("kilometraje"));
                camion.setEstadoMantenimiento(rs.getString("estado_mantenimiento"));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar camión");
            e.printStackTrace();
        }

        return camion;
    }

    // Actualizar kilometraje SUMANDO
    public int actualizarKilometraje(int idCamion, int kmIngresado) {

        String sqlSelect = "SELECT kilometraje FROM camion WHERE id_camion = ?";
        String sqlUpdate = "UPDATE camion SET kilometraje = ? WHERE id_camion = ?";

        try {
            PreparedStatement stmtSelect = conexion.prepareStatement(sqlSelect);
            stmtSelect.setInt(1, idCamion);

            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                int kmActual = rs.getInt("kilometraje");

                int nuevoKM = kmActual + kmIngresado;

                PreparedStatement stmtUpdate = conexion.prepareStatement(sqlUpdate);
                stmtUpdate.setInt(1, nuevoKM);
                stmtUpdate.setInt(2, idCamion);

                stmtUpdate.executeUpdate();

                return nuevoKM; // IMPORTANTEE
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // 🔍 Obtener kilometraje del último mantenimiento
    public int obtenerKmUltimoMantenimiento(int idCamion) {
        String sql = "SELECT kilometraje FROM mantenimiento WHERE id_camion = ? ORDER BY fecha DESC LIMIT 1";

        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idCamion);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("kilometraje");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener último mantenimiento");
            e.printStackTrace();
        }

        return 0; // Si no hay mantenimientos, retorna 0
    }

}