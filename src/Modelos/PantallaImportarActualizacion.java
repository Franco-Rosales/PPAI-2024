package Modelos;

import java.util.List;

public class PantallaImportarActualizacion {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private GestorImportarActualizacion gestor;

    public void tomarOpcionActualizacionVinos(){
        HabilitarPantalla();
        List<String> bodegasActualizables = gestor.opcionActualizarVinos();
        mostrarBodegasActualizables(bodegasActualizables);
    }

    public String tomarSeleccionBodega(String nombreBodegaSeleccionada) {
        // Este metodo va a recibir la bodega seleccionada por medio del boton
        // falta implementacion de la funcionalidad de la pantalla que le mande a este metodo la seleccion, esta solamente se la retona al gestor

        return gestor.tomarSeleccionDeBodega(nombreBodegaSeleccionada);
    }


    public void HabilitarPantalla(){
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
