<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="Estilos/infoUsuario.css" rel="stylesheet" type="text/css" media="all">

</head>



<body>

	
    <div class="header">
    
        <div class="user-info">
        	<a>${sessionScope.nombre}</a>
        	<a>${sessionScope.apellido}</a>
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
		    </div>
		</div>
        
    </div>

    <div class="sidebar">
        <button id="user-data-btn">Datos Usuario</button>
        <button id="plan-info-btn">Info Plan</button>
    </div>
    <div class="main-content">
        <div id="user-data-container" class="user" style="display: none;">
            <h2>Datos Usuario</h2>
		    <p class="info-container">Nombre: <h4>${sessionScope.nombre}</h4></p>
		    <p class="info-container">Apellido: <h4>${sessionScope.apellido}</h4></p>
		    <p class="info-container">Email: <h4>${sessionScope.email}</h4></p>
		    <p class="info-container">Tipo Cuenta: 
			    <h4>
			        <c:choose>
			            <c:when test="${sessionScope.idTipoCuenta == 1}">
			                FREE
			            </c:when>
			            <c:otherwise>
			                Premium
			            </c:otherwise>
			        </c:choose>
			    </h4>
		    </p>
        </div>
        <div class="conten-boleta" id="plan-info-container" style="display: none;">
        	<h2>Informacion Plan</h2>
         	<div class="btnSwitch">
	        	<label class="switch">
	               <input type="checkbox" id="toggle-btn">
	               <span class="slider"></span>
	           </label>
	           <h3>Factura / Boleta</h3>
         	</div>

            
            <div class="table-container">
                <div class="factura" id="facturaDatos"></div>
                <div class="boleta" id="boletaDatos"></div>
                <table id="boleta-table" style="display: none;">
                    <tr>
                        <th>Boleta</th>
                    </tr>
                    <tr>
                        <td>Detalle 1</td>
                    </tr>
                    <tr>
                        <td>Detalle 2</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

<script>
    // Add event listeners to the buttons
    document.getElementById('user-data-btn').addEventListener('click', () => {
        document.getElementById('user-data-container').style.display = 'block';
        document.getElementById('plan-info-container').style.display = 'none';
    });

    document.getElementById('plan-info-btn').addEventListener('click', () => {
        document.getElementById('user-data-container').style.display = 'none';
        document.getElementById('plan-info-container').style.display = 'block';
    });

    // Add event listener to the toggle button
    document.getElementById('toggle-btn').addEventListener('change', (event) => {
        const facturaTable = document.getElementById('facturaDatos');
        const boletaTable = document.getElementById('boletaDatos');
        if (event.target.checked) {
            facturaTable.style.display = 'none';
            boletaTable.style.display = 'block';
        } else {
            facturaTable.style.display = 'block';
            boletaTable.style.display = 'none';
        }
    });
</script>
    
<!-- este se encarga del boton de cerrar sesion -->    
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


    // Función para enviar la solicitud a ServletUsuario con acción 'cerrar'
    function cerrarSesion() {
        // Aquí se puede hacer la llamada AJAX para enviar la solicitud al servlet
        // Por simplicidad, se redirige directamente a la URL deseada
        window.location.href = "ServletUsuario?accion=cerrar";
    }
</script>

<!-- este es carga la factura de cliente -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    facturaUser(); // Llamada a la función facturaUser al cargar la página
    function facturaUser(){
        var idUser = "${sessionScope.id}"; // Obtener el ID de usuario de la sesión
        $.ajax({
            type: "GET",
            url: "ServletFacturaEntregaJSON?accion=fac",
            data: { idUsuarioPag: idUser }, // Pasar el ID de usuario real, no la cadena "idUser"
            success: function(response){
                var facturaDatos = '';
                // Construir una lista con los datos de la factura recibida
                facturaDatos += '<ul>';
                $.each(response, function(index, factura){
                    facturaDatos += '<li>';
                    facturaDatos += 'ID Factura: ' + factura.idFacturaE + '<br>';
                    facturaDatos += 'Fecha: ' + factura.fechaFacturaE + '<br>';
                    facturaDatos += 'Razon social Empresa: ' + factura.empresaFacturaE + '<br>';
                    facturaDatos += 'Razon social Cliente: ' + factura.clienteFacturaE + '<br>';
                    facturaDatos += 'Direccion: ' + factura.direccionFacturaE + '<br>';
                    facturaDatos += 'Monto: ' + factura.montoFacturaE + '<br>';
                    facturaDatos += 'IGV: ' + factura.igvFacturaE + '<br>';
                    facturaDatos += 'Nombre Cliente: ' + factura.userFactruaE + '<br>';
                    facturaDatos += 'Apellido Cliente: ' + factura.apellidoFacturaE + '<br>';
                    facturaDatos += 'Correo del Cliente: ' + factura.emailFacturaE + '<br>';
                    facturaDatos += 'Paquete: ' + factura.tipoCuentaFacturaE + '<br>';
                    facturaDatos += 'Total: ' + factura.costoFacturaE + '<br>';
                    facturaDatos += '</li>';
                });
                facturaDatos += '</ul>';
                // Agregar la lista al div con id "facturaDatos"
                $('#facturaDatos').html(facturaDatos);
            }
        });
    }
</script>

<!-- este es carga la boleta de cliente -->
<script>

        boletasUser(); 
 

    function boletasUser(){
        var idUser = "${sessionScope.id}"; 
        $.ajax({
            type: "GET",
            url: "ServletBoletaEntregaJSON?accion=bol",
            data: { idUsuarioPag: idUser }, 
            success: function(response){
                var boletaDatos = ''; 
                boletaDatos += '<ul>';
                $.each(response, function(index, boleta){
                    boletaDatos += '<li>';
                    boletaDatos += 'ID Boleta: ' + boleta.idBoletaE + '<br>';
                    boletaDatos += 'Fecha: ' + boleta.fechaBoletaE + '<br>';
                    boletaDatos += 'Monto: ' + boleta.montoBoletaE + '<br>';
                    boletaDatos += 'Nombre Cliente: ' + boleta.nameuserBoletaE + '<br>';
                    boletaDatos += 'Apellido Cliente: ' + boleta.apellidouserBoletaE + '<br>';
                    boletaDatos += 'Correo del Cliente: ' + boleta.emailBoletaE + '<br>';
                    boletaDatos += 'Paquete: ' + boleta.tipoCuentaBoletaE + '<br>';
                    boletaDatos += 'Total: ' + boleta.costoBoletaE + '<br>';
                    boletaDatos += '</li>';
                });
                boletaDatos += '</ul>';
                $('#boletaDatos').html(boletaDatos);
            }
        });
    }
</script>

</body>
</html>