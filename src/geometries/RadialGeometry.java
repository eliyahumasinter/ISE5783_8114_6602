package geometries;

/**
 * Abstract class for all radial geometric shapes
 */
public abstract class RadialGeometry implements Geometry {
    protected double radius;

    /**
     * Constructor setting the radius of the shape to r
     * @param r
     */
    public RadialGeometry(double r) {
        this.radius = r;
    }


}
