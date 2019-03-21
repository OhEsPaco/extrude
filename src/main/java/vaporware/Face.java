package vaporware;

import java.util.ArrayList;

public class Face {
    ArrayList<Point3D>vertexes=new ArrayList<Point3D>();

    public Face(){

    }

    public void addPoint(Point3D p){
        vertexes.add(p);
    }

    public Point3D getPoint(int i){
       return vertexes.get(i);
    }

    public ArrayList<Point3D> getPoints(){
        return vertexes;
    }

    @Override
    public String toString() {
        return "Face{" +
                "vertexes=" + vertexes +
                '}';
    }
}
