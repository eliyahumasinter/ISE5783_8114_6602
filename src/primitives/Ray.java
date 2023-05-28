package primitives;


import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * Manages a ray which defines a set of all points on one side of a line in one direction
 * @author Eliyahu and Yishai
 */
public class Ray {
    Point p0;
    Vector dir;

    public Vector getDir() {
        return dir;
    }
    public Point getP0() {
        return p0;
    }
    /**
     * Initialises fields p0 and dir to Point p and normalized Vector v
     * @param p
     * @param v
     */
    public Ray(Point p, Vector v) {
        this.p0 = p;
        this.dir = v.normalize();
    }
    private static final double DELTA = 0.1;

    /**
     * Constructor accepting a Point and 2 Vectors
     * @param p0 - Point
     * @param dir - Vector
     * @param normal - Vector
     */
    public Ray(Point p0, Vector dir, Vector normal) {
        this(p0, dir);
        double nv = normal.dotProduct(this.dir);
        if (!Util.isZero(nv)) {
            Vector delta = normal.scale(nv > 0 ? DELTA : -DELTA);
            this.p0 = p0.add(delta);
        }
    }

    /**
     * A method to simplify testing
     *
     * @param t
     * @return a Vector
     */
    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }


    /**
     * Returns the closest point to the head of our ray
     * @param points
     * @return a point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Returns the closest GeoPoint to the head of our ray
     * @param geoPoints
     * @return a GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints == null || geoPoints.isEmpty())
            return null;

        GeoPoint min = geoPoints.get(0);
        double minDis = min.point.distance(p0);
        for (GeoPoint gp : geoPoints){
            if (gp.point.distance(p0) < minDis) {
                min = gp;
                minDis = gp.point.distance(p0);
            }
        }
        return min;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray) obj;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString(){
        return "Ray: Center at " + this.p0 + "\n Direction " + this.dir;
    }
}
