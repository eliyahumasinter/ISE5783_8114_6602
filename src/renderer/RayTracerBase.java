package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Ray Trace Base Class
 * @author Eliyahu and Yishai
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructor that takes in a scene
     * @param s
     */
    public RayTracerBase(Scene s) {
        scene = s;
    }

    /**
     * trace ray function that returns a color
     * @param ray
     * @return Color
     */
    public abstract Color traceRay (Ray ray);

}
