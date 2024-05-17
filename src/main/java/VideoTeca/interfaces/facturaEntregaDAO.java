package VideoTeca.interfaces;

import java.util.List;

import VideoTeca.entidad.FacturaEntregaUser;


public interface facturaEntregaDAO {
	List<FacturaEntregaUser> findAllFacturaEntrega(int user);
}
