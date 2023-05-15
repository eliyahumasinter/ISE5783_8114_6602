package renderer;

import geometries.Triangle;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Ray Tracer Basic Implementation
 * @author Eliyahu and Yishai
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;

    /**
     * Function to determine if a point is not being shadowed
     * @param l Vector
     * @param n Vector
     * @param gp GeoPoint
     * @return boolean
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl){
            Vector lightDirection = l.scale(-1); // from point to light source

            Vector epsVector = n.scale(nl <0 ? DELTA: -DELTA);
            Point point = gp.point.add(epsVector);
            Ray lightRay = new Ray(point, lightDirection);

            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);


            if (intersections == null)
                return true;

            double lightDistance = light.getDistance(gp.point);
            for (GeoPoint geopoint : intersections) {
                if (Util.alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0);
                    return false;
            }
            return true;
    }

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
        return calcColor(closestPoint, ray);
    }

    /**
     * Return the ambient light intensity
     * @param gp GeoPoint
     * @return a Color
     */
    public Color calcColor(GeoPoint gp, Ray ray){
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp, ray));
    }


    /**
     * Helper function to calc diffusive light
     * @param mat - Material
     * @param nl - double - nl
     * @return
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.Kd.scale(Math.abs(nl));
    }

    /**
     * Helper function to calc the specular light
     * @param mat - Material
     * @param n - Vector
     * @param l - Vector
     * @param nl - double
     * @param v - Vector
     * @return
     */
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2*nl)).normalize();
        double vr = alignZero(v.dotProduct(r));
        return mat.Ks.scale(Math.pow(Math.max(0, -vr), mat.shininess));

    }

    /**
     * Calculates local effects for each light source on given GeoPoint
     * @param gp - GeoPoint
     * @param ray - Ray
     * @return color - Color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir (); Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v)); if (nv == 0) return color;
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(gp, lightSource, l, n, nl)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffusive(mat, nl)),
                            iL.scale(calcSpecular(mat, n, l, nl, v)));
                }
            }
        }
        return color;
    }




}
