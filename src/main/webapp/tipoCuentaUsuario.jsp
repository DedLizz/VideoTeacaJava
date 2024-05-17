<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de paquetes</title>
    <!-- Enlaces a las bibliotecas de estilos de Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1 class="mt-5 text-center">Listado Paquetes</h1>
        <div class="mt-3">
            <table id="TablePaquete" class="table table-striped" style="width: 100%">
                <thead>
                    <tr>
                        <th>ID Tipo Cuenta</th>
                        <th>Tipo Paquete</th>
                        <th>Costo</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listatipoCuenta}" var="item">
                        <tr>
                            <td>${item.idTipoCuenta}</td>
                            <td>${item.tipoCuenta}</td>
                            <td>${item.costo}</td>
                            <td><button type="button" class="btn btn-success btn-editar" data-bs-toggle="modal" data-bs-target="#exampleModal">Editar</button></td>
                            <td><button type="button" class="btn btn-danger btn-eliminar" id="btnEliminar" onclick="eliminarCliente(${row.idTipoCuenta})">Eliminar</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!-- Scripts de Bootstrap (jQuery primero, luego Popper.js y finalmente Bootstrap) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>