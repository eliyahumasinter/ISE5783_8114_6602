package geometries;

import primitives.Point;

/**
 * A class of extending the Polygon class to represent a triangle
 */
public class Triangle extends Polygon{

    /**
     * Constructor that takes in 3 points
     * @param p0
     * @param p1
     * @param p2
     */
    public Triangle(Point p0, Point p1, Point p2){
        super(p0,p1,p2);
    }

}
