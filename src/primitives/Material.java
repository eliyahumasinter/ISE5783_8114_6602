package primitives;


/**
 * Matieral Class - a class to represent the material object
 * @author Eliyahu and Yishai
 */
public class Material {
    public Double3 Kd = Double3.ZERO;
    public Double3 Ks =Double3.ZERO;
    public Double3 Kt = Double3.ZERO;
    public Double3 Kr = Double3.ZERO;
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
     * set the kt parameter
     * @param kt Double3
     */
    public Material setKt(Double3 kt) {
        this.Kt = kt;
        return this;
    }

    /**
     * set the kr parameter
     * @param kr Double3
     */
    public Material setKr(Double3 kr) {
        this.Kr = kr;
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
     * set the kt parameter
     * @param kt double
     */
    public Material setKt(double kt) {
        this.Kt = new Double3(kt);
        return this;
    }

    /**
     * set the kr parameter
     * @param kr double
     */
    public Material setKr(double kr) {
        this.Kr = new Double3(kr);
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
