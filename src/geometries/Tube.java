package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class to represent a Tube 3D object
 * @author Eliyahu and Yishai
 */
public class Tube extends RadialGeometry {
    Ray axisRay;

    /**
     * Constructor of a Tube setting fields to parsed fields
     * @param radius
     * @param ray
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        this.axisRay = ray;
    }

    /**
     * Getter
     * @return axisRay field's value
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point p) {
        double t = getAxisRay().getDir().dotProduct(p.subtract(getAxisRay().getP0()));
        Point O = getAxisRay().getP0().add(getAxisRay().getDir().scale(t));
        return p.subtract(O).normalize();
    }
}
