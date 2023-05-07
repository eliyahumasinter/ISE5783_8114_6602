package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

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
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Return the ambient light intensity
     * @param gp Point
     * @return a Color
     */
    public Color calcColor(GeoPoint gp){
        return gp.geometry.getEmission().add(scene.ambientLight.getIntensity());
    }


}
