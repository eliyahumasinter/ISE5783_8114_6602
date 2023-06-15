package renderer;
import java.util.stream.*;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

/**
 * A class to manage the view of objects as through a camera
 * @author Eliyahu and Yishai
 */
public class Camera {

    private ImageWriter ImageWriter;


    private int threads;
    private double printInterval;
    private RayTracerBase rayTracer;
    private  Point loc;
    private  Vector to;
    private  Vector up;
    private  Vector right;

    //View Plane
    private double height, width, distance;
    private int AntiAliasing=50;

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

    public Camera setMultithreading(int threads){
        this.threads = threads;
        return this;
    }

    public Camera setDebugPrint(double printInterval){
        this.printInterval = printInterval;
        return this;
    }

    public Camera setAntiAliasing(int num) {
        this.AntiAliasing = num;
        return this;
    }

    /**
     * Getter method for point location
     * @return loc
     */
    public Point getLoc() {
        return this.loc;
    }

    public Camera setLoc(Point p){
        this.loc = p;
        return this;
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

    public Camera shiftCamera(Vector v){
         return setLoc(loc.add(v));

    }

    public Camera rotate(double pitch, double yaw, double roll) {
        Matrix m = new Matrix(degreesToRadians(pitch), degreesToRadians(yaw),degreesToRadians(roll));
        this.to = m.multiply(this.to);
        this.up = m.multiply(this.up);
        return this;

    }



    public double degreesToRadians(double degrees){
        return degrees * Math.PI/180;
    }


    /**
     * Class that creates a ray starting from the camera to the View Plane
     * @param Nx
     * @param Ny
     * @param j
     * @param i
     * @return
     */

    //----------------------------------------------- ANTIALIASING FUNCTIONS --------------------------------------------------------------

    public List<Ray> constructRays(int nX, int nY, int j, int i) {

        Point Pc = this.loc.add(this.to.scale(distance));

        double Ry = this.height / nY;
        double Rx = this.width / nX;
        double valt1 = Ry/2;
        double negvalt1 = valt1* (-1);
        double ranget1 = valt1 - negvalt1 + 1;
        double valt2 = Rx/2;
        double negvalt2 = valt2 * (-1);
        double ranget2 = valt2 - negvalt2 + 1;
        List <Ray> list = new LinkedList<>();
        Random random = new Random();


        double Yi = -(i - (nY - 1) / 2.0) * Ry;
        double Xj = (j - (nX - 1) / 2.0) * Rx;


        for (int x = 0; x < AntiAliasing; ++x) {
            Point Pij = Pc;
            if (Xj != 0) {
                if (x==0){
                    Pij = Pij.add(this.right.scale(Xj));
                }
                else {
                    Pij = Pij.add(this.right.scale(Xj + random.nextDouble(ranget2)));
                }
            }
            if (Yi != 0)
            {
                if (x==0){
                    Pij = Pij.add(this.up.scale(Yi));
                }
                else {
                    Pij = Pij.add(this.up.scale(Yi + random.nextDouble(ranget1)));
                }
            }

            Vector Vij = Pij.subtract(this.loc);
            Ray r = new Ray(this.loc, Vij);
            list.add(r);
        }


        return list;

    }

    /**
     * a function that renders the image
     */
    public Camera renderImage() {
        if(this.loc ==null || this.to ==null|| this.up==null || this.rayTracer==null || this.right==null||this.ImageWriter ==null) // if any of the feilds are empty, throw exception
            throw new MissingResourceException("one of the properties contains empty value", "Camera", null);
        // throw new UnsupportedOperationException();
        int nX = ImageWriter.getNx();
        int nY = ImageWriter.getNy();

        if (this.threads > 1){

            Pixel.initialize(nY, nX, printInterval);
            IntStream.range(0, nY).parallel().forEach(i -> {
                IntStream. range(0, nX).parallel().forEach(j -> {
//                    Color color = rayTracer.traceRay(constructRay(nX, nY, j, i));

                    Color color = Color.BLACK;
                    if (AntiAliasing > 1) {
                        // from each pixel, make a ray, give it a color, and save it in the image
                        List<Ray> list = constructRays(nX, nY, j, i);
                        for (int x = 0; x < AntiAliasing; ++x) {
                            color = color.add(rayTracer.traceRay(list.get(x)));
                        }
                        color = color.reduce(AntiAliasing);
                    } else {
                        Ray ray = constructRay(nX, nY, j, i);
                        color = rayTracer.traceRay(ray);
                        ImageWriter.writePixel(j, i, color);

                    }
                    ImageWriter.writePixel(j, i, color);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                });
            });
        }

        else {

            for (int i = 0; i < nY; i++) { // iterate through the given values and make pixels
                for (int j = 0; j < nX; j++) {
                    Color color = Color.BLACK;
                    if (AntiAliasing > 1) {
                        // from each pixel, make a ray, give it a color, and save it in the image
                        List<Ray> list = constructRays(nX, nY, j, i);
                        for (int x = 0; x < AntiAliasing; ++x) {
                            color = color.add(rayTracer.traceRay(list.get(x)));
                        }
                        color = color.reduce(AntiAliasing);
                    } else {
                        Ray ray = constructRay(nX, nY, j, i);
                        color = rayTracer.traceRay(ray);
                        ImageWriter.writePixel(j, i, color);

                    }
                    ImageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;

    }



    public Ray constructRay(int Nx, int Ny, double j, double i) {
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



    /**
     * Creates a grid on the Image Writer
     * @param interval
     * @param color
     */
    public Camera printGrid(int interval, Color color)
    {
        if(this.ImageWriter ==null)
            throw new MissingResourceException("image writer not set", null, null);

        for (int i = 0; i < this.ImageWriter.getNx(); i++) {
            for (int j = 0; j < this.ImageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0 || i == this.ImageWriter.getNx()-1 || j == this.ImageWriter.getNx()-1)
                    this.ImageWriter.writePixel(j, i, color);
            }
        }
        return this;

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
