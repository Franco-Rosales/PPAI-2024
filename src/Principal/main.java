package Principal;

import Modelos.Bodega;
import Modelos.GestorImportarActualizacion;
import Modelos.PantallaImportarActualizacion;
import Modelos.PantallaNotificacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class main {
    public static void main(String[] args) {
        PantallaNotificacion pantallaNotificacion = new PantallaNotificacion();
        GestorImportarActualizacion gestor = new GestorImportarActualizacion(pantallaNotificacion);
        gestor.crearBodegasDesdeJSON("ApiBodegas/bodegas.json");

        // Crear y configurar la ventana principal
        JFrame frame = new JFrame("Importar Actualización de Vinos de Bodega");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Tamaño similar a un login de Apple
        frame.setLayout(new BorderLayout());

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 245, 245)); // Fondo claro

        // Nombre del usuario
        JLabel usuarioLabel = new JLabel("Administrador de BonVino");
        usuarioLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        usuarioLabel.setForeground(new Color(34, 34, 34)); // Color del texto
        usuarioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Descripción de uso
        JLabel descripcionLabel = new JLabel("<html><h3>Descripción de Uso</h3><p>Esta aplicación tiene como finalidad actualizar o crear los datos de una bodega seleccionada por el administrador, para poder continuar con esta ejecución haga click en el siguiente botón:</p></html>");
        descripcionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descripcionLabel.setForeground(new Color(100, 100, 100)); // Color del texto
        descripcionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón para mostrar bodegas actualizables
        JButton boton = new JButton("Importar Actualización de Vinos de Bodega");
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(41, 128, 185)); // Azul moderno
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        // Crear una instancia de PantallaImportarActualizacion
        PantallaImportarActualizacion pantalla = new PantallaImportarActualizacion(pantallaNotificacion);

        // Crear la lista de TODAS las bodegas
        List<Bodega> listaBodegas = gestor.getBodegas();

        // Agregar ActionListener al botón
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al método de la clase PantallaImportarActualizacion para habilitar la pantalla
                pantalla.tomarOpcionActualizacionVinos(listaBodegas);
            }
        });

        // Añadir componentes al panel principal
        panelPrincipal.add(usuarioLabel);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre componentes
        panelPrincipal.add(descripcionLabel);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre componentes
        panelPrincipal.add(boton);

        // Añadir el panel principal al marco
        frame.getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
