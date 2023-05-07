package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Abstract class for all radial geometric shapes
 * @author Eliyahu and Yishai
 */
public abstract class RadialGeometry extends Geometry {
    protected double radius;

    /**
     * Constructor setting the radius of the shape to r
     * @param r
     */
    public RadialGeometry(double r) {
        this.radius = r;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        return null;
    }
}
