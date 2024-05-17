package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.FacturaEntregaUser;
import VideoTeca.interfaces.facturaEntregaDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlFacturaEntregaDAO implements facturaEntregaDAO {

	@Override
	public List<FacturaEntregaUser> findAllFacturaEntrega(int user) {
	    List<FacturaEntregaUser> facturas = new ArrayList<>();
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT f.idFactura AS 'ID Factura', " +
	                     "f.fecha AS 'Fecha', " +
	                     "f.razonSocialEmpre AS 'Razón Social Empresa', " +
	                     "f.razonSocialClient AS 'Razón Social Cliente', " +
	                     "f.direccion AS 'Dirección', " +
	                     "f.monto AS 'Monto', " +
	                     "f.igv AS 'IGV', " +
	                     "u.nombreU AS 'Nombre Usuario', " +
	                     "u.apellido AS 'Apellido Usuario', " +
	                     "u.Email AS 'Email Usuario', " +
	                     "t.tipoCuenta AS 'Tipo de Cuenta', " +
	                     "t.costo AS 'Costo' " +
	                     "FROM factura f " +
	                     "JOIN usuario u ON f.idUsuario = u.idUsuario " +
	                     "JOIN tipocuenta t ON f.idTipoCuenta = t.idTicuenta " +
	                     "WHERE u.idUsuario = ?";
	        pstm = cn.prepareStatement(sql);
	        pstm.setInt(1, user);
	        rs = pstm.executeQuery();
	        while (rs.next()) {
	            FacturaEntregaUser factura = new FacturaEntregaUser();
	            factura.setIdFacturaE(rs.getInt("ID Factura"));
	            factura.setFechaFacturaE(rs.getString("Fecha"));
	            factura.setEmpresaFacturaE(rs.getString("Razón Social Empresa"));
	            factura.setClienteFacturaE(rs.getString("Razón Social Cliente"));
	            factura.setDireccionFacturaE(rs.getString("Dirección"));
	            factura.setMontoFacturaE(rs.getInt("Monto"));
	            factura.setIgvFacturaE(rs.getInt("IGV"));
	            factura.setUserFactruaE(rs.getString("Nombre Usuario"));
	            factura.setApellidoFacturaE(rs.getString("Apellido Usuario"));
	            factura.setEmailFacturaE(rs.getString("Email Usuario"));
	            factura.setTipoCuentaFacturaE(rs.getString("Tipo de Cuenta"));
	            factura.setCostoFacturaE(rs.getInt("Costo"));
	            facturas.add(factura);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(rs != null)
	                rs.close();
	            if(pstm != null)
	                pstm.close();
	            if(cn != null)
	                cn.close();
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }
	    return facturas;
	}




}
