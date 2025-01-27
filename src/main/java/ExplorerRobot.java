package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * The ExplorerRobot is a type of robot that explores the arena while avoiding obstacles
 * using an attached ObstacleSensor.
 */
public class ExplorerRobot extends Robot {
    private Random random = new Random(); // Random generator for movement and direction changes

    /**
     * Constructs an ExplorerRobot with specified initial position.
     *
     * @param x the initial x-coordinate of the robot
     * @param y the initial y-coordinate of the robot
     */
    public ExplorerRobot(double x, double y) {
        super(x, y, 20, 3); // Initialize with position, radius, and speed
        addSensor(new ObstacleSensor(this, 50)); // Add an obstacle sensor with a range of 50 units
    }

    /**
     * Draws the ExplorerRobot on the canvas, including its body and direction indicator.
     *
     * @param gc the GraphicsContext used for drawing
     */
    @Override
    protected void drawRobot(GraphicsContext gc) {
        gc.setFill(Color.BLUE); // Set color for the robot body
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2); // Draw the robot body

        // Draw the direction indicator
        double dirX = x + radius * Math.cos(direction);
        double dirY = y + radius * Math.sin(direction);
        gc.setStroke(Color.RED);
        gc.strokeLine(x, y, dirX, dirY);
    }

    /**
     * Renders the ExplorerRobot on the canvas by calling drawRobot().
     *
     * @param gc the GraphicsContext used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        drawRobot(gc);
    }

    /**
     * Moves the ExplorerRobot, adjusting its direction and handling collisions.
     * Includes random direction changes and obstacle avoidance using sensors.
     */
    @Override
    public void move() {
        updateSpeed(); // Update the robot's speed

        // Randomly change direction with a small probability
        if (random.nextDouble() < 0.02) {
            direction = random.nextDouble() * 2 * Math.PI;
        }

        // Check sensors for obstacle detection and adjust direction if necessary
        for (Sensor sensor : sensors) {
            if (sensor.detect(Arena.getInstance().getObjects())) {
                direction += Math.PI / 2 + (random.nextDouble() - 0.5); // Adjust direction upon detection
                break;
            }
        }

        double newX = x + currentSpeed * Math.cos(direction);
        double newY = y + currentSpeed * Math.sin(direction);

        // Handle wall collisions with a random bounce effect
        if (newX - radius < 0 || newX + radius > 800) { // Left or right wall
            direction = Math.PI - direction + (random.nextDouble() - 0.5); // Bounce horizontally
            x = Math.max(radius, Math.min(800 - radius, x)); // Keep within bounds
        }
        if (newY - radius < 0 || newY + radius > 600) { // Top or bottom wall
            direction = -direction + (random.nextDouble() - 0.5); // Bounce vertically
            y = Math.max(radius, Math.min(600 - radius, y)); // Keep within bounds
        }

        // Update the robot's position
        x += currentSpeed * Math.cos(direction);
        y += currentSpeed * Math.sin(direction);
    }

    /**
     * Updates the state of the ExplorerRobot by performing its movement.
     */
    @Override
    public void update() {
        move();
    }
}
