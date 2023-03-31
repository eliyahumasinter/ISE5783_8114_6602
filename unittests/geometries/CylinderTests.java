package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link geometries.Cylinder} class
 * @author Eliyahu and Yishai
 */
class CylinderTests {


    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)} class
     */
    @Test
    void testGetNormal() {
        Point p = new Point(1,0,1);
        Cylinder c = new Cylinder(1, new Ray(new Point(0,0,0), new Vector(0,0,1)), 1);
        // ============ Equivalence Partitions Tests ==============
        //TC01: ensure there are no exceptions
        assertDoesNotThrow(() -> c.getNormal(p));
        Vector result = c.getNormal(p);
        //TC02: ensure |result| = 1
        assertEquals(1,result.length(),0.000001, "Result is not normalized");

    }
}