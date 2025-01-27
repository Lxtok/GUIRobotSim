package ok;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

/**
 * The InfoPanel class provides a UI component to display information about the robots in the arena.
 * It extends VBox to organize its elements vertically.
 */
public class InfoPanel extends VBox {
    private Label infoLabel; // Label to display information about robots

    /**
     * Constructs an InfoPanel with a predefined style and layout.
     */
    public InfoPanel() {
        // Align content to the top-right
        setAlignment(Pos.TOP_RIGHT);
        // Add padding around the panel
        setPadding(new Insets(10));
        // Set spacing between elements
        setSpacing(5);
        // Set background color and border
        setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-border-color: black;");
        // Set minimum width for the panel
        setMinWidth(200);

        // Initialize the label to display robot information
        infoLabel = new Label();
        // Use a monospace font for consistent alignment
        infoLabel.setStyle("-fx-font-family: monospace;");
        // Add the label to the panel
        getChildren().add(infoLabel);
    }

    /**
     * Updates the information displayed on the panel based on the current state of the arena.
     *
     * @param arena the Arena object containing robots and other items
     */
    public void update(Arena arena) {
        StringBuilder info = new StringBuilder(); // Builder for assembling info text

        // Iterate through all items in the arena
        for (Item item : arena.getObjects()) {
            // Check if the item is a robot
            if (item instanceof Robot robot) {
                // Append robot details to the info string
                info.append(String.format("%s\n", robot.getClass().getSimpleName())); // Robot type
                info.append(String.format("Speed: %.2f\n", robot.getCurrentSpeed())); // Current speed
                info.append(String.format("Angle: %.2f°\n", Math.toDegrees(robot.getDirection()))); // Direction in degrees
                info.append(String.format("Location: (%.0f, %.0f)\n\n", robot.getX(), robot.getY())); // Location coordinates
            }
        }

        // Update the label text with the assembled info
        infoLabel.setText(info.toString());
    }
}
