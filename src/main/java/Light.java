package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a light source in the arena.
 * The light is visualized as a glowing circle with an outer aura.
 */
public class Light extends Item {
    private double intensity; // Intensity of the light (currently unused but reserved for future functionality)

    /**
     * Constructs a Light object with a fixed radius and default intensity.
     *
     * @param x The x-coordinate of the light's position.
     * @param y The y-coordinate of the light's position.
     */
    public Light(double x, double y) {
        super(x, y, 15); // Initialize the light with a fixed radius of 15
        this.intensity = 1.0; // Default intensity
    }

    /**
     * Updates the state of the light.
     * Currently, the light does not change its state over time.
     */
    @Override
    public void update() {
        // No dynamic behavior for the light at the moment
    }

    /**
     * Renders the light on the given graphics context.
     * The light is represented by a solid yellow core and a semi-transparent yellow aura.
     *
     * @param gc The GraphicsContext used for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        // Draw the core of the light
        gc.setFill(Color.YELLOW);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw the glowing aura around the light
        gc.setFill(Color.YELLOW.deriveColor(1, 1, 1, 0.3));
        gc.fillOval(x - radius * 2, y - radius * 2, radius * 4, radius * 4);
    }
}
