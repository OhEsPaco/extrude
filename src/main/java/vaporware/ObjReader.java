package vaporware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ObjReader {
    public static Shape readOBJ(String path) throws IOException {


        ArrayList<Point3D>vertexes=new ArrayList<Point3D>();
        ArrayList<Face>faces=new ArrayList<Face>();

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] lineSplit = line.split(" ");
                if(lineSplit[0].equals("v") ){
                    vertexes.add(new Point3D(Double.parseDouble(lineSplit[1]),Double.parseDouble(lineSplit[2]),Double.parseDouble(lineSplit[3])));
                }else if( lineSplit[0].equals("f")){
                    Face face=new Face();
                    for(int i=1;i<lineSplit.length;i++){
                        face.addPoint(vertexes.get(Integer.parseInt(lineSplit[i])-1));
                    }
                    faces.add(face);

                }
            }

        }

        return new Shape(faces, vertexes);


    }
}
