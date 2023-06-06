/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private final Scene scene = new Scene("Test scene");

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                           new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVPDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

      scene.geometries.add( //
                           new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of two triangles lighted by a spotlight with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.geometries.add( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKl(4E-5).setKq(2E-7));

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /*
   Produce a picture containing four objects showing all the effects implemented in this stage
    */
   @Test
   public void fourObjects() {
      Camera camera = new Camera(new Point(120, 150, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPSize(200, 200).setVPDistance(450);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.geometries.add(//
              new Sphere(new Point(90,178,0), 150).setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.6).setShininess(10).setKt(0.6)),
              new Sphere(new Point(62, 97, 0), 100).setEmission(new Color(RED)) //
                      .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(30).setKt(1)),
              new Triangle(new Point(275, -376, -233), new Point(573, 456, -247), new Point(-447, 101, -145)).setEmission(new Color(30,30,30))//
                      .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))),//
              new Triangle(new Point(190,278,200), new Point(-447, 101, -145), new Point(-200, -400, -151))//
                      .setEmission(new Color(40,40,40))//
                      .setMaterial(new Material().setKr(new Double3(0.5, 0.6, 0.4)).setKt(1)));

      scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(321, 397, 69)));
      ImageWriter iw = new ImageWriter("customScene", 500, 500);
      camera.setImageWriter(iw).setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();
   }


   @Test
   public void snowMan() {
      Camera camera = new Camera(new Point(60, 0, 37), new Vector(-2, 0, -1), new Vector(-1, 0, 2)) //
              .setVPSize(200, 200).setVPDistance(300).rotate(0,0,20);//.shiftCamera(new Vector(0,0,0));

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.geometries.add(//
              new Plane(new Point(0,0,0), new Point(1,0,0),new Point(0,1,0)).setEmission(new Color(100,100,100)).setMaterial(new Material().setKd(0.2).setKs(0.3)),
              new Sphere(new Point(0,0,5), 3).setEmission(new Color(255,0,0)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Sphere(new Point(0,0,10), 2).setEmission(new Color(0,255,0)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Sphere(new Point(0,0,13), 1).setEmission(new Color(0,0,255)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Polygon(new Point(-10,-10,0), new Point(-10,10,0), new Point(-10,10,15), new Point(-10,-10,15)).setEmission(new Color(255,0,255)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Polygon(new Point(-10,10,0), new Point(-10,10,15), new Point(10,20,15), new Point(10,20,0)).setEmission(new Color(0,255,255)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Polygon(new Point(-10,-10,0), new Point(-10,-10,15), new Point(10,-20,15), new Point(10,-20,0)).setEmission(new Color(255,255,0)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Sphere(new Point(15,0,10), 2).setEmission(new Color(100,200,300)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100))


      );


      scene.lights.add(new DirectionalLight(new Color(800,500,0), new Vector(-1, 0, -2)));
      scene.lights.add(new SpotLight(new Color(0, 0, 255), new Point(20, -20, 10), new Vector(-1,1,0))
              .setKl(0.0001).setKq(0.0001).setRadius(10));

      ImageWriter iw = new ImageWriter("snowMan", 500, 500);
      camera.setImageWriter(iw).setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();
   }

   @Test
   public void snowManRotate() {
      Camera camera = new Camera(new Point(60, 0, 37), new Vector(-2, 0, -1), new Vector(-1, 0, 2)) //
              .setVPSize(200, 200).setVPDistance(400).rotate(0,0,0).shiftCamera(new Vector(-60,0,0));

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.geometries.add(//
              new Plane(new Point(0,0,0), new Point(1,0,0),new Point(0,1,0)).setEmission(new Color(100,100,100)).setMaterial(new Material().setKd(0.2).setKs(0.3)),
              new Sphere(new Point(0,0,5), 3).setEmission(new Color(255,0,0)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Sphere(new Point(0,0,10), 2).setEmission(new Color(0,255,0)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Sphere(new Point(0,0,13), 1).setEmission(new Color(0,0,255)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Polygon(new Point(-10,-10,0), new Point(-10,10,0), new Point(-10,10,15), new Point(-10,-10,15)).setEmission(new Color(255,0,255)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Polygon(new Point(-10,10,0), new Point(-10,10,15), new Point(10,20,15), new Point(10,20,0)).setEmission(new Color(0,255,255)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100)),
              new Polygon(new Point(-10,-10,0), new Point(-10,-10,15), new Point(10,-20,15), new Point(10,-20,0)).setEmission(new Color(255,255,0)).setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(100))
      );


      scene.lights.add(new DirectionalLight(new Color(800,500,0), new Vector(-1, 0, -2)));
      scene.lights.add(new SpotLight(new Color(0, 0, 255), new Point(20, -20, 10), new Vector(-1,1,0))
              .setKl(0.0001).setKq(0.0001));;

      ImageWriter iw = new ImageWriter("snowManRotate", 500, 500);
      camera.setImageWriter(iw).setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();
   }

   @Test
   public void tests() {
      Camera camera = new Camera(new Point(3100, -3100, -2600), new Vector(-2, 2, 2), new Vector(-1, -2, 1)).setVPDistance(600).setVPSize(200, 200);
      scene.setBackground(new Color(BLACK));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.geometries.add( //
              new Plane(new Point(0,400,100), new Vector(0,-1,0)).setEmission(Color.BLACK).setMaterial(new Material().setKd(0.2).setKs(0.1).setShininess(60)),
              new Polygon(new Point(-1, -300, 500), new Point(-1, -140, 500), new Point(1, -140, 500), new Point(1, -300, 500)).setEmission(Color.BLACK).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(200).setKr(0.5)),
              new Sphere(new Point(-1,-120, 500), 80).setEmission(new Color(YELLOW)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(200).setKt(0.8)),
              new Polygon(new Point(-150, -150, 1999), new Point(-150, 200, 1999), new Point(150, 200, 1999), new Point(150, -150, 1999)).setEmission(Color.BLACK).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(200).setKr(0.5)),
              new Sphere(new Point(300,260, 600), 140).setEmission(new Color(800,0,0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(200).setKr(0.2)),
              new Sphere(new Point(-260, 260, 0), 140).setEmission(new Color(0,0,200)).setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.25)),
              new Sphere(new Point(-600,300,1300), 100).setEmission(new Color(700,20,20)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(200)),
              new Triangle(new Point(-100, 400, 150), new Point(100, 400, 350), new Point(0, 200, 250)).setEmission(new Color(100,300,100)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKr(0.5))//
      );

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(0, 0, -1500), new Vector(0, 0, 1))
              .setKc(1).setKl(4E-5).setKq(2E-7).setRadius(15));//no. 1
//      scene.lights.add(new PointLight(new Color(200, 600, 200), new Point(0.001, -100, 499))
//                      .setKc(1).setKl(4E-5).setKq(2E-7).setRadius(7));//no.2
      scene.lights.add(new PointLight(new Color(200, 200, 600), new Point(0.001, -50, 1000)).setKc(1).setKl(4E-5).setKq(2E-7).setRadius(15));//no.3

      ImageWriter iw = new ImageWriter("The magical room moving camera to right - soft shadow 2",  500, 500);
      camera.setImageWriter(iw).setRayTracer(new RayTracerBasic(scene).setSuper_sampling(true).setNumberOfRays(400)).renderImage().writeToImage();

      //render.renderImage();
      //render.writeToImage();
   }

}
