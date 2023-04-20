package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for {@link primitives.Vector} class
 * @author Eliyahu Masinter Yishai Dredzen
 */

class VectorTests {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test vector adding negative vector
        assertEquals(v1.add(new Vector(-2,-4,-6)), new Vector(-1, -2, -3), "ERROR: Point - Point does not work correctly");

        // TC02: test vector subtracting negative vector
        assertEquals(v1.subtract(new Vector(-2,-4,-6)), new Vector(3, 6, 9), "ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================

        // TC11: test vector adding the negative of itself
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)),"ERROR: Vector - itself throws wrong exception" );

        //TC12: test vector adding itself to itself.
        assertEquals(v1.add(new Vector(1,2,3)), v1.scale(2), "ERROR: Vector + itself not working");
    }


    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ensures Subtraction works
        assertEquals(v1.subtract(new Point(1,1,1)), new Vector(0,1,2), "ERROR: Vector subtraction doesn't work");

        // =============== Boundary Values Tests ==================
        //TC11: Checks subtraction with itself
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1));
    }


    /**
    * Test method for {@link primitives.Vector#scale(double)} }.
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        //todo comment
        assertEquals(new Vector(2,4,6), v1.scale(2), "Scaling not working");

        // =============== Boundary Values Tests ==================
    }


    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    void testDotProduct() {

        // ============ Equivalence Partitions Tests ==============

        //Test dot product function on non-orthogonal vectors
        assertTrue(isZero((v1.dotProduct(v2) + 28)), "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================

        // TC11: Test dot product function on orthogonal vectors
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }


    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector vr = v1.crossProduct(v3);
        System.out.println(vr.toString());
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }


    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {

        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

        // TC11: Test the length squared function on the vector (1,2,3)
        assertEquals(v1.lengthSquared(), 14, "ERROR: lengthSquared() wrong value");

    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

        // TC11: Test the length function on the vector (0,3,4)
        assertEquals(new Vector(0, 3, 4).length(), 5, "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {

        Vector u = v1.normalize();

        // ============ Equivalence Partitions Tests ==============

        // test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u), "ERROR: the normalized vector is not parallel to the original one");

        // =============== Boundary Values Tests ==================

        // tests that the normalized vector has a length of 1 (it is a unit vector)
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");

        // tests that the normalized vector is in the same direction as the original vector
        assertFalse(v1.dotProduct(u) < 0, "ERROR: the normalized vector is opposite to the original one");

    }
}