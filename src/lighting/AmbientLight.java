package lighting;

import primitives.Color;
import primitives.Double3;


/**
 * Class for representing Ambient Light
 * @Author Eliyahu and Yishai
 */
public class AmbientLight extends Light {

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


    /**
     * Constructor that takes in a color and an attenuation factor
     * and calls the parent constructor with the intensity of the color scaled by the factor
     *
     * @param Ia a primitives.Color object
     * @param Ka a Double3
     */
    public AmbientLight(Color Ia, Double3 Ka){
        super(Ia.scale(Ka));
    }

    public AmbientLight(Color Ia, double Ka){
        super(Ia.scale(Ka));
    }

    /**
     * Empty constructor that calls the parent constructor with the intensity of black
     */
    public AmbientLight(){
        super(Color.BLACK);
    }

}
