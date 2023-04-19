package geometries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for {@link geometries.Plane} class
 */
class PlaneTests {


    /**
     *   Test for Plane constructor that receives 3 Points
     */
    @Test
    void testPlane() {
        // ======= Boundary Value Analysis Tests ==============
        //TC11: Ensures first two points are not equal
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0,0,1), new Point(0,0,1), new Point(1,0,0)));

        Point[] pts = { new Point(1, 0 ,0), new Point(2, 0, 0), new Point(3, 0, 0)};
        Vector v1 = pts[1].subtract(pts[0]);
        Vector v2 = pts[2].subtract(pts[1]);
        //TC12: Ensures all points are not on same line
        assertThrows(IllegalArgumentException.class, ()->v1.crossProduct(v2), "Doesn't throw error when points are on same line" );
    }


    @Test
    void testGetNormal() {
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
        // ============ Equivalence Partitions Tests ==============
        Plane plane = new Plane(pts[0], pts[1], pts[2]);
        //TC01: Ensures there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(0, 0, 1)));
        Vector result = plane.getNormal(new Point(0, 0, 1));

        //TC02: Ensure that the length of a vector is 1
        assertEquals(1, result.length(), 0.000001,"Plane's normal is not a unit vector");

        //TC03: Ensures result is orthogonal to the plane
        for (int i = 0; i < 3; i++) {
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i-1]))),
                    "Plane's normal is not orthogonal to the plane");
        }

    }

    @Test
    void testFindIntersections(){
        Point p1 = new Point(1,0,0);
        Point p2 = new Point(0,1,0);
        Point p3 = new Point(1,1,0);
        Plane plane = new Plane(p1,p2,p3);
        // ============ Equivalence Partitions Tests ==============
        // **** Group: Ray's line is neither parallel nor orthogonal to plane
        // TC01: Ray intersects the plane
        Ray ray = new Ray(new Point(0,0,-1), new Vector(0,2,1));
        List<Point> result = plane.findIntersections(ray);
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0,2,0)), result, "Ray crosses plane");

        // TC02: Ray doesn't intersect the plane
        ray = new Ray(new Point(1,0,1), new Vector(1,1,5)); //Start the ray above our plane
        assertNull(plane.findIntersections(ray), "Ray's line out of plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line parallel to plane
        // TC11: Ray is outside the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0,0,5), new Vector(1,0,0))), "Ray's line outside of plane");

        // TC12: Ray is inside the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(-5,0,0), new Vector(1,0,0))), "Ray's line inside of plane");

        // **** Group: Ray's orthogonal to plane
        // TC13: Ray is before plane (1 point)
        result = plane.findIntersections(new Ray(new Point(0,0,-1), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0,0,0)), result, "Ray crosses plane");


        // TC14: Ray is in plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(0,0,1))), "Ray's line after plane");

        // **** Group: Special cases
        // TC15: Ray is neither orthogonal nor parallel to plane and begins at the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1,5,0), new Vector(1,1,1))));

        // TC16: Ray is neither orthogonal nor parallel to plane and begins at one of the defining points (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1,1,0), new Vector(1,1,1))));


    }
}