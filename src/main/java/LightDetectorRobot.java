package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Represents a robot capable of detecting and moving towards light sources.
 * It avoids obstacles and changes direction randomly when no light is detected.
 */
public class LightDetectorRobot extends SimpleRobot {
    private static final double DETECTION_RADIUS = 80; // Radius within which the robot detects light
    private static int nextIndex = 1; // Static counter to assign unique indices to each robot
    private int index; // Unique identifier for the robot
    private Random random = new Random(); // Random number generator for movement variation

    /**
     * Constructs a LightDetectorRobot at the given position.
     *
     * @param x The x-coordinate of the robot's initial position.
     * @param y The y-coordinate of the robot's initial position.
     */
    public LightDetectorRobot(double x, double y) {
        super(x, y);
        addSensor(new LightSensor(this, DETECTION_RADIUS)); // Add a light sensor to the robot
        index = nextIndex++; // Assign a unique index to the robot
    }

    /**
     * Returns a string representation of the robot.
     *
     * @return A string with the robot's type and unique index.
     */
    @Override
    public String toString() {
        return "LightDetectorRobot " + index;
    }

    /**
     * Draws the robot, including its body, wheels, direction indicator, and detection radius.
     *
     * @param gc The GraphicsContext used for rendering.
     */
    @Override
    protected void drawRobot(GraphicsContext gc) {
        // Draw the robot's body
        gc.setFill(Color.ORANGE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw the wheels
        drawWheels(gc);

        // Draw the direction indicator
        double dirX = x + radius * Math.cos(direction);
        double dirY = y + radius * Math.sin(direction);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x, y, dirX, dirY);

        // Draw the detection radius
        gc.setStroke(Color.YELLOW);
        gc.strokeOval(x - DETECTION_RADIUS, y - DETECTION_RADIUS,
                DETECTION_RADIUS * 2, DETECTION_RADIUS * 2);
    }

    /**
     * Updates the robot's position and direction.
     * The robot moves towards detected light sources and avoids obstacles.
     */
    @Override
    public void move() {
        updateSpeed();

        // Randomly change direction with a small probability
        if (random.nextDouble() < 0.03) {
            direction = random.nextDouble() * 2 * Math.PI;
        }

        // Check sensors for light or obstacles
        for (Sensor sensor : sensors) {
            if (sensor.detect(Arena.getInstance().getObjects())) {
                Light nearestLight = findNearestLight(); // Find the closest light source
                if (nearestLight != null) {
                    // Move towards the nearest light source
                    direction = Math.atan2(nearestLight.y - y, nearestLight.x - x);
                }
                handleObstacleAvoidance(sensor); // Adjust direction to avoid obstacles
                break;
            }
        }

        // Handle wall collisions and adjust direction accordingly
        handleWallCollision(x + currentSpeed * Math.cos(direction),
                y + currentSpeed * Math.sin(direction));

        // Update the robot's position
        x += currentSpeed * Math.cos(direction);
        y += currentSpeed * Math.sin(direction);
    }

    /**
     * Adjusts the robot's direction to avoid obstacles detected by sensors.
     *
     * @param sensor The sensor that detected an obstacle.
     */
    private void handleObstacleAvoidance(Sensor sensor) {
        // Turn the robot to avoid the obstacle
        direction += Math.PI / 2 + (random.nextDouble() - 0.5);
    }

    /**
     * Finds the nearest light source to the robot.
     *
     * @return The nearest Light object, or null if no lights are found.
     */
    private Light findNearestLight() {
        Light nearest = null;
        double minDistance = Double.MAX_VALUE;

        // Iterate through all objects in the arena
        for (Item item : Arena.getInstance().getObjects()) {
            if (item instanceof Light) {
                // Calculate the distance to the light
                double distance = Math.sqrt(
                        Math.pow(x - item.x, 2) +
                                Math.pow(y - item.y, 2)
                );
                // Update the nearest light if this one is closer
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = (Light) item;
                }
            }
        }
        return nearest;
    }
}
