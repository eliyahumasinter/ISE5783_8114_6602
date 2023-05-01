package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * Ray Tracer Basic Implementation
 * @author Eliyahu and Yishai
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructor that takes in a scene
     */
    public RayTracerBasic(Scene s){
        super(s);
    }

    /**
     * Implementation of traceRay method
     * @param ray ray
     * @return Color
     */
    @Override
    public Color traceRay(Ray ray){
        List<Point> intersections = this.scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Return the ambient light intensity
     * @param p Point
     * @return a Color
     */
    public Color calcColor(Point p){
        return scene.ambientLight.getIntensity();
    }


}
