package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link geometries.Sphere} class
 * @author Eliyahu and Yishai
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)} class
     */
    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);
        Vector normal = sphere.getNormal(new Point(0,1,0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ensure length is equal to 1
        assertEquals(1, normal.length(), 0.000001, "Length is not 1 after normalisation");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray)} )} class
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point (1, 0, 0), 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
            "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");


        // TC03: Ray starts inside the sphere (1 point)
//        Sphere sphere2 = new Sphere(new Point (1, 0, 0), 3);
        result = sphere.findIntersections(new Ray(new Point(1.5, 0, 0),
                new Vector(1, 0, 0))); //can not start at center


        assertEquals(1, result.size(), "Wrong number of points");
        p1 = new Point(2,0,0);
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                "Ray's line out of sphere");


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        p1 = new Point(1,1,0);
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Ray's line out of sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        p1 = new Point(0,0,0);
        p2 = new Point(2,0,0);
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");


        // TC14: Ray starts at sphere and goes inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(0, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        p1 = new Point(2,0,0);
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Ray's line out of sphere");


        // TC15: Ray starts inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(1.25, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        p1 = new Point(2,0,0);
        assertEquals(List.of(p1), result, "Ray crosses sphere");


        // TC16: Ray starts at the center (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        p1 = new Point(2,0,0);
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");


        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 2, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 1, 0), new Vector(1, 0, 0))),
                "Ray's line out of sphere");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
                "Ray's line out of sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(3, 1, 0), new Vector(1, 0, 0))),
                "Ray's line out of sphere");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(0, 3, 0), new Vector(1, 0, 0))),
                "Ray's line out of sphere");
        }
}