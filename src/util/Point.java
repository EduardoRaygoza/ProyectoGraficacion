package util;

import java.io.Serializable;

/**
 *
 * @author eduardo
 */
public class Point implements Serializable{
    private int X, Y;
    
    public Point(){}

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
    
    public Point(Point p){
        this.X = Integer.valueOf(p.getX());
        this.Y = Integer.valueOf(p.getY());
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }
}
