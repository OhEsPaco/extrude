package vaporware;

import java.util.ArrayList;

public class Shape {
    private ArrayList<Face> faces=new ArrayList<Face>();


    public Shape(ArrayList<Face> faces) {
        this.faces = faces;
    }

    public Shape(){

    }

    public ArrayList<Face> getFaces() {
        return faces;
    }

    public void addFace(Face f) {
        faces.add(f);
    }

    @Override
    public String toString() {
        return "Shape{" +
                "faces=" + faces +
                '}';
    }
}
