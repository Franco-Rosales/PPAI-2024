package Modelos;

import java.util.Date;

public class Siguiendo {
    private Date fechaInicio;
    private Date fechaFin;
    private String bodegaSeguida;


    public Siguiendo(Date fechaInicio, Date fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Boolean sosDeBodega(Bodega bodegaSeleccionada){
        if (bodegaSeleccionada.getDatos().equals(this.bodegaSeguida)){
            return true;
        }
        return false;
    }

    public Date getFechaFin() {
        return fechaFin;
    }
}
