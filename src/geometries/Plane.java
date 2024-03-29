package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

/**
 * Class that represents a Plane
 * @author Yishai and Eliyahu
 */
public class Plane extends Geometry {
    Point p0;
    Vector normal;

    /**
     * Sets p0 to p1 and finds normal from three points p1, p2 and p3.
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.p0 = p1;
        this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
    }

    /**
     * Normalizes received vector v and sets the fields to p and v.
     * @param p - Point
     * @param v - Vector
     */
    public Plane(Point p, Vector v) {
        this.p0 = p;
        this.normal = v.normalize();
    }

    /**
     * Getter for p0
     * @return Point p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for normal vector
     * @return Vector normal
     */

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * Override function for findGeoIntersectionsHelper
     * @param ray
     * @return a list of intersecting points with the ray
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        double nv = normal.dotProduct(ray.getDir());
        if (isZero(nv))
            return null;
        try {
            double t = alignZero(normal.dotProduct(p0.subtract(ray.getP0())) / nv);
            if (t > 0) {
                var p1 = ray.getPoint(t);
                return List.of(new GeoPoint(this, p1));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
