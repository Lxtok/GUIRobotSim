package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * The MagnetRobot class represents a robot with a magnetic ability to attract or repel nearby magnetic objects,
 * such as other robots or metal walls. It uses a magnetic sensor to detect these objects within a defined range.
 */
public class MagnetRobot extends SimpleRobot {
    private static final double MAGNETIC_RANGE = 150; // Magnetic range in units
    private static int nextIndex = 1; // Static counter to assign unique indexes to robots
    private int index; // Unique identifier for this robot
    private boolean isAttract = true; // Determines whether the robot is attracting or repelling
    private Random random = new Random(); // Random instance for toggling attract/repel mode

    /**
     * Constructs a MagnetRobot at the specified coordinates and initializes its magnetic sensor.
     *
     * @param x The x-coordinate of the robot's initial position.
     * @param y The y-coordinate of the robot's initial position.
     */
    public MagnetRobot(double x, double y) {
        super(x, y);
        addSensor(new MagneticSensor(this, MAGNETIC_RANGE));
        index = nextIndex++;
    }

    /**
     * Moves the robot based on its magnetic behavior. The robot attracts or repels
     * the nearest magnetic object, adjusting its direction accordingly.
     */
    @Override
    public void move() {
        updateSpeed();

        // Randomly toggle between attract and repel with a small probability
        if (random.nextDouble() < 0.01) {
            isAttract = !isAttract;
        }

        // Find the nearest magnetic object (Robot or MetalWall)
        Item nearestMagnetic = findNearestMagnetic();
        if (nearestMagnetic != null) {
            // Calculate the angle to the target object
            double angleToTarget = Math.atan2(nearestMagnetic.y - y, nearestMagnetic.x - x);
            // Adjust direction based on attract/repel mode
            direction = isAttract ? angleToTarget : angleToTarget + Math.PI;
        }

        // Handle collisions with walls
        handleWallCollision(x + currentSpeed * Math.cos(direction),
                y + currentSpeed * Math.sin(direction));

        // Update position based on current speed and direction
        x += currentSpeed * Math.cos(direction);
        y += currentSpeed * Math.sin(direction);
    }

    /**
     * Finds the nearest magnetic object (Robot or MetalWall) within the magnetic range.
     *
     * @return The nearest magnetic object, or null if none are within range.
     */
    private Item findNearestMagnetic() {
        Item nearest = null;
        double minDistance = MAGNETIC_RANGE;

        // Iterate through all objects in the arena
        for (Item item : Arena.getInstance().getObjects()) {
            // Check if the item is a magnetic object (Robot or MetalWall) and is not this robot
            if ((item instanceof Robot || item instanceof MetalWall) && item != this) {
                // Calculate the distance to the object
                double distance = Math.sqrt(
                        Math.pow(x - item.x, 2) + Math.pow(y - item.y, 2)
                );
                // Update the nearest object if within range and closer than the current nearest
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = item;
                }
            }
        }
        return nearest;
    }

    /**
     * Draws the robot on the canvas with a visual representation of its magnetic range
     * and its current mode (attract/repel).
     *
     * @param gc The GraphicsContext object used for drawing.
     */
    @Override
    protected void drawRobot(GraphicsContext gc) {
        // Draw the robot's body
        gc.setFill(Color.PURPLE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw the robot's wheels
        drawWheels(gc);

        // Draw the magnetic range with a color indicating the mode
        gc.setStroke(isAttract ? Color.RED : Color.BLUE);
        gc.setLineWidth(1);
        gc.strokeOval(x - MAGNETIC_RANGE, y - MAGNETIC_RANGE,
                MAGNETIC_RANGE * 2, MAGNETIC_RANGE * 2);
    }
}
