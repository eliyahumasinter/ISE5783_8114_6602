package elements;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    Color intensity;
    public AmbientLight(Color Ia, Double3 Ka){
        intensity = Ia.scale(Ka);
    }

    public AmbientLight(){
        intensity = Color.BLACK;
    }

    Color getIntensity(){
        return intensity;
    }

}
