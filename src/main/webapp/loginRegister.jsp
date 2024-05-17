<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <title>CodePen - Sign up / Login Form</title>
 <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="EstiloLogin/stiloss.css">

</head>
<body>


<div class="main">
    <input type="checkbox" id="chk" aria-hidden="true">

    <div class="signup">
        <form id="register-form" method="post" action="ServletUsuario?accion=register" onsubmit="return validateRegisterForm()">
            <label for="chk" aria-hidden="true">Registro</label>
            <input type="text" id="nombreRPag" name="nombreRPag" placeholder="Nombre" required>
            <input type="text" id="apellidoRPag" name="apellidoRPag" placeholder="Apellido" required>
            <input type="email" id="emailRPag" name="emailRPag" placeholder="Email" required>
            <input type="password" id="contraRPag" name="contraRPag" placeholder="Password" required>
            <button type="submit">Procesar</button>
        </form>
    </div>

    <div class="login">
        <form id="register-login" method="post" action="ServletUsuario?accion=login" onsubmit="return validateLoginForm()">
            <label for="chk" aria-hidden="true">login</label>
            <input type="email" id="EmailPagLog" name="EmailPagLog" placeholder="Email" required>
            <input type="password" id="ContraPagLog" name="ContraPagLog" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
    </div>
</div>

<script>
function validateRegisterForm() {
    var nombre = document.getElementById('nombreRPag').value;
    var apellido = document.getElementById('apellidoRPag').value;
    var email = document.getElementById('emailRPag').value;
    var password = document.getElementById('contraRPag').value;

    if (!/^[A-Za-z]+$/.test(nombre)) {
        alert('El nombre solo debe contener letras.');
        return false;
    }
    if (!/^[A-Za-z]+$/.test(apellido)) {
        alert('El apellido solo debe contener letras.');
        return false;
    }
    if (password.length < 6) {
        alert('La contraseña debe tener al menos 6 caracteres.');
        return false;
    }
    return true;
}

function validateLoginForm() {
    var email = document.getElementById('EmailPagLog').value;
    var password = document.getElementById('ContraPagLog').value;

    if (email === "") {
        alert('Por favor ingrese un correo electrónico.');
        return false;
    }
    if (password === "") {
        alert('Por favor ingrese una contraseña.');
        return false;
    }
    return true;
}
</script>

	

</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
cargarTiposCuenta()

function cargarTiposCuenta() {
    $.get("ServletTipoCuentaJSON", function(response) {
        // Limpiar el select antes de agregar nuevas opciones
        $("#id-grado").empty();

        // Agregar una opción por cada tipo de cuenta
        $.each(response, function(index, tipoCuenta) {
            var option = '<option value="' + tipoCuenta.idTipoCuenta + '">' + tipoCuenta.tipoCuenta + '</option>';
            $("#id-grado").append(option);
        });
    });
}
</script>




</body>

</html>
