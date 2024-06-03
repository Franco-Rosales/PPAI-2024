package Modelos;

public class Maridaje {
    private String nombre;
    private String descripcion;

    public Boolean esMaridajeActualizacion(Maridaje maridaje){
        if (maridaje.equals(this.nombre)){
            return true;
        }
        return false;
    }

    public String getDatos(){
        if (nombre != null){
            return nombre;
        }
        return "-";
    }

}
