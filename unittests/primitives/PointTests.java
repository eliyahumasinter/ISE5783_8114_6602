package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link primitives.Point} class
 * @author Eliyahu Masinter Yishai Dredzen
 */
class PointTests {
    Point p1 = new Point(1, 2, 3);
    Point p2 = new Point(2,3,4);

    /**
     * Test method for {@link primitives.Point#subtract(Point)}.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
        // TC11: Test subtracting point from vector
        assertEquals(p2.subtract(p1), new Vector(1, 1, 1), "ERROR: Point - Point does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#add(Vector)}
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

        // TC11: Test adding vector to point
        assertEquals(p1.add(new Vector(-1, -2, -3)), new Point(0, 0, 0), "ERROR: Point + Vector does not work correctly");
    }


    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}
     */
    @Test
    void testDistanceSquared() {
        assertEquals(3, p1.distanceSquared(p2),"Distance squared Function not working");
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)}
     */
    @Test
    void testDistance() {
        assertEquals(Math.sqrt(3), p1.distance(p2),"Distance Function not working");

    }
}