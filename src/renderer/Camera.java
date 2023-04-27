package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class to manage the view of objects as through a camera
 * @author Eliyahu and Yishai
 */
public class Camera {

    private Point loc;
    private Vector to, up, right;

    //View Plane
    private double height, width, distance;

    /**
     * Constructor to set Point location and Vectors up, to and right
     * @param location
     * @param up
     * @param to
     */
    public Camera(Point location, Vector to, Vector up) {
        this.loc = location;
        this.up = up.normalize();
        this.to = to.normalize();
        if (this.up.dotProduct(this.to) == 0) {
            this.right = this.to.crossProduct(this.up).normalize();
        }
        else {
            throw new IllegalArgumentException("Vectors: up and to must be perpendicular");
        }
    }

    /**
     * Getter method for point location
     * @return loc
     */
    public Point getLoc() {
        return this.loc;
    }

    /**
     * Getter method for Vector to
     * @return to
     */
    public Vector getTo() {
        return this.to;
    }

    /**
     * Getter method for Vector up
     * @return up
     */
    public Vector getUp() {
        return this.up;
    }

    /**
     * Getter method for Vector right
     * @return right
     */
    public Vector getRight() {
        return this.right;
    }

    /**
     * Sets the height and width of View Plane to given values
     * @param height
     * @param width
     * @return this - the Camera object being manipulated
     */
    public Camera setVPSize(double width, double height) {
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * Sets the distance from the camera to the View Plane.
     * @param distance
     * @return this - the Camera object being manipulated
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Class that creates a ray starting from the camera to the View Plane
     * @param Nx
     * @param Ny
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        Point Pc = this.loc.add((this.to.scale(this.distance)));
        double Rx = this.width/Nx;
        double Ry = this.height/Ny;
        double Xj = (j-((Nx-1)/2.0))*Rx;
        double Yi = -(i-((Ny-1)/2.0))*Ry;
        Point Pij = Pc;
        if (Xj != 0)
            Pij = Pij.add(this.right.scale(Xj));

        if (Yi != 0)
            Pij = Pij.add(this.up.scale(Yi));

        return new Ray(this.loc, Pij.subtract(this.loc));

    }

}
