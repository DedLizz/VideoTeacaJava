package VideoTeca.interfaces;

import java.util.List;

import VideoTeca.entidad.Video;

public interface videoDAO {
	
	int save(Video bean);
    int update(Video bean);
    int deleteById(int id);
    Video findById(int cod);
    List<Video> findVideosByCategoria(int idCategoria);
	List<Video> findAllVideo();
	boolean videoExistente(int idVideo);


}
