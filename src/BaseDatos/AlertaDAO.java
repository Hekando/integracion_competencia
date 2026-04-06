package BaseDatos;
import Modelo.Alerta;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AlertaDAO {

    public void insertarAlerta(Alerta alerta) {

        String sql = "INSERT INTO alerta (id_camion, kilometraje, fecha, estado) VALUES (?, ?, NOW(), ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alerta.getIdCamion());
            ps.setInt(2, alerta.getKilometraje());
            ps.setString(3, alerta.getEstado());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
