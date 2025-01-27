package ok;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * MenuSetup class is responsible for creating and managing the menu bar for the RobotSimulationApp.
 * It includes functionality for saving and loading configurations, as well as displaying help and about dialogs.
 */
public class MenuSetup {
    private RobotSimulationApp app;
    private Stage stage;

    /**
     * Constructor for MenuSetup. Initializes the app and stage for use in menu operations.
     *
     * @param app The RobotSimulationApp instance.
     * @param stage The Stage for the application.
     */
    public MenuSetup(RobotSimulationApp app, Stage stage) {
        this.app = app;
        this.stage = stage;
    }

    /**
     * Creates the menu bar for the application with File and Help menus.
     *
     * @return The constructed MenuBar.
     */
    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem saveItem = new MenuItem("Save Configuration");
        MenuItem loadItem = new MenuItem("Load Configuration");
        MenuItem exitItem = new MenuItem("Exit");

        // File Menu Actions
        saveItem.setOnAction(e -> saveConfiguration());
        loadItem.setOnAction(e -> loadConfiguration());
        exitItem.setOnAction(e -> System.exit(0)); // Exits the application

        fileMenu.getItems().addAll(saveItem, loadItem, new SeparatorMenuItem(), exitItem);

        // Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        MenuItem helpItem = new MenuItem("Help");

        // Help Menu Actions
        aboutItem.setOnAction(e -> showAboutDialog());
        helpItem.setOnAction(e -> showHelpDialog());

        helpMenu.getItems().addAll(aboutItem, helpItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    /**
     * Saves the current configuration of robots to a JSON file.
     */
    private void saveConfiguration() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Robot Configuration");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json")
        );

        // Show file save dialog and get selected file
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                // Use Jackson to serialize the objects into JSON and save them
                ObjectMapper mapper = new ObjectMapper();
                List<Item> items = Arena.getInstance().getObjects();
                mapper.writeValue(file, items);
            } catch (IOException ex) {
                // Show error dialog if saving fails
                showError("Error saving configuration: " + ex.getMessage());
            }
        }
    }

    /**
     * Loads a configuration of robots from a JSON file.
     */
    private void loadConfiguration() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Robot Configuration");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json")
        );

        // Show file open dialog and get selected file
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                // Use Jackson to deserialize the JSON into a list of items
                ObjectMapper mapper = new ObjectMapper();
                List<Item> items = mapper.readValue(file,
                        new TypeReference<List<Item>>() {});
                Arena.getInstance().getObjects().clear(); // Clear the existing items
                Arena.getInstance().getObjects().addAll(items); // Load the new items
            } catch (IOException ex) {
                // Show error dialog if loading fails
                showError("Error loading configuration: " + ex.getMessage());
            }
        }
    }

    /**
     * Displays an About dialog with information about the simulation.
     */
    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Robot Simulation");
        alert.setHeaderText("Robot Simulation v1.0");
        alert.setContentText("A simple robot simulation demonstrating different types of robots " +
                "and their behaviors. Created for educational purposes.");
        alert.showAndWait();
    }

    /**
     * Displays a Help dialog with information on robot types and controls.
     */
    private void showHelpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Robot Simulation Help");
        alert.setContentText(
                "Robot Types:\n\n" +
                        "- Simple Robot: Basic movement and obstacle avoidance\n" +
                        "- Whisker Robot: Uses whiskers to detect obstacles\n" +
                        "- Echo Robot: Uses echo location to detect objects\n" +
                        "- Light Detector: Follows light sources\n" +
                        "- Magnet Robot: Attracted to or repelled by other robots\n" +
                        "- Pathfinder Robot: Follows preset patrol waypoints\n" +
                        "- Swarm Robot: Forms groups with nearby robots\n\n" +
                        "Controls:\n" +
                        "- Use buttons to add/remove robots\n" +
                        "- Pause/Resume to control simulation\n" +
                        "- Save/Load to manage configurations"
        );
        alert.showAndWait();
    }

    /**
     * Displays an error dialog with a given message.
     *
     * @param message The error message to be displayed.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
