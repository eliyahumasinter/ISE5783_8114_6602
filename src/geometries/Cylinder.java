package geometries;

import primitives.Ray;

/**
 * Represents a Cylinder 3D shape
 * @author Eliyahu and Yishai
 */
public class Cylinder extends Tube {
    double height;

    /**
     * Contstruct a Cylinder
     * @param radius
     * @param ray
     * @param height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this.height = height;
    }


}
