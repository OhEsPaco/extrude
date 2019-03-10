package vaporware;

import java.util.ArrayList;

public class ExtrudeAlg {

    public static Shape extrude(double distance, Shape origin) {


        Point3D p1 = new Point3D(0.44425, 0.47858, 5.79136);
        Point3D p2 = new Point3D(2.28168, 0.4743, 5.00152);
        Point3D p3 = new Point3D(1.72776, 1.89304, 3.70524);
       /* Point3D p1 = new Point3D(-1, 0, 2);
        Point3D p2 = new Point3D(1, 1, 4);
        Point3D p3 = new Point3D(3, 2, 6);*/

        Point3D normal = null;
        try {
            normal = calculateNormalVector(p1, p2, p3);
            Point3D ext = extrudePoint(p2, normal, 3.2671);
            System.out.println("Normal Vector: " + normal.getX() + " " + normal.getY() + " " + normal.getZ());
            System.out.println("Extruded point: " + ext.getX() + " " + ext.getY() + " " + ext.getZ());
        } catch (CollinearPointsException e) {
            System.out.println("These points are collinear");
        }


        //  }

        return null;

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

    private static Point3D extrudePoint(Point3D orig, Point3D normal, double distance) {
        return new Point3D(orig.getX() + normal.getX() * distance, orig.getY() + normal.getY() * distance, orig.getZ() + normal.getZ() * distance);
    }
}
