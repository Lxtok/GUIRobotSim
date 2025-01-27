package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * A WhiskerRobot is a robot equipped with whisker sensors that help detect obstacles in its path.
 * It moves randomly and uses its whiskers to detect and avoid obstacles.
 */
public class WhiskerRobot extends SimpleRobot {
    private static final double WHISKER_LENGTH = 30;  // Length of the whiskers used for obstacle detection
    private static final double WHISKER_ANGLE = Math.PI / 8;  // Angle between the two whiskers
    private static int nextIndex = 1;  // Index to differentiate multiple WhiskerRobots
    private int index;  // Unique index for each WhiskerRobot
    private Random random = new Random();  // Random object for random movement decisions

    /**
     * Constructor for WhiskerRobot.
     * Initializes the robot with the given position and attaches whisker sensors for obstacle detection.
     *
     * @param x The x-coordinate of the robot's initial position.
     * @param y The y-coordinate of the robot's initial position.
     */
    public WhiskerRobot(double x, double y) {
        super(x, y);  // Call the constructor of the parent SimpleRobot class
        addSensor(new WhiskerSensor(this, WHISKER_LENGTH));  // Attach a whisker sensor with a specified length
        index = nextIndex++;  // Assign a unique index to this robot
    }

    /**
     * Returns a string representation of the WhiskerRobot with its index.
     *
     * @return A string representing the WhiskerRobot.
     */
    @Override
    public String toString() {
        return "WhiskerRobot " + index;
    }

    /**
     * Moves the robot, updating its position based on random movement and obstacle detection.
     * The robot uses whisker sensors to detect obstacles and avoid them.
     */
    @Override
    public void move() {
        updateSpeed();  // Update the robot's speed
        if (random.nextDouble() < 0.01) {
            // Occasionally change the robot's direction randomly
            direction = random.nextDouble() * 2 * Math.PI;
        }

        // Check for obstacle detection using whisker sensors
        for (Sensor sensor : sensors) {
            if (sensor.detect(Arena.getInstance().getObjects())) {
                // If an obstacle is detected, change direction randomly
                direction += Math.PI / 2 + (random.nextDouble() - 0.5);
                break;
            }
        }

        // Calculate the new position based on current speed and direction
        double newX = x + currentSpeed * Math.cos(direction);
        double newY = y + currentSpeed * Math.sin(direction);

        // Handle potential collisions with walls
        handleWallCollision(newX, newY);

        // Update the robot's position
        x += currentSpeed * Math.cos(direction);
        y += currentSpeed * Math.sin(direction);
    }

    /**
     * Handles collisions with walls by changing the robot's direction when it reaches the boundary.
     * Adds a random element to the new direction to simulate more natural movement.
     *
     * @param newX The proposed new x-coordinate after movement.
     * @param newY The proposed new y-coordinate after movement.
     */
    @Override
    protected void handleWallCollision(double newX, double newY) {
        if (newX - radius < 0 || newX + radius > Arena.getInstance().getWidth()) {
            // Reverse direction on x-axis when hitting the horizontal wall
            direction = Math.PI - direction + (random.nextDouble() - 0.5);
        }
        if (newY - radius < 0 || newY + radius > Arena.getInstance().getHeight()) {
            // Reverse direction on y-axis when hitting the vertical wall
            direction = -direction + (random.nextDouble() - 0.5);
        }
    }

    /**
     * Renders the robot and its whisker sensors on the canvas.
     * The whiskers are drawn at specified angles from the robot's body to visualize obstacle detection.
     *
     * @param gc The GraphicsContext used for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        super.drawRobot(gc);  // Draw the robot's body

        // Set whisker properties for rendering
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // Left whisker drawn at an angle to the left of the robot's direction
        double leftWhiskerAngle = direction + WHISKER_ANGLE;
        gc.strokeLine(x, y,
                x + WHISKER_LENGTH * Math.cos(leftWhiskerAngle),
                y + WHISKER_LENGTH * Math.sin(leftWhiskerAngle));

        // Right whisker drawn at an angle to the right of the robot's direction
        double rightWhiskerAngle = direction - WHISKER_ANGLE;
        gc.strokeLine(x, y,
                x + WHISKER_LENGTH * Math.cos(rightWhiskerAngle),
                y + WHISKER_LENGTH * Math.sin(rightWhiskerAngle));
    }
}
