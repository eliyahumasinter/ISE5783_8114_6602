package primitives;


/**
 * Matieral Class - a class to represent the material object
 * @author Eliyahu and Yishai
 */
public class Material {
    public Double3 Kd = Double3.ZERO;
    public Double3 Ks =Double3.ZERO;
    public int shininess =0;

    /**
     * set the kD parameter
     * @param kd Double3
     */
    public Material setKd(Double3 kd) {
        this.Kd = kd;
        return this;
    }

    /**
     * set the kS parameter
     * @param ks Double3
     */
    public Material setKs(Double3 ks) {
        this.Ks = ks;
        return this;
    }

    /**
     * set the kD parameter
     * @param kD double
     */
    public Material setKd(double kD) {
        this.Kd = new Double3(kD);
        return this;
    }

    /**
     * set the kS parameter
     * @param kS double
     */
    public Material setKs(double kS) {
        this.Ks = new Double3(kS);
        return this;
    }


    /**
     * set the Shininess parameter
     * @param shininess int
     */
    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }
}
