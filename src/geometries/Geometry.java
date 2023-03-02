package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface that manages the methods of all geometries
 */
public interface Geometry {
    public Vector getNormal(Point p);
}
