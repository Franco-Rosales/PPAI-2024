package Modelos;

import java.util.List;

public class Enofilo {
    private String nombre;
    private String apellido;
    private String imagenDePerfil;
    private List<Siguiendo> siguiendo;
    private Usuario usuario;


    public Enofilo(String nombre, String apellido, String imagenDePerfil) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.imagenDePerfil = imagenDePerfil;
    }

    public String obtenerNombreEnofiloSuscripto(Bodega bodegaSeleccionada){
        String nombreUsuario = estaSuscripto(bodegaSeleccionada);
        return nombreUsuario;
    }

    public String estaSuscripto(Bodega bodegaSeleccionada){
        if (siguiendo.size() > 0){
            for (Siguiendo siguiendo : siguiendo) {
                if (siguiendo.getFechaFin() != null){
                    Boolean esDeBodega = siguiendo.sosDeBodega(bodegaSeleccionada);
                    if (esDeBodega) {
                        String nombreUsuario = usuario.getNombre();
                        return nombreUsuario;
                    }
                }
            }
        }
        return "-";
    }


}
