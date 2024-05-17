package VideoTeca.interfaces;

import java.util.List;

import VideoTeca.entidad.BoletaEntregaUser;


public interface boletaEntregaDAO {
	List<BoletaEntregaUser> findAllBoletaEntrega(int user);
}
