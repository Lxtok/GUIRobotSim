package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * PathfinderRobot is a subclass of SimpleRobot that follows a predefined patrol path.
 * The robot moves between a series of waypoints and loops through them in a rectangular patrol path.
 */
public class PathfinderRobot extends SimpleRobot {
    private List<Point2D> waypoints; // List of waypoints to follow
    private int currentWaypoint = 0;  // Index of the current waypoint the robot is heading to
    private static final double WAYPOINT_RADIUS = 5;  // Radius for visualizing waypoints
    private static final double ARRIVAL_THRESHOLD = 10; // Distance threshold to consider arrival at a waypoint

    /**
     * Constructor for PathfinderRobot, initializing the waypoints to form a rectangular patrol path.
     *
     * @param x The initial x-coordinate of the robot.
     * @param y The initial y-coordinate of the robot.
     */
    public PathfinderRobot(double x, double y) {
        super(x, y);
        waypoints = new ArrayList<>();
        // Create a rectangular patrol path with four waypoints
        waypoints.add(new Point2D(100, 100));  // Top-left corner
        waypoints.add(new Point2D(700, 100));  // Top-right corner
        waypoints.add(new Point2D(700, 500));  // Bottom-right corner
        waypoints.add(new Point2D(100, 500));  // Bottom-left corner
    }

    /**
     * Moves the robot towards the current waypoint. Once the robot reaches the waypoint,
     * it moves to the next waypoint in the list.
     */
    @Override
    public void move() {
        if (waypoints.isEmpty()) return; // No waypoints to follow

        Point2D target = waypoints.get(currentWaypoint);
        // Calculate the difference in position (dx, dy) from the robot to the target waypoint
        double dx = target.x - x;
        double dy = target.y - y;
        double distanceToTarget = Math.sqrt(dx * dx + dy * dy);

        // If the robot is close enough to the target, switch to the next waypoint
        if (distanceToTarget < ARRIVAL_THRESHOLD) {
            currentWaypoint = (currentWaypoint + 1) % waypoints.size(); // Loop back to the first waypoint after the last one
        } else {
            // Calculate direction and move the robot towards the target
            direction = Math.atan2(dy, dx); // Calculate the angle towards the target
            x += currentSpeed * Math.cos(direction); // Update x position based on speed and direction
            y += currentSpeed * Math.sin(direction); // Update y position based on speed and direction
        }
    }

    /**
     * Draws the robot and its patrol path on the provided GraphicsContext.
     * It visualizes the waypoints and the lines connecting them.
     *
     * @param gc The GraphicsContext used to draw on the canvas.
     */
    @Override
    protected void drawRobot(GraphicsContext gc) {
        // Draw the base of the robot as an orange circle
        gc.setFill(Color.ORANGE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        drawWheels(gc); // Draw the robot's wheels (defined in SimpleRobot)

        // Draw waypoints and the path between them
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);

        // Loop through the waypoints and draw them, as well as the path between them
        for (int i = 0; i < waypoints.size(); i++) {
            Point2D wp = waypoints.get(i);  // Current waypoint
            Point2D nextWp = waypoints.get((i + 1) % waypoints.size()); // Next waypoint (loop back to first waypoint)

            // Draw the current waypoint as a small circle
            gc.fillOval(wp.x - WAYPOINT_RADIUS, wp.y - WAYPOINT_RADIUS,
                    WAYPOINT_RADIUS * 2, WAYPOINT_RADIUS * 2);

            // Draw a line from the current waypoint to the next waypoint
            gc.strokeLine(wp.x, wp.y, nextWp.x, nextWp.y);
        }
    }

    /**
     * Inner class representing a 2D point with x and y coordinates.
     * Used to define waypoints for the robot's patrol path.
     */
    private static class Point2D {
        double x, y; // x and y coordinates of the point

        // Constructor to initialize the point
        Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
