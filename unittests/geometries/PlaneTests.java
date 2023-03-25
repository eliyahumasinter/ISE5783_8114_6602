package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for {@link geometries.Plane} class
 */
class PlaneTests {


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