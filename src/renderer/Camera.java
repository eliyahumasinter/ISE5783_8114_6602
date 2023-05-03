package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

/**
 * A class to manage the view of objects as through a camera
 * @author Eliyahu and Yishai
 */
public class Camera {

    private ImageWriter ImageWriter;



    private RayTracerBase rayTracer;
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

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.ImageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
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

    public Camera renderImage()
    {
        if(this.loc ==null || this.to ==null|| this.up==null || this.rayTracer==null || this.right==null||this.ImageWriter ==null)
            throw new MissingResourceException("One or more of the attributes are null", null, null);
        //throw new UnsupportedOperationException();

        for (int i = 0; i < this.ImageWriter.getNy() ; i++) {
            for (int j = 0; j < ImageWriter.getNx(); j++) {
                Ray ray = constructRay(ImageWriter.getNx(), ImageWriter.getNy(), j, i);
                Color color = rayTracer.traceRay(ray);
                ImageWriter.writePixel(j, i, color);
            }
        }
        return null;

    }

    /**
     * Creates a grid on the Image Writer
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color)
    {
        if(this.ImageWriter ==null)
            throw new MissingResourceException("image writer not set", null, null);

        for (int i = 0; i < this.ImageWriter.getNx(); i++) {
            for (int j = 0; j < this.ImageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0 || i == this.ImageWriter.getNx()-1 || j == this.ImageWriter.getNx()-1)
                    this.ImageWriter.writePixel(j, i, color);
            }
        }

    }

    /**
     * Write to image method
     */
    public void writeToImage()
    {
        if(this.ImageWriter ==null)
            throw new MissingResourceException("image writer failed", null, null);
        ImageWriter.writeToImage();
    }
}
