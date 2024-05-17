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

import VideoTeca.dao.MySqlBoletaDAO;
import VideoTeca.dao.MySqlFacturaDAO;
import VideoTeca.dao.MySqlUsuarioDAO;
import VideoTeca.entidad.Boleta;
import VideoTeca.entidad.Factura;
import VideoTeca.entidad.Usuario;


@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Usuario usuarioRegistrado;
	

	
    public ServletUsuario() {
        super();
    }

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String tipo = request.getParameter("accion");
		
		if(tipo.equals("lista"))
			listarUsuarios(request, response);
		else if (tipo.equals("grabar"))
			registroGrabar(request,response);
		else if (tipo.equals("eliminar"))
			eliminarUsuario(request, response);
		else if (tipo.equals("login"))
			iniciarLogin(request, response);
		else if (tipo.equals("cerrar"))
			cerrarLogin(request, response);
		else if (tipo.equals("register"))
			paqueteUsuario(request, response);
		else if (tipo.equals("boleta"))
			boletaUsuairoRegister(request, response);
		else if (tipo.equals("factura"))
			facturaUsuairoRegister(request, response);
		else if (tipo.equals("tipUpdateBol"))
			updateTipBoleta(request, response);
		else if (tipo.equals("tipUdateFac"))
			updateTipFactura(request, response);
		
	}

	
	
	protected void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> lista = null;
        try {
            lista = new MySqlUsuarioDAO().findAllUsuario();
            System.out.println("Datos de usuarios obtenidos correctamente de la base de datos:");
            if (lista != null) {
                for (Usuario usuario : lista) {
                    System.out.println("ID Usuario: " + usuario.getIdUsuario());
                    System.out.println("Nombre: " + usuario.getNombreUsuario());
                    System.out.println("Apellido: " + usuario.getApellidoUsuario());
                    System.out.println("Email: " + usuario.getEmailUsuaio());
                    System.out.println("Tipo de Cuenta: " + usuario.getTipoCuentaUsuario());
                    System.out.println("---------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al obtener los datos de usuarios de la base de datos: " + e.getMessage());
        }
        
        request.setAttribute("listaUsuarios", lista);
        request.getRequestDispatcher("/usuario2.jsp").forward(request, response);
	}
	

	
	protected void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idUsuario;
		idUsuario = request.getParameter("idUsuarioPag");
		
		int estado = new MySqlUsuarioDAO().deleteById(Integer.parseInt(idUsuario));
		
		if (estado == 1)
			System.out.println("SI");
		else 
			System.out.println("NO");
		
		request.getSession().setAttribute("MENSAJE", "Persona eliminada");
	}
	
	
	
	private void iniciarLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String EmailLog, ContraLog;
		EmailLog = request.getParameter("EmailPagLog");
		ContraLog = request.getParameter("ContraPagLog");
		
		//valida si el usuario y la clave coninciden
		Usuario usu = new MySqlUsuarioDAO().iniciarSesion(EmailLog, ContraLog);
		
		if (usu == null) {
			//atributo de tipo sesion
			request.getSession().setAttribute("MENSAJE", "Usuario y/o clave incorrectos");
			response.sendRedirect("loginRegister.jsp");
		} else {
			// Se obtienen los datos del usuario que inició sesión
			int idUsuario = usu.getIdUsuario();
            String nombre = usu.getNombreUsuario();
            String apellido = usu.getApellidoUsuario();
            String email = usu.getEmailUsuaio();
            int idTipoCuenta = usu.getTipoCuentaUsuario();

            // Se almacenan los datos del usuario en la sesión HTTP
            HttpSession session = request.getSession();
            session.setAttribute("id", idUsuario);
            session.setAttribute("nombre", nombre);
            session.setAttribute("apellido", apellido);
            session.setAttribute("email", email);
            session.setAttribute("idTipoCuenta", idTipoCuenta);
            


            // Redireccionar a la página de inicio o a alguna otra página de tu aplicación
            response.sendRedirect("MenuHome.jsp");
		}
	}
		
		
		
	private void cerrarLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//obtener la sesión actual
		HttpSession session=request.getSession();
		//invalidar todos los atributos de tipo sesión
		session.invalidate();
		request.getSession().setAttribute("MENSAJE", "Sesión terminada");
		response.sendRedirect("index.jsp");
		
	}
	
	private void registroGrabar(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    // valores recuperados de resgistro
		String nombreR, apellidoR, emailR, contraR, tipoCuentaR;
		nombreR = request.getParameter("nombreRPag");
		apellidoR = request.getParameter("apellidoRPag");
		emailR = request.getParameter("emailRPag");
		contraR = request.getParameter("contraRPag");
		tipoCuentaR = request.getParameter("tipoCuentaRPag");
		
		//crear objeto usuario
		Usuario user = new Usuario();
		
		//setar los atributos del objeto usuario
		user.setNombreUsuario(nombreR);
		user.setApellidoUsuario(apellidoR);
		user.setEmailUsuaio(emailR);
		user.setContraUsuario(contraR);
		user.setTipoCuentaUsuario(Integer.parseInt(tipoCuentaR));
		
		//invocar metodo save y enviar el objeto
		int estado = new MySqlUsuarioDAO().save(user);
		
		if (estado == 1) {
		    if (tipoCuentaR.equals("1")) { // Corregido aquí
		        response.sendRedirect("MenuHome.jsp");
		    } else {
		        response.sendRedirect("paquetes.jsp");
		    }
		    // La siguiente línea es problemática y debe ser eliminada
		    // response.sendRedirect("Personas.jsp");
		    System.out.print("usuario registrado");
		} else {
		    System.out.print("error al registrar");
		}
	
	}
	
	//recibir los datos de usuario del registro sin tipo cuenta
	private void paqueteUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    // Obtener datos del formulario
	    String nombre = request.getParameter("nombreRPag");
	    String apellido = request.getParameter("apellidoRPag");
	    String email = request.getParameter("emailRPag");
	    String contra = request.getParameter("contraRPag");

	    // Crear un nuevo usuario con los datos del formulario
	    Usuario usuario = new Usuario();
	    usuario.setNombreUsuario(nombre);
	    usuario.setApellidoUsuario(apellido);
	    usuario.setEmailUsuaio(email);
	    usuario.setContraUsuario(contra);

	    // Asignar el usuario registrado
	    usuarioRegistrado = usuario;

	    // Imprimir los datos del usuario registrado
	    System.out.println(usuarioRegistrado.getIdUsuario());
	    System.out.println(usuarioRegistrado.getNombreUsuario());
	    System.out.println(usuarioRegistrado.getApellidoUsuario());
	    System.out.println(usuarioRegistrado.getEmailUsuaio());
	    System.out.println(usuarioRegistrado.getContraUsuario());
	    System.out.println(usuarioRegistrado.getTipoCuentaUsuario());

	    // Redirigir a la página paquete.jsp
	    response.sendRedirect("paquetes.jsp");
	}
	
	
	// Crear una boleta y del usuario
	private void boletaUsuairoRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int tipCuenta = Integer.parseInt(request.getParameter("tipCuentaPag"));

	    // Verificar que usuarioRegistrado no sea nulo antes de asignarle el tipo de cuenta
	    if(usuarioRegistrado != null) {
	        usuarioRegistrado.setTipoCuentaUsuario(tipCuenta);
	        
	        // Imprimir los datos del usuario registrado
	        System.out.println(usuarioRegistrado.getIdUsuario());
	        System.out.println(usuarioRegistrado.getNombreUsuario());
	        System.out.println(usuarioRegistrado.getApellidoUsuario());
	        System.out.println(usuarioRegistrado.getEmailUsuaio());
	        System.out.println(usuarioRegistrado.getContraUsuario());
	        System.out.println(usuarioRegistrado.getTipoCuentaUsuario());
	        
	        // Guardar el nuevo usuario en la base de datos
	        int estado = new MySqlUsuarioDAO().save(usuarioRegistrado);
	        
	        if (estado == 1) {
	            // Si el usuario se guardó correctamente
	            String emailUsuario = usuarioRegistrado.getEmailUsuaio();
	            String contraUsuario = usuarioRegistrado.getContraUsuario();
	            
	            // Intentar iniciar sesión con los datos del usuario guardado
	            Usuario usuarioIniciado = new MySqlUsuarioDAO().iniciarSesion(emailUsuario, contraUsuario);
	            
	            if (usuarioIniciado == null) {
	                // Manejar el caso en que el usuario no se pueda iniciar sesión
	                System.out.println("No se pudo iniciar sesión con el usuario guardado.");
	            } else {
	                // Almacenar los datos del usuario en la sesión HTTP
	                HttpSession session = request.getSession();
	                session.setAttribute("id", String.valueOf(usuarioIniciado.getIdUsuario()));
	                session.setAttribute("nombre", usuarioIniciado.getNombreUsuario());
	                session.setAttribute("apellido", usuarioIniciado.getApellidoUsuario());
	                session.setAttribute("email", usuarioIniciado.getEmailUsuaio());
	                session.setAttribute("idTipoCuenta", usuarioIniciado.getTipoCuentaUsuario());
	                

	                // Crear y almacenar la boleta
	                String fecha = request.getParameter("fechaPag");
	                int monto = Integer.parseInt(request.getParameter("montoPag"));
	                int codigoUsu = usuarioIniciado.getIdUsuario();
	                int tipoCuenta = usuarioIniciado.getTipoCuentaUsuario();
	                
	                Boleta boleta = new Boleta();
	                boleta.setFechaBoleta(fecha);
	                boleta.setMontoBoleta(monto);
	                boleta.setIdUserBoleta(codigoUsu);
	                boleta.setIdTipoCuentaBoleta(tipoCuenta);
	                
	                System.out.println("Boleta guardada correctamente:");
	                System.out.println("Fecha: " + boleta.getFechaBoleta());
	                System.out.println("Monto: " + boleta.getMontoBoleta());
	                System.out.println("ID Usuario: " + boleta.getIdUserBoleta());
	                System.out.println("ID Tipo Cuenta: " + boleta.getIdTipoCuentaBoleta());
	                
	                int estadoBoleta = new MySqlBoletaDAO().save(boleta);
	                
	                // Manejar el estado de la boleta guardada
	                if (estadoBoleta == 1) {
	                    System.out.println("Boleta guardada correctamente.");
	                    response.sendRedirect("MenuHome.jsp");
	                    
	                } else {
	                    System.out.println("Error al guardar la boleta.");
	                }
	            }
	        } else {
	            System.out.println("Error al guardar el usuario.");
	        }
	    } else {
	        System.out.println("No hay usuario registrado para crear la boleta.");
	    }
	}
	
	//crear una factura y del usuario
	private void facturaUsuairoRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int tipCuenta = Integer.parseInt(request.getParameter("tipCuentaPag"));

	    // Verificar que usuarioRegistrado no sea nulo antes de asignarle el tipo de cuenta
	    if(usuarioRegistrado != null) {
	        usuarioRegistrado.setTipoCuentaUsuario(tipCuenta);
	        
	        // Imprimir los datos del usuario registrado
	        System.out.println(usuarioRegistrado.getIdUsuario());
	        System.out.println(usuarioRegistrado.getNombreUsuario());
	        System.out.println(usuarioRegistrado.getApellidoUsuario());
	        System.out.println(usuarioRegistrado.getEmailUsuaio());
	        System.out.println(usuarioRegistrado.getContraUsuario());
	        System.out.println(usuarioRegistrado.getTipoCuentaUsuario());
	        
	        // Guardar el nuevo usuario en la base de datos
	        int estado = new MySqlUsuarioDAO().save(usuarioRegistrado);
	        
	        if (estado == 1) {
	            // Si el usuario se guardó correctamente
	            String emailUsuario = usuarioRegistrado.getEmailUsuaio();
	            String contraUsuario = usuarioRegistrado.getContraUsuario();
	            
	            // Intentar iniciar sesión con los datos del usuario guardado
	            Usuario usuarioIniciado = new MySqlUsuarioDAO().iniciarSesion(emailUsuario, contraUsuario);
	            
	            if (usuarioIniciado == null) {
	                // Manejar el caso en que el usuario no se pueda iniciar sesión
	                System.out.println("No se pudo iniciar sesión con el usuario guardado.");
	            } else {
	                // Almacenar los datos del usuario en la sesión HTTP
	                HttpSession session = request.getSession();
	                session.setAttribute("id", String.valueOf(usuarioIniciado.getIdUsuario()));
	                session.setAttribute("nombre", usuarioIniciado.getNombreUsuario());
	                session.setAttribute("apellido", usuarioIniciado.getApellidoUsuario());
	                session.setAttribute("email", usuarioIniciado.getEmailUsuaio());
	                session.setAttribute("idTipoCuenta", usuarioIniciado.getTipoCuentaUsuario());
	                
	
	                // Crear y almacenar la factura
	                String fecha = request.getParameter("fechaPag");
	                int monto = Integer.parseInt(request.getParameter("montoPag"));
	                int codigoUsu = usuarioIniciado.getIdUsuario();
	                int tipoCuenta = usuarioIniciado.getTipoCuentaUsuario();
	                String razonEmpresa = request.getParameter("razonEmpresaPag");
	                String razonCliente = request.getParameter("razonClientePag");
	                String direccion = request.getParameter("direccionPag");
	                int igv = Integer.parseInt(request.getParameter("igvPag"));
	                
	                Factura factura = new Factura();
	                factura.setFechaFactura(fecha);
	                factura.setMontoFactura(monto);
	                factura.setIdUsuarioFactura(codigoUsu);
	                factura.setIdTipoCuentaFactura(tipoCuenta);
	                factura.setRazonEFactura(razonEmpresa);
	                factura.setRazonCFactura(razonCliente);
	                factura.setDireccionFactura(direccion);
	                factura.setIgv(igv);
	                
	                System.out.println("Factura guardada correctamente:");
	                System.out.println("Fecha: " + factura.getFechaFactura());
	                System.out.println("Monto: " + factura.getMontoFactura());
	                System.out.println("ID Usuario: " + factura.getIdUsuarioFactura());
	                System.out.println("ID Tipo Cuenta: " + factura.getIdTipoCuentaFactura());
	                
	                System.out.println("razon Empresa: " + factura.getRazonEFactura());
	                System.out.println("razon Cliente: " + factura.getRazonCFactura());
	                System.out.println("Dirección: " + factura.getDireccionFactura());
	                System.out.println("igv: " + factura.getIgv());
	                
	                int estadoFactura = new MySqlFacturaDAO().save(factura);
	                
	                // Manejar el estado de la factura guardada
	                if (estadoFactura == 1) {
	                    System.out.println("factura guardada correctamente.");
	                    response.sendRedirect("MenuHome.jsp");
	                    
	                } else {
	                    System.out.println("Error al guardar la factura.");
	                }
	            }
	        } else {
	            System.out.println("Error al guardar el usuario.");
	        }
	    } else {
	        System.out.println("No hay usuario registrado para crear la factura.");
	    }
	
	}
	
	private void updateTipBoleta(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int idUsuario = Integer.parseInt(request.getParameter("idUserPag"));
		int tipCuenta = Integer.parseInt(request.getParameter("tipCuentaPag"));
		
		int estado = new MySqlUsuarioDAO().updateTipoCuenta(idUsuario, tipCuenta);
		
		
		if (estado == 1) {
            // Crear y almacenar la boleta
            String fecha = request.getParameter("fechaPag");
            int monto = Integer.parseInt(request.getParameter("montoPag"));
            int codigoUsu = idUsuario;
            int tipoCuenta = tipCuenta;
            
            Boleta boleta = new Boleta();
            boleta.setFechaBoleta(fecha);
            boleta.setMontoBoleta(monto);
            boleta.setIdUserBoleta(codigoUsu);
            boleta.setIdTipoCuentaBoleta(tipoCuenta);
            
            System.out.println("Boleta guardada Editado:");
            System.out.println("Fecha: " + boleta.getFechaBoleta());
            System.out.println("Monto: " + boleta.getMontoBoleta());
            System.out.println("ID Usuario: " + boleta.getIdUserBoleta());
            System.out.println("ID Tipo Cuenta: " + boleta.getIdTipoCuentaBoleta());
            
            int estadoBoleta = new MySqlBoletaDAO().save(boleta);
            
            // Manejar el estado de la boleta guardada
            if (estadoBoleta == 1) {
                System.out.println("Boleta guardada correctamente.");
                
                Usuario usuarioIniciado = new MySqlUsuarioDAO().findUsuarioById(idUsuario);
                
                if (usuarioIniciado == null) {
	                System.out.println("No se pudo iniciar sesión con el usuario guardado.");
	            } else {
	            	//finalizar la sesion anterior
	        		HttpSession session=request.getSession();
	        		//invalidar todos los atributos de tipo sesión
	        		session.invalidate();
	        		
	            	// Almacenar los datos del usuario en la sesión HTTP
	                HttpSession session2 = request.getSession();
	                session2.setAttribute("id", String.valueOf(usuarioIniciado.getIdUsuario()));
	                session2.setAttribute("nombre", usuarioIniciado.getNombreUsuario());
	                session2.setAttribute("apellido", usuarioIniciado.getApellidoUsuario());
	                session2.setAttribute("email", usuarioIniciado.getEmailUsuaio());
	                session2.setAttribute("idTipoCuenta", usuarioIniciado.getTipoCuentaUsuario());
	            }
                
                
                
                response.sendRedirect("MenuHome.jsp");
                
            } else {
                System.out.println("Error al guardar la boleta.");
            }
		}
		
		
	}
	
	private void updateTipFactura(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int idUsuario = Integer.parseInt(request.getParameter("idUserPag"));
		int tipCuenta = Integer.parseInt(request.getParameter("tipCuentaPag"));
		
		int estado = new MySqlUsuarioDAO().updateTipoCuenta(idUsuario, tipCuenta);
		
		
		if (estado == 1) {
            // Crear y almacenar la factura
            String fecha = request.getParameter("fechaPag");
            int monto = Integer.parseInt(request.getParameter("montoPag"));
            int codigoUsu = idUsuario;
            int tipoCuenta = tipCuenta;
            String razonEmpresa = request.getParameter("razonEmpresaPag");
            String razonCliente = request.getParameter("razonClientePag");
            String direccion = request.getParameter("direccionPag");
            int igv = Integer.parseInt(request.getParameter("igvPag"));
            
            Factura factura = new Factura();
            factura.setFechaFactura(fecha);
            factura.setMontoFactura(monto);
            factura.setIdUsuarioFactura(codigoUsu);
            factura.setIdTipoCuentaFactura(tipoCuenta);
            factura.setRazonEFactura(razonEmpresa);
            factura.setRazonCFactura(razonCliente);
            factura.setDireccionFactura(direccion);
            factura.setIgv(igv);
            
            System.out.println("Factura guardada correctamente:");
            System.out.println("Fecha: " + factura.getFechaFactura());
            System.out.println("Monto: " + factura.getMontoFactura());
            System.out.println("ID Usuario: " + factura.getIdUsuarioFactura());
            System.out.println("ID Tipo Cuenta: " + factura.getIdTipoCuentaFactura());
            
            System.out.println("razon Empresa: " + factura.getRazonEFactura());
            System.out.println("razon Cliente: " + factura.getRazonCFactura());
            System.out.println("Dirección: " + factura.getDireccionFactura());
            System.out.println("igv: " + factura.getIgv());
            
            int estadoFactura = new MySqlFacturaDAO().save(factura);
            
            // Manejar el estado de la factura guardada
            if (estadoFactura == 1) {
                System.out.println("factura guardada correctamente.");
                
                Usuario usuarioIniciado = new MySqlUsuarioDAO().findUsuarioById(idUsuario);
                
                if (usuarioIniciado == null) {
	                System.out.println("No se pudo iniciar sesión con el usuario guardado.");
	            } else {
	            	//finalizar la sesion anterior
	        		HttpSession session=request.getSession();
	        		//invalidar todos los atributos de tipo sesión
	        		session.invalidate();
	        		
	            	// Almacenar los datos del usuario en la sesión HTTP
	                HttpSession session2 = request.getSession();
	                session2.setAttribute("id", String.valueOf(usuarioIniciado.getIdUsuario()));
	                session2.setAttribute("nombre", usuarioIniciado.getNombreUsuario());
	                session2.setAttribute("apellido", usuarioIniciado.getApellidoUsuario());
	                session2.setAttribute("email", usuarioIniciado.getEmailUsuaio());
	                session2.setAttribute("idTipoCuenta", usuarioIniciado.getTipoCuentaUsuario());
	            }
                
                
                response.sendRedirect("MenuHome.jsp");
                
            } else {
                System.out.println("Error al guardar la factura.");
            }
		}
		
		
	}
	
	
	
	
	

}
