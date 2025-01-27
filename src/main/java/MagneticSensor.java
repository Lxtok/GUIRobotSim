package ok;

import java.util.List;

/**
 * Represents a magnetic sensor attached to a robot that detects nearby robots within a specified range.
 */
public class MagneticSensor implements Sensor {
    private Robot robot; // The robot to which this sensor is attached
    private double range; // The detection range of the sensor

    /**
     * Constructs a MagneticSensor with a specified robot and detection range.
     *
     * @param robot The robot to which the sensor is attached.
     * @param range The detection range of the sensor in units.
     */
    public MagneticSensor(Robot robot, double range) {
        this.robot = robot;
        this.range = range;
    }

    /**
     * Detects if there are other robots within the sensor's range, excluding the robot to which the sensor is attached.
     *
     * @param objects The list of items in the arena to check for nearby robots.
     * @return True if another robot is detected within range, false otherwise.
     */
    @Override
    public boolean detect(List<Item> objects) {
        // Iterate through all items in the arena
        for (Item obj : objects) {
            // Check if the item is a robot and is not the robot to which this sensor is attached
            if (obj instanceof Robot && obj != robot) {
                // Calculate the distance between the sensor's robot and the other robot
                double distance = Math.sqrt(
                        Math.pow(robot.x - obj.x, 2) + // Difference in x-coordinates
                                Math.pow(robot.y - obj.y, 2)   // Difference in y-coordinates
                );
                // Return true if the other robot is within the sensor's range
                if (distance < range) {
                    return true;
                }
            }
        }
        // No robot detected within range
        return false;
    }
}
