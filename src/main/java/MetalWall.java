package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

/**
 * MetalWall is a class that represents a metal wall item in the simulation.
 * It extends the Item class and renders a metallic-looking wall on the screen.
 */
public class MetalWall extends Item {

    /**
     * Constructor for MetalWall with a default size of 40.
     *
     * @param x The x-coordinate of the wall's position.
     * @param y The y-coordinate of the wall's position.
     */
    public MetalWall(double x, double y) {
        super(x, y, 40); // Default size for the wall
    }

    /**
     * Constructor for MetalWall with a customizable size.
     *
     * @param x The x-coordinate of the wall's position.
     * @param y The y-coordinate of the wall's position.
     * @param size The size of the wall.
     */
    public MetalWall(double x, double y, double size) {
        super(x, y, size); // Customizable size for the wall
    }

    /**
     * Updates the MetalWall. Since it's a static object, no updates are necessary.
     */
    @Override
    public void update() {
        // This is a static object, so no update logic is required.
    }

    /**
     * Renders the MetalWall on the provided GraphicsContext.
     * It uses a gradient to create a metallic appearance and adds a border for definition.
     *
     * @param gc The GraphicsContext used to draw on the canvas.
     */
    @Override
    public void render(GraphicsContext gc) {
        // Create a linear gradient to give the wall a metallic look
        LinearGradient gradient = new LinearGradient(
                x - radius, y - radius, // Start point of the gradient
                x + radius, y + radius, // End point of the gradient
                false, CycleMethod.NO_CYCLE, // No repeat of the gradient
                new Stop(0, Color.LIGHTGRAY), // Light gray at the start
                new Stop(1, Color.DARKGRAY)   // Dark gray at the end
        );

        // Set the gradient as the fill for the rectangle
        gc.setFill(gradient);
        gc.fillRect(x - radius, y - radius, radius * 2, radius * 2); // Draw the rectangle with the gradient fill

        // Add a metallic sheen effect using a white stroke
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeLine(x - radius + 5, y - radius + 5, // Starting point of the line
                x + radius - 5, y - radius + 5); // Ending point of the line

        // Add a darker border around the rectangle for definition
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(x - radius, y - radius, radius * 2, radius * 2); // Draw the border around the wall
    }
}
