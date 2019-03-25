package vaporware.objects;

import java.util.ArrayList;

public class Obj {

    //An obj object is composed by an array of shapes
    private ArrayList<Shape> shapes = new ArrayList<>();

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void addShape(Shape s) {
        shapes.add(s);
    }

    public String toString() {
        String output = "";
        for (Shape s : shapes) {
            output += s.toString();
        }
        return output;
    }
}
