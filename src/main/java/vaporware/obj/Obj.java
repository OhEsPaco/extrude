package vaporware.obj;

import vaporware.objects.Shape;

import java.util.ArrayList;

public class Obj {
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
