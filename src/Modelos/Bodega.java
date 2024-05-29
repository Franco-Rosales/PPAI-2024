package Modelos;

import java.util.List;

public class Bodega {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private String nombre;

    private
    boolean actualizacionDisponible;

    public Bodega(String nombre, boolean actualizacionDisponible) {
        this.nombre = nombre;
        this.actualizacionDisponible = actualizacionDisponible;
    }

    public Boolean tieneActualizacion(){

       if (actualizacionDisponible){
           return true;
       }
         return false;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDatos(){
        return null;
    }

    public void actualizarVinos(String bodegaSeleccionada,List<String> actualizaciones){
        //primero pregunta si el vino ya existe en la bodega y si existe lo actualiza y si no existe lo crea con los datos
            Vino vinoActualizable = new Vino();
            if(vinoActualizable.sosActualizable()){
                vinoActualizable.setImagenEtiqueta();
                vinoActualizable.setPrecioARS();
                vinoActualizable.setNotaDeCataBodega();


        }
    }

    public void crearVinos(String bodegaSeleccionada,List<String> actualizaciones){

    }
}
