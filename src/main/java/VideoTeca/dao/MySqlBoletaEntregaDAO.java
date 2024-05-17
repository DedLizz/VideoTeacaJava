package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.BoletaEntregaUser;
import VideoTeca.interfaces.boletaEntregaDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlBoletaEntregaDAO implements boletaEntregaDAO{

	@Override
	public List<BoletaEntregaUser> findAllBoletaEntrega(int user) {
	    List<BoletaEntregaUser> boletas = new ArrayList<>();
	    try (Connection cn = new MySqlConectar().getConnection();
	         PreparedStatement pstm = cn.prepareStatement("SELECT " +
	                     "b.idBoleta AS 'ID Boleta', " +
	                     "b.fecha AS 'Fecha', " +
	                     "b.monto AS 'Monto', " +
	                     "u.nombreU AS 'Nombre Usuario', " +
	                     "u.apellido AS 'Apellido Usuario', " +
	                     "u.Email AS 'Email Usuario', " +
	                     "t.tipoCuenta AS 'Tipo de Cuenta', " +
	                     "t.costo AS 'Costo' " +
	                     "FROM " +
	                     "boleta b " +
	                     "JOIN " +
	                     "usuario u ON b.idUsuario = u.idUsuario " +
	                     "JOIN " +
	                     "tipocuenta t ON b.idTipoCuenta = t.idTicuenta " +
	                     "WHERE " +
	                     "u.idUsuario = ?")) {
	        pstm.setInt(1, user);
	        try (ResultSet rs = pstm.executeQuery()) {
	            while (rs.next()) {
	                BoletaEntregaUser boleta = new BoletaEntregaUser();
	                boleta.setIdBoletaE(rs.getInt("ID Boleta"));
	                boleta.setFechaBoletaE(rs.getString("Fecha"));
	                boleta.setMontoBoletaE(rs.getInt("Monto"));
	                boleta.setNameuserBoletaE(rs.getString("Nombre Usuario"));
	                boleta.setApellidouserBoletaE(rs.getString("Apellido Usuario"));
	                boleta.setEmailBoletaE(rs.getString("Email Usuario"));
	                boleta.setTipoCuentaBoletaE(rs.getString("Tipo de Cuenta"));
	                boleta.setCostoBoletaE(rs.getInt("Costo"));
	                boletas.add(boleta);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return boletas;
	}


}
