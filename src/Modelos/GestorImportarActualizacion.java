package Modelos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorImportarActualizacion {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private List<Bodega> bodegas;
    private String nombreBodegaSeleccionada;
    public void crearBodegas(){
        this.bodegas = new ArrayList<>();
        // Creación de bodegas hardcodeadas
        bodegas.add(new Bodega("Bodega 1"));
        bodegas.add(new Bodega("Bodega 2"));
        bodegas.add(new Bodega("Bodega 3"));
        bodegas.add(new Bodega("Bodega 4"));
        bodegas.add(new Bodega("Bodega 5"));
    }

    public List<String> opcionActualizarVinos(){
        return buscarBodegaActualizacion();
    }

    public Date obtenerFechaActual(){
        //retornar la fecha actual
        return new Date();
    }
    public List<String> buscarBodegaActualizacion() {
        List<String> bodegasConActualizacion = new ArrayList<>();
        Date fechaActual = obtenerFechaActual(); // Obtener la fecha actual

        for (Bodega bodega : bodegas) {
            if (bodega.tieneActualizacion(fechaActual)) { // Preguntar a cada bodega si necesita actualización
                bodegasConActualizacion.add(bodega.getDatos()); // Agregar bodega a la lista si necesita actualización
            }
        }
        return bodegasConActualizacion; // Devolver la lista de bodegas que necesitan actualización
    }


    public void tomarSeleccionDeBodega(String bodegaSeleccionada) throws IOException, InterruptedException {
        this.nombreBodegaSeleccionada = bodegaSeleccionada;
        obtenerActualizacionesBodega(nombreBodegaSeleccionada);
    }

    public void obtenerActualizacionesBodega(String bodegaSeleccionada) throws IOException, InterruptedException {
        //como implementar la llamada a un ENDPOINT


        String direccion = "http://localhost:8080/actualizaciones" + bodegaSeleccionada;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String respuestaConActualizaciones  = response.body();
        System.out.println(respuestaConActualizaciones);


        // Lógica para decidir si crear o actualizar un vino
        //if (informacion != null && !informacion.isEmpty()) {
            // Dentro de este if es que se implementa el optional
            //si es actualizacion llamar al metodo actualizarVinos
            //si es creacion llamar al metodo crearVinos
            //gestionBodegas.tomarDecisionCrearActualizarVino(informacion);
        //} else {
           // System.out.println("No se pudo obtener información de actualización para la bodega seleccionada.");
        //}
    }


    public void actualizarVinos() {

        String nombreBodegaSeleccionada = this.nombreBodegaSeleccionada;

        // Buscar la bodega seleccionada en la lista de bodegas
        Bodega bodegaSeleccionada = null;
        for (Bodega bodega : bodegas) {
            if (bodega.getNombre().equals(nombreBodegaSeleccionada)) {
                bodegaSeleccionada = bodega;
                break;
            }
        }
        // Verificar si se encontró la bodega seleccionada
        if (bodegaSeleccionada != null) {
            // Llamar al método actualizarVino de la bodega seleccionada
            //TODO: Arreglar como se le envia los parametros al metodo actualizar vinos
            //bodegaSeleccionada.actualizarVinos();
        } else {
            System.out.println("No se encontró la bodega seleccionada.");
        }
    }

}
