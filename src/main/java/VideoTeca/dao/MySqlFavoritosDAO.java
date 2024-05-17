package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.Favorito;
import VideoTeca.entidad.Video;
import VideoTeca.interfaces.favoritoDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlFavoritosDAO implements favoritoDAO{

	@Override
	public int save(Favorito bean) {
	    int salida = -1;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "INSERT INTO favoritos (idVideo, idUsuario) VALUES (?,?)";
	        pstm = cn.prepareStatement(sql);
	        pstm.setInt(1, bean.getIdVideo());
	        pstm.setInt(2, bean.getIdUsuario());
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
	public List<Favorito> getFavoritosByIdUsuario(int idUsuario) {
	    List<Favorito> favoritos = new ArrayList<>();
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT * FROM favoritos WHERE idUsuario = ?";
	        pstm = cn.prepareStatement(sql);
	        pstm.setInt(1, idUsuario);
	        rs = pstm.executeQuery();
	        while (rs.next()) {
	            Favorito favorito = new Favorito();
	            favorito.setIdFavorito(rs.getInt("idFavoritos"));
	            favorito.setIdVideo(rs.getInt("idVideo"));
	            favorito.setIdUsuario(rs.getInt("idUsuario"));
	            favoritos.add(favorito);
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
	    return favoritos;
	}
	
	//busca favoritos mediante idVideo, idUsuario y etorna un boolean
	@Override
	public boolean buscarFavorito(int idVideo, int idUsuario) {
	    boolean encontrado = false;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT COUNT(*) AS count FROM favoritos WHERE idVideo = ? AND idUsuario = ?";
	        pstm = cn.prepareStatement(sql);
	        pstm.setInt(1, idVideo);
	        pstm.setInt(2, idUsuario);
	        rs = pstm.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt("count");
	            encontrado = count > 0;
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
	    return encontrado;
	}

	
	//eliminar favorito mediante idvideo y idusuario
	@Override
	public int deleteByVideoAndUser(int idVideo, int idUsuario) {
	    int salida = -1;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    try {
	        // 1. Obtener conexión
	        cn = new MySqlConectar().getConnection();
	        // 2. Sentencia SQL
	        String sql = "DELETE FROM favoritos WHERE idVideo=? AND idUsuario=?";
	        // 3. Crear objeto "pstm" y enviar la variable "sql"
	        pstm = cn.prepareStatement(sql);
	        // 4. Parámetros
	        pstm.setInt(1, idVideo);
	        pstm.setInt(2, idUsuario);
	        // 5. Ejecutar "pstm"
	        salida = pstm.executeUpdate();
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
	    return salida;
	}
	

	//filtra usa la tabla favoritos y de videos para extraer solo videos de favoritos de un unico usuario
	@Override
    public List<Video> getVideosFavoritos(int idUsuario) {
        List<Video> videos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT v.idVideo, v.tituloVideo, v.descriVideo, v.enlaceURL, v.idCategoria " +
                         "FROM favoritos AS f " +
                         "JOIN video AS v ON f.idVideo = v.idVideo " +
                         "JOIN categoria AS c ON v.idCategoria = c.idCategoria " +
                         "WHERE f.idUsuario = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idUsuario);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Video video = new Video();
                video.setIdVideo(rs.getInt("idVideo"));
                video.setTituloVideo(rs.getString("tituloVideo"));
                video.setDescripcion(rs.getString("descriVideo"));
                video.setUrlVideo(rs.getString("enlaceURL"));
                video.setCategoriaVideo(rs.getInt("idCategoria"));
                videos.add(video);
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
        return videos;
    }
    
   //extrae los videos filtrados por idUsuario y categoria tabla conbinadas de favoritos y video
	@Override
    public List<Video> getVideosFavoritos2(int idUsuario, int idCategoria) {
        List<Video> videos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT v.idVideo, v.tituloVideo, v.descriVideo, v.enlaceURL, v.idCategoria " +
                         "FROM favoritos AS f " +
                         "JOIN video AS v ON f.idVideo = v.idVideo " +
                         "JOIN categoria AS c ON v.idCategoria = c.idCategoria " +
                         "WHERE f.idUsuario = ? AND v.idCategoria = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idUsuario);
            pstm.setInt(2, idCategoria);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Video video = new Video();
                video.setIdVideo(rs.getInt("idVideo"));
                video.setTituloVideo(rs.getString("tituloVideo"));
                video.setDescripcion(rs.getString("descriVideo"));
                video.setUrlVideo(rs.getString("enlaceURL"));
                video.setCategoriaVideo(rs.getInt("idCategoria"));
                videos.add(video);
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
        return videos;
    }
}
