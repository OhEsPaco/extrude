package vaporware.algorithms;

import vaporware.obj.Obj;
import vaporware.objects.Point3D;
import vaporware.objects.Shape;

import java.util.ArrayList;

public class Lathe {
    public static Obj lathe(Obj obj, int steps, double angle, Point3D axis) {

        Obj obj_output = new Obj();

        Point3D normalizedAxis=normalizeVector(axis);

        for (Shape s_orig : obj.getShapes()) {

            Shape s_output = new Shape(s_orig);
            ArrayList<ArrayList<Point3D>> contours = new ArrayList<>();
            contours.add(s_output.getVertexes());
            double anglePerStep=angle/steps;
            double nextAngle=anglePerStep;
            for (int i = 0; i < steps; i++,nextAngle+=anglePerStep) {
                ArrayList<Point3D> new_contour = new ArrayList<>();
                ArrayList<Point3D> old_contour = contours.get(i);

                double[][]rotationMatrix=generateRotationMatrix(normalizedAxis, nextAngle);

                for(Point3D p:old_contour){
                    new_contour.add(calculateNewPoint(rotationMatrix,p));
                }

                contours.add(new_contour);

            }

            for(int i=0;i<contours.size()-1;i++){
                ArrayList<Point3D> this_contour = contours.get(i);
                ArrayList<Point3D> next_contour = contours.get(i+1);


                for(int j=0;j<this_contour.size()-1;j++){
                    int[]face=new int[4];
                    face[0]=s_output.addVertex(this_contour.get(i));
                    face[1]=s_output.addVertex(next_contour.get(i));
                    face[2]=s_output.addVertex(next_contour.get(i+1));
                    face[3]=s_output.addVertex(this_contour.get(i+1));
                    s_output.addFace(face);

                }
            }



        }

        return obj_output;

    }

    private static double[][] generateRotationMatrix(Point3D u, double angle) {
        double radians= Math.toRadians(angle);
        double[][] output = {
                {Math.cos(radians)+Math.pow(u.getX(),2)*(1-Math.cos(radians)), u.getX()*u.getY()*(1-Math.cos(radians))-u.getZ()*Math.sin(radians), u.getZ()*u.getX()*(1-Math.cos(radians))+u.getY()*Math.sin(radians)},
                {u.getY()*u.getX()*(1-Math.cos(radians))+u.getZ()*Math.sin(radians), Math.cos(radians)+Math.pow(u.getY(),2)*(1-Math.cos(radians)),  u.getY()*u.getZ()*(1-Math.cos(radians))-u.getX()*Math.sin(radians)},
                {u.getZ()*u.getX()*(1-Math.cos(radians))-u.getY()*Math.sin(radians), u.getZ()*u.getY()*(1-Math.cos(radians))+u.getX()*Math.sin(radians), Math.cos(radians)+Math.pow(u.getZ(),2)*(1-Math.cos(radians))}
        };
        return output;
    }

    private static Point3D calculateNewPoint(double[][]r, Point3D o){
        double x=r[0][0]*o.getX()+r[0][1]*o.getY()+r[0][2]*o.getZ();
        double y=r[1][0]*o.getX()+r[1][1]*o.getY()+r[1][2]*o.getZ();
        double z=r[2][0]*o.getX()+r[2][1]*o.getY()+r[2][2]*o.getZ();
        return new Point3D(x,y,z);
    }

    private static Point3D normalizeVector(Point3D v){
        double module = Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) + Math.pow(v.getZ(), 2));
        return new Point3D(v.getX() / module,v.getY() / module,v.getZ() / module);
    }
}
