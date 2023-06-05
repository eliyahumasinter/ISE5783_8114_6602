package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * PointLight class - a class to represent a point light object
 * @author Eliyahu and Yishai
 */
public class PointLight extends Light implements LightSource {
    protected Point position;
    protected double Kc=1, Kl=0, Kq=0;
    protected double radius;

    public double getRadius() {
        return radius;
    }

    /**
     * Constructor for PointLight
     * @param c
     * @param p
     */
    public PointLight(Color c, Point p){
        super(c);
        this.position = p;
    }

    public LightSource setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    /**
     * setter for double kC
     * @param kc
     * @return this - PointLight
     */
    public PointLight setKc(double kc) {
        this.Kc = kc;
        return this;
    }

    /**
     * setter for double kL
     * @param kl
     * @return this - PointLight
     */
    public PointLight setKl(double kl) {
        this.Kl = kl;
        return this;
    }

    /**
     * setter for double kQ
     * @param kq
     * @return this - PointLight
     */
    public PointLight setKq(double kq) {
        this.Kq = kq;
        return this;
    }

    /**
     * Gets the intensity
     * @param p
     * @return Color
     */
    @Override
    public Color getIntensity(Point p){
        double d = this.position.distance(p);
        double denominator = this.Kc+this.Kl*d+Kq*d*d;
        return this.intensity.scale(1/denominator);
    }

    /**
     * Get distance override
     * @param point - Point
     * @return double
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }


    /**
     * Gets the light direction
     * @param p - Point
     * @return Vector
     */
    @Override
    public Vector getL(Point p){
        return p.subtract(this.position).normalize();  //this.position.subtract(p).normalize();
    }

    public Point getPosition(){
        return this.position;
    }

}
