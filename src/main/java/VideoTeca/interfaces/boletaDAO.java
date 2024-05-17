package VideoTeca.interfaces;

import java.util.List;

import VideoTeca.entidad.Boleta;

public interface boletaDAO {

	int save(Boleta bean);
	List<Boleta> findAllBoleta();
}
