package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {


    /**
     *
     * Test function for the {@link primitives.Ray#findClosestPoint(List)}
     */

    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 The closest point is in the middle of the list
        Ray ray = new Ray(new Point(1,1,1), new Vector(1,1,1));
        assertEquals(new Point(0.7,0.7,0.7), ray.findClosestPoint(List.of(new Point(-1,-1,-1), new Point(0.7,0.7,0.7), new Point(4,4,4))), "Function did not return correct point");

        // ======= Boundary Value Analysis Tests ==============
        // TC11 An Empty list
        assertNull(ray.findClosestPoint(List.of()), "Empty list returns not null");

        // TC12 The closest point is the first of the list
        assertEquals(new Point(0.7,0.7,0.7), ray.findClosestPoint(List.of(new Point(0.7,0.7,0.7), new Point(-1,-1,-1), new Point(4,4,4))), "Function did not return correct point");

        // TC13 The closest point is the last of the list
        assertEquals(new Point(0.7,0.7,0.7), ray.findClosestPoint(List.of( new Point(-1,-1,-1), new Point(4,4,4), new Point(0.7,0.7,0.7))), "Function did not return correct point");


    }
}