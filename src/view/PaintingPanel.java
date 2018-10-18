package view;

import java.awt.Color;
import javax.swing.JPanel;
import util.Point;
import util.Figura;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author eduardo
 */
public class PaintingPanel extends JPanel{
    private Point origen, destino;
    private int seleccion;
    private ArrayList<Figura> lista;
    private Color color;
    
    public static final int LINEA = 0;
    public static final int RECT = 1;
    public static final int OVAL = 2;
    
    
    public PaintingPanel(Point origen, Point destino, ArrayList<Figura> lista){
        this.seleccion = 0;
        this.origen = origen;
        this.destino = destino;
        this.lista = lista;
        setBackground(Color.WHITE);
    }
    
    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        if(!lista.isEmpty()){
            lista.forEach((fig) -> {
                g.setColor(fig.getColor());
                fig.draw(g);
            });
        }
        g.setColor(color);
        switch(seleccion){
            case LINEA:
                g.drawLine(origen.getX(), origen.getY(), destino.getX(), destino.getY());
                break;
            case RECT:
                g.drawRect(origen.getX(), origen.getY(),
                        (destino.getX()-origen.getX()),
                        (destino.getY()-origen.getY()));
                break;
            case OVAL:
                g.drawOval(origen.getX(), origen.getY(),
                        (destino.getX()-origen.getX()),
                        (destino.getY()-origen.getY()));
                break;
        }
    }
    
    public void setSeleccion(int s){
        this.seleccion = s;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
}