package Modelos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class PantallaImportarActualizacion {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private List<String> bodegasActualizables;
    private String nombreBodegaSeleccionada;
    private GestorImportarActualizacion gestor;
    private JTextArea areaTexto;
    private JList<String> listaBodegas;

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
        // Crear y configurar una nueva ventana
        JFrame nuevaVentana = new JFrame("Bodegas Actualizables");
        nuevaVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaVentana.setSize(400, 300);

        // Crear una lista para mostrar las bodegas
        listaBodegas = new JList<>();
        listaBodegas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Agregar la lista a la ventana
        nuevaVentana.getContentPane().add(new JScrollPane(listaBodegas), BorderLayout.CENTER);

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

                    tomarSeleccionBodegaActualizar(listaTodasBodegas);
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
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        // Panel para el nombre de la bodega
        JPanel panelNombreBodega = new JPanel();
        JLabel labelNombreBodega = new JLabel(bodegaSeleccionada.getDatos(), SwingConstants.CENTER);
        panelNombreBodega.add(labelNombreBodega);
        frame.add(panelNombreBodega, BorderLayout.NORTH);

        // Panel para los datos de los vinos
        JPanel panelDatosVinos = new JPanel();
        panelDatosVinos.setLayout(new BoxLayout(panelDatosVinos, BoxLayout.Y_AXIS));
        panelDatosVinos.setBorder(BorderFactory.createTitledBorder("Datos de los Vinos Actualizados/Creados"));

        for (Vino vino : vinosActualizadosOCreados) {
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setText(vino.getDatos());
            panelDatosVinos.add(new JScrollPane(textArea));
        }

        frame.add(panelDatosVinos, BorderLayout.CENTER);

        // Mostrar la ventana
        frame.setVisible(true);

        gestor.buscarEnofilosSuscriptosABodega();
    }



}
