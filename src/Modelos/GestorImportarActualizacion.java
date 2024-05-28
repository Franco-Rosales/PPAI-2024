package Modelos;

import java.util.ArrayList;
import java.util.List;

public class GestorImportarActualizacion {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private List<Bodega> bodegas;
    private String nombreBodegaSeleccionada;
    public void crearBodegas(){
        this.bodegas = new ArrayList<>();
        // Creación de bodegas hardcodeadas
        bodegas.add(new Bodega("Bodega 1", true));
        bodegas.add(new Bodega("Bodega 2", true));
        bodegas.add(new Bodega("Bodega 3", true));
        bodegas.add(new Bodega("Bodega 4", true));
        bodegas.add(new Bodega("Bodega 5", true));
    }

    public List<String> opcionActualizarVinos(){
        return buscarBodegaActualizacion();
    }


    public List<String> buscarBodegaActualizacion(){
        List<String> bodegasConActualizacion = new ArrayList<>();
        for (Bodega bodega : bodegas) {
            if (bodega.tieneActualizacion()) {
                bodegasConActualizacion.add(bodega.getDatos());
            }
        }
        return bodegasConActualizacion;
    }


    public String tomarSeleccionDeBodega(String nombreBodegaSeleccionada) {
        this.nombreBodegaSeleccionada = nombreBodegaSeleccionada;
        return nombreBodegaSeleccionada;
    }

    public void obtenerActualizacionesBodega(String nombreBodegaSeleccionada) {
        // Realizar la llamada al endpoint para obtener la información
        //String informacion = gestionBodegas.llamarEndpointObtenerInformacion(nombreBodegaSeleccionada);

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
            bodegaSeleccionada.actualizarVinos();
        } else {
            System.out.println("No se encontró la bodega seleccionada.");
        }
    }

}
