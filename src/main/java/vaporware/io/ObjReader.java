package vaporware.io;

import vaporware.objects.Obj;
import vaporware.objects.Point3D;
import vaporware.objects.Shape;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ObjReader {

    public Obj readOBJ(String path) throws IOException {

        Obj obj = new Obj();
        Shape current_shape = null;
        int lastShapeLength = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] lineSplit = line.split(" ");
                switch (lineSplit[0]) {
                    case "o":
                        if (current_shape != null) {
                            lastShapeLength += current_shape.getVertexes().size();
                            obj.addShape(current_shape);
                            current_shape = new Shape();
                            current_shape.setObject_name(lineSplit[1]);
                        } else if (current_shape == null) {
                            current_shape = new Shape();
                            current_shape.setObject_name(lineSplit[1]);
                        }
                        break;
                    case "v":
                        if (current_shape != null) {
                            current_shape.addVertex(new Point3D(Double.parseDouble(lineSplit[1]), Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3])));
                        }
                        break;
                    case "s":
                        if (current_shape != null) {
                            current_shape.setSmooth_shading(lineSplit[1]);
                        }

                        break;
                    case "f":
                        if (current_shape != null) {
                            int[] vertexes = new int[lineSplit.length - 1];
                            for (int i = 0; i < vertexes.length; i++) {
                                vertexes[i] = (Integer.parseInt(lineSplit[i + 1]) - 1) - lastShapeLength;
                            }
                            current_shape.addFace(vertexes);
                        }
                        break;
                    case "l":
                        if (current_shape != null) {
                            int[] edge = new int[2];
                            edge[0] = Integer.parseInt(lineSplit[1]);
                            edge[1] = Integer.parseInt(lineSplit[2]);
                            current_shape.addEdge(edge);
                        }
                        break;

                }


            }

            if (current_shape != null) {
                obj.addShape(current_shape);
            }

        }

        return obj;


    }

    public String writeOBJ(String s, Obj obj) throws IOException {

        String output = "";
        int lastShapeLength = 0;
        for (Shape shape : obj.getShapes()) {

            output += "o " + shape.getObject_name() + "\n";
            for (Point3D v : shape.getVertexes()) {
                output += "v " + v.getX() + " " + v.getY() + " " + v.getZ() + "\n";
            }
            output += "s " + shape.getSmooth_shading() + "\n";
            for (int[] face : shape.getFaces()) {
                output += "f";
                for (int ind : face) {
                    output += " " + ((ind + 1) + lastShapeLength);
                }
                output += "\n";
            }
            lastShapeLength += shape.getVertexes().size();

        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(s, false);
            fw.write(output);
        } catch (Exception e) {

        } finally {
            fw.close();
        }

        return output;
    }


}
