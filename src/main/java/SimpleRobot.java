package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * A simple robot class that represents a robot with basic movement and collision handling.
 * The robot moves randomly within the arena and avoids obstacles by using sensors.
 */
public class SimpleRobot extends Robot {
    private static int nextIndex = 1;  // To keep track of the robot's index
    private int index;  // Unique index for this robot
    private Random random = new Random();  // Random object to generate random values

    /**
     * Constructor for the SimpleRobot.
     * Initializes the robot with the specified position and adds an obstacle sensor.
     *
     * @param x The x-coordinate of the robot's initial position.
     * @param y The y-coordinate of the robot's initial position.
     */
    public SimpleRobot(double x, double y) {
        super(x, y, 20, 3);  // Call the superclass constructor (Robot) with radius and speed
        addSensor(new ObstacleSensor(this, 30));  // Add an obstacle sensor to the robot
        index = nextIndex++;  // Assign a unique index to this robot
    }

    /**
     * Returns a string representation of the robot.
     *
     * @return A string representing the robot's type and unique index.
     */
    @Override
    public String toString() {
        return "SimpleRobot " + index;
    }

    /**
     * Draws the robot on the canvas.
     *
     * @param gc The GraphicsContext to draw on.
     */
    @Override
    protected void drawRobot(GraphicsContext gc) {
        // Draw the robot's body
        gc.setFill(Color.GREEN);  // Set the color to green
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);  // Draw a circle representing the robot's body

        // Draw wheels (optional method, could be implemented for more detail)
        drawWheels(gc);

        // Draw direction indicator
        double dirX = x + radius * Math.cos(direction);  // Calculate the x position of the direction indicator
        double dirY = y + radius * Math.sin(direction);  // Calculate the y position of the direction indicator
        gc.setStroke(Color.BLACK);  // Set the stroke color to black
        gc.strokeLine(x, y, dirX, dirY);  // Draw a line indicating the robot's direction
    }

    /**
     * Handles collisions with walls and adjusts the robot's direction if it hits the arena boundaries.
     *
     * @param newX The new x-coordinate of the robot.
     * @param newY The new y-coordinate of the robot.
     */
    protected void handleWallCollision(double newX, double newY) {
        // Check if the robot has collided with the left or right walls
        if (newX - radius < 0 || newX + radius > Arena.getInstance().getWidth()) {
            direction = Math.PI - direction;  // Reverse the direction horizontally
        }
        // Check if the robot has collided with the top or bottom walls
        if (newY - radius < 0 || newY + radius > Arena.getInstance().getHeight()) {
            direction = -direction;  // Reverse the direction vertically
        }
    }

    /**
     * Moves the robot by updating its position and handling collisions with obstacles or walls.
     */
    @Override
    public void move() {
        updateSpeed();  // Update the robot's speed based on the current conditions
        if (random.nextDouble() < 0.02) {
            direction = random.nextDouble() * 2 * Math.PI;  // Change direction randomly
        }

        // Check if the robot detects any obstacles using its sensors
        for (Sensor sensor : sensors) {
            if (sensor.detect(Arena.getInstance().getObjects())) {
                // If an obstacle is detected, the robot turns to avoid it
                direction += Math.PI/2 + (random.nextDouble() - 0.5);
                break;
            }
        }

        // Handle collisions with the arena walls based on the robot's movement
        handleWallCollision(x + currentSpeed * Math.cos(direction),
                y + currentSpeed * Math.sin(direction));

        // Update the robot's position based on the current direction and speed
        x += currentSpeed * Math.cos(direction);
        y += currentSpeed * Math.sin(direction);
    }

    /**
     * Updates the robot's state, such as movement and interactions.
     */
    @Override
    public void update() {
        move();  // Call the move method to update the robot's position
    }

    /**
     * Renders the robot on the canvas.
     *
     * @param gc The GraphicsContext to render the robot.
     */
    @Override
    public void render(GraphicsContext gc) {
        drawRobot(gc);  // Draw the robot on the canvas
    }
}
