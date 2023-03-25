package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link primitives.Point} class
 * @author Eliyahu Masinter Yishai Dredzen
 */
class PointTests {
    Point p1 = new Point(1, 2, 3);

    /**
     * Test method for {@link primitives.Point#subtract(Point)}.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
        // TC11: Test subtracting point from vector
        assertEquals(p1.add((new Vector(2, 3, 4)).subtract(p1)), new Vector(1, 1, 1), "ERROR: Point - Point does not work correctly");
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

    @Test
    void distanceSquared() {
        fail("Not yet implemented");
    }

    @Test
    void distance() {
        fail("Not yet implemented");
    }
}