package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface that manages the methods of all geometries
 * @author Eliyahu and Yishai
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();


    /**
     * Setter for material field
     * @param material Material
     * @return Geometry - this
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return  this;
    }

    /**
     * Getter for material field
     * @return Material
     */
    public Material getMaterial() {
        return material;
    }

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
