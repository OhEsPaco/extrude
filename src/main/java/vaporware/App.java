package vaporware;

import vaporware.algorithms.Extrude;
import vaporware.io.ObjReader;
import vaporware.objects.Obj;
import vaporware.objects.Point3D;

import java.io.IOException;

import static vaporware.algorithms.Lathe.lathe;

public class App {
    public static void main(String[] args) {
        if (args.length < 1) {

            printInfo();
            System.exit(0);
        }
        ObjReader o = new ObjReader();
        switch (args[0]) {
            case "extrude":
                if (args.length != 4) {
                    printInfo();
                } else {
                    try {

                        Obj o1 = null;
                        try {
                            o1 = o.readOBJ(args[1]);
                        } catch (IOException e) {
                            System.out.println("Impossible to read " + args[1]);
                            System.exit(1);
                        }

                        float distance = 0;
                        try {
                            distance = Float.parseFloat(args[3]);
                        } catch (java.lang.NumberFormatException w) {
                            System.out.println("Incorrect length.");
                            System.exit(1);
                        }

                        Obj s2 = Extrude.extrude(distance, o1);

                        try {
                            o.writeOBJ(args[2], s2);
                        } catch (IOException e) {
                            System.out.println("Impossible to write " + args[2]);
                            System.exit(1);
                        }

                    } catch (Exception e3) {
                        e3.printStackTrace();
                        System.out.println("Unknown exception.");
                        System.exit(1);
                    }

                }
                break;
            case "lathe":

                if (args.length != 8) {
                    printInfo();
                } else {
                    try {

                        Obj o1 = null;
                        try {
                            o1 = o.readOBJ(args[1]);
                        } catch (IOException e) {
                            System.out.println("Impossible to read " + args[1]);
                            System.exit(1);
                        }

                        int steps = 0;
                        try {
                            steps = Integer.parseInt(args[3]);
                        } catch (java.lang.NumberFormatException w) {
                            System.out.println("Incorrect step number.");
                            System.exit(1);
                        }
                        Point3D axis = null;
                        try {
                            axis = new Point3D(Double.parseDouble(args[5]), Double.parseDouble(args[6]), Double.parseDouble(args[7]));
                        } catch (Exception e) {
                            System.out.println("Wrong axis.");
                            System.exit(1);
                        }

                        double angle = 0;
                        try {
                            angle = Double.parseDouble(args[4]);
                        } catch (Exception e) {
                            System.out.println("Wrong angle.");
                            System.exit(1);
                        }

                        Obj s2 = lathe(o1, steps, angle, axis);

                        try {
                            o.writeOBJ(args[2], s2);
                        } catch (IOException e) {
                            System.out.println("Impossible to write " + args[2]);
                            System.exit(1);
                        }

                    } catch (Exception e3) {
                        System.out.println("Unknown exception.");
                        System.exit(1);
                    }

                }

                break;
            default:
                printInfo();
                System.exit(0);


        }


    }

    public static void printInfo() {
        System.out.println("extrude <input> <output> <length>");
        System.out.println("e.g.");
        System.out.println("extrude cube.obj cube2.obj 10\n");
        System.out.println("lathe <input> <output> <steps> <angle> <axis>");
        System.out.println("e.g.");
        System.out.println("lathe cube.obj cube2.obj 10 90 6 2 5");
    }
}
