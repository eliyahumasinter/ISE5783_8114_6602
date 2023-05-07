package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Interface that manages the methods of all geometries
 * @author Eliyahu and Yishai
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    /**
     * Getter for emission field
     * @return emission - a Color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets emission to given value
     * @param emission - Color field
     * @return this - the Geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public abstract Vector getNormal(Point p);
}
