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
     * Getter for field center
     * @return Point center
     */
    public Point getCenter() {
        return center;
    }


    /**
     * Override function for findGeoIntersectionsHelper
     * @param ray
     * @return a list of intersecting points with the ray
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        double tm = 0;
        double d = 0;
        if (!ray.getP0().equals(center)) {
            Vector u = this.center.subtract(ray.getP0());
            tm = ray.getDir().dotProduct(u);
            d = Math.sqrt(u.lengthSquared() - (tm * tm));
        }

        if (d >= this.radius)
            return null;
        double th = Math.sqrt(radius*radius - d*d);
        double t1 = tm + th;
        double t2 = tm - th;
        GeoPoint p1 = null;
        GeoPoint p2 = null;
        boolean includeP1 = false;
        boolean includeP2 = false;
        if (t1 > 0){
            p1 = new GeoPoint(this, ray.getPoint(t1));
            includeP1 = true;
        }
        if (t2 > 0){
            p2 = new GeoPoint(this, ray.getPoint(t2));
            includeP2 = true;
        }

        if (!includeP1 && !includeP2)
            return null;
        else if (includeP1 && !includeP2){
            return List.of(p1);
        }
        else if (!includeP1 && includeP2) {
            return List.of(p2);
        }
        else {
            return List.of(p1,p2);
        }
    }
}
