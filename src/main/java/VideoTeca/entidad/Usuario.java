package VideoTeca.entidad;

public class Usuario {
	private int idUsuario;
	private String nombreUsuario;
	private String apellidoUsuario;
	private String contraUsuario;
	private String emailUsuaio;
	private int tipoCuentaUsuario;
	
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getApellidoUsuario() {
		return apellidoUsuario;
	}
	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}
	public String getContraUsuario() {
		return contraUsuario;
	}
	public void setContraUsuario(String contraUsuario) {
		this.contraUsuario = contraUsuario;
	}
	public String getEmailUsuaio() {
		return emailUsuaio;
	}
	public void setEmailUsuaio(String emailUsuaio) {
		this.emailUsuaio = emailUsuaio;
	}
	public int getTipoCuentaUsuario() {
		return tipoCuentaUsuario;
	}
	public void setTipoCuentaUsuario(int tipoCuentaUsuario) {
		this.tipoCuentaUsuario = tipoCuentaUsuario;
	}
}
