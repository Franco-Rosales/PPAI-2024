package Modelos;

import javax.swing.*;
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
        this.vinos = vinos != null ? vinos : new ArrayList<>();
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
        return  nombre;
    }
    public  List<Vino> getVinos(){
        return vinos;
    }

    public void agregarVino(Vino vino) {
        this.vinos.add(vino);
    }


}
