package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;
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
        List<Point> interceptPlane = super.plane.findIntersections(ray);

        if (interceptPlane != null) {
            Vector v1 = super.vertices.get(0).subtract(ray.getP0());
            Vector v2 = super.vertices.get(1).subtract(ray.getP0());
            Vector v3 = super.vertices.get(2).subtract(ray.getP0());
            Vector n1 = (v1.crossProduct(v2)).normalize();
            Vector n2 = (v2.crossProduct(v3)).normalize();
            Vector n3 = (v3.crossProduct(v1)).normalize();

            Vector v = ray.getDir();
            double d1 = v.dotProduct(n1);
            double d2 = v.dotProduct(n2);
            double d3 = v.dotProduct(n3);
            if ( isZero(d1) || isZero(d2) || isZero(d3))
                return null;
            if ((d1>0 && d2 >0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0))
                return interceptPlane;
            return null;
        }
        return null;
    }

}
