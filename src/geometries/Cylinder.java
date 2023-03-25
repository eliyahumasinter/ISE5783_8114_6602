package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a Cylinder 3D shape
 * @author Eliyahu and Yishai
 */
public class Cylinder extends Tube {
    double height;

    /**
     * Construct a Cylinder
     * @param radius - radius of cylinder
     * @param ray - ray defining cylinder
     * @param height - height of cylinder
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        //Figure out where the point is: if it's on the base return below otherwise return normal of tube
        return getAxisRay().getDir().normalize();
    }



}
