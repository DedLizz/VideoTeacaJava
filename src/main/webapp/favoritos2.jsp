<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="Estilos/favoritos2.css" rel="stylesheet" type="text/css" media="all">

<style type="text/css">


.video-conta {
    background: black;
    text-align: center;
    border-radius: 30px;
    margin-top: 10px;
}

.video-conta p{
display: none;
}

.video-conta a {
  text-decoration: none;
  font-weight: bold;
  color: #e1ff00;
  transition: color 0.3s, text-decoration 0.3s; /* Transición suave para los cambios */
}

.video-conta a:hover {
  color: #ffcc00; /* Cambia el color al pasar el ratón */
  text-decoration: underline; /* Sube una línea al pasar el ratón */
}

.video-conta iframe {
    border-top-left-radius: 30px;
    border-top-right-radius: 30px;
}

.botones-l h3{
margin-right: 5px;
color: yellow;
}

</style>


</head>
<body>




<div class="container">
 	<div class="botones-categoria">
        <div class="botones-l">
        	<h3>Favoritos: </h3>
            <select id="selectCategorias"></select>
        </div>
    </div>
    <div class="lita-videos">
        <div id="listaVideos"></div>
    </div>
</div>





<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	    cargarCategorias();
	
	    function cargarCategorias(){
	        $.get("ServletCategoriaJSON", function(response){
	            // Recorrer la respuesta
	            $.each(response, function(index, categoria){
	                // Crear una opción por cada categoría en el select
	                $('#selectCategorias').append($('<option>', {
	                    value: categoria.idCategoria,
	                    text: categoria.nombreCategoria
	                }));
	            });
	        });
	    }
	
	    // Función para manejar el cambio en el select
	    $('#selectCategorias').change(function() {
	        var categoriaSeleccionada = $(this).val();
	        verVideosDeCategoria(categoriaSeleccionada);
	    });

        function verVideosDeCategoria(idCategoria) {
            // Obtener el ID de usuario de la sesión
            var idUsuario = <%= session.getAttribute("id") %>;

            // Hacer una solicitud AJAX para obtener los videos de la categoría seleccionada
            $.ajax({
                url: 'ServletFavorito?accion=listar',
                type: 'GET',
                data: { idUsuarioPag: idUsuario, idCategoriaPag: idCategoria },
                dataType: 'json',
                success: function(response) {
                    // Manejar la respuesta JSON
                    var listaVideosHTML = '';
                    $.each(response, function(index, video) {
                        var iframeCode = '<div class="video-conta"><iframe width="100%" height="315" src="' + video.urlVideo + '" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>';
                        iframeCode += '<div class="video-info"><a href="ServletVideo?accion=buscar&idVideoPag=' + video.idVideo + '">' + video.tituloVideo + '</a>';
                        iframeCode += '<p><strong>Categoría:</strong> ' + video.categoriaVideo + '</p>';
                        iframeCode += '</div></div>';
                        listaVideosHTML += iframeCode;
                    });
                    $('#listaVideos').html(listaVideosHTML);
                },
                error: function(xhr, status, error) {
                    // Manejar errores
                    console.error(xhr.responseText);
                }
            });
        }
 
</script>






</body>
</html>