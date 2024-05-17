package VideoTeca.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import VideoTeca.dao.MySqlVideoDAO;
import VideoTeca.entidad.Video;


@WebServlet("/ServletVideo")
public class ServletVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ServletVideo() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("accion");
		
		if(tipo.equals("lista"))
			listarVideo(request, response);
		else if (tipo.equals("buscar"))
			buscarVideo(request,response);
		else if (tipo.equals("buscarVi"))
			buscarVideoTitulo(request,response);
		else if (tipo.equals("eliminar"))
			eliminarVideo(request, response);
		
	}
	
	 protected void buscarVideo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int idVideo;
	    
	    try {
	        idVideo = Integer.parseInt(request.getParameter("idVideoPag"));
	    } catch(NumberFormatException e) {
	        request.getSession().setAttribute("MENSAJE", "ID de video no válido");
	        response.sendRedirect("MenuHome.jsp");
	        return;
	    }
	    
	    Video video = new MySqlVideoDAO().findById(idVideo);
	    
	    if(video == null) {
	        request.getSession().setAttribute("MENSAJE", "Video no encontrado");
	        response.sendRedirect("MenuHome.jsp");
	    } else {
	        // Almacenar cada dato del video en la sesión HTTP
	        HttpSession session = request.getSession();
	        session.setAttribute("idVideo", video.getIdVideo());
	        session.setAttribute("tituloVideo", video.getTituloVideo());
	        session.setAttribute("descripcionVideo", video.getDescripcion());
	        session.setAttribute("urlVideo", video.getUrlVideo());
	        session.setAttribute("categoriaVideo", video.getCategoriaVideo());
	        
	        // Redireccionar a la página que mostrará los datos del video por separado
	        response.sendRedirect("MenuVideo.jsp");
	    }
	}

	protected void listarVideo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        List<Video> lista = null;
        try {
            
            lista = new MySqlVideoDAO().findAllVideo();
            System.out.println("Datos de usuarios obtenidos correctamente de la base de datos:");
            if (lista != null) {
                for (Video video : lista) {
                    System.out.println("ID video: " + video.getIdVideo());
                    System.out.println("titulo: " + video.getTituloVideo());
                    System.out.println("URL: " + video.getUrlVideo());
                    System.out.println("Descripción: " + video.getDescripcion());
                    System.out.println("Tipo de categoria: " + video.getCategoriaVideo());
                    System.out.println("---------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al obtener los datos de usuarios de la base de datos: " + e.getMessage());
        }
        request.setAttribute("listaVideos", lista);
        request.getRequestDispatcher("/videos.jsp").forward(request, response);
	
	}
	

	protected void buscarVideoTitulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String tituloVi = request.getParameter("palabraPag");
	    
	    // Eliminar espacios en blanco y convertir a minúsculas
	    tituloVi = tituloVi.trim().toLowerCase();

	    List<Video> videos = new MySqlVideoDAO().findAllVideo();
	    
	    List<Video> videosEncontrados = new ArrayList<>();

	    // Iterar sobre la lista de videos
	    for (Video video : videos) {
	        // Obtener el título del video y aplicar las mismas transformaciones
	        String tituloVideo = video.getTituloVideo().trim().toLowerCase();

	        // Comparar si el título coincide con el título buscado
	        if (tituloVideo.equals(tituloVi)) {
	            // Si coincide, añadir este video a la lista de videos encontrados
	            videosEncontrados.add(video);
	        }
	    }

	    // Si se encontró algún video con el título buscado
	    if (!videosEncontrados.isEmpty()) {
	        // Tomamos el primer video de la lista (si hay más de uno con el mismo título)
	        Video videoEncontrado = videosEncontrados.get(0);

	        // Almacenar cada dato del video en la sesión HTTP
	        HttpSession session = request.getSession();
	        session.setAttribute("idVideo", videoEncontrado.getIdVideo());
	        session.setAttribute("tituloVideo", videoEncontrado.getTituloVideo());
	        session.setAttribute("descripcionVideo", videoEncontrado.getDescripcion());
	        session.setAttribute("urlVideo", videoEncontrado.getUrlVideo());
	        session.setAttribute("categoriaVideo", videoEncontrado.getCategoriaVideo());
	        
	        // Redireccionar a la página que mostrará los datos del video por separado
	        response.sendRedirect("MenuVideo.jsp");
	    } else {
	    	response.sendRedirect("MenuHome.jsp");
	        // Manejar el caso en que no se encontró ningún video con el título buscado
	        // Por ejemplo, mostrar un mensaje de error o redirigir a una página de búsqueda nuevamente
	        // response.sendRedirect("PaginaDeError.jsp");
	    }
		
		
	}
	
	protected void eliminarVideo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
   
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
