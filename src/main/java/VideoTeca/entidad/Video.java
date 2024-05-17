package VideoTeca.entidad;

public class Video {
	
	private int idVideo;
	private String tituloVideo;
	private String descripcion;
	private String urlVideo;
	private int categoriaVideo;
	public int getIdVideo() {
		return idVideo;
	}
	public void setIdVideo(int idVideo) {
		this.idVideo = idVideo;
	}
	public String getTituloVideo() {
		return tituloVideo;
	}
	public void setTituloVideo(String tituloVideo) {
		this.tituloVideo = tituloVideo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUrlVideo() {
		return urlVideo;
	}
	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}
	public int getCategoriaVideo() {
		return categoriaVideo;
	}
	public void setCategoriaVideo(int categoriaVideo) {
		this.categoriaVideo = categoriaVideo;
	}
}
