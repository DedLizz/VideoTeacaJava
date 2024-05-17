<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="Estilos/MenuVideo.css" rel="stylesheet" type="text/css" media="all">

</head>
<body>


    <div class="header">
    
        <div class="user-info">
        	<a>${sessionScope.nombre}</a>
        	<a>${sessionScope.apellido}</a>
        	<div class="btnPaqueteHeader">
        		<a  id="botonPaquete" href="#"></a>
        	</div>
        </div>
        
        <div class="logo" id="logo"><a href="MenuHome.jsp">VideoTeca</a></div>

        
		<!-- Contenedor del botón de usuario -->
		<div class="tooltip" onmouseover="mostrarCerrarSesion()" onmouseout="ocultarCerrarSesion()">
		    <!-- Botón de usuario -->
		    <span class="user-btn">&#128100;</span>
		    <!-- Tooltip -->
		    <div class="tooltiptext" id="tooltip">
		        <!-- Botón de cerrar sesión -->
		        <button id="cerrar-sesion-btn" onclick="cerrarSesion()" onmouseover="mostrarCerrarSesion()" onmouseout="ocultarCerrarSesion()">Cerrar sesión</button>
		        <!-- Botón de información -->
		        <button id="cerrar-sesion-btn" onclick="redirigirUsuario()">Información</button>
		    </div>
		</div>
        
    </div>
    
    <div class="content">
    
    	<div class="contenedor-video">
	    	<div class="video">
	    		<jsp:include page="video.jsp" />
	    	</div>
        </div>
    
        <div class="sidebar">
            <div class="favoritos" >
               <jsp:include page="favoritos2.jsp" />
            </div>
        </div>
        
    </div>


    
    
    <script type="text/javascript">
    //fuciones para btn de cerrar cesion
    // Función para mostrar el botón de cerrar sesión en el tooltip
    function mostrarCerrarSesion() {
        var cerrarSesionBtn = document.getElementById("cerrar-sesion-btn");
        cerrarSesionBtn.style.display = "inline-block";
    }

    // Función para ocultar el botón de cerrar sesión en el tooltip
    function ocultarCerrarSesion() {
        var cerrarSesionBtn = document.getElementById("cerrar-sesion-btn");
        cerrarSesionBtn.style.display = "none";
    }

    // Función para redirigir a usuario.jsp
    function redirigirUsuario() {
        window.location.href = "infoUsuario.jsp";
    }

    // Función para enviar la solicitud a ServletUsuario con acción 'cerrar'
    function cerrarSesion() {
        // Aquí se puede hacer la llamada AJAX para enviar la solicitud al servlet
        // Por simplicidad, se redirige directamente a la URL deseada
        window.location.href = "ServletUsuario?accion=cerrar";
    }
    
    //funcion para el boton paquete
    window.onload = function() {
        var idTipoCuenta = "${sessionScope.idTipoCuenta}";
        var botonPaquete = document.getElementById("botonPaquete");

        if (idTipoCuenta === "1") {
            botonPaquete.innerHTML = "Free";
            botonPaquete.href = "paquetes2.jsp";
        } else {
            botonPaquete.innerHTML = "Premium";
        }
    };

    
    </script>
</body>
</html>