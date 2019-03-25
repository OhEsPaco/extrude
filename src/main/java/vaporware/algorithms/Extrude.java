package vaporware.algorithms;

import vaporware.exceptions.CollinearPointsException;
import vaporware.obj.Obj;
import vaporware.objects.Point3D;
import vaporware.objects.Shape;

public class Extrude {

    public static Obj extrude(float distance, Obj obj) {

        Obj obj_output = new Obj();

        for (Shape s_orig : obj.getShapes()) {

            Shape s_output = new Shape(s_orig);
            for (int[] back : s_orig.getFaces()) {
                Point3D normal = null;
                try {
                    normal = calculateNormalVector(s_output.getVertex(back[0]), s_output.getVertex(back[1]), s_output.getVertex(back[2]));

                } catch (CollinearPointsException e) {

                    continue;
                }

                int[] top = new int[back.length];
                for (int i = 0; i < top.length; i++) {
                    top[i] = s_output.addVertex(extrudePoint(s_output.getVertex(back[i]), normal, distance));
                }

                s_output.addFace(top);

                for (int faceNumber = 0; faceNumber < back.length; faceNumber++) {

                    int[] side = new int[4];
                    side[0] = back[faceNumber];
                    if (faceNumber == back.length - 1) {
                        side[1] = back[0];
                        side[2] = top[0];
                    } else {
                        side[1] = back[faceNumber + 1];
                        side[2] = top[faceNumber + 1];
                    }
                    side[3] = top[faceNumber];
                    s_output.addFace(side);
                }


            }

            obj_output.addShape(s_output);
        }

        return obj_output;

    }

    private static Point3D calculateNormalVector(Point3D p1, Point3D p2, Point3D p3) throws CollinearPointsException {

        Point3D normal = new Point3D(
                (p2.getY() - p1.getY()) * (p3.getZ() - p1.getZ()) - (p3.getY() - p1.getY()) * (p2.getZ() - p1.getZ()),
                (p2.getZ() - p1.getZ()) * (p3.getX() - p1.getX()) - (p2.getX() - p1.getX()) * (p3.getZ() - p1.getZ()),
                (p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p3.getX() - p1.getX()) * (p2.getY() - p1.getY())

        );

        if (normal.getX() == 0 && normal.getY() == 0 && normal.getZ() == 0) {
            throw new CollinearPointsException();
        }

        double module = Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2));
        normal.setX(normal.getX() / module);
        normal.setY(normal.getY() / module);
        normal.setZ(normal.getZ() / module);

        return normal;

    }



    private static Point3D extrudePoint(Point3D orig, Point3D normal, float distance) {
        return new Point3D(orig.getX() + normal.getX() * distance, orig.getY() + normal.getY() * distance, orig.getZ() + normal.getZ() * distance);
    }
}
