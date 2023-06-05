package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for light source
 * @author Eliyahu and Yishai
 */
public interface LightSource {
    /**
     * Get the light intensity at a particular point p
     * @param p
     * @return Color
     */
    Color getIntensity(Point p);

    /**
     *
     * @param p
     * @return Vector
     */
    Vector getL(Point p);

    double getDistance(Point point);


}

