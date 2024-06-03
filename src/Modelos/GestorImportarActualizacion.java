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
    private Date fechaHoraActual;
    private Bodega bodegaSeleccionada;
    private List<Vino> actualizaciones;
    private List<String> enofilosANotificar;
    private TipoUva uvaSeleccionada;
    private Enofilo enofiloSeleccionado;
    private PantallaImportarActualizacion pantalla;
    private Maridaje maridajeSeleccionado;
    private List<Bodega> Listabodegas;
    private List<TipoUva> listaTipoUvas;
    private List<Maridaje> listaMaridajes;
    private List<Enofilo> listaEnofilos;
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
        this.actualizaciones = new ArrayList<>();
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
                // Agregar la bodega a la lista de bodegas
                Listabodegas.add(bodega);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> opcionActualizacionVinos(List<Bodega> listaBodegas){
        return buscarBodegasActualizacion(listaBodegas);
    }

    public Date getFechaHoraActual(){
        this.fechaHoraActual = new Date();
        //retornar la fecha actual
        return fechaHoraActual;
    }
    public List<String> buscarBodegasActualizacion(List<Bodega> listaBodegas) {

        List<String> bodegasConActualizacion = new ArrayList<>();

        Date fechaActual = getFechaHoraActual(); // Obtener la fecha actual

        for (Bodega bodega : listaBodegas) {
            if (bodega.tieneActualizacion(fechaActual)) { // Preguntar a cada bodega si necesita actualización
                bodegasConActualizacion.add(bodega.getDatos()); // Agregar bodega a la lista si necesita actualización
            }
        }
        return bodegasConActualizacion; // Devolver la lista de bodegas que necesitan actualización
    }




    public void tomarSeleccionBodega(String bodegaSeleccionada, List<Bodega> listaBodegas) {
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
        List<Vino> actualizaciones = parsearRespuesta(respuestaConActualizaciones);

        // Lógica para decidir si crear o actualizar un vino
        for (Vino vino : actualizaciones) {
            Optional<Vino> vinoExistente = buscarVinoPorNombre(vino.getNombre(), bodegaSeleccionadaString, listaBodegas);
            if (vinoExistente.isPresent()) {
                actualizarVinosExistentes(vinoExistente.get(), vino);
                this.actualizaciones.add(vinoExistente.get());
            } else {
                buscarTipoUva(vino);
                this.actualizaciones.add(vino);
            }
        }
        pantalla.mostrarResumenBodegasActualizadasCreadas(bodegaSeleccionada, this.actualizaciones);

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
            buscarMaridaje(vino);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void buscarMaridaje(Vino vino) {
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


    private void actualizarVinosExistentes(Vino vinoExistente, Vino datoDeActualizacionVino) {
        bodegaSeleccionada.actualizarVino(vinoExistente, datoDeActualizacionVino);
    }

    private void crearVinos(Vino vino) {
        // Agregar el nuevo vino a la bodega correspondiente
        bodegaSeleccionada.crearVino(vino, uvaSeleccionada, maridajeSeleccionado);
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

    public void buscarEnofiloSuscriptosABodega(){
        listaEnofilos.clear();
        Gson gson = new Gson();
        Type enofiloType = new TypeToken<ArrayList<Enofilo>>() {}.getType();
        try (FileReader reader = new FileReader("ApiBodegas/enofilos.json")) {
            List<Enofilo> enofilos = gson.fromJson(reader, enofiloType);
            listaEnofilos.addAll(enofilos);
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
