package ok;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A SwarmRobot represents a robot that moves in a group, following basic swarm behaviors.
 * It tries to stay within a group while avoiding too close proximity to other robots.
 */
public class SwarmRobot extends SimpleRobot {
    private static final double SWARM_RADIUS = 100;  // Maximum distance within which robots will swarm
    private static final double SEPARATION_DISTANCE = 40;  // Minimum distance to maintain between robots

    /**
     * Constructor for SwarmRobot.
     * Initializes the robot with the specified position and inherits behavior from SimpleRobot.
     *
     * @param x The x-coordinate of the robot's initial position.
     * @param y The y-coordinate of the robot's initial position.
     */
    public SwarmRobot(double x, double y) {
        super(x, y);  // Call the constructor of the parent SimpleRobot
    }

    /**
     * Moves the robot by calculating swarm behavior: cohesion and separation.
     * Robots will try to move towards the average position of nearby robots
     * while avoiding being too close to them.
     */
    @Override
    public void move() {
        // Find all nearby SwarmRobot objects within the defined swarm radius
        List<SwarmRobot> nearbyRobots = Arena.getInstance().getObjects().stream()
                .filter(obj -> obj instanceof SwarmRobot && obj != this)  // Filter for other SwarmRobots
                .map(obj -> (SwarmRobot) obj)
                .filter(robot -> distance(robot) < SWARM_RADIUS)  // Check if within swarm radius
                .collect(Collectors.toList());

        if (!nearbyRobots.isEmpty()) {
            // Calculate the average position of nearby robots (cohesion)
            double avgX = nearbyRobots.stream().mapToDouble(r -> r.x).average().getAsDouble();
            double avgY = nearbyRobots.stream().mapToDouble(r -> r.y).average().getAsDouble();

            // Calculate separation force if robots are too close (avoid collision)
            double dx = 0, dy = 0;
            for (SwarmRobot other : nearbyRobots) {
                if (distance(other) < SEPARATION_DISTANCE) {
                    dx += x - other.x;  // Move away in the x-direction
                    dy += y - other.y;  // Move away in the y-direction
                }
            }

            // Combine the cohesion (move towards average position) and separation (avoid others)
            direction = Math.atan2(
                    (avgY - y) * 0.5 + dy,  // Cohesion factor with separation correction
                    (avgX - x) * 0.5 + dx   // Cohesion factor with separation correction
            );
        }

        // Handle wall collisions before moving
        handleWallCollision(x + currentSpeed * Math.cos(direction),
                y + currentSpeed * Math.sin(direction));

        // Update the robot's position based on its current direction and speed
        x += currentSpeed * Math.cos(direction);
        y += currentSpeed * Math.sin(direction);
    }

    /**
     * Calculates the distance between this robot and another SwarmRobot.
     *
     * @param other The other SwarmRobot to calculate the distance to.
     * @return The Euclidean distance between the two robots.
     */
    private double distance(SwarmRobot other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }

    /**
     * Draws the SwarmRobot on the canvas, including its swarm radius.
     *
     * @param gc The GraphicsContext to render the robot.
     */
    @Override
    protected void drawRobot(GraphicsContext gc) {
        // Set the color for the robot's body and draw it
        gc.setFill(Color.LIGHTGREEN);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw the robot's wheels (inherits from SimpleRobot)
        drawWheels(gc);

        // Draw the swarm radius around the robot
        gc.setStroke(Color.LIGHTGREEN.deriveColor(1, 1, 1, 0.3));  // Semi-transparent color
        gc.strokeOval(x - SWARM_RADIUS, y - SWARM_RADIUS, SWARM_RADIUS * 2, SWARM_RADIUS * 2);  // Draw the swarm radius
    }
}
