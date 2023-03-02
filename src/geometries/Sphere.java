package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    Point center;

    public Sphere(Point c, double r){
        super(r);
        this.center = c;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

}
