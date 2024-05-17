package VideoTeca.interfaces;

import java.util.List;

import VideoTeca.entidad.Factura;


public interface facturaDAO {
	int save(Factura bean);
	List<Factura> findAllFactura();
	
	

}
