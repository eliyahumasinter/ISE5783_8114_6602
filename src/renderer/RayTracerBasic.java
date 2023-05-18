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
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

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
                if (Util.alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0
                && gp.geometry.getMaterial().Kt == Double3.ZERO);
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
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null) {
            return scene.background;
        }
        return calcColor(closestPoint, ray);
    }

    /**
     * calculates color
     * @param gp
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates color
     * @param closestPoint
     * @param ray
     * @param level
     * @param kkt
     * @return Color
     * @return Color
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray, int level, Double3 kkt) {
        if (closestPoint == null)
            return scene.background;
        Color color = closestPoint.geometry.getEmission().add(calcLocalEffects(closestPoint, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(closestPoint, ray, level, kkt));
    }

    /**
     * Computes Global effects on geometries
     * @param geopoint
     * @param ray
     * @param level
     * @param kkt2
     * @return Color
     */
    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, Double3 kkt2) {
        Color color = Color.BLACK;
        Material material = geopoint.geometry.getMaterial();
        Double3 kr = material.Kr;
        Double3 kkr = kr.subtract(kkt2);
        Vector v = ray.getDir();
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = calcRayReflection(n, v, geopoint.point, nv);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        Double3 kt = material.Kt;
        Double3 kkt = kt.subtract(kkt2);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = calcRefractionRay(n, v, geopoint.point, nv);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
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
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
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

    /**
     * Calculates the reflection ray
     * @param n - normal Vector
     * @param v - Vector
     * @param p - Point
     * @param nv - dot product of n and v
     * @return r - Ray
     */
    private Ray calcRayReflection(Vector n, Vector v, Point p, double nv) {
        Vector r = v.add(n.scale(-2*nv));
        if (!Util.isZero(nv)) {
            Vector delta = n.scale(nv > 0 ? DELTA : -DELTA);
            p = p.add(delta);
        }
        return new Ray(p, r);
    }

    /**
     * Calculates the refraction ray
     * @param n - normal Vector
     * @param v - Vector
     * @param p - Point
     * @param nv - dot product of n and v
     * @return  Ray
     */
    private Ray calcRefractionRay(Vector n, Vector v, Point p, double nv) {
        if (!Util.isZero(nv)) {
            Vector delta = n.scale(nv > 0 ? DELTA : -DELTA);
            p = p.add(delta);
        }
        return new Ray(p, v);
    }

    /**
     * calculates closest intersection
     * @param r - Ray
     * @return GeoPoint
     */
    private GeoPoint findClosestIntersection(Ray r) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(r);
        if (intersections == null) {
            return null;
        }
        return r.findClosestGeoPoint(intersections);
    }


}
