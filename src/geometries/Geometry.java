package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Interface that manages the methods of all geometries
 * @author Eliyahu and Yishai
 */
public interface Geometry extends Intersectable{
    public Vector getNormal(Point p);

}
