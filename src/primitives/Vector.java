package primitives;

/*
A class to represent a Vector
@Author Yishai
@Author Eliyahu
 */

public class Vector extends Point{

    /**
     * Constructor that takes three doubles
     * @param d1 first double
     * @param d2 second double
     * @param d3 third double
     */
    public Vector(double d1, double d2, double d3){
        super(d1,d2,d3);
        if (this.xyz.equals(Double3.ZERO){
            throw new IllegalArgumentException("Can not be 0");
        }
    }

    /**
     * Constructor that takes in a Double3 Object
     * @param d double 3
     */
    public Vector(Double3 d){
        super(d);
        if (this.xyz.equals(Double3.ZERO){
            throw new IllegalArgumentException("Can not be 0");
        }
    }

    /**
     * Adds 2 vectors and returns the resulting vector
     * @param v - Vector
     * @return Vector
     */
    public Vector add(Vector v){
        return new Vector(this.add(v).xyz);
    }

    /**
     * Scalar multiplication: Return the resulting vector after scalar multiplication by i
     * @param i multiplier (integer)
     * @return A vector
     */
    public Vector scale(int i){
        return new Vector(this.xyz.scale(i));
    }

    /**
     * Return the dot product of 2 vectors
     * @param v A vector
     * @return a double
     */
    public double dotProduct(Vector v){
        return this.xyz.d1*v.xyz.d1 + this.xyz.d2*v.xyz.d2 + this.xyz.d3*v.xyz.d3;
    }

    /**
     * Return the cross product of 2 vectors
     * @param v a vector
     * @return
     */
    public Vector crossProduct(Vector v){
        double val1 = this.xyz.d2*v.xyz.d3 - this.xyz.d3*v.xyz.d2;
        double val2 = -(this.xyz.d1*v.xyz.d3 - this.xyz.d3*v.xyz.d1);
        double val3 = this.xyz.d1*v.xyz.d2 - this.xyz.d2*v.xyz.d1;
        return new Vector(val1,val2,val3);
    }

    /**
     * Return the length squared of the distance with itself
     * @param v a vector
     * @return double
     */
    public double lengthSquared(){
        return this.dotProduct(this);

    }

    /**
     * Return the length between two vectors
     * @param v a vector
     * @return double
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Return the normalized vector
     * @return vector
     */
    public Vector normalize(){
        double length = this.length();
        return new Vector(this.xyz.d1/length,this.xyz.d2/length,this.xyz.d3/length);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString(){
        return "Vector: " + this.xyz.toString();
    }

}
