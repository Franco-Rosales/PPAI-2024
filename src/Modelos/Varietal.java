package Modelos;

public class Varietal {
    private String descripcion;
    private int porcentajeComposicion;
    private TipoUva tipoUva;


    public String getDescripcion() {
        if (descripcion != null){
            return descripcion;
        }
        return "-";
    }

    public int getPorcentajeComposicion() {
        return porcentajeComposicion;
    }

    public TipoUva getTipoUva() {
        return tipoUva;
    }

    public Varietal(String descripcion, int porcentComposicion, TipoUva tipoUva) {
        this.descripcion = descripcion;
        this.porcentajeComposicion = porcentComposicion;
        this.tipoUva = tipoUva;
    }
}
