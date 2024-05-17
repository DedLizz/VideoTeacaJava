<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Lobster&display=swap">
<link href="Estilos/paquetes.css" rel="stylesheet" type="text/css" media="all">
<style type="text/css">
            #monto {
            width: 50px;
	    text-align: center;
	    font-weight: bold;
	    color: red;
            
        }
</style>
</head>
<body>
<div class="popup" id="popup">
  <div class="popup-content">
    <div class="card1" id="free">
        <h2>Free</h2>
        <ul>
            <li>limitado</li>
            <li>anuncios</li>
            <li>$/0.00</li>
        </ul>
        <button class="btnTar" onclick="openForm('Free', '0', '1')">Comprar</button>
    </div>
    <div class="card2" id="monthly">
        <h2>Premium Mensual</h2>
        <ul>
            <li>Ilimitado</li>
            <li>Sin anuncios</li>
            <li>$/10.00</li>
        </ul>
        <button class="btnTar" onclick="openForm('Premium Mensual', '10', '2')">Comprar</button>
    </div>
    <div class="card3" id="annual">
        <h2>Premium Anual</h2>
        <ul>
            <li>Ilimitado</li>
            <li>Sin anuncios</li>
            <li>$/75.00</li>
        </ul>
        <button class="btnTar" onclick="openForm('Premium Anual', '75', '3')">Comprar</button>
    </div>
  </div>
</div>

<div id="form-popup" class="conten-form">
    <div id="form-content">
        <h2 id="form-title"></h2>
        <!-- Aquí puedes agregar los campos del formulario -->
        <form id="myForm" method="post" action="ServletUsuario?accion=factura">
            <div id="boleta-campos" class="form">
                <!-- Campos específicos para boleta -->
                <label>Fecha: </label>
                <input type="date" id="fecha" name="fechaPag" placeholder="fecha" style="pointer-events: none; background-color: #f0f0f0;" ><br><br>
                <label>Precio: </label>
                <input type="text" id="monto" name="montoPag" placeholder="monto" style="pointer-events: none; background-color: #f0f0f0;" ><br><br>
                <input type="text" id="tipoId" name="tipCuentaPag" placeholder="TipoCuenta" style="display: none;"><br><br>
            </div>
            <div id="factura-campos" class="form">
                <!-- Campos específicos para factura -->
                <input type="text" id="rasonEmpresa" name="razonEmpresaPag" placeholder="Razonsocial Empresa"><br><br>
                <input type="text" id="rasonCliente" name="razonClientePag" placeholder="Razonsocial Cliente"><br><br>
                <input type="text" id="direcion" name="direccionPag" placeholder="dirección"><br><br>
                <input type="text" id="igv" name="igvPag" placeholder="IGV" value="18" readonly style="pointer-events: none; background-color: #f0f0f0; display: none;"><br><br>
                
            </div>
            <button class="btn1" type="submit">Enviar</button>
            <button class="btn2" type="button" onclick="closeForm()">Cerrar</button>
		    <button class="btn3" type="button" onclick="toggleTipo()">
		      <h6>Boleta</h6>
			  <img class="imgImage" src="imagenes/sincronizar.png" alt="Descripción de la imagen">
			  <h6>Factura</h6>
			</button>
        </form>
    </div>
</div>

<script>
    function openForm(cardType, monto, tipoId) {
        document.getElementById("popup").style.display = "none";
        document.getElementById("form-popup").style.display = "flex";
        document.getElementById("form-title").innerText = cardType;
        document.getElementById("monto").value = monto; // Establecer el valor del campo monto en el formulario
        document.getElementById("tipoId").value = tipoId;
    }

    function closeForm() {
        document.getElementById("popup").style.display = "flex";
        document.getElementById("form-popup").style.display = "none";
    }
    
    /*alternancia de boleta o factura*/
    function toggleTipo() {
        var facturaCampos = document.getElementById("factura-campos");
        var formulario = document.getElementById("myForm");

        if (facturaCampos.style.display === "none") {
            facturaCampos.style.display = "block";
            formulario.action = "ServletUsuario?accion=factura";
        } else {
            facturaCampos.style.display = "none";
            formulario.action = "ServletUsuario?accion=boleta";
        }
    }
</script>

<script type="text/javascript">
        function setCurrentDate() {
            // Obtener la fecha actual
            var today = new Date();
            
            // Formatear la fecha a YYYY-MM-DD
            var dd = String(today.getDate()).padStart(2, '0');
            var mm = String(today.getMonth() + 1).padStart(2, '0'); // Enero es 0
            var yyyy = today.getFullYear();
            
            var formattedDate = yyyy + '-' + mm + '-' + dd;
            
            // Establecer la fecha en el campo de entrada
            document.getElementById('fecha').value = formattedDate;
        }

        // Llamar a la función setCurrentDate cuando la página se carga
        window.onload = setCurrentDate;
</script>



</body>
</html>