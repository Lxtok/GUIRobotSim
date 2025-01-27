package ok;

import java.util.List;

/**
 * Represents a sensor attached to a robot that can detect light sources within a specified range.
 */
public class LightSensor implements Sensor {
    private Robot robot; // The robot to which this sensor is attached
    private double range; // The detection range of the sensor

    /**
     * Constructs a LightSensor with a specified robot and detection range.
     *
     * @param robot The robot to which the sensor is attached.
     * @param range The detection range of the sensor in units.
     */
    public LightSensor(Robot robot, double range) {
        this.robot = robot;
        this.range = range;
    }

    /**
     * Detects if there are any light sources within the sensor's range.
     *
     * @param objects The list of items in the arena to check for light sources.
     * @return True if a light source is detected within range, false otherwise.
     */
    @Override
    public boolean detect(List<Item> objects) {
        // Iterate through all objects in the arena
        for (Item obj : objects) {
            // Check if the object is a light source
            if (obj instanceof Light) {
                // Calculate the distance between the robot and the light source
                double distance = Math.sqrt(
                        Math.pow(robot.x - obj.x, 2) + // Difference in x-coordinates
                                Math.pow(robot.y - obj.y, 2)   // Difference in y-coordinates
                );
                // Return true if the light source is within the sensor's range
                if (distance < range) {
                    return true;
                }
            }
        }
        // No light source detected within range
        return false;
    }
}
