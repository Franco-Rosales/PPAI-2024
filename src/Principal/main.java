package Principal;

import Modelos.Bodega;
import Modelos.GestorImportarActualizacion;
import Modelos.PantallaImportarActualizacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class main {
    public static void main(String[] args) {
        GestorImportarActualizacion gestor = new GestorImportarActualizacion();
        gestor.crearBodegasDesdeJSON("ApiBodegas/bodegas.json");


        // Crear y configurar la ventana principal
        JFrame frame = new JFrame("Ventana Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Crear un botón
        JButton boton = new JButton("Mostrar Bodegas Actualizables");

        // Crear una instancia de Boundary
        PantallaImportarActualizacion pantalla = new PantallaImportarActualizacion();

        // Crear la lista de TODAS las bodegas
        List<Bodega> listaBodegas = gestor.getBodegas();

        // Agregar ActionListener al botón
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Ejecute la accion para llamar el metodo tomar opcion");
                // Llamar al método de la clase Boundary para habilitar la pantalla
                pantalla.tomarOpcionActualizacionVinos(listaBodegas);
            }
        });

        // Agregar el botón al marco
        frame.getContentPane().add(boton);
        frame.setVisible(true);
    }
}
