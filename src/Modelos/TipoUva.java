package Modelos;

import java.util.List;

public class TipoUva {
    private String nombre;
    private String descripcion;

    public Boolean esTipoUvaActualizacion(TipoUva tipoUva){
        if (tipoUva.equals(this.nombre)){
            return true;
        }
        return false;
    }

    public String getDatos(){
        return nombre;
    }
}
