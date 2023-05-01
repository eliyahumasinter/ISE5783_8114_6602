package elements;

import primitives.Color;
import primitives.Double3;


/**
 * Class for representing Ambient Light
 * @Author Eliyahu and Yishai
 */
public class AmbientLight {
    Color intensity;


    /**
     * Constructor that takes in a color and an attenuation factor
     * and sets the intensity to the color scaled by the factor
     *
     * @param Ia a primitives.Color object
     * @param Ka a Double3
     */
    public AmbientLight(Color Ia, Double3 Ka){
        intensity = Ia.scale(Ka);
    }

    /**
     * Empty constructor that sets the intensity to black
     */
    public AmbientLight(){
        intensity = Color.BLACK;
    }

    /**
     *
     * @return intensity - a color object
     */
    public Color  getIntensity(){
        return intensity;
    }

}
