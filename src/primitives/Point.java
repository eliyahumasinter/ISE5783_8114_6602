package primitives;

/**
 * This class will serve as a Point holding a Double3 object
 * @author Eliyahu and Yishai
 */
public class Point {
    Double3 xyz;

    /**
     * Constructor that receives a Double3 object and sets xyz to the received object's values.
     * @param xyz - the received Double3 object
     */
    public Point(Double3 xyz) {
        this.xyz = new Double3(xyz.d1, xyz.d2, xyz.d3);
    }

    /**
     * Constructor that receives 3 double objects and creates a Point containing a Double3 with the recieved values
     * @param d1 - received double object, same for d2 and d3
     * @param d2
     * @param d3
     */
    public Point (double d1, double d2, double d3) {
        this.xyz = new Double3(d1, d2, d3);
    }

    /**
     * Calculates the negative distance from two Points returning a Vector
     * @param p - Point
     * @return Vector
     */
    public Vector subtract(Point p) {
        return new Vector(this.xyz.subtract((p.xyz)));
    }

    /**
     * Adds a vector to a Point
     * @param v - Vector
     * @return a Point which lies at the end of the added Vector
     */
    public Point add(Vector v) {
        return new Point(this.xyz.add(v.xyz));
    }

    /**
     * Calculates the distance between two Points squared
     * @param p - a Point
     * @return double
     */
    public double distanceSquared(Point p) {
        double d1Dist = (this.xyz.d1 - p.xyz.d1);
        double d2Dist = (this.xyz.d2 - p.xyz.d2);
        double d3Dist = (this.xyz.d3 - p.xyz.d3);
        return d1Dist*d1Dist + d2Dist*d2Dist + d3Dist*d3Dist;
    }

    /**
     * calculates the distance between two points
     * @param p - a Point
     * @return double
     */
    public double distance(Point p) {
        return Math.sqrt(this.distanceSquared(p));
    }


    @Override
    public String toString(){
        return "Point: " + this.xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point)obj;
        return this.xyz.equals(other.xyz);
    }





}
