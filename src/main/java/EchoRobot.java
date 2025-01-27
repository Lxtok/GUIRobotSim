package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Represents an EchoRobot, a type of SimpleRobot equipped with an EchoSensor
 * and capable of detecting objects within a specified beam range.
 */
public class EchoRobot extends SimpleRobot {
    private static final double BEAM_RANGE = 100; // Range of the echo beam
    private static int nextIndex = 1; // Used to assign unique IDs to robots
    private int index; // Unique identifier for this robot instance
    private Random random = new Random(); // Random generator for movement and direction changes

    /**
     * Constructs an EchoRobot at the specified coordinates.
     *
     * @param x the initial x-coordinate of the robot
     * @param y the initial y-coordinate of the robot
     */
    public EchoRobot(double x, double y) {
        super(x, y);
        addSensor(new EchoSensor(this, BEAM_RANGE)); // Add an echo sensor to the robot
        index = nextIndex++; // Assign a unique index to this robot
    }

    /**
     * Returns a string representation of the EchoRobot.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "EchoRobot " + index;
    }

    /**
     * Draws the EchoRobot on the canvas, including its body, wheels, beam, and direction.
     *
     * @param gc the GraphicsContext used for drawing
     */
    @Override
    protected void drawRobot(GraphicsContext gc) {
        gc.setFill(Color.RED); // Set color for the robot body
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2); // Draw the robot body

        drawWheels(gc); // Draw the robot's wheels

        gc.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.2)); // Set color for the echo beam
        gc.fillArc(x - BEAM_RANGE, y - BEAM_RANGE, BEAM_RANGE * 2, BEAM_RANGE * 2,
                Math.toDegrees(direction) - 30, 60, javafx.scene.shape.ArcType.ROUND); // Draw the echo beam

        // Draw the direction indicator
        double dirX = x + radius * Math.cos(direction);
        double dirY = y + radius * Math.sin(direction);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x, y, dirX, dirY);
    }

    /**
     * Moves the EchoRobot, updating its direction and handling wall collisions.
     * Includes random direction changes and sensor-based reactions to detected objects.
     */
    @Override
    public void move() {
        updateSpeed(); // Update the robot's speed

        // Randomly change direction with a small probability
        if (random.nextDouble() < 0.015) {
            direction = random.nextDouble() * 2 * Math.PI;
        }

        // Check sensors for object detection and adjust direction if necessary
        for (Sensor sensor : sensors) {
            if (sensor.detect(Arena.getInstance().getObjects())) {
                direction += Math.PI + (random.nextDouble() - 0.5); // Adjust direction upon detection
                break;
            }
        }

        // Calculate the next position and handle potential wall collisions
        handleWallCollision(x + currentSpeed * Math.cos(direction),
                y + currentSpeed * Math.sin(direction));

        // Update the robot's position
        x += currentSpeed * Math.cos(direction);
        y += currentSpeed * Math.sin(direction);
    }
}
