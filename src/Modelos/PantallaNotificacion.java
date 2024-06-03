package Modelos;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PantallaNotificacion {

    public static void notificarNovedadesImportadas(List<String> enofilosANotificar) {
        // Mensajes en la consola
        System.out.println("Notificando a los enófilos: " + enofilosANotificar);
        System.out.println("Notificación enviada");

        Timer timer = new Timer(3000, e -> {
            // Crear y configurar la ventana
            JFrame frame = new JFrame("Enviar Notificación a Enófilo");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            // Panel principal
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // Lista de enófilos
            JList<String> enofilosList = new JList<>(enofilosANotificar.toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(enofilosList);
            panel.add(scrollPane, BorderLayout.CENTER);

            // Leyenda de éxito
            JLabel leyenda = new JLabel("Las notificaciones sobre las actualizaciones de los vinos fueron enviadas con éxito.", JLabel.CENTER);
            leyenda.setFont(new Font("Arial", Font.ITALIC, 12));
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
