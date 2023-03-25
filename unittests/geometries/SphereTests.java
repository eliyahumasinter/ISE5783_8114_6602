package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}