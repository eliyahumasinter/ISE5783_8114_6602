package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {

    /**
     * Test integration of camera with plane
     */
    @Test
    void testPlane(){
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));
        Camera camera = new Camera(new Point(Double3.ZERO), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPDistance(1).setVPSize(3, 3);

        // TC01: 9 points
        assertEquals(9, numberOfIntersections(camera, plane, 3, 3));

        // TC02: 9 points
        plane = new Plane(new Point(0, 0, -2.5), new Vector(0, -0.9, 1));
        assertEquals(9, numberOfIntersections(camera, plane, 3, 3));

        // TC03: 6 points
        plane = new Plane(new Point(0, 0, -3), new Vector(0, -1, 1));
        assertEquals(6, numberOfIntersections(camera, plane, 3, 3));
    }

    @Test
    public void testTriangle() {
        Triangle tri = new Triangle(new Point(1, -1, -2), new Point(0, 1, -2), new Point(-1, -1, -2));
        Camera camera = new Camera(new Point(Double3.ZERO), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPDistance(1).setVPSize(3, 3);

        // TC01: 1 point
        assertEquals(1, numberOfIntersections(camera, tri, 3, 3));

        // TC02: 2 points
        tri = new Triangle(new Point(1, -1, -2), new Point(0, 20, -2), new Point(-1, -1, -2));
        assertEquals(2, numberOfIntersections(camera, tri, 3, 3));
    }


    @Test
    public void testSphere() {
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        Camera camera = new Camera(new Point(Double3.ZERO), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPDistance(1).setVPSize(3, 3);

        // TC01: Two intersection points
        assertEquals(2, numberOfIntersections(camera, sphere, 3, 3));

        // TC02: 18 points
        sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
        camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPDistance(1).setVPSize(3, 3);
        assertEquals( 18, numberOfIntersections(camera, sphere, 3, 3));

        // TC03: 10  points
        sphere = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, numberOfIntersections(camera, sphere, 3, 3));

        // TC04: 9 points
        sphere = new Sphere(new Point(0, 0, -1), 4);
        assertEquals(9, numberOfIntersections(camera, sphere, 3, 3));

        // TC05: 0 points
        sphere = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, numberOfIntersections(camera, sphere, 3, 3));
    }


    /**
     * Generate rays through every point in view plane
     * @param cam  - camera
     * @param body - a Geometry
     * @param nX   - rows in the VP
     * @param nY   - columns in the VP
     * @return number of intersections between view plane and geometry.
     */
    private int numberOfIntersections(Camera cam, Geometry geo, int nX, int nY) {
        var rays = new LinkedList<Ray>();
        for (int i = 0; i < nX; i++)
            for (int j = 0; j < nY; j++) {
                rays.add(cam.constructRay(nX, nY, j, i));
            }
        var allPoints = new LinkedList<Point>();
        for (var ray : rays) {
            var result = geo.findIntersections(ray);
            if (result != null)
                allPoints.addAll(result);
        }
        return allPoints.size();
    }
}

