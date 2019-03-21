package vaporware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ObjReader {

    public Shape readOBJ(String path) throws IOException {


        ArrayList<Point3D> vertexes = new ArrayList<Point3D>();
        ArrayList<Face> faces = new ArrayList<Face>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] lineSplit = line.split(" ");
                if (lineSplit[0].equalsIgnoreCase("v")) {
                    vertexes.add(new Point3D(Double.parseDouble(lineSplit[1]), Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3])));
                } else if (lineSplit[0].equalsIgnoreCase("f")) {
                    Face face = new Face();
                    for (int i = 1; i < lineSplit.length; i++) {
                        face.addPoint(vertexes.get(Integer.parseInt(lineSplit[i]) - 1));
                    }
                    faces.add(face);

                }
            }

        }

        return new Shape(faces);


    }

    public String writeOBJ(String s, Shape s2) throws IOException {
        ArrayList<Point3D> vertexes = new ArrayList<>();
        ArrayList<FaceAux> faces = new ArrayList<>();

        for (Face f : s2.getFaces()) {
            FaceAux faceAux = new FaceAux();

            for (Point3D p : f.getPoints()) {
                int index = exists(p, vertexes);
                if (index == -1) {
                    vertexes.add(p);
                    faceAux.addIndex(vertexes.size());

                } else {
                    faceAux.addIndex(index + 1);

                }
            }
            faces.add(faceAux);
        }
        String output = "o Plane\n";
        for (Point3D p : vertexes) {
            output += p.toString() + "\n";
        }
        output += "s off\n";
        for (FaceAux f : faces) {
            output += f.toString() + "\n";
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

    private static int exists(Point3D p, ArrayList<Point3D> a) {
        int out = -1;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(p)) {
                out = i;
                break;
            }
        }
        return out;
    }

    private class FaceAux {
        private ArrayList<Integer> indexes = new ArrayList<>();

        public FaceAux() {

        }

        public void addIndex(int ind) {
            indexes.add(ind);
        }

        public ArrayList<Integer> getIndexes() {
            return indexes;
        }

        public String toString() {
            String out = "f ";
            for (int i : indexes) {
                out = out + i + " ";
            }
            return out;

        }

    }
}
