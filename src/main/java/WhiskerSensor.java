package ok;

import java.util.List;

/**
 * The WhiskerSensor class implements the Sensor interface and simulates a whisker sensor for a robot.
 * The whisker sensor detects obstacles within a certain distance (defined by the length of the whisker).
 */
public class WhiskerSensor implements Sensor {
    private Robot robot;  // The robot that this whisker sensor is attached to
    private double length;  // The length of the whisker sensor

    /**
     * Constructor for WhiskerSensor.
     * Initializes the sensor with the robot it belongs to and the whisker's length.
     *
     * @param robot The robot that the sensor is attached to.
     * @param length The length of the whisker sensor.
     */
    public WhiskerSensor(Robot robot, double length) {
        this.robot = robot;  // Set the robot
        this.length = length;  // Set the whisker's length
    }

    /**
     * Detects obstacles in the robot's environment by calculating the distance between the robot and each object.
     * If an object is within the range of the whisker, it returns true.
     *
     * @param objects The list of objects (including other robots or obstacles) to check against.
     * @return true if an object is detected within the whisker's range, otherwise false.
     */
    @Override
    public boolean detect(List<Item> objects) {
        for (Item obj : objects) {
            // Ignore the robot itself while checking for obstacles
            if (obj != robot) {
                // Calculate the distance between the robot and the object
                double distance = Math.sqrt(
                        Math.pow(robot.x - obj.x, 2) +
                                Math.pow(robot.y - obj.y, 2)
                );

                // If the distance is less than the whisker's length plus the object's radius, it's detected
                if (distance < length + obj.radius) {
                    return true;
                }
            }
        }
        // If no object is detected within range, return false
        return false;
    }
}
