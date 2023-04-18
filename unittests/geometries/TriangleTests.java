package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Triangle
 * @author Eliyahu and Yishai
 */
class TriangleTests {

    /** Test method for {@link geometries.Triangle#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
        Triangle triangle = new Triangle(pts[0], pts[1], pts[2]);
        //TC02: ensure there are no exceptions
        assertDoesNotThrow(() -> triangle.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = triangle.getNormal(new Point(0, 0, 1));
        //TC03: ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");

        //TC04: ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    @Test
    void testFindIntersections() {
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(1, 1, 0) };
        Triangle triangle = new Triangle(pts[0], pts[1], pts[2]);
        Vector v = pts[0].subtract(pts[1]);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects triangle interior (1 point)

        // TC02: Ray intersects line extensions at vertex

        // TC03: Ray intersects line extensions at base

        // =============== Boundary Values Tests ==================
        // TC11: Ray intersects triangle side extension  (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 0, -1), new Vector(0, 0, 1))), "Ray intersects side extension");

        // TC12: Ray intersects triangle side (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0.5, 0, -1), new Vector(0, 0, 1))), "Ray intersects side");

        // TC13: Ray intersects triangle vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0, 0, 1))), "Ray intersects triangle vertext");

    }



}