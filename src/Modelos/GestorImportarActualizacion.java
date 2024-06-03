package Modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.*;

public class GestorImportarActualizacion {
    private TipoUva uvaSeleccionada;
    private Enofilo enofiloSeleccionado;
    private PantallaImportarActualizacion pantalla;

    private Maridaje maridajeSeleccionado;

    private List<Bodega> Listabodegas;

    private Bodega bodegaSeleccionada;
    private List<TipoUva> listaTipoUvas;
    private List<Maridaje> listaMaridajes;
    private List<Enofilo> listaEnofilos;

    private List<String> enofilosANotificar;
    private PantallaNotificacion pantallaNotificacion;

    public void setListabodegas(List<Bodega> listabodegas) {
        Listabodegas = listabodegas;
    }

    public GestorImportarActualizacion(PantallaNotificacion pantallaNotificacion) {
        this.Listabodegas = new ArrayList<>();
        this.listaTipoUvas = new ArrayList<>();
        this.listaEnofilos = new ArrayList<>();
        this.listaMaridajes = new ArrayList<>();
        this.enofilosANotificar = new ArrayList<>();
        this.pantallaNotificacion = pantallaNotificacion;

    }
    public void setPantalla(PantallaImportarActualizacion pantalla) {
        this.pantalla = pantalla;
    }



    public List<Bodega> getBodegas() {
        return this.Listabodegas;
    }

    public void crearBodegasDesdeJSON(String filePath) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try (FileReader reader = new FileReader(filePath)) {
            Type bodegaListType = new TypeToken<List<Bodega>>(){}.getType();
            List<Bodega> bodegasFromJson = gson.fromJson(reader, bodegaListType);

            for (Bodega bodega : bodegasFromJson) {
                // Imprimir el nombre de la bodega
                System.out.println("Bodega: " + bodega.getDatos());

                // Imprimir los nombres de los vinos de la bodega
                System.out.println("Vinos de la bodega:");
                for (Vino vino : bodega.getVinos()) {
                    System.out.println("- " + vino.getNombre());
                }

                // Agregar la bodega a la lista de bodegas
                Listabodegas.add(bodega);

            }

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

        List<String> bodegasConActualizacion = new ArrayList<>();

        Date fechaActual = obtenerFechaActual(); // Obtener la fecha actual

        for (Bodega bodega : listaBodegas) {
            if (bodega.tieneActualizacion(fechaActual)) { // Preguntar a cada bodega si necesita actualización
                bodegasConActualizacion.add(bodega.getDatos()); // Agregar bodega a la lista si necesita actualización
            }
        }
        return bodegasConActualizacion; // Devolver la lista de bodegas que necesitan actualización
    }




    public void tomarSeleccionDeBodega(String bodegaSeleccionada, List<Bodega> listaBodegas) {

        System.out.println("Bodega seleccionada: " + bodegaSeleccionada);

        for (Bodega bodega: listaBodegas){
            if (bodega.getDatos().equals(bodegaSeleccionada)){
                this.bodegaSeleccionada = bodega;
            }
        }
        try {
            obtenerActualizacionesBodega(bodegaSeleccionada, listaBodegas);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void obtenerActualizacionesBodega(String bodegaSeleccionadaString, List<Bodega> listaBodegas) throws IOException, InterruptedException {
        // Construir la dirección del endpoint con el nombre de la bodega seleccionada
        String direccion = "http://localhost:3000/vinos";
                //+ URLEncoder.encode(bodegaSeleccionada, StandardCharsets.UTF_8.toString());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Obtener la respuesta y parsearla a una lista de vinos
        String respuestaConActualizaciones = response.body();
        System.out.println(respuestaConActualizaciones);

        List<Vino> actualizaciones = parsearRespuesta(respuestaConActualizaciones);
        System.out.println(actualizaciones);

        // Lista para almacenar vinos actualizados o creados
        List<Vino> vinosProcesados = new ArrayList<>();


        // Lógica para decidir si crear o actualizar un vino
        for (Vino vino : actualizaciones) {
            Optional<Vino> vinoExistente = buscarVinoPorNombre(vino.getNombre(), bodegaSeleccionadaString, listaBodegas);
            if (vinoExistente.isPresent()) {
                actualizarVinos(vinoExistente.get(), vino);
                vinosProcesados.add(vinoExistente.get());
            } else {
                buscarTipoUva(vino);
                vinosProcesados.add(vino);
            }
        }
        pantalla.resumenBodegasActualizadas(bodegaSeleccionada, vinosProcesados);

    }

    private void buscarTipoUva(Vino vino) {
        Gson gson = new Gson();
        Type tipoDeUvaListType = new TypeToken<ArrayList<TipoUva>>() {}.getType();
        try (FileReader reader = new FileReader("ApiBodegas/tipoUva.json")) {
            List<TipoUva> tiposDeUva = gson.fromJson(reader, tipoDeUvaListType);
            listaTipoUvas.addAll(tiposDeUva);
            for (TipoUva uva : listaTipoUvas){
                if(uva.esTipoUvaActualizacion(uva)){
                    uva.getDatos();
                    this.uvaSeleccionada = uva;
                }
            }
            buscarTipoMaridaje(vino);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void buscarTipoMaridaje(Vino vino) {
        Gson gson = new Gson();
        Type maridajesListType = new TypeToken<ArrayList<Maridaje>>() {}.getType();
        try (FileReader reader = new FileReader("ApiBodegas/maridajes.json")) {
            List<Maridaje> maridajes = gson.fromJson(reader, maridajesListType);
            //listaMaridajes.addAll(maridajes);
            this.listaMaridajes = maridajes;
            for (Maridaje maridaje : listaMaridajes){
                if(maridaje.esMaridajeActualizacion(maridaje)){
                    maridaje.getDatos();
                    this.maridajeSeleccionado = maridaje;
                }
            }
            crearVinos(vino);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void actualizarVinos(Vino vinoExistente, Vino datoDeActualizacionVino) {
        bodegaSeleccionada.actualizarVino(vinoExistente, datoDeActualizacionVino);
        System.out.println("Vino actualizado: " + vinoExistente.getNombre());

    }

    private void crearVinos(Vino vino) {
        // Agregar el nuevo vino a la bodega correspondiente
        bodegaSeleccionada.crearVino(vino, uvaSeleccionada, maridajeSeleccionado);
        System.out.println("Vino creado: " + vino.getNombre());
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

    private Optional<Vino> buscarVinoPorNombre(String nombreVino, String bodegaSeleccionada, List<Bodega> listabodegas) {
        // Buscar el vino por nombre dentro de la bodega seleccionada

        for (Bodega bodega : listabodegas) {
            if (bodega.getDatos().equals(bodegaSeleccionada)) {
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

    public void buscarEnofiloSuscriptoABodega(){
        Gson gson = new Gson();
        Type enofiloType = new TypeToken<ArrayList<Enofilo>>() {}.getType();
        try (FileReader reader = new FileReader("ApiBodegas/enofilos.json")) {
            List<Enofilo> enofilos = gson.fromJson(reader, enofiloType);
            listaEnofilos.addAll(enofilos);
            System.out.println(listaEnofilos);
            for (Enofilo enofilo : listaEnofilos) {
                String nombreUsuario = enofilo.obtenerNombreEnofiloSuscripto(bodegaSeleccionada);
                if (!nombreUsuario.equals("-")) {
                    enofilosANotificar.add(nombreUsuario);
                }
            }
            pantallaNotificacion.notificarNovedadesImportadas(enofilosANotificar);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
