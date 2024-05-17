package VideoTeca.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import VideoTeca.dao.MySqlFavoritosDAO;
import VideoTeca.entidad.Favorito;
import VideoTeca.entidad.Video;


@WebServlet("/ServletFavorito")
public class ServletFavorito extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletFavorito() {
        super();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("accion");
		
		if(tipo.equals("agregar"))
			agregarFavorito(request, response);
		else if (tipo.equals("eliminar"))
			eliminarFavorito(request, response);
		else if (tipo.equals("listar"))
			listarVideosFavorito(request, response);
		else if (tipo.equals("btnFavoritos"))
			respuestaBotonFavorito(request, response);
	}
	
	
	protected void agregarFavorito(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String idVideoStr = request.getParameter("idVideoPag");
	    String idUsuarioStr = request.getParameter("idUsuarioPag");

	     int idVideo = Integer.parseInt(idVideoStr);
	     int idUsuario = Integer.parseInt(idUsuarioStr);
	     
		    System.out.println(idVideo);
		     System.out.println(idUsuario);
	            
	     boolean encontrado = new MySqlFavoritosDAO().buscarFavorito(idVideo, idUsuario);
	     
	     System.out.println(encontrado);
         

	     if (!encontrado) {
	    	 Favorito fav = new Favorito();
	    	 fav.setIdVideo(idVideo);
	    	 fav.setIdUsuario(idUsuario);
	    	 
	    	 int estado = new  MySqlFavoritosDAO().save(fav);
	    	 
	    	 if(estado == 1) {
	    		 System.out.print("favorito guardado correctamente");
	    	 } else {
	    		 System.out.print("favorito no se guardo");
	    	 }
	     }
	}
	
    protected void eliminarFavorito(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idVideoStr = request.getParameter("idVideoPag");
	    String idUsuarioStr = request.getParameter("idUsuarioPag");
	    
	    int idVideo = Integer.parseInt(idVideoStr);
	    int idUsuario = Integer.parseInt(idUsuarioStr);
	    
	    System.out.println(idVideo);
	     System.out.println(idUsuario);
	    
	    
      
	    boolean encontrado = new MySqlFavoritosDAO().buscarFavorito(idVideo, idUsuario);
	    
	    System.out.println(encontrado);
	    
	    
	     if (encontrado) {
	    	 
	    	 int estado = new  MySqlFavoritosDAO().deleteByVideoAndUser(idVideo, idUsuario);
	    	 
	    	 if(estado == 1) {
	    		 System.out.print("favorito eliminado correctamente");
	    	 } else {
	    		 System.out.print("favorito no  eliminado");
	    	 }
	     }
    }
    
 

    
    
    
    protected void listarVideosFavorito(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String idUsuarioStr = request.getParameter("idUsuarioPag");
	    String idCategoriaStr = request.getParameter("idCategoriaPag");
	    
	    int idUsuario = Integer.parseInt(idUsuarioStr);
	    int idCategoria = Integer.parseInt(idCategoriaStr);
	    
	    System.out.println(idUsuario);
	     
	    List<Video> videoFav = new MySqlFavoritosDAO().getVideosFavoritos2(idUsuario, idCategoria);
	    
	    
	    if (videoFav != null && !videoFav.isEmpty()) {
	        // Se recuperaron los favoritos correctamente
	        System.out.println("Se recuperaron los favoritos correctamente");
	        // Imprimir la lista en la consola
	        System.out.println("Lista de Videos Favoritos:");
	        for (Video video : videoFav) {
	            System.out.println("ID: " + video.getIdVideo() + ", Título: " + video.getTituloVideo() + ", Descripción: " + video.getDescripcion() + ", URL: " + video.getUrlVideo() + ", Categoría: " + video.getCategoriaVideo());
	        }
	        
	        
	        
	       //crear objeto de la clase Gson
			Gson gson = new Gson();
			//convertir a JSON(STRING) el arreglo favorito
			String json = gson.toJson(videoFav);
			//preparar salida en formato JSON
			response.setContentType("application/json;charset=UTF-8");
			//
			PrintWriter pw=response.getWriter();
			pw.print(json); 

	    } else {
	        // No se recuperaron los favoritos
	        System.out.println("No se recuperaron los favoritos");
	    }
	     
    }
    
    //para la funcion de ocultamiento de btn favoritos
    protected void respuestaBotonFavorito (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idVideoStr = request.getParameter("idVideoPag");
	    String idUsuarioStr = request.getParameter("idUsuarioPag");
	    
	    int idVideo = Integer.parseInt(idVideoStr);
        int idUsuario = Integer.parseInt(idUsuarioStr);
        
        // Instancia la clase MySqlFavoritosDAO
        MySqlFavoritosDAO favoritosDAO = new MySqlFavoritosDAO();
        boolean esFavorito = favoritosDAO.buscarFavorito(idVideo, idUsuario);
        
        
        // Usa Gson para convertir el resultado a JSON
        Gson gson = new Gson();
        String json = gson.toJson(esFavorito);
        
        // Configura la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    	
    }
    
   
    

	
	
	
	
	

}
