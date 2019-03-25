package vaporware.objects;

public class Point3D {
    private double x;
    private double y;
    private double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }


    @Override
    public boolean equals(Object o) {


        if (o == this) {
            return true;
        }

        if (!(o instanceof Point3D)) {
            return false;
        }

        Point3D p = (Point3D) o;

        return p.getX() == x && p.getY() == y && p.getZ() == z;
    }

    @Override
    public String toString() {
        return "v " + x + " " + y + " " + z;
    }
}

