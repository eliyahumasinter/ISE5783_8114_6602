package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void findIntersections() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Several (but not all) geometries intersect (
        Geometries geometries = new Geometries();
        geometries.add(new Plane(new Point(0,0,1),new Point(1,0,1),new Point(0,1,1)));
        geometries.add(new Sphere(new Point(10,0,1), 1));
        Ray ray = new Ray(new Point(0,0,-1), new Vector(0,0,1));
        assertEquals(1, geometries.findIntersections(ray).size(), "all but 1 intersect");

        // ======= Boundary Value Analysis Tests ==============

        //TC11: Empty Geometries Collection
        geometries = new Geometries();
        ray = new Ray(new Point(-1,0,0), new Vector(1,0,0));
        assertNull(geometries.findIntersections(ray), "Empty Geometry Collection");

        //TC 12: No Intersection Point
        geometries.add(new Sphere(new Point(1,0,10), 1));
        assertNull(geometries.findIntersections(ray), "No intersections");

        //TC 13: Only one geometry intersects
        geometries.add(new Sphere(new Point(1,0,0), 1));
        assertEquals(1, geometries.findIntersections(ray).size(), "1 intersection");

        //TC 14: All geometries intersect
        geometries = new Geometries();
        geometries.add(new Plane(new Point(0,0,1),new Point(1,0,1),new Point(0,1,1)));
        geometries.add(new Sphere(new Point(0,0,5), 1));
        ray = new Ray(new Point(0,0,-1), new Vector(0,0,1));
        assertEquals(3, geometries.findIntersections(ray).size(), "all intersect");

    }
}