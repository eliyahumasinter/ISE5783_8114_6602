package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * A class of extending the Polygon class to represent a triangle
 */
public class Triangle extends Polygon{

    /**
     * Constructor that takes in 3 points
     * @param p0
     * @param p1
     * @param p2
     */
    public Triangle(Point p0, Point p1, Point p2){
        super(p0,p1,p2);
    }


    /**
     * Override function for findIntersections
     * @param ray
     * @return a list of interesecting points with the ray
     */
    @Override
    public List<Point> findIntersections(Ray ray){
        //TODO: Check this please
        if (super.plane.findIntersections(ray).size() > 0) {
            Vector v1 = super.vertices.get(0).subtract(ray.getPoint());
            Vector v2 = super.vertices.get(1).subtract(ray.getPoint());
            Vector v3 = super.vertices.get(2).subtract(ray.getPoint());
            Vector n1 = (v1.crossProduct(v2)).normalize();
            Vector n2 = (v2.crossProduct(v3)).normalize();
            Vector n3 = (v3.crossProduct(v1)).normalize();

            if (n1.dotProduct(ray.getDir()) > 0 && n2.dotProduct(ray.getDir()) > 0 && n3.dotProduct(ray.getDir()) > 0) {
                return super.plane.findIntersections(ray);
            }
            else if (n1.dotProduct(ray.getDir()) < 0 && n2.dotProduct(ray.getDir()) < 0 && n3.dotProduct(ray.getDir()) < 0) {
                return super.plane.findIntersections(ray);
            }
        }
        return null;
    }

}
