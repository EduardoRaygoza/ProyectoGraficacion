package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JColorChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import model.Point;
import model.Figura;
import view.PaintingPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author eduardo
 */
public class Main extends JFrame implements MouseListener, MouseMotionListener, ActionListener{
    private PaintingPanel pp;
    private JPanel toolPanel, colorPanel;
    private JButton btnLine, btnRect, btnOval, btnColor;
    private JCheckBox chkFill;
    private Point origen, destino;
    private boolean clicked, input;
    private int seleccion;
    private ArrayList<Figura> lista;
    private Color color;
    private JMenuBar barraMenu;
    private JMenu menu;
    private JMenuItem itmAbrir, itmGuardar, itmNuevo, itmInfo;
    private JFileChooser fc;
    private File archivo;
    private ObjectOutputStream oos;
    private ObjectInputStream ios;
    private String msg, title;
    
    public Main(){
        seleccion = 0;
        clicked = false;
        input = false;
        origen = new Point();
        destino = new Point();
        lista = new ArrayList<Figura>();
        toolPanel = new JPanel(); 
        colorPanel = new JPanel();
        colorPanel.setBackground(Color.BLACK);
        pp = new PaintingPanel(origen, destino, lista);
        btnLine = new JButton("Linea");
        btnRect = new JButton("Rectangulo");
        btnOval = new JButton("Ovalo");
        btnColor = new JButton("Color");
        chkFill = new JCheckBox("Fill");
        barraMenu = new JMenuBar();
        menu = new JMenu("Archivo");
        barraMenu.add(menu);
        itmNuevo = new JMenuItem("Nuevo");
        itmAbrir = new JMenuItem("Abrir archivo");
        itmGuardar = new JMenuItem("Guardar archivo");
        itmInfo = new JMenuItem("Info");
        barraMenu.add(itmInfo);
        menu.add(itmNuevo);
        menu.add(itmAbrir);
        menu.add(itmGuardar);
        setJMenuBar(barraMenu);
        
        toolPanel.setLayout(new FlowLayout());
        toolPanel.add(btnLine);
        toolPanel.add(btnRect);
        toolPanel.add(btnOval);
        toolPanel.add(btnColor);
        toolPanel.add(colorPanel);
        toolPanel.add(chkFill);
        btnLine.addActionListener(this);
        btnRect.addActionListener(this);
        btnOval.addActionListener(this);
        btnColor.addActionListener(this);
        itmAbrir.addActionListener(this);
        itmGuardar.addActionListener(this);
        itmNuevo.addActionListener(this);
        itmInfo.addActionListener(this);
        setLayout(new BorderLayout());
        add(toolPanel, BorderLayout.NORTH);
        pp.addMouseListener(this);
        pp.addMouseMotionListener(this);
        add(pp, BorderLayout.CENTER);
        
        fc = new JFileChooser();
        setTitle("Proyecto Graficacion 2018");
        
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(!clicked){
            origen.setX(e.getX());
            origen.setY(e.getY());
            pp.setColor(color);
            clicked = true;
        }else{
            destino.setX(e.getX());
            destino.setY(e.getY());
            lista.add(new Figura(seleccion, new Point(origen), new Point(destino), color));
            clicked = false;
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        if(clicked){
            destino.setX(e.getX());
            destino.setY(e.getY());
            pp.repaint();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnLine){
            seleccion = PaintingPanel.LINEA;
        }
        if(e.getSource() == btnRect){
            if(chkFill.isSelected())
                seleccion = PaintingPanel.FRECT;
            else
                seleccion = PaintingPanel.RECT;
        }
        if(e.getSource() == btnOval){
            if(chkFill.isSelected())
                seleccion = PaintingPanel.FOVAL;
            else
                seleccion = PaintingPanel.OVAL;
        }
        if(e.getSource() == btnColor){
            color = JColorChooser.showDialog(null, "Selecciona un color...", Color.BLACK);
            colorPanel.setBackground(color);
        }
        if(e.getSource() == itmNuevo){
            lista.clear();
            pp.repaint();
        }
        if(e.getSource() == itmAbrir){
            fc.showOpenDialog(this);
            archivo = fc.getSelectedFile();
            try{
                ios = new ObjectInputStream(new FileInputStream(archivo));
                lista.clear();
                Figura aux = (Figura) ios.readObject();
                while(aux != null){
                    lista.add(aux);
                    aux = (Figura) ios.readObject();
                }
                ios.close();
            }catch(Exception ex){
            }finally{
                pp.repaint();
            }
        }
        if(e.getSource() == itmGuardar){
            fc.showSaveDialog(this);
            archivo = fc.getSelectedFile();
            try{
                oos = new ObjectOutputStream(new FileOutputStream(archivo));
                lista.forEach((fig) -> {
                    try{
                        oos.writeObject(fig);
                    }catch(IOException ex){
                    }
                });
                oos.close();
            }catch(IOException ex){
                System.out.println(e.paramString());
            }
        }
        if(e.getSource() == itmInfo){
            msg = "Proyecto de la clase de Graficacion Julio-Diciembre 2018\n"
                    + "El programa se compone de tres partes, la barra de menu, "
                    + "la barra de herramientas y el panel de dibujo.\n"
                    + "en la barra de menu se encuentran las opciones de nuevo dibujo, "
                    + "abrir un dibujo guardado en un archivo y guardar el dibujo actual "
                    + "en un archivo.\nEn la barra de herramientas se encuentran las diferentes "
                    + "opciones de dibujo que se pueden usar las cuales son Linea, Rectangulo y "
                    + "Ovalo, \nademas de el boton para poder cambiar el color que se usara para "
                    + "pintar las figuras, tambien se incluye una casilla de seleccion que permite "
                    + "decidir\nsi la figura que se va a pintar estara rellena de ese color o unicamente.\n"
                    + "El panel de dibujo es la seccion de color blanco que cubre la mayor parte de la ventana\n"
                    + "en el se dibujan las figuras que se desean haciendo primero click en el punto que quiere\n"
                    + "que sea el origen, moviendo el cursor hasta el punto origen deseado y haciendo click de nuevo\n"
                    + "Se hace uso del efecto liga\n "
                    + "se pintaran los bordes.\n\n"
                    + "Integrantes del equipo:\n"
                    + "-Hector Eduardo Raygoza Aguirre\n"
                    + "-Diego Valadez Olmos\n"
                    + "-Jose Julio Villegas Ayala\n"
                    + "-Cristofer Tosado";
            title = "Acerca del proyecto";
            JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
        }
        
        pp.setSeleccion(seleccion);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}
}
