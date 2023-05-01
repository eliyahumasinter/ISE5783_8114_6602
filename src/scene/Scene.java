package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * Class that manages visual elements such as background and geometries
 * @author Eliyahu and Yishai
 */
public class Scene {
    public String name = null;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries = null;

    /**
     * Constructer that accepts a String as the name of the Scene
     * @param name
     */
    public Scene(String name){
        name=name;
        geometries = new Geometries();
    }

    /**
     * Method to set the background color
     * @param background
     * @return this - the scene object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light to a different intensity
     * @param ambientLight
     * @return this=- - the Scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter to set the list of geometries to a new list of Geometries
     * @param geometries - Geometries object
     * @return this - the Scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
