package primitives;


/**
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Matrix {

    // rows of matrix
    private final Vector r1;
    private final Vector r2;
    private final Vector r3;

    /**
     * Matrix constructor which takes three {@link Vector}s.
     *
     * @param r1 The first row.
     * @param r2 The second row.
     * @param r3 The third row.
     */
    public Matrix(Vector r1, Vector r2, Vector r3) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
    }

    public Matrix(double pitch, double yaw, double roll) {
        this(Math.sin(pitch), Math.cos(pitch), Math.sin(yaw), Math.cos(yaw), Math.sin(roll), Math.cos(roll));
    }

    private Matrix(double sinPitch, double cosPitch, double sinYaw, double cosYaw, double sinRoll,
                           double cosRoll) {
        this(
                new Vector(cosPitch * cosYaw, sinPitch * cosYaw * sinRoll - sinYaw * cosRoll,
                        sinPitch * cosYaw * cosRoll + sinYaw * sinRoll),
                new Vector(cosPitch * sinYaw, sinYaw * sinPitch * sinRoll + cosYaw * cosRoll,
                        sinYaw * sinPitch * cosRoll - cosYaw * sinRoll),
                new Vector(-sinPitch, cosPitch * sinRoll, cosPitch * cosRoll));
    }

    public Vector multiply(Vector v){
        return new Vector(r1.dotProduct(v), r2.dotProduct(v), r3.dotProduct(v));
    }


    public Matrix scale(double t) {
        return new Matrix(r1.scale(t), r2.scale(t), r3.scale(t));
    }

    @Override
    public String toString() {
        return "Matrix3x3 [r1=" + r1 + ", r2=" + r2 + ", r3=" + r3 + "]";
    }

}