package Modelos;

public class Maridaje {
    private String nombre;
    private String descripcion;

    public Boolean esMaridajeActualizacion(Maridaje maridaje){
        return maridaje.equals(this.nombre);
    }

    public String getDatos(){
        if (nombre != null){
            return nombre;
        }
        return "-";
    }

}
