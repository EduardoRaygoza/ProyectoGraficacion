/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Graphics;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author eduardo
 */
public class Figura implements Serializable{
    private int fig;
    private Point origen, destino;
    private Color color;
    
    public static final int LINEA = 0;
    public static final int RECT = 1;
    public static final int OVAL = 2;

    public Figura(final int fig, Point origen, Point destino, Color color) {
        this.fig = fig;
        this.origen = origen;
        this.destino = destino;
        this.color = color;
    }
    
    public void draw(Graphics g){
        g.setColor(color);
        switch(fig){
            case LINEA: g.drawLine(origen.getX(), origen.getY(),
                    destino.getX(), destino.getY());
            break;
            
            case RECT: g.drawRect(origen.getX(), origen.getY(),
                    (destino.getX()-origen.getX()), (destino.getY()-origen.getY()));
            break;
            
            case OVAL: g.drawOval(origen.getX(), origen.getY(),
                    (destino.getX()-origen.getX()), (destino.getY()-origen.getY()));
            break;
        }
    }
    
    public void setDestino(Point p){
        this.destino = p;
    }
    
    public Color getColor(){
        return color;
    }
    
    @Override
    public String toString(){
        return "Figura: "+fig+" x1: "+origen.getX()+" y1: "+origen.getY();
    }
}
