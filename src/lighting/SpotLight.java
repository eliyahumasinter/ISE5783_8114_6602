package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * SpotLight class
 * @author Eliyahu and Yishai
 */
public class SpotLight extends PointLight {
    private final Vector direction;

    /**
     * Constructor for SpotLight
     * @param c Color
     * @param p Point
     * @param v Vector
     */
    public SpotLight(Color c, Point p, Vector v){
        super(c,p);
        this.direction=v.normalize();
    }

    /**
     * getIntensity function
     * @param p Point
     * @return Color
     */
    @Override
    public Color getIntensity(Point p){
        return super.getIntensity(p).scale(Math.max(0,this.direction.dotProduct(getL(p))));
    }

}
