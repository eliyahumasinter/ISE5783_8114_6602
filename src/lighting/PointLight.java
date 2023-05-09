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
    protected double Kc, Kl, Kq;

    /**
     * Constructor for PointLight
     * @param c
     * @param p
     */
    public PointLight(Color c, Point p){
        super(c);
        this.position = p;
        this.Kc =1;
        this.Kl =0;
        this.Kq =0;
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
     *
     * @param p
     * @return Color
     */
    @Override
    public Color getIntensity(Point p){
        double d = this.position.distance(p);
        double denominator = this.Kc+this.Kl*d+Kq*d*d;
        return this.intensity.reduce(denominator);
    }


    /**
     *
     * @param p
     * @return Vector
     */
    @Override
    public Vector getL(Point p){
        return this.position.subtract(p);
    }

}
