package VideoTeca.interfaces;

import java.util.List;

import VideoTeca.entidad.Categoria;

public interface categoriaDAO {
	
	int save(Categoria bean);
    int update(Categoria bean);
    int deleteById(int id);
    Categoria findById(int id);
	List<Categoria> findAllCategoria();
	boolean categoriaExistente(String nombreCategoria);
}
