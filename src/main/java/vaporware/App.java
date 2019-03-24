package vaporware;

import vaporware.algorithms.Extrude;
import vaporware.obj.Obj;
import vaporware.obj.ObjReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {

            printInfo();
            System.exit(0);
        }
        ObjReader o = new ObjReader();

        switch (args[0]) {
            case "-e":
                float distance = 0;
                try {
                    distance = Float.parseFloat(args[1]);
                } catch (java.lang.NumberFormatException w) {
                    System.out.println("Incorrect distance");
                    System.exit(1);
                }

                for(int i=2;i<args.length;i++){
                    Obj s = o.readOBJ(args[i]);
                    if (s.getShapes().size() == 0) {
                        System.out.println(args[i]+" is empty");
                        continue;
                    }
                    Obj s2 = Extrude.extrude(distance, s);

                    Path path = Paths.get(args[i]);
                    String file = path.getFileName().toString();

                    o.writeOBJ(file.split("\\.")[0]+"_extruded.obj",s2);
                }


                break;
            default:
                printInfo();
        }


    }

    public static void printInfo() {
        System.out.println("Options:");
        System.out.println("-e distance file file...");

    }
}
