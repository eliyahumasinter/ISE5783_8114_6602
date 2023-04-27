package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.LinkedList;
public class Geometries implements Intersectable{
    private List<Intersectable> allGeometries;
    public Geometries(){
        allGeometries = new LinkedList<Intersectable>();
    }

    public void add(Intersectable... geometries){
        for( Intersectable is : geometries ) {
            allGeometries.add(is);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        List<Point> points = null;
        for ( Intersectable g : allGeometries){
            List<Point> geometryPoints = g.findIntersections(ray);
            if (geometryPoints != null){
                if (points == null)
                    points = new LinkedList<Point>();
                for (Point p : geometryPoints){
                    points.add(p);
                }
            }
        }
        if (points.size() > 0)
            return points;
        return null;
    }

}
