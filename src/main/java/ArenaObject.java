package ok;

import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract class representing an object within an Arena.
 * Each object has a position (x, y), a radius, and can be updated or rendered.
 */
public abstract class ArenaObject {
    // X-coordinate of the object's position
    protected double x;

    // Y-coordinate of the object's position
    protected double y;

    // Radius of the object, used for rendering and collision detection
    protected double radius;

    /**
     * Constructs an ArenaObject with a given position and radius.
     *
     * @param x the x-coordinate of the object's position
     * @param y the y-coordinate of the object's position
     * @param radius the radius of the object
     */
    public ArenaObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * Abstract method to update the state of the object.
     * Subclasses must provide a concrete implementation of this method.
     */
    public abstract void update();

    /**
     * Abstract method to render the object on a canvas.
     * Subclasses must provide a concrete implementation of this method.
     *
     * @param gc the GraphicsContext used to draw the object
     */
    public abstract void render(GraphicsContext gc);

    /**
     * Checks if this object collides with another ArenaObject.
     * Collision is determined based on the distance between the two objects
     * and their radii.
     *
     * @param other the other ArenaObject to check collision with
     * @return true if the objects collide, false otherwise
     */
    public boolean collidesWith(ArenaObject other) {
        // Calculate the distance between the two objects using the distance formula
        double distance = Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));

        // Check if the distance is less than the sum of the two radii (collision detection)
        return distance < (this.radius + other.radius);
    }
}