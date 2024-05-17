package VideoTeca.entidad;

public class Factura {
	private int idFactura;
	private String fechaFactura;
	private String razonEFactura;
	private String razonCFactura;
	private String direccionFactura;
	private int montoFactura;
	private int igv;
	private int idUsuarioFactura;
	private int idTipoCuentaFactura;
	
	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public String getFechaFactura() {
		return fechaFactura;
	}
	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
	public String getRazonEFactura() {
		return razonEFactura;
	}
	public void setRazonEFactura(String razonEFactura) {
		this.razonEFactura = razonEFactura;
	}
	public String getRazonCFactura() {
		return razonCFactura;
	}
	public void setRazonCFactura(String razonCFactura) {
		this.razonCFactura = razonCFactura;
	}
	public String getDireccionFactura() {
		return direccionFactura;
	}
	public void setDireccionFactura(String direccionFactura) {
		this.direccionFactura = direccionFactura;
	}
	public int getMontoFactura() {
		return montoFactura;
	}
	public void setMontoFactura(int montoFactura) {
		this.montoFactura = montoFactura;
	}
	public int getIgv() {
		return igv;
	}
	public void setIgv(int igv) {
		this.igv = igv;
	}
	public int getIdUsuarioFactura() {
		return idUsuarioFactura;
	}
	public void setIdUsuarioFactura(int idUsuarioFactura) {
		this.idUsuarioFactura = idUsuarioFactura;
	}
	public int getIdTipoCuentaFactura() {
		return idTipoCuentaFactura;
	}
	public void setIdTipoCuentaFactura(int idTipoCuentaFactura) {
		this.idTipoCuentaFactura = idTipoCuentaFactura;
	}
}
