package geometries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import primitives.Point;
import primitives.Vector;

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
}