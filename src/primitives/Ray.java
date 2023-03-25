package primitives;

/**
 * Manages a ray which defines a set of all points on one side of a line in one direction
 * @author Eliyahu and Yishai
 */
public class Ray {
    Point p0;
    Vector dir;

    public Vector getDir() {
        return dir;
    }
    public Point getPoint() {
        return p0;
    }
    /**
     * Initialises fields p0 and dir to Point p and normalized Vector v
     * @param p
     * @param v
     */
    public Ray(Point p, Vector v) {
        this.p0 = p;
        this.dir = v.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray) obj;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString(){
        return "Ray: Center at " + this.p0 + "\n Direction " + this.dir;
    }
}
