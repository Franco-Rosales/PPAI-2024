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

    public PantallaImportarActualizacion() {
        this.gestor = new GestorImportarActualizacion();
    }

    public void tomarOpcionActualizacionVinos(List<Bodega> listaBodegas){
        habilitarPantalla();
        this.bodegasActualizables = gestor.opcionActualizarVinos(listaBodegas);
        mostrarBodegasActualizables(bodegasActualizables);

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

    public void mostrarBodegasActualizables(List<String> bodegasActualizables) {
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
                    tomarSeleccionBodegaActualizar();
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

    public void tomarSeleccionBodegaActualizar() throws IOException, InterruptedException {
        String bodegaSeleccionada = listaBodegas.getSelectedValue();
        if (bodegaSeleccionada != null) {
            // Llamar al método del controlador con la bodega seleccionada
            gestor.tomarSeleccionDeBodega(bodegaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una bodega.");
        }
    }



}
