package ok;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a generic item in the arena.
 * This is an abstract base class for all items, providing common properties such as position and radius.
 *
 * <p>
 * Includes support for JSON serialization via Jackson, which allows the class to store its type information.
 * </p>
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS, // Use minimal class name for type identification
        include = JsonTypeInfo.As.PROPERTY,  // Include type information as a property
        property = "@type"                  // JSON property name for type information
)
public abstract class Item {
    protected double x;       // X-coordinate of the item's position
    protected double y;       // Y-coordinate of the item's position
    protected double radius;  // Radius of the item, used for rendering and collision detection

    /**
     * Default constructor for Item. Necessary for JSON deserialization.
     */
    public Item() {}

    /**
     * Constructs an item with specified position and radius.
     *
     * @param x      The x-coordinate of the item.
     * @param y      The y-coordinate of the item.
     * @param radius The radius of the item.
     */
    public Item(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * Updates the state of the item.
     * This method is abstract and must be implemented by subclasses.
     */
    @JsonIgnore
    public abstract void update();

    /**
     * Renders the item on the given graphics context.
     * This method is abstract and must be implemented by subclasses.
     *
     * @param gc The GraphicsContext used for rendering.
     */
    @JsonIgnore
    public abstract void render(GraphicsContext gc);

    /**
     * Gets the x-coordinate of the item.
     *
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the item.
     *
     * @param x The new x-coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the item.
     *
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the item.
     *
     * @param y The new y-coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the radius of the item.
     *
     * @return The radius.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the item.
     *
     * @param radius The new radius.
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }
}
