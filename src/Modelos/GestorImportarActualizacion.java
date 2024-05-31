package Modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GestorImportarActualizacion {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private List<Bodega> bodegas;
    private String nombreBodegaSeleccionada;

    public GestorImportarActualizacion() {
        this.bodegas = new ArrayList<>();
    }



    public void crearBodegasDesdeJSON(String filePath) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try (FileReader reader = new FileReader(filePath)) {
            Type bodegaListType = new TypeToken<List<Bodega>>(){}.getType();
            List<Bodega> bodegasFromJson = gson.fromJson(reader, bodegaListType);
            this.bodegas.addAll(bodegasFromJson);
            System.out.println("Se han cargado " + bodegasFromJson.size() + " bodegas desde el JSON.");
            System.out.println("Tamaño de la lista de bodegas después de la carga: " + this.bodegas.size());
            // Imprimir los nombres de las bodegas para verificar
            for (Bodega bodega : this.bodegas) {
                System.out.println(bodega.getNombre());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> opcionActualizarVinos(){
        System.out.println("llegue a la opcoon del gestor");
        return buscarBodegaActualizacion();
    }

    public Date obtenerFechaActual(){
        //retornar la fecha actual
        return new Date();
    }
    public List<String> buscarBodegaActualizacion() {
        System.out.println("Entre a buscar las bodegas");
        List<String> bodegasConActualizacion = new ArrayList<>();

        Date fechaActual = obtenerFechaActual(); // Obtener la fecha actual

        System.out.println("Número de bodegas en la lista: " + bodegas.size());

        for (Bodega bodega : bodegas) {
            System.out.println("La bodega a mostrar es: " + bodega.getNombre());
            if (bodega.tieneActualizacion(fechaActual)) { // Preguntar a cada bodega si necesita actualización
                bodegasConActualizacion.add(bodega.getDatos()); // Agregar bodega a la lista si necesita actualización
            }
        }
        System.out.println(bodegasConActualizacion);
        return bodegasConActualizacion; // Devolver la lista de bodegas que necesitan actualización
    }




    public void tomarSeleccionDeBodega(String bodegaSeleccionada) {
        this.nombreBodegaSeleccionada = bodegaSeleccionada;
        System.out.println("Bodega seleccionada: " + bodegaSeleccionada);
        try {
            obtenerActualizacionesBodega(bodegaSeleccionada);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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

        List<Vino> actualizaciones = parsearRespuesta(respuestaConActualizaciones);

        // Lógica para decidir si crear o actualizar un vino
        // for (Vino vino : actualizaciones) {
            // TODO: Terminar de definir quien tiene el metodo buscarVinoPorNombre para decifrar si ese vino existe o no para crearlo o actualizarlo
        //    Optional<Vino> vinoExistente = buscarVinoPorNombre(vino.getNombre());
        //    if (vinoExistente.isPresent()) {
        //        actualizarVinos(vino);
        //    } else {
        //        crearVinos(vino);
        //    }
        // }
    }


    public void actualizarVinos(Vino vino) {
        //TODO: Implementar la lógica para actualizar un vino
    }

    public void crearVinos(Vino vino){
        //TODO: Implementar la lógica para crear un vino
    }

    private List<Vino> parsearRespuesta(String respuestaConActualizaciones) {
        Gson gson = new Gson();
        try {
            // Definir el tipo de la lista de vinos
            java.lang.reflect.Type tipoListaVinos = new TypeToken<List<Vino>>() {
            }.getType();
            // Convertir el JSON en una lista de objetos Vino
            return gson.fromJson(respuestaConActualizaciones, tipoListaVinos);
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, devolver una lista vacía o manejar el error según tus necesidades
            return List.of();
        }
    }

}
