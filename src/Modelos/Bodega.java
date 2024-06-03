package Modelos;

import java.util.*;

public class Bodega {


    private String nombre;
    private Date fechaUltimaActualizacion;
    private int periodicidadActualizacion;
    private String descripcion;
    private List<Double> coordenadasUbicacion;
    private String historia;
    private List<Vino> vinos;


    public Bodega(String nombre, Date fechaUltimaActualizacion, int periodicidadActualizacion, String descripcion, List<Double> coordenadasUbicacion, String historia) {
        this.nombre = nombre;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
        this.periodicidadActualizacion = periodicidadActualizacion;
        this.descripcion = descripcion;
        this.coordenadasUbicacion = coordenadasUbicacion;
        this.historia = historia;
        this.vinos = new ArrayList<>();
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

    public String getDatos(){
        return nombre;
    }
    public  List<Vino> getVinos(){
        return vinos;
    }
    public void setVinos(List<Vino> vinos) {
        this.vinos = vinos;
    }


    public void addVino(Vino vino) {
        if (vinos == null) {
            vinos = new ArrayList<>();
        }
        vinos.add(vino);
    }

    public void actualizarVino(Vino vinoExistente, Vino datoDeActualizacionVino){
        for(Vino vino : vinos){
            if(vino.equals(vinoExistente)){
                vino.setImagenEtiqueta(datoDeActualizacionVino.getImagenEtiqueta());
                vino.setNotaCataBodega(datoDeActualizacionVino.getNotaCataBodega());
                vino.setPrecioARS(datoDeActualizacionVino.getPrecioARS());
            }
        }
    }

    public void crearVino (Vino vino, TipoUva uvaSeleccionada, Maridaje maridajeSeleccionado){
        // Se crea un nuevo vino
        Vino vinoNuevo = new Vino(vino.getNombre(), vino.getImagenEtiqueta(), vino.getPrecioARS(), vino.getNotaCataBodega(), vino.getVarietal(), maridajeSeleccionado);
    }

}
