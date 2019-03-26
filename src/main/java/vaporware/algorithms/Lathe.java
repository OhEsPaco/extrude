package vaporware.algorithms;

import vaporware.objects.Obj;
import vaporware.objects.Point3D;
import vaporware.objects.Shape;

import java.util.ArrayList;

public class Lathe {
    public static Obj lathe(Obj obj, int steps, double angle, Point3D axis) {


        //First, we create a new obj
        Obj obj_output = new Obj();

        //Then, the axis is normalized
        Point3D normalizedAxis = normalizeVector(axis);

        //Then, we spin every shape
        for (Shape s_orig : obj.getShapes()) {
            Shape s_output = new Shape(s_orig);

            //Number of edges in the original shape
            int numberOfEdges = s_output.getEdges().size();

            //The angle of every step
            double anglePerStep = angle / steps;

            //The next angle that must be calculated
            double nextAngle = anglePerStep;

            ArrayList<int[]> edges = s_orig.getEdges();

            for (; nextAngle <= angle; nextAngle += anglePerStep) {

                //Calculate rotation matrix
                double[][] rotationMatrix = generateRotationMatrix(normalizedAxis, nextAngle);

                //Then every new edge is calculated and added to the edge list of the shape
                for (int j = 0; j < edges.size(); j++) {
                    int[] newEdge = new int[2];
                    newEdge[0] = s_output.addVertex(calculateNewPoint(rotationMatrix, s_output.getVertex(edges.get(j)[0])));
                    newEdge[1] = s_output.addVertex(calculateNewPoint(rotationMatrix, s_output.getVertex(edges.get(j)[1])));
                    s_output.addEdge(newEdge);
                }

            }

            //Every new face is a quad.
            //If an edge of a face is stored in the position i,
            //the oposite edge can be found in i + numberOfEdges
            for (int i = 0; i < (s_output.getEdges().size()) - numberOfEdges; i++) {
                int[] face = new int[4];
                face[0] = s_output.getEdges().get(i)[1];
                face[1] = s_output.getEdges().get(i + numberOfEdges)[1];
                face[2] = s_output.getEdges().get(i + numberOfEdges)[0];
                face[3] = s_output.getEdges().get(i)[0];
                s_output.addFace(face);
            }

            obj_output.addShape(s_output);

        }

        return obj_output;

    }

    private static double[][] generateRotationMatrix(Point3D u, double angle) {
        double radians = Math.toRadians(angle);

        //https://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle
        double[][] output = {
                {Math.cos(radians) + Math.pow(u.getX(), 2) * (1 - Math.cos(radians)), u.getX() * u.getY() * (1 - Math.cos(radians)) - u.getZ() * Math.sin(radians), u.getZ() * u.getX() * (1 - Math.cos(radians)) + u.getY() * Math.sin(radians)},
                {u.getY() * u.getX() * (1 - Math.cos(radians)) + u.getZ() * Math.sin(radians), Math.cos(radians) + Math.pow(u.getY(), 2) * (1 - Math.cos(radians)), u.getY() * u.getZ() * (1 - Math.cos(radians)) - u.getX() * Math.sin(radians)},
                {u.getZ() * u.getX() * (1 - Math.cos(radians)) - u.getY() * Math.sin(radians), u.getZ() * u.getY() * (1 - Math.cos(radians)) + u.getX() * Math.sin(radians), Math.cos(radians) + Math.pow(u.getZ(), 2) * (1 - Math.cos(radians))}
        };
        return output;
    }

    private static Point3D calculateNewPoint(double[][] r, Point3D o) {
        double x = r[0][0] * o.getX() + r[0][1] * o.getY() + r[0][2] * o.getZ();
        double y = r[1][0] * o.getX() + r[1][1] * o.getY() + r[1][2] * o.getZ();
        double z = r[2][0] * o.getX() + r[2][1] * o.getY() + r[2][2] * o.getZ();
        return new Point3D(x, y, z);
    }

    private static Point3D normalizeVector(Point3D v) {
        double module = Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) + Math.pow(v.getZ(), 2));
        return new Point3D(v.getX() / module, v.getY() / module, v.getZ() / module);
    }
}
