package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Eliyahu Masinter and Yishai Dredzen
 * Interface Intersectable
 *
 */
public abstract class Intersectable {
    /**
     * Function for finding intersection points between a ray and a geometry
     *
     * @param ray
     * @return a list of points
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    /**
     * Static class that holds a Geometry and a Point
     * @author Eliyahu and Yishai
     */
    public static class GeoPoint {

        public Geometry geometry;
        //TODO: Check if Point and Point3D are the same...
        public Point point;

        /**
         * Constructor that sets both fields to given values
         * @param g - Geometry parameter
         * @param p - Point parameter
         */
        public GeoPoint(Geometry g, Point p) {
            this.geometry = g;
            this.point = p;
        }

        /**
         * Equals method that checks whether two GeoPoint objects are equal
         * @param o - the object given to the function
         * @return boolean value - true if equal and false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;

            GeoPoint geoPoint = (GeoPoint) o;

            if (!geometry.equals(geoPoint.geometry)) return false;
            return point.equals(geoPoint.point);
        }

        /**
         * Returns a String containt=ing the values and details of this GeoPoint object
         * @return String of values about GeoPoint
         */
        @Override
        public String toString() {
            return "Colour: " + this.geometry.emission + ", Point: " + this.point.toString();
        }
    }

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    public abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}