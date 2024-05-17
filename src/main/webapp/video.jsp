<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="Estilos/video.css" rel="stylesheet" type="text/css" media="all">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


</head>
<body>
    <div class="container">
        <div class="video-container">
            <iframe class="video" src="<%= session.getAttribute("urlVideo") %>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            <h2 class="video-title">Título: <%= session.getAttribute("tituloVideo") %></h2>
            <div class="botones">
                <!-- Modificar los botones para que pasen los parámetros al servlet -->
                <button class="add-to-favorites-btn add" onclick="agregarFavorito('<%= session.getAttribute("id") %>', '<%= session.getAttribute("idVideo") %>')">Agregar a Favoritos</button>
                <button class="add-to-favorites-btn remove" onclick="eliminarFavorito('<%= session.getAttribute("id") %>', '<%= session.getAttribute("idVideo") %>')">Eliminar de Favoritos</button>
            </div>
        </div>
        <div class="description-container">
            <p class="video-description">Descripción: <%= session.getAttribute("descripcionVideo") %></p>
            <!-- Imprimir los valores de sesión para depuración -->
            <p>ID de Usuario: <%= session.getAttribute("id") %></p>
            <p>ID de Video: <%= session.getAttribute("idVideo") %></p>
            <h1 id="estadoFavorito">Verificando favorito...</h1>
        </div>
    </div>
 
    <script>
        function agregarFavorito(idUsuario, idVideo) {
            // Crear la URL con los parámetros
            var url = "ServletFavorito?accion=agregar&idUsuarioPag=" + idUsuario + "&idVideoPag=" + idVideo;
            // Realizar la solicitud GET
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al agregar a favoritos');
                    }
                    console.log('Video agregado a favoritos');
                    document.querySelector('.add-to-favorites-btn.add').style.display = 'none';
                    document.querySelector('.add-to-favorites-btn.remove').style.display = 'block';
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }

        function eliminarFavorito(idUsuario, idVideo) {
            // Crear la URL con los parámetros
            var url = "ServletFavorito?accion=eliminar&idUsuarioPag=" + idUsuario + "&idVideoPag=" + idVideo;
            // Realizar la solicitud GET
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al eliminar de favoritos');
                    }
                    console.log('Video eliminado de favoritos');
                    document.querySelector('.add-to-favorites-btn.add').style.display = 'block';
                    document.querySelector('.add-to-favorites-btn.remove').style.display = 'none';
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }

        $(document).ready(function() {
            consultaExistenciaFavorito();
        });

        function consultaExistenciaFavorito() {
            var idUsuario = '<%= session.getAttribute("id") %>';
            var idVideo = '<%= session.getAttribute("idVideo") %>';

            // Hacer una solicitud AJAX para verificar si el video es favorito
            $.ajax({
                url: 'ServletFavorito?accion=btnFavoritos',
                type: 'GET',
                data: { idUsuarioPag: idUsuario, idVideoPag: idVideo },
                dataType: 'json',
                success: function(response) {
                    // Manejar la respuesta JSON
                    console.log(response); // Imprimir en consola la respuesta
                    var mensaje = response ? 'Este video es uno de tus favoritos' : 'Este video no es uno de tus favoritos';
                    $('#estadoFavorito').text(mensaje);

                    if (response) {
                        document.querySelector('.add-to-favorites-btn.add').style.display = 'none';
                        document.querySelector('.add-to-favorites-btn.remove').style.display = 'block';
                    } else {
                        document.querySelector('.add-to-favorites-btn.add').style.display = 'block';
                        document.querySelector('.add-to-favorites-btn.remove').style.display = 'none';
                    }
                },
                error: function(xhr, status, error) {
                    // Manejar errores
                    console.error('Error al verificar favorito:', xhr.responseText);
                }
            });
        }
    </script>
</body>
    

</html>