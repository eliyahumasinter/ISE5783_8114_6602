package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Test class for {@link geometries.Tube} class
 * @author Eliyahu and Yishai
 */
class TubeTests {


    /**
     * Test method for {@link geometries.Tube#getNormal(Point)} class
     */
    @Test
    void testGetNormal() {

        Point p = new Point(1,0,1);
        Tube tube = new Tube(1, new Ray(new Point(0,0,0), new Vector(0,0,1)));
        //ensure there are no exceptions
        assertDoesNotThrow(() -> tube.getNormal(p));

        Vector result = tube.getNormal(p);

        // ============ Boundary Value Analysis Tests ==============
        //TC11: ensure |result| = 1
        assertEquals(1,result.length(),0.000001, "Result is not normalized");

        // ============ Equivalence Partitions Tests ==============
        //TC01: ensure |result| = 1
        assertEquals(0,result.dotProduct(new Vector(0,0,1)), "Result is not normal to the tube");
    }
}