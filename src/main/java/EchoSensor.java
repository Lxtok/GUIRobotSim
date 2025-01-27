package ok;

import java.util.List;

/**
 * The EchoSensor is responsible for detecting nearby objects within a specified range and beam angle.
 * It is attached to a Robot and uses its position and direction to perform object detection.
 */
public class EchoSensor implements Sensor {
    private Robot robot; // The robot to which this sensor is attached
    private double range; // The maximum detection range of the sensor
    private static final double BEAM_ANGLE = Math.PI / 3; // The detection beam angle (60 degrees)

    /**
     * Constructs an EchoSensor for a given robot and detection range.
     *
     * @param robot the robot to attach the sensor to
     * @param range the maximum range of the sensor
     */
    public EchoSensor(Robot robot, double range) {
        this.robot = robot;
        this.range = range;
    }

    /**
     * Detects if any objects are within the sensor's range and beam angle.
     *
     * @param objects the list of objects to check for detection
     * @return true if an object is detected within range and beam angle, false otherwise
     */
    @Override
    public boolean detect(List<Item> objects) {
        for (Item obj : objects) {
            if (obj != robot) { // Ignore the robot itself
                // Calculate the distance between the robot and the object
                double distance = Math.sqrt(
                        Math.pow(robot.x - obj.x, 2) +
                                Math.pow(robot.y - obj.y, 2)
                );

                if (distance < range) { // Check if the object is within the sensor's range
                    // Calculate the angle to the object relative to the robot's direction
                    double angle = Math.atan2(obj.y - robot.y, obj.x - robot.x);
                    double angleDiff = Math.abs(angle - robot.direction);

                    // Check if the object is within the beam angle
                    if (angleDiff < BEAM_ANGLE / 2 || angleDiff > 2 * Math.PI - BEAM_ANGLE / 2) {
                        return true; // Object detected
                    }
                }
            }
        }
        return false; // No objects detected
    }
}
