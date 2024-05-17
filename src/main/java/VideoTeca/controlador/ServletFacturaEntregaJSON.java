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

import VideoTeca.dao.MySqlFacturaEntregaDAO;
import VideoTeca.entidad.FacturaEntregaUser;

@WebServlet("/ServletFacturaEntregaJSON")
public class ServletFacturaEntregaJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ServletFacturaEntregaJSON() {
        super();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("accion");
		
		if (tipo.equals("fac"))
			facturaUser(request, response);
	}
	
	private void facturaUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idUsuario = request.getParameter("idUsuarioPag");
		
		int idUser = Integer.parseInt(idUsuario);
		
		System.out.print("ESTE ES EL ID PRA JSON FACTURA"+idUsuario);
		
		List<FacturaEntregaUser> facturaUser = new MySqlFacturaEntregaDAO().findAllFacturaEntrega(idUser);
		
	    // Imprimir los datos de cada factura recibida
	    for (FacturaEntregaUser factura : facturaUser) {
	        System.out.println("ID Factura: " + factura.getIdFacturaE());
	        System.out.println("Fecha: " + factura.getFechaFacturaE());
	        System.out.println("Empresa: " + factura.getEmpresaFacturaE());
	        // Imprime los demás campos de la factura de manera similar
	    }
		
		
       //crear objeto de la clase Gson
		Gson gson = new Gson();
		//convertir a JSON(STRING) el arreglo favorito
		String json = gson.toJson(facturaUser);
		//preparar salida en formato JSON
		response.setContentType("application/json;charset=UTF-8");
		//
		PrintWriter pw=response.getWriter();
		pw.print(json); 
	}
	
	

}
