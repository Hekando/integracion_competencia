package BaseDatos;

import Modelo.Administrador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    private Connection conn;

    public AdministradorDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Administrador> listarTecnicosMantenimientoOficina() throws SQLException {
        List<Administrador> lista = new ArrayList<>();

        String sql = "SELECT id_admin, nombre, usuario, rol FROM administrador WHERE rol = 'TecMantenimientoOficina'";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Administrador admin = new Administrador();
            admin.setId_admin(rs.getInt("id_admin"));
            admin.setNombre(rs.getString("nombre"));
            admin.setUsuario(rs.getString("usuario"));
            admin.setRol(rs.getString("rol"));
            lista.add(admin);
        }

        return lista;
    }
}