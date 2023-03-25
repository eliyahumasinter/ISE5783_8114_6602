package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a Sphere 3D shape
 * @author Eliyahu and Yishai
 */
public class Sphere extends RadialGeometry{
    Point center;

    public Sphere(Point c, double r){
        super(r);
        this.center = c;
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(getCenter()).normalize();
    }

    /**
     * Getter for field center
     * @return Point center
     */
    public Point getCenter() {
        return center;
    }
}
