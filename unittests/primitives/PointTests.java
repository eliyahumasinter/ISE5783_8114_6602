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
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Checks subtraction with positive vectors
        Point p = new Point(5,5,5);
        assertEquals(new Vector(1, 2, 3), p.subtract(new Point(4,3,2)));
        //TC02: Checks subtraction with negative vectors
        Point p3 = new Point(-10, -10, -10);
        assertEquals(new Vector(-3, -2, -6), p3.subtract(new Point(-7, -8, -4)));
        //TC03: Checks subtraction with one negative and one positive vector
        assertEquals(new Vector(3, 2, 6), p1.subtract(new Point(-2, 0, -3)));
        // =============== Boundary Values Tests ==================
        // TC11: Test subtracting point from itself
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1));
    }

    /**
     * Test method for {@link primitives.Point#add(Vector)}
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Checks addition with positive vectors
        assertEquals(new Point(5, 5, 5), p1.add(new Vector(4,3,2)));
        //TC02: Checks addition with negative vectors
        Point p3 = new Point(-3, -2, -6);
        assertEquals(new Point(-10, -10, -10), p3.add(new Vector(-7, -8, -4)));
        //TC03: Checks addition with one negative and one positive vector
        assertEquals(new Point(-2, 0, -3), p1.add(new Vector(-3, -2, -6)));
        // =============== Boundary Values Tests ==================
    }


    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ensures that length squared works
        assertEquals(3, p1.distanceSquared(p2),"Distance squared Function not working");
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)}
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ensures distance works
        assertEquals(Math.sqrt(3), p1.distance(p2),"Distance Function not working");
    }
}