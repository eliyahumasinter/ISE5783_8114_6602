package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * DirectionalLight class - a class to represent a directional light object
 * @author Eliyahu and Yishai
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * Constructor for DirectionalLight
     * @param c Color
     * @param v Vector
     */
    public DirectionalLight(Color c, Vector v){
        super(c);
        direction=v;
    }

    /**
     * get the color intensity at a point p
     * @param p Point
     * @return Color
     */
    @Override
    public Color getIntensity(Point p){
        return this.intensity;
    }

    @Override
    public Vector getL(Point p){
        return this.direction;
    }

}