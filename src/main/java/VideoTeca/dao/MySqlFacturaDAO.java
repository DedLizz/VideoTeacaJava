package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.Factura;
import VideoTeca.interfaces.facturaDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlFacturaDAO implements facturaDAO{

	@Override
	public int save(Factura factura) {
	    int salida = -1;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "INSERT INTO factura (fecha, razonSocialEmpre, razonSocialClient, direccion, monto, igv, idUsuario, idTipoCuenta) VALUES (?,?,?,?,?,?,?,?)";
	        pstm = cn.prepareStatement(sql);
	        pstm.setString(1, factura.getFechaFactura());
	        pstm.setString(2, factura.getRazonEFactura());
	        pstm.setString(3, factura.getRazonCFactura());
	        pstm.setString(4, factura.getDireccionFactura());
	        pstm.setInt(5, factura.getMontoFactura());
	        pstm.setInt(6, factura.getIgv());
	        pstm.setInt(7, factura.getIdUsuarioFactura());
	        pstm.setInt(8, factura.getIdTipoCuentaFactura());
	        salida = pstm.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(pstm != null)
	                pstm.close();
	            if(cn != null)
	                cn.close();
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }
	    return salida;    
	}


	@Override
	public List<Factura> findAllFactura() {
	    List<Factura> lista = new ArrayList<>();
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT * FROM factura";
	        pstm = cn.prepareStatement(sql);
	        rs = pstm.executeQuery();
	        while (rs.next()) {
	            Factura factura = new Factura();
	            factura.setIdFactura(rs.getInt("idFactura"));
	            factura.setFechaFactura(rs.getString("fecha"));
	            factura.setRazonEFactura(rs.getString("razonSocialEmpre"));
	            factura.setRazonCFactura(rs.getString("razonSocialClient"));
	            factura.setDireccionFactura(rs.getString("direccion"));
	            factura.setMontoFactura(rs.getInt("monto"));
	            factura.setIgv(rs.getInt("igv"));
	            factura.setIdUsuarioFactura(rs.getInt("idUsuario"));
	            factura.setIdTipoCuentaFactura(rs.getInt("idTipoCuenta"));
	            lista.add(factura);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstm != null)
	                pstm.close();
	            if (cn != null)
	                cn.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return lista;
	}


}
