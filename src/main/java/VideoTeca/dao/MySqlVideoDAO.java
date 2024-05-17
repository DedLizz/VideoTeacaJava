package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.Video;
import VideoTeca.interfaces.videoDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlVideoDAO implements videoDAO {

	@Override
	public int save(Video bean) { // Cambié el parámetro de Usuario a Video
	    int salida = -1;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "INSERT INTO video (tituloVideo, descriVideo, enlaceURL, idCategoria) VALUES (?,?,?,?)"; 
	        pstm = cn.prepareStatement(sql);
	        pstm.setString(1, bean.getTituloVideo());
	        pstm.setString(2, bean.getDescripcion()); 
	        pstm.setString(3, bean.getUrlVideo()); 
	        pstm.setInt(4, bean.getCategoriaVideo());
	        salida = pstm.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstm != null)
	                pstm.close();
	            if (cn != null)
	                cn.close();
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }
	    return salida;    
	}


	@Override
	public int update(Video bean) { // Cambié el parámetro de Usuario a Video
	    int salida = -1;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    try {
	        // 1. Obtener conexión
	        cn = new MySqlConectar().getConnection();
	        // 2. Sentencia SQL
	        String sql = "UPDATE video SET tituloVideo=?, descriVideo=?, enlaceURL=?, idCategoria=? WHERE idVideo=?"; // Ajusté el nombre de la tabla y de los campos
	        // 3. Crear objeto "pstm" y enviar la variable "sql"
	        pstm = cn.prepareStatement(sql);
	        // 4. Parámetros
	        pstm.setString(1, bean.getTituloVideo());
	        pstm.setString(2, bean.getDescripcion()); 
	        pstm.setString(3, bean.getUrlVideo()); 
	        pstm.setInt(4, bean.getCategoriaVideo());
	        pstm.setInt(5, bean.getIdVideo());
	        // 5. Ejecutar "pstm"
	        // Si la sentencia UPDATE se ejecuta correctamente, el método executeUpdate retorna un valor mayor a 0
	        salida = pstm.executeUpdate();
	    } catch (SQLException e) {
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
	    return salida;
	}


	@Override
	public int deleteById(int id) {
	    int salida = -1;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    try {
	        // 1. Obtener conexión
	        cn = new MySqlConectar().getConnection();
	        // 2. Sentencia SQL
	        String sql = "DELETE FROM video WHERE idVideo=?"; // Ajusté el nombre de la tabla
	        // 3. Crear objeto "pstm" y enviar la variable "sql"
	        pstm = cn.prepareStatement(sql);
	        // 4. Parámetros
	        pstm.setInt(1, id);
	        // 5. Ejecutar "pstm"
	        salida = pstm.executeUpdate();
	    } catch (SQLException e) {
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
	    return salida;
	}


	@Override
	public Video findById(int cod) {
	    Video bean = null;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT * FROM video WHERE idVideo=?";
	        pstm = cn.prepareStatement(sql);
	        pstm.setInt(1, cod);
	        rs = pstm.executeQuery();
	        
	        if (rs.next()) {
	            bean = new Video();
	            bean.setIdVideo(rs.getInt("idVideo"));
	            bean.setTituloVideo(rs.getString("tituloVideo"));
	            bean.setDescripcion(rs.getString("descriVideo")); 
	            bean.setUrlVideo(rs.getString("enlaceURL")); 
	            bean.setCategoriaVideo(rs.getInt("idCategoria"));
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
	    return bean;
	}
	
	@Override
	public List<Video> findVideosByCategoria(int idCategoria) {
	    List<Video> videos = new ArrayList<>();
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT * FROM video WHERE idCategoria = ?";
	        pstm = cn.prepareStatement(sql);
	        pstm.setInt(1, idCategoria);
	        rs = pstm.executeQuery();
	        while (rs.next()) {
	            Video video = new Video();
	            video.setIdVideo(rs.getInt("idVideo"));
	            video.setTituloVideo(rs.getString("tituloVideo"));
	            video.setDescripcion(rs.getString("descriVideo"));
	            video.setUrlVideo(rs.getString("enlaceURL"));
	            videos.add(video);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null)
	                rs.close();
	            if (pstm != null)
	                pstm.close();
	            if (cn != null)
	                cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return videos;
	}


	@Override
	public List<Video> findAllVideo() {
	    List<Video> lista = new ArrayList<>();
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT * FROM video"; 
	        pstm = cn.prepareStatement(sql);
	        rs = pstm.executeQuery();
	        while (rs.next()) {
	            Video video = new Video();
	            video.setIdVideo(rs.getInt("idVideo"));
	            video.setTituloVideo(rs.getString("tituloVideo"));
	            video.setDescripcion(rs.getString("descriVideo"));
	            video.setUrlVideo(rs.getString("enlaceURL"));
	            video.setCategoriaVideo(rs.getInt("idCategoria"));
	            lista.add(video);
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

	
	@Override
	public boolean videoExistente(int idVideo) { 
	    PreparedStatement pstm = null;
	    Connection cn = null;
	    ResultSet rs = null;
	    boolean existe = false;

	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT COUNT(*) FROM video WHERE idVideo = ?"; 
	        pstm = cn.prepareStatement(sql);
	        pstm.setInt(1, idVideo); 
	        rs = pstm.executeQuery();

	        if (rs.next() && rs.getInt(1) > 0) {
	            existe = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null)
	                rs.close();
	            if (pstm != null)
	                pstm.close();
	            if (cn != null)
	                cn.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return existe;
	}
}
