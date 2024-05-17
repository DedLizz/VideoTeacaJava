package VideoTeca.interfaces;



import java.util.List;

import VideoTeca.entidad.Favorito;
import VideoTeca.entidad.Video;

public interface favoritoDAO {
	
	int save(Favorito bean);
	List<Favorito> getFavoritosByIdUsuario(int idUsuario);
	boolean buscarFavorito(int idVideo, int idUsuario);
    int deleteByVideoAndUser(int idVideo, int idUsuario);
    List<Video> getVideosFavoritos(int idUsuario);
    List<Video> getVideosFavoritos2(int idUsuario, int idCategoria);
}
