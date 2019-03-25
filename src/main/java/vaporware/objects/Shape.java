package vaporware.objects;

import java.util.ArrayList;

public class Shape {
    private ArrayList<int[]> faces = new ArrayList<int[]>();
    private ArrayList<Point3D> vertexes = new ArrayList<Point3D>();
    private String object_name = "default";
    private String smooth_shading = "off";
    private ArrayList<int[]> edges = new ArrayList<int[]>();

    public Shape() {
        //Empty constructor
    }


    public Shape(Shape orig) {
        //This constructor clones a shape
        if (orig != null) {
            this.object_name = orig.getObject_name();
            this.smooth_shading = orig.getSmooth_shading();
            for (Point3D p : orig.getVertexes()) {
                this.addVertex(new Point3D(p.getX(), p.getY(), p.getZ()));
            }
            for (int[] face : orig.getFaces()) {
                int[] dest = new int[5];
                System.arraycopy(face, 0, dest, 0, face.length);
                this.addFace(dest);
            }

            for (int[] edge : orig.getEdges()) {
                int[] dest = new int[2];
                System.arraycopy(edge, 0, dest, 0, edge.length);
                this.addEdge(dest);
            }


        }
    }

    public void addEdge(int[] edge) {
        edges.add(edge);
    }

    public ArrayList<int[]> getEdges() {
        return edges;
    }

    public ArrayList<int[]> getFaces() {
        return faces;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getSmooth_shading() {
        return smooth_shading;
    }

    public void setSmooth_shading(String smooth_shading) {
        this.smooth_shading = smooth_shading;
    }

    public Point3D getVertex(int i) {
        return vertexes.get(i);
    }

    public int addVertex(Point3D v) {
        if (!vertexes.contains(v)) {
            vertexes.add(v);
            return vertexes.size() - 1;
        } else {
            return vertexes.indexOf(v);
        }
    }

    public void addFace(int[] f) {
        if (!faces.contains(f)) {
            faces.add(f);
        }
    }

    public ArrayList<Point3D> getVertexes() {
        return vertexes;
    }

    @Override
    public String toString() {

        String output = "o " + this.getObject_name() + "\n";
        for (Point3D v : this.getVertexes()) {
            output += "v " + v.getX() + " " + v.getY() + " " + v.getZ() + "\n";
        }
        output += "s " + this.getSmooth_shading() + "\n";
        for (int[] face : this.getFaces()) {
            output += "f";
            for (int ind : face) {
                output += " " + (ind + 1);
            }
            output += "\n";
        }
        return output;
    }


}
