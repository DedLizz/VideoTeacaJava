<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>VideoTeca</title>
    
    <style>
        .video-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        .video-info {
            text-align: center;
        }
        
        .video-info a {
		  text-decoration: none;
		  font-weight: bold;
		  color: white;
		  transition: color 0.3s, text-decoration 0.3s; /* Transición suave para cambios */
		}
		
		.video-info a:hover {
		  color: yellow; /* Cambia el color al pasar el ratón */
		  text-decoration: underline; /* Sube una línea al pasar el ratón */
		}
        
        .video-info p{
        display: none;
        }
        
        .anuncio{
        width: 100%;
	    align-items: center;
	    display: flex;
	    justify-content: center;
        }
    </style>
</head>
<body>

	<div class="anuncio">
	   <img alt="" src="imagenes/anuncio.png">
	</div>
    <div class="video-grid" id="lista-videos"></div>






    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        // Cargar los videos desde el servlet
        cargarVideos();

        function cargarVideos(){
            $.get("ServletVideoJSON", function(response){    
                // Recorrer la respuesta
                $.each(response, function(index, video){
                    // Crear un elemento de lista por cada video
                    var iframeCode = '<div class="video-container"><iframe width="100%" height="315" src="' + video.urlVideo + '" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>';
                    iframeCode += '<div class="video-info"><a href="ServletVideo?accion=buscar&idVideoPag=' + video.idVideo + '">' + video.tituloVideo + '</a>';
                    iframeCode += '<p><strong>Categoría:</strong> ' + video.categoriaVideo + '</p>';
                    iframeCode += '</div></div>';

                    // Agregar el iframe al contenedor de videos
                    $("#lista-videos").append(iframeCode);
                });
            });
        }

        // Función para agregar a favoritos
        function agregarFavorito(url){
            // Aquí puedes implementar la lógica para agregar el video a favoritos.
            alert("Agregado a Favoritos: " + url);
        }
    </script>
    
    
    <!-- para ocultar anuncio -->
<script type="text/javascript">
	// Obtener el valor de sessionScope.idTipoCuenta
	var idTipoCuenta = "${sessionScope.idTipoCuenta}";
	
	// Verificar si idTipoCuenta es diferente de 1
	if (idTipoCuenta !== "1") {
	    // Ocultar el div con la clase "anuncio"
	    document.getElementsByClassName("anuncio")[0].style.display = "none";
	}
</script>
</body>
</html>