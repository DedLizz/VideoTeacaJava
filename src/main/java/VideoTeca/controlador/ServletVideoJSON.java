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

import VideoTeca.dao.MySqlVideoDAO;
import VideoTeca.entidad.Video;


@WebServlet("/ServletVideoJSON")
public class ServletVideoJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String idCategoriaPersistente; 
       
   
    public ServletVideoJSON() {
        super();
    }

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idCategoria = request.getParameter("idCategoria");
		System.out.print(idCategoria);
		
		
	      if (idCategoria != null && idCategoria.equals("0")) {
	            // Si se recibe el parámetro 'borrar', borra la variable idCategoriaPersistente
	            idCategoriaPersistente = null;
	        } else {
	            if (idCategoria != null && !idCategoria.isEmpty()) {
	                // Si se recibe un nuevo valor de idCategoria, actualizar el valor persistente
	                if (!idCategoria.equals(idCategoriaPersistente)) {
	                    idCategoriaPersistente = idCategoria;
	                }
	            } else {
	                // Si no se recibe un nuevo valor de idCategoria, pero hay uno persistente, usar ese
	                if (idCategoriaPersistente != null && !idCategoriaPersistente.isEmpty()) {
	                    idCategoria = idCategoriaPersistente;
	                }
	            }
	        }
		
		
		 List<Video> lista;
		
	      if(idCategoria != null && !idCategoria.isEmpty()) {
	          lista = new MySqlVideoDAO().findVideosByCategoria(Integer.parseInt(idCategoria));
	      } else {
	          lista = new MySqlVideoDAO().findAllVideo();
	      }
			
		
		//List<Video>   lista = new MySqlVideoDAO().findAllVideo() ; 
		//crear objeto de la clase Gson
		Gson gson = new Gson();
		//convertir a JSON(STRING) el arreglo lista
		String json = gson.toJson(lista);
		//preparar salida en formato JSON
		response.setContentType("application/json;charset=UTF-8");
		//
		PrintWriter pw=response.getWriter();
		pw.print(json); 
		
	}
	

	 


}
