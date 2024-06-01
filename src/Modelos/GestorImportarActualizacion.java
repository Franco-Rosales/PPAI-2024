package Modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class GestorImportarActualizacion {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private List<Bodega> bodegas;
    private String nombreBodegaSeleccionada;

    public GestorImportarActualizacion() {
        this.bodegas = new ArrayList<>();
    }

    public List<Bodega> getBodegas() {
        return this.bodegas;
    }

    public void crearBodegasDesdeJSON(String filePath) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try (FileReader reader = new FileReader(filePath)) {
            Type bodegaListType = new TypeToken<List<Bodega>>(){}.getType();
            List<Bodega> bodegasFromJson = gson.fromJson(reader, bodegaListType);
            this.bodegas.addAll(bodegasFromJson);
            // Imprimir los nombres de las bodegas para verificar
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> opcionActualizarVinos(List<Bodega> listaBodegas){
        return buscarBodegaActualizacion(listaBodegas);
    }

    public Date obtenerFechaActual(){
        //retornar la fecha actual
        return new Date();
    }
    public List<String> buscarBodegaActualizacion(List<Bodega> listaBodegas) {
        System.out.println("Entre a buscar las bodegas");
        List<String> bodegasConActualizacion = new ArrayList<>();

        Date fechaActual = obtenerFechaActual(); // Obtener la fecha actual

        for (Bodega bodega : listaBodegas) {
            if (bodega.tieneActualizacion(fechaActual)) { // Preguntar a cada bodega si necesita actualización
                bodegasConActualizacion.add(bodega.getDatos()); // Agregar bodega a la lista si necesita actualización
            }
        }
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
        // Construir la dirección del endpoint con el nombre de la bodega seleccionada
        String direccion = "http://localhost:8080/actualizaciones/" + URLEncoder.encode(bodegaSeleccionada, StandardCharsets.UTF_8.toString());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Obtener la respuesta y parsearla a una lista de vinos
        String respuestaConActualizaciones = response.body();
        System.out.println(respuestaConActualizaciones);

        List<Vino> actualizaciones = parsearRespuesta(respuestaConActualizaciones);

        // Lista para almacenar vinos actualizados o creados
        List<Vino> vinosProcesados = new ArrayList<>();

        // Lógica para decidir si crear o actualizar un vino
        for (Vino vino : actualizaciones) {
            Optional<Vino> vinoExistente = buscarVinoPorNombre(vino.getNombre(), bodegaSeleccionada);
            if (vinoExistente.isPresent()) {
                actualizarVinos(vinoExistente.get(), vino);
                vinosProcesados.add(vinoExistente.get());
            } else {
                crearVinos(vino, bodegaSeleccionada);
                vinosProcesados.add(vino);
            }
        }
    }


    private void actualizarVinos(Vino vinoExistente, Vino vinoActualizado) {
        // Actualizar los campos del vino existente con los datos del vino actualizado
        //vinoExistente.setPrecio(vinoActualizado.getPrecio());
        //vinoExistente.setCantidad(vinoActualizado.getCantidad());
        // Actualizar otros campos según sea necesario
        System.out.println("Vino actualizado: " + vinoExistente.getNombre());
    }

    private void crearVinos(Vino vino, String bodegaSeleccionada) {
        // Agregar el nuevo vino a la bodega correspondiente
        for (Bodega bodega : bodegas) {
            if (bodega.getNombre().equals(bodegaSeleccionada)) {
                bodega.getVinos().add(vino);
                System.out.println("Vino creado: " + vino.getNombre());
                return;
            }
        }
    }

    private List<Vino> parsearRespuesta(String respuestaConActualizaciones) {
        Gson gson = new Gson();
        try {
            Type tipoListaVinos = new TypeToken<List<Vino>>() {}.getType();
            return gson.fromJson(respuestaConActualizaciones, tipoListaVinos);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private Optional<Vino> buscarVinoPorNombre(String nombreVino, String bodegaSeleccionada) {
        // Buscar el vino por nombre dentro de la bodega seleccionada
        for (Bodega bodega : bodegas) {
            if (bodega.getNombre().equals(bodegaSeleccionada)) {
                List<Vino> vinos = bodega.getVinos();
                if (vinos != null) {
                    for (Vino vino : vinos) {
                        if (vino.getNombre().equalsIgnoreCase(nombreVino)) {
                            return Optional.of(vino);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }


}
