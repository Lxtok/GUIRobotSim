package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract base class representing a robot in the simulation.
 * It provides common properties and behavior for all robots, including movement, speed, sensors, and wheel rendering.
 */
public abstract class Robot extends Item {
    protected double maxSpeed;   // Maximum speed of the robot
    protected double currentSpeed;  // Current speed of the robot
    protected double direction;    // Direction the robot is facing (in radians)
    protected List<Sensor> sensors;  // List of sensors attached to the robot
    protected Random random = new Random(); // Random object for random number generation

    /**
     * Constructor to initialize the robot with a position, radius, and maximum speed.
     * The initial speed is randomly chosen to be between 50% and 100% of the maximum speed.
     *
     * @param x The initial x-coordinate of the robot.
     * @param y The initial y-coordinate of the robot.
     * @param radius The radius of the robot.
     * @param maxSpeed The maximum speed the robot can travel.
     */
    public Robot(double x, double y, double radius, double maxSpeed) {
        super(x, y, radius);
        this.maxSpeed = maxSpeed;
        // Set initial speed randomly between 50% and 100% of max speed
        this.currentSpeed = maxSpeed * (0.5 + random.nextDouble() * 0.5);
        // Set random initial direction in radians
        this.direction = random.nextDouble() * 2 * Math.PI;
        this.sensors = new ArrayList<>(); // Initialize sensors list
    }

    /**
     * Abstract method for robot movement. This method should be implemented by subclasses to define how the robot moves.
     */
    public abstract void move();

    /**
     * Abstract method to draw the robot. This method should be implemented by subclasses to render the robot on the canvas.
     *
     * @param gc The GraphicsContext used to draw the robot on the canvas.
     */
    protected abstract void drawRobot(GraphicsContext gc);

    /**
     * Adds a sensor to the robot.
     *
     * @param sensor The sensor to be added to the robot.
     */
    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    /**
     * Gets the current speed of the robot.
     *
     * @return The current speed of the robot.
     */
    public double getCurrentSpeed() { return currentSpeed; }

    /**
     * Gets the direction the robot is facing.
     *
     * @return The direction of the robot in radians.
     */
    public double getDirection() { return direction; }

    /**
     * Gets the x-coordinate of the robot.
     *
     * @return The x-coordinate of the robot.
     */
    public double getX() { return x; }

    /**
     * Gets the y-coordinate of the robot.
     *
     * @return The y-coordinate of the robot.
     */
    public double getY() { return y; }

    /**
     * Updates the robot's speed based on a random chance.
     * The speed has a 5% chance to change every time this method is called.
     */
    protected void updateSpeed() {
        if (random.nextDouble() < 0.05) {
            // Randomly adjust speed between 50% and 100% of max speed
            currentSpeed = maxSpeed * (0.5 + random.nextDouble() * 0.5);
        }
    }

    /**
     * Draws the wheels of the robot on the canvas.
     * The wheels are positioned based on the robot's direction and rendered as black rectangles.
     *
     * @param gc The GraphicsContext used to draw the wheels on the canvas.
     */
    protected void drawWheels(GraphicsContext gc) {
        // Calculate the positions of the left and right wheels
        double leftWheelX = x + radius * Math.cos(direction + Math.PI/4);
        double leftWheelY = y + radius * Math.sin(direction + Math.PI/4);

        double rightWheelX = x + radius * Math.cos(direction - Math.PI/4);
        double rightWheelY = y + radius * Math.sin(direction - Math.PI/4);

        // Draw wheels as black rectangles
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);

        double wheelWidth = radius * 0.5;
        double wheelHeight = radius * 0.2;

        // Left wheel
        gc.save();
        gc.translate(leftWheelX, leftWheelY); // Translate to wheel position
        gc.rotate(Math.toDegrees(direction)); // Rotate to the direction of the robot
        gc.fillRect(-wheelWidth/2, -wheelHeight/2, wheelWidth, wheelHeight); // Draw the wheel rectangle
        gc.restore();

        // Right wheel
        gc.save();
        gc.translate(rightWheelX, rightWheelY); // Translate to wheel position
        gc.rotate(Math.toDegrees(direction)); // Rotate to the direction of the robot
        gc.fillRect(-wheelWidth/2, -wheelHeight/2, wheelWidth, wheelHeight); // Draw the wheel rectangle
        gc.restore();
    }
}
