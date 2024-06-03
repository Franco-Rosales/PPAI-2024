package Modelos;

public class Siguiendo {
    private String fechaInicio;
    private String fechaFin;

    private String bodegaSeguida;


    public Siguiendo(String fechaInicio, String fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Boolean sosDeBodega(Bodega bodegaSeleccionada){
        if (bodegaSeleccionada.getDatos().equals(this.bodegaSeguida)){
            return true;
        }
        return false;
    }

    public String getFechaFin() {
        return fechaFin;
    }
}
