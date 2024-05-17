package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import VideoTeca.entidad.ReportComentario;
import VideoTeca.interfaces.reportComentDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlReportComentDAO implements reportComentDAO{

	@Override
	public int save(ReportComentario bean) {
	    int salida = -1;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "INSERT INTO reportcoment (coment, idUsuario) VALUES (?,?)";
	        pstm = cn.prepareStatement(sql);
	        pstm.setString(1, bean.getComentaReport());
	        pstm.setInt(2, bean.getIdUserReport());
	        salida = pstm.executeUpdate();

	        // Obtener el ID generado automáticamente
	        ResultSet generatedKeys = pstm.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            bean.setIdRerportComent(generatedKeys.getInt(1));
	        } else {
	            throw new SQLException("Fallo al obtener el ID generado para el reporte del comentario.");
	        }
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


}
