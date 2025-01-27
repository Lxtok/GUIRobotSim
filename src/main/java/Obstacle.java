package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Obstacle class represents a simple obstacle item in the simulation.
 * It extends the Item class and renders a gray square as an obstacle.
 */
public class Obstacle extends Item {

    /**
     * Constructor for Obstacle with a default size of 25.
     *
     * @param x The x-coordinate of the obstacle's position.
     * @param y The y-coordinate of the obstacle's position.
     */
    public Obstacle(double x, double y) {
        super(x, y, 25); // Default size for the obstacle
    }

    /**
     * Updates the Obstacle. Since it's a static object, no updates are necessary.
     */
    @Override
    public void update() {
        // This is a static object, so no update logic is required.
    }

    /**
     * Renders the Obstacle on the provided GraphicsContext.
     * It fills a square with a gray color to represent the obstacle.
     *
     * @param gc The GraphicsContext used to draw on the canvas.
     */
    @Override
    public void render(GraphicsContext gc) {
        // Set the fill color to gray for the obstacle
        gc.setFill(Color.GRAY);

        // Draw the square at the specified position with the given size (radius)
        gc.fillRect(x - radius, y - radius, radius * 2, radius * 2);
    }
}
