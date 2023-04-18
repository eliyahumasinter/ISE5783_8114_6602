package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * @author Eliyahu Masinter and Yishai Dredzen
 * Interface Intersectable
 *
 */
public interface Intersectable {
    /**
     * Function for finding intersection points between a ray and a geometry
     *
     * @param ray
     * @return a list of points
     */
    List<Point> findIntersections(Ray ray);
}