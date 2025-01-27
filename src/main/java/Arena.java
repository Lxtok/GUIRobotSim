package ok;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class representing an Arena where items can be added and updated.
 * The Arena is a fixed size and provides methods to manage and update its items.
 */
public class Arena {
    // Static instance of the Arena for singleton pattern
    private static Arena instance;

    // List to hold all items within the Arena
    private List<Item> objects;

    // Dimensions of the Arena (width and height in pixels)
    private double width = 800;
    private double height = 600;

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the list of items in the Arena.
     */
    private Arena() {
        objects = new ArrayList<>();
    }

    /**
     * Returns the single instance of the Arena. If it doesn't exist, it creates one.
     *
     * @return the singleton instance of Arena
     */
    public static Arena getInstance() {
        if (instance == null) {
            instance = new Arena(); // Create a new instance if none exists
        }
        return instance;
    }

    /**
     * Adds a new item to the Arena.
     *
     * @param obj the item to be added
     */
    public void addObject(Item obj) {
        objects.add(obj); // Add the item to the list
    }

    /**
     * Retrieves the list of items currently in the Arena.
     *
     * @return a list of items
     */
    public List<Item> getObjects() {
        return objects; // Return the list of items
    }

    /**
     * Gets the width of the Arena.
     *
     * @return the width in pixels
     */
    public double getWidth() {
        return width; // Return the width
    }

    /**
     * Gets the height of the Arena.
     *
     * @return the height in pixels
     */
    public double getHeight() {
        return height; // Return the height
    }

    /**
     * Updates all items in the Arena by calling their update methods.
     * This is typically used to apply logic like movement or state changes.
     */
    public void update() {
        for (Item obj : objects) {
            obj.update(); // Update each item in the list
        }
    }
}
