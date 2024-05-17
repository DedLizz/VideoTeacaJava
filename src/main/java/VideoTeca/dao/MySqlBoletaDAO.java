package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.Boleta;
import VideoTeca.interfaces.boletaDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlBoletaDAO implements boletaDAO {

	@Override
	public int save(Boleta bean) {
	    int salida = -1;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "INSERT INTO boleta (fecha, monto, idUsuario, idTipoCuenta) VALUES (?,?,?,?)";
	        pstm = cn.prepareStatement(sql);
	        pstm.setString(1, bean.getFechaBoleta());
	        pstm.setInt(2, bean.getMontoBoleta());
	        pstm.setInt(3, bean.getIdUserBoleta());
	        pstm.setInt(4, bean.getIdTipoCuentaBoleta());
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
	public List<Boleta> findAllBoleta() {
	    List<Boleta> lista = new ArrayList<Boleta>();
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT * FROM boleta";
	        pstm = cn.prepareStatement(sql);
	        rs = pstm.executeQuery();
	        while (rs.next()) {
	            Boleta boleta = new Boleta();
	            boleta.setIdBoleta(rs.getInt("idBoleta"));
	            boleta.setFechaBoleta(rs.getString("fecha"));
	            boleta.setMontoBoleta(rs.getInt("monto"));
	            boleta.setIdUserBoleta(rs.getInt("idUsuario"));
	            boleta.setIdTipoCuentaBoleta(rs.getInt("idTipoCuenta"));
	            lista.add(boleta);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstm != null)
	                pstm.close();
	            if ( cn != null)
	                cn.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return lista;
	}


}
