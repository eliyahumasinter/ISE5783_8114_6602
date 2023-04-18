package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
     * Override function for findIntersections
     * @param ray
     * @return a list of interesecting points with the ray
     */
    @Override
    public List<Point> findIntersections(Ray ray){
        return null;
    }

    /**
     * Getter for field center
     * @return Point center
     */
    public Point getCenter() {
        return center;
    }
}
