package renderer;

import geometries.Triangle;
import lighting.LightSource;
import lighting.PointLight;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
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


    public RayTracerBasic setSuper_sampling(boolean super_sampling) {
        this.super_sampling = super_sampling;
        return this;
    }

    private boolean super_sampling = false;

    private int numberOfRays = 400;

    public RayTracerBasic setNumberOfRays(int numberOfRays) {
        this.numberOfRays = numberOfRays;
        return this;
    }

    /**
     * Function to determine if a point is not being shadowed
     *
     * @param l  Vector
     * @param n  Vector
     * @param gp GeoPoint
     * @return boolean
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);


        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);


        if (intersections == null) return true;

        // only keep completely opaque geometries
        for (GeoPoint intersection : intersections) {
            // if kT==0, it is opaque, and hence casting shade, ie not unshaded
            if (intersection.geometry.getMaterial().Kt.equals(Double3.ZERO))
                return false;
        }
        return true;
    }


//    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl){
//            Vector lightDirection = l.scale(-1); // from point to light source
//            Vector epsVector = n.scale(nl <0 ? DELTA: -DELTA);
//            Point point = gp.point.add(epsVector);
//            Ray lightRay = new Ray(point, lightDirection);
//
//            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//
//
//            if (intersections == null)
//                return true;
//
//            double lightDistance = light.getDistance(gp.point);
//            for (GeoPoint geopoint : intersections) {
//                if (Util.alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0
//                && gp.geometry.getMaterial().Kt == Double3.ZERO);
//                    return false;
//            }
//            return true;
//    }

    /**
     * Constructor that takes in a scene
     */
    public RayTracerBasic(Scene s) {
        super(s);
    }

    /**
     * Implementation of traceRay method
     *
     * @param ray ray
     * @return Color
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null) {
            return scene.background;
        }
        return calcColor(closestPoint, ray);
    }

    /**
     * calculates color
     *
     * @param gp
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates color
     *
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
        Color color = calcLocalEffects(closestPoint, ray, kkt);
        return 1 == level ? color : color.add(calcGlobalEffects(closestPoint, ray, level, kkt)).scale(kkt);
    }



    /**
     * Computes global effects of a GeoPoint
     *
     * @param gp    - GeoPoint
     * @param ray   - Ray
     * @param level - int
     * @param k     - Double3
     * @return Color
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.Kr).add(calcGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.Kt));
    }


    /**
     * Computes Color global effects
     *
     * @param ray   - Ray
     * @param level - int
     * @param k     - Double3
     * @param kx    - Double3
     * @return Color
     */

    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return Util.isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK : calcColor(gp, ray, level - 1, kkx);
    }


    /**
     * Helper function to calc diffusive light
     *
     * @param mat - Material
     * @param nl  - double - nl
     * @return
     */
    private Double3 calcDiffusive(Material mat, double nl) {
        return mat.Kd.scale(Math.abs(nl));
    }

    /**
     * Helper function to calc the specular light
     *
     * @param mat - Material
     * @param n   - Vector
     * @param l   - Vector
     * @param nl  - double
     * @param v   - Vector
     * @return
     */
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl)).normalize();
        double vr = alignZero(v.dotProduct(r));
        return mat.Ks.scale(Math.pow(Math.max(0, -vr), mat.shininess));

    }

    /**
     * Calculates local effects for each light source on given GeoPoint
     *
     * @param gp  - GeoPoint
     * @param ray - Ray
     * @return color - Color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
//                if (unshaded(gp, lightSource, l, n, nl)) {
                Double3 ktr = transparency(gp, lightSource, l, n, nl);
//                if (ktr.getD1() != 0 && ktr.getD1() != 1){
//                    System.out.println(ktr);
//                }
//                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(mat, nl)),
                            iL.scale(calcSpecular(mat, n, l, nl, v)));



            }
        }
        return color;
    }

    /**
     * Calculates the reflection ray
     *
     * @param n  - normal Vector
     * @param v  - Vector
     * @param p  - Point
     * @param nv - dot product of n and v
     * @return r - Ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        double nv = v.dotProduct(n);
        Vector r = v.subtract(n.scale(2 * nv));
        return new Ray(gp.point, r, n);
    }

    /**
     * Calculates the refraction ray
     *
     * @param n  - normal Vector
     * @param v  - Vector
     * @param gp - GeoPoint
     * @return Ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

    /**
     * calculates closest intersection
     *
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

    private List<Ray> getRays(Point p0, double radius, Vector rayDir, Vector normal, GeoPoint gp ,int amount) {

        Vector x = rayDir.normalize().createNormal();
        ;
        Vector y = x.crossProduct(rayDir.normalize());

        List<Point> points = new LinkedList<Point>();
        for (int i = 0; i < amount; i++) {
            double t1 = Util.random(-radius, radius);
            double t2Limit = Math.sqrt(radius*radius -t1*t1) ; //ensure circle, not square
            double t2 = Util.random(-t2Limit, t2Limit);
            Point p = p0.add(x.scale(t1).add(y.scale(t2)));
            points.add(p);
        }
        List<Ray> rays = new LinkedList<Ray>();
        if (points != null)
            for (Point p : points)
                rays.add(new Ray(gp.point, p.subtract(gp.point).normalize(), normal));
        return rays;
    }

    /**
     * Computes the transparency of an object
     * @param gp - GeoPoint
     * @param ls - LightSource
     * @param l - Vector
     * @param n - Vector
     * @param nl - double
     * @return ktr - Double3
     */
    /**
     * Computes the transparency of an object
     *
     * @param gp - GeoPoint
     * @param ls - LightSource
     * @param l  - Vector
     * @param n  - Vector
     * @param nl - double
     * @return ktr - Double3
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        if ( super_sampling == true &&  ls.getDistance(gp.point) != Double.POSITIVE_INFINITY) {
            Point lightSourceCenter = ((PointLight) ls).getPosition();
            double lightSourceRadius = ((PointLight) ls).getRadius();
            double lightDistance = ls.getDistance(gp.point);
            Double3 sumKtrAll = Double3.ZERO;
            Double3 ktr;

            List<Ray> beamRays = getRays(lightSourceCenter, lightSourceRadius, lightRay.getDir(), n, gp, numberOfRays);
            for (Ray ray : beamRays) {
                List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
                if (intersections == null) {
                    sumKtrAll = sumKtrAll.add(Double3.ONE);
                    continue;
                }
                ktr = Double3.ONE;
                for (GeoPoint g : intersections) {
                    if (alignZero(g.point.distance(gp.point) - lightDistance) <= 0) {
                        ktr = ktr.product(g.geometry.getMaterial().Kt);
                    }
                    if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                        ktr = Double3.ZERO;
                        break;
                    }
                }
                sumKtrAll = sumKtrAll.add(ktr);


            }
            Double3 res = sumKtrAll.scale(1.0 / beamRays.size());

            return res;
        }


        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return Double3.ONE;

        double lightDistance = ls.getDistance(gp.point);

        Double3 ktr = new Double3(1,1,1);
        for (GeoPoint geopoint : intersections) {
            if (Util.alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0)
                ktr= ktr.product(geopoint.geometry.getMaterial().Kt);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return  new Double3(0,0,0);
        }
        return ktr;

    }

}

