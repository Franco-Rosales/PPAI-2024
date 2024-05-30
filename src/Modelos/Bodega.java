package Modelos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Bodega {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private String nombre;
    private Date fechaActualizacion;

    private Date fechaUltimaActualizacion;

    private int periodicidadActualizacion;

    public Bodega(String nombre) {
        this.nombre = nombre;
    }

    public boolean tieneActualizacion(Date fechaActual) {
        // Sumar la periodicidad a la fecha de la última actualización
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaUltimaActualizacion);
        cal.add(Calendar.DAY_OF_YEAR, periodicidadActualizacion);

        // Fecha de la próxima actualización
        Date fechaProximaActualizacion = cal.getTime();

        // Comparar la fecha actual con la fecha de la próxima actualización
        return !fechaActual.before(fechaProximaActualizacion);
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
