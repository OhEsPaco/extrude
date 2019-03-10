package vaporware;

import java.util.ArrayList;

public class Face {
    ArrayList<Point3D>points=new ArrayList<Point3D>();

    public Face(){

    }

    public void addPoint(Point3D p){
        points.add(p);
    }

    public void delPoint(Point3D p){
        for(Point3D a:points){
            if(a.equals(p)){
                points.remove(a);
            }
        }
    }

    public ArrayList<Point3D> getPoints(){
        return points;
    }


}
