package vaporware;

public class ExtrudeAlg {

    public static Shape extrude(float distance, Shape s_orig) {

        Shape output = new Shape();

        for(int i=0;i<s_orig.getFaces().size();i++){
            Face back = s_orig.getFaces().get(i);
            output.addFace(back);
            Point3D normal = null;

            try {
                normal = calculateNormalVector(back.getPoints().get(0), back.getPoints().get(1), back.getPoints().get(2));

            } catch (CollinearPointsException e) {

                continue;
            }

            Face top = new Face();
            for(Point3D p : back.getPoints()){
                top.addPoint(extrudePoint(p, normal, distance));
            }

            output.addFace(top);

            for(int faceNumber=0;faceNumber<back.getPoints().size();faceNumber++){


                Face side= new Face();

                if(faceNumber==back.getPoints().size()-1){
                    side.addPoint(back.getPoint(faceNumber));
                    side.addPoint(back.getPoint(0));
                    side.addPoint(top.getPoint(0));
                    side.addPoint(top.getPoint(faceNumber));
                    output.addFace(side);
                }else{
                    side.addPoint(back.getPoint(faceNumber));
                    side.addPoint(back.getPoint(faceNumber+1));
                    side.addPoint(top.getPoint(faceNumber+1));
                    side.addPoint(top.getPoint(faceNumber));
                    output.addFace(side);
                }

            }


        }


        return output;

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
