package lighting;

import primitives.Color;

/**
 * Abstract Class Light
 * @author Eliyahu and Yishai
 */
public abstract class Light{
    protected Color intensity;

    /**
     * Constructor for abstract class Light that takes in a color
     * @param i - Color
     */
    protected Light(Color i){
        this.intensity = i;
    }

    /**
     * Getter for intensity attribute
     * @return Color
     */
    public Color getIntensity() {
        return intensity;
    }
}
