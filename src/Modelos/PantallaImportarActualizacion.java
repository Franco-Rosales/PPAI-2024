package Modelos;

import java.io.IOException;
import java.util.List;

public class PantallaImportarActualizacion {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private List<String> bodegasActualizables;
    private String nombreBodegaSeleccionada;
    private GestorImportarActualizacion gestor;

    public void tomarOpcionActualizacionVinos(){
        habilitarPantalla();
        this.bodegasActualizables = gestor.opcionActualizarVinos();
        mostrarBodegasActualizables(bodegasActualizables);
    }

    public void tomarSeleccionBodega(String bodegaSeleccionada) throws IOException, InterruptedException {
        this.nombreBodegaSeleccionada = bodegaSeleccionada;

        // Este metodo va a recibir la bodega seleccionada por medio del boton
        // falta implementacion de la funcionalidad de la pantalla que le mande a este metodo la seleccion, esta solamente se la retona al gestor
        gestor.tomarSeleccionDeBodega(nombreBodegaSeleccionada);


    }


    public void habilitarPantalla(){
        // Agregar funcionalidad para habilitar una pantalla
        // preferentemente usar Swing por que es mas simple
    }

    public List<String> mostrarBodegasActualizables(List<String> bodegasActualizables) {
        // Esto ahora lo musetra por consola pero habria que implementar la funcionalidad para que se muestre en la pantalla
        //lo que se debe mostrar es lo de dentro del for

        System.out.println("Bodegas con actualización disponible:");
        for (String nombreBodega : bodegasActualizables) {
            System.out.println(nombreBodega);
        }

        return null;
    }
}
