package vaporware;

import java.util.ArrayList;

public class Shape {
    private ArrayList<Face> faces;
    private ArrayList<Point3D> vertexes;

    public Shape(ArrayList<Face> faces, ArrayList<Point3D> vertexes) {
        this.faces = faces;
        this.vertexes = vertexes;
    }

    public ArrayList<Face> getFaces() {
        return faces;
    }

    public void addVertex(Point3D v) {
        vertexes.add(v);
    }

    public void addFace(Face f) {
        faces.add(f);
    }

    public void setFaces(ArrayList<Face> faces) {
        this.faces = faces;
    }

    public ArrayList<Point3D> getVertexes() {
        return vertexes;
    }

    public void setVertexes(ArrayList<Point3D> vertexes) {
        this.vertexes = vertexes;
    }
}
