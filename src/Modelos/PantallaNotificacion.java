package Modelos;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class PantallaNotificacion {

    public static void notificarNovedadesImportadas(List<String> enofilosANotificar) {
        // Mensajes en la consola
        System.out.println("Notificando a los enófilos: " + enofilosANotificar);
        System.out.println("Notificación enviada");

        Timer timer = new Timer(2000, e -> {
            // Crear y configurar la ventana
            JFrame frame = new JFrame("Enviar Notificación a Enófilo");
            frame.setSize(500, 400); // Tamaño ajustado para un mejor diseño
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            // Panel principal
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen alrededor del panel
            panel.setBackground(new Color(245, 245, 245)); // Fondo claro

            // Título de la ventana
            JLabel titulo = new JLabel("La Notificación a Enófilo Fue Realizada!", JLabel.CENTER);
            titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
            titulo.setForeground(new Color(41, 128, 185));
            panel.add(titulo, BorderLayout.NORTH);

            // Panel para la lista de enófilos
            JPanel panelEnofilos = new JPanel();
            panelEnofilos.setLayout(new BoxLayout(panelEnofilos, BoxLayout.Y_AXIS));
            panelEnofilos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(41, 128, 185)),
                    "Lista de Enófilos a Notificados",
                    TitledBorder.LEFT,
                    TitledBorder.TOP,
                    new Font("SansSerif", Font.BOLD, 16),
                    new Color(41, 128, 185)));
            panelEnofilos.setBackground(new Color(255, 255, 255)); // Fondo blanco

            // Lista de enófilos
            JList<String> enofilosList = new JList<>(enofilosANotificar.toArray(new String[0]));
            enofilosList.setFont(new Font("SansSerif", Font.PLAIN, 14));
            enofilosList.setForeground(new Color(34, 34, 34)); // Color del texto
            enofilosList.setBackground(new Color(255, 255, 255)); // Fondo blanco
            JScrollPane scrollPane = new JScrollPane(enofilosList);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            panelEnofilos.add(scrollPane);

            panel.add(panelEnofilos, BorderLayout.CENTER);

            // Leyenda de éxito
            JLabel leyenda = new JLabel("<html><div style='text-align: center;'>Las notificaciones sobre las actualizaciones de los vinos fueron enviadas con éxito a los enófilos seleccionados.</div></html>", JLabel.CENTER);
            leyenda.setFont(new Font("SansSerif", Font.ITALIC, 14));
            leyenda.setForeground(new Color(39, 174, 96)); // Color del texto
            leyenda.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Margen superior
            panel.add(leyenda, BorderLayout.SOUTH);

            // Agregar el panel a la ventana y hacerla visible
            frame.add(panel);
            frame.setVisible(true);
        });

        // Iniciar el timer (una sola vez)
        timer.setRepeats(false);
        timer.start();
    }


}
