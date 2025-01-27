package ok;

import java.util.List;

/**
 * ObstacleSensor is a sensor used by a robot to detect nearby obstacles within a given range.
 * It implements the Sensor interface and provides the logic to detect objects in the robot's environment.
 */
public class ObstacleSensor implements Sensor {
    private Robot robot;          // The robot that owns this sensor
    private double detectionRange; // The range within which the sensor can detect obstacles

    /**
     * Constructor for ObstacleSensor.
     *
     * @param robot The robot that owns this sensor.
     * @param detectionRange The detection range of the sensor.
     */
    public ObstacleSensor(Robot robot, double detectionRange) {
        this.robot = robot;
        this.detectionRange = detectionRange;
    }

    /**
     * Detects if any object in the given list of items is within the detection range of the sensor.
     *
     * @param objects The list of items to check for obstacles.
     * @return true if an obstacle is detected within the range, false otherwise.
     */
    @Override
    public boolean detect(List<Item> objects) {
        // Loop through all the objects to check for proximity to the robot
        for (Item obj : objects) {
            // Skip the robot itself
            if (obj != robot) {
                // Calculate the distance between the robot and the object
                double distance = Math.sqrt(
                        Math.pow(robot.x - obj.x, 2) + // Difference in x coordinates
                                Math.pow(robot.y - obj.y, 2) // Difference in y coordinates
                );

                // If the distance is less than the detection range, return true (obstacle detected)
                if (distance < detectionRange) {
                    return true;
                }
            }
        }

        // If no obstacle was detected within the range, return false
        return false;
    }
}
