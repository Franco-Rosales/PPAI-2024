package Modelos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class PantallaImportarActualizacion {
    private List<String> bodegasActualizables;
    private JList<String> listaBodegas;
    private GestorImportarActualizacion gestor;


    public PantallaImportarActualizacion(PantallaNotificacion pantallaNotificacion) {
        this.gestor = new GestorImportarActualizacion(pantallaNotificacion);
        gestor.setPantalla(this);

    }

    public void tomarOpcionActualizacionVinos(List<Bodega> listaTodasBodegas){
        habilitarPantalla();
        this.bodegasActualizables = gestor.opcionActualizacionVinos(listaTodasBodegas);
        mostrarBodegasActualizables(bodegasActualizables, listaTodasBodegas);

    }

    public void habilitarPantalla(){
        JFrame nuevaVentana = new JFrame("Bodegas Actualizables");
        nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaVentana.setSize(700, 600);

        // Crear una lista para mostrar las bodegas
        listaBodegas = new JList<>();
        listaBodegas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Personalizar el renderizador de celdas para estilizar los elementos de la lista
        listaBodegas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("SansSerif", Font.PLAIN, 16));
                label.setForeground(new Color(41, 128, 185)); // Color del texto
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(41, 128, 185), 1, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setIcon(new ImageIcon("path/to/icon.png")); // Añadir ícono (opcional)
                if (isSelected) {
                    label.setBackground(new Color(220, 220, 220)); // Color de fondo cuando está seleccionado
                }
                return label;
            }
        });

        // Crear un panel principal para contener todos los componentes
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 245, 245)); // Fondo claro

        // Añadir un título a la ventana
        JLabel tituloLabel = new JLabel("Bodegas Actualizables");
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloLabel.setForeground(new Color(34, 34, 34)); // Color del texto
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(tituloLabel, BorderLayout.NORTH);

        // Agregar la lista de bodegas dentro de un JScrollPane al panel principal
        JScrollPane scrollPane = new JScrollPane(listaBodegas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Añadir un botón en la parte inferior
        JButton botonActualizar = new JButton("Actualizar Selección");
        botonActualizar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        botonActualizar.setBackground(new Color(41, 128, 185));
        botonActualizar.setForeground(Color.WHITE);
        botonActualizar.setFocusPainted(false);
        botonActualizar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelPrincipal.add(botonActualizar, BorderLayout.SOUTH);

        // Añadir el panel principal a la ventana
        nuevaVentana.getContentPane().add(panelPrincipal);

        // Mostrar la ventana
        nuevaVentana.setVisible(true);
    }

    public void mostrarBodegasActualizables(List<String> bodegasActualizables, List<Bodega> listaTodasBodegas) {
        // Convertir la lista a un array para el JList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String bodega : bodegasActualizables) {

            listModel.addElement(bodega);
        }
        listaBodegas.setModel(listModel);

        // Crear un botón para confirmar la selección
        JButton botonSeleccionar = new JButton("Seleccionar Bodega");
        botonSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    solicitarSeleccionBodegas(listaTodasBodegas);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Crear un panel para el botón y agregarlo a la ventana
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(listaBodegas);
        JPanel panelBoton = new JPanel();
        panelBoton.add(botonSeleccionar);
        ventana.getContentPane().add(panelBoton, BorderLayout.SOUTH);

    }
    public void solicitarSeleccionBodegas(List<Bodega> listaTodasBodegas)throws IOException, InterruptedException{
        tomarSeleccionBodegaActualizar(listaTodasBodegas);
    }

    public void tomarSeleccionBodegaActualizar(List<Bodega> listaTodasBodegas) throws IOException, InterruptedException {
        String bodegaSeleccionada = listaBodegas.getSelectedValue();
        if (bodegaSeleccionada != null) {
            // Llamar al método del controlador con la bodega seleccionada
            gestor.tomarSeleccionBodega(bodegaSeleccionada, listaTodasBodegas);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una bodega.");
        }
    }

    public void mostrarResumenBodegasActualizadasCreadas(Bodega bodegaSeleccionada, List<Vino> vinosActualizadosOCreados) {
        // Crear la ventana
        JFrame frame = new JFrame("Resumen de Bodegas Actualizadas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 800); // Tamaño más grande para acomodar más datos
        frame.setLayout(new BorderLayout());

        // Panel para el nombre de la bodega
        JPanel panelNombreBodega = new JPanel();
        panelNombreBodega.setBackground(new Color(245, 245, 245));
        panelNombreBodega.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel labelNombreBodega = new JLabel(bodegaSeleccionada.getDatos(), SwingConstants.CENTER);
        labelNombreBodega.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelNombreBodega.setForeground(new Color(41, 128, 185));
        panelNombreBodega.add(labelNombreBodega);
        frame.add(panelNombreBodega, BorderLayout.NORTH);

        // Panel para los datos de los vinos
        JPanel panelDatosVinos = new JPanel();
        panelDatosVinos.setLayout(new BoxLayout(panelDatosVinos, BoxLayout.Y_AXIS));
        panelDatosVinos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(41, 128, 185)),
                "Datos de los Vinos Actualizados/Creados",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16),
                new Color(41, 128, 185)));
        panelDatosVinos.setBackground(new Color(255, 255, 255)); // Fondo blanco

        // Estilo para los datos de los vinos
        for (Vino vino : vinosActualizadosOCreados) {
            JPanel vinoPanel = new JPanel();
            vinoPanel.setLayout(new BorderLayout());
            vinoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            vinoPanel.setBackground(new Color(250, 250, 250)); // Fondo más claro
            vinoPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

            JLabel textArea = new JLabel(vino.getDatos());
            textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
            textArea.setForeground(new Color(34, 34, 34)); // Color del texto
            textArea.setOpaque(true);
            textArea.setBackground(new Color(250, 250, 250)); // Fondo más claro

            vinoPanel.add(textArea, BorderLayout.CENTER);
            panelDatosVinos.add(vinoPanel);
            panelDatosVinos.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre paneles
        }

        // Agregar el panel de datos de los vinos a un JScrollPane para scroll
        JScrollPane scrollPaneVinos = new JScrollPane(panelDatosVinos);
        scrollPaneVinos.setBorder(BorderFactory.createEmptyBorder());
        frame.add(scrollPaneVinos, BorderLayout.CENTER);

        // Mostrar la ventana
        frame.setVisible(true);

        gestor.buscarEnofiloSuscriptosABodega();
    }



}
