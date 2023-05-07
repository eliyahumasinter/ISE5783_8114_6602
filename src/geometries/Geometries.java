package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.LinkedList;
public class Geometries extends Intersectable {
    private List<Intersectable> allGeometries;
    public Geometries(){
        allGeometries = new LinkedList<Intersectable>();
    }

    public void add(Intersectable... geometries){
        for( Intersectable is : geometries ) {
            allGeometries.add(is);
        }
    }


    /**
     * Helper function for findGeoIntersections
     * @param ray
     * @return list of GeoPoints
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> geoPoints = null;
        for ( Intersectable g : allGeometries){
            List<GeoPoint> geometryPoints = g.findGeoIntersections(ray);
            if (geometryPoints != null){
                if (geoPoints == null)
                    geoPoints = new LinkedList<GeoPoint>();
                for (GeoPoint gp : geometryPoints){
                    geoPoints.add(gp);
                }
            }
        }
        if (geoPoints == null)
            return null;
        return geoPoints;
    }

}
