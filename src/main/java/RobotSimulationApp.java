package ok;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.Random;

/**
 * Main application class for the robot simulation.
 * It initializes the simulation environment, controls, and handles animation.
 */
public class RobotSimulationApp extends Application {
    private static final double ARENA_WIDTH = 800;   // Width of the arena
    private static final double ARENA_HEIGHT = 600;  // Height of the arena
    private Canvas canvas;   // Canvas where the simulation will be drawn
    private GraphicsContext gc;  // Graphics context for drawing on the canvas
    private AnimationTimer animator;   // Animator to continuously update the scene
    private boolean isPaused = false;   // Flag to pause or resume the simulation
    private Random random = new Random();  // Random object to generate random values
    private BorderPane root;   // Root layout for the scene
    private InfoPanel infoPanel;  // Info panel to show statistics about the simulation

    /**
     * Initializes the application, sets up the layout, controls, and starts the animation.
     *
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();  // Create the root layout
        MenuSetup menuSetup = new MenuSetup(this, primaryStage);  // Create menu setup for the simulation
        root.setTop(menuSetup.createMenuBar());  // Add menu to the top of the layout

        canvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);  // Create a canvas for drawing the simulation
        gc = canvas.getGraphicsContext2D();  // Get the graphics context for drawing

        infoPanel = new InfoPanel();  // Initialize the info panel
        root.setRight(infoPanel);  // Add the info panel to the right side of the layout

        // Add obstacles to the arena
        Arena.getInstance().addObject(new Obstacle(100, 100));
        Arena.getInstance().addObject(new Obstacle(300, 400));
        Arena.getInstance().addObject(new Obstacle(600, 200));

        // Add metal walls to the arena
        Arena.getInstance().addObject(new MetalWall(200, 300));
        Arena.getInstance().addObject(new MetalWall(500, 150));

        createControls();  // Set up the control buttons
        setupAnimator();   // Set up the animation timer

        root.setCenter(canvas);  // Set the canvas as the center element in the layout

        Scene scene = new Scene(root);  // Create the scene with the root layout
        primaryStage.setTitle("Robot Simulation");  // Set the title of the window
        primaryStage.setScene(scene);  // Set the scene to the primary stage
        primaryStage.show();  // Display the stage
    }

    /**
     * Sets up the animation timer to continuously update and render the simulation.
     */
    private void setupAnimator() {
        animator = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isPaused) {
                    gc.clearRect(0, 0, ARENA_WIDTH, ARENA_HEIGHT);  // Clear the canvas
                    Arena.getInstance().update();  // Update the state of all objects in the arena
                    for (Item obj : Arena.getInstance().getObjects()) {
                        obj.render(gc);  // Render each object in the arena
                    }
                    infoPanel.update(Arena.getInstance());  // Update the info panel with current data
                }
            }
        };
        animator.start();  // Start the animation timer
    }

    /**
     * Creates the control buttons and sets their actions.
     */
    private void createControls() {
        Button pauseResumeBtn = new Button("Pause");
        pauseResumeBtn.setOnAction(e -> {
            isPaused = !isPaused;  // Toggle pause/resume state
            pauseResumeBtn.setText(isPaused ? "Resume" : "Pause");  // Update button text accordingly
        });

        // Create buttons to add various types of robots to the arena
        Button addSimpleRobotBtn = new Button("Add Simple Robot");
        Button addWhiskerRobotBtn = new Button("Add Whisker Robot");
        Button addEchoRobotBtn = new Button("Add Echo Robot");
        Button addLightDetectorBtn = new Button("Add Light Detector");
        Button addMagnetRobotBtn = new Button("Add Magnet Robot");
        Button addPathfinderRobotBtn = new Button("Add Pathfinder Robot");
        Button addSwarmRobotBtn = new Button("Add Swarm Robot");
        Button removeRobotBtn = new Button("Remove Robot");

        // Set button actions to add robots to the arena
        addSimpleRobotBtn.setOnAction(e -> addRobot(RobotType.SIMPLE));
        addWhiskerRobotBtn.setOnAction(e -> addRobot(RobotType.WHISKER));
        addEchoRobotBtn.setOnAction(e -> addRobot(RobotType.ECHO));
        addLightDetectorBtn.setOnAction(e -> addRobot(RobotType.LIGHT_DETECTOR));
        addMagnetRobotBtn.setOnAction(e -> addRobot(RobotType.MAGNET));
        addPathfinderRobotBtn.setOnAction(e -> addRobot(RobotType.PATHFINDER));
        addSwarmRobotBtn.setOnAction(e -> addRobot(RobotType.SWARM));
        removeRobotBtn.setOnAction(e -> removeRandomRobot());  // Remove a random robot

        // Create a horizontal box to hold the control buttons
        HBox controls = new HBox(10,
                pauseResumeBtn,
                addSimpleRobotBtn,
                addWhiskerRobotBtn,
                addEchoRobotBtn,
                addLightDetectorBtn,
                addMagnetRobotBtn,
                addPathfinderRobotBtn,
                addSwarmRobotBtn,
                removeRobotBtn
        );
        controls.setPadding(new Insets(10));  // Set padding for the controls
        controls.setAlignment(Pos.CENTER);  // Align buttons to the center

        root.setBottom(controls);  // Set the controls at the bottom of the layout
    }

    /**
     * Enum to define the types of robots that can be added to the arena.
     */
    private enum RobotType {
        SIMPLE, WHISKER, ECHO, LIGHT_DETECTOR, MAGNET, PATHFINDER, SWARM
    }

    /**
     * Adds a robot of the specified type to the arena at a random position.
     * It ensures the robot does not overlap with any existing obstacles.
     *
     * @param type The type of robot to be added.
     */
    private void addRobot(RobotType type) {
        double x, y;
        boolean validPosition;

        // Try placing the robot until a valid position is found
        do {
            validPosition = true;
            x = random.nextDouble(ARENA_WIDTH);  // Random x-coordinate
            y = random.nextDouble(ARENA_HEIGHT);  // Random y-coordinate

            // Check if the position overlaps with any obstacles
            for (Item obj : Arena.getInstance().getObjects()) {
                if (obj instanceof Obstacle) {
                    double distance = Math.sqrt(
                            Math.pow(x - obj.x, 2) +
                                    Math.pow(y - obj.y, 2)
                    );
                    if (distance < obj.radius + 40) {  // If too close to an obstacle
                        validPosition = false;
                        break;
                    }
                }
            }
        } while (!validPosition);  // Repeat until a valid position is found

        // Create the robot based on the selected type
        Robot robot = switch(type) {
            case SIMPLE -> new SimpleRobot(x, y);
            case WHISKER -> new WhiskerRobot(x, y);
            case ECHO -> new EchoRobot(x, y);
            case LIGHT_DETECTOR -> new LightDetectorRobot(x, y);
            case MAGNET -> new MagnetRobot(x, y);
            case PATHFINDER -> new PathfinderRobot(x, y);
            case SWARM -> new SwarmRobot(x, y);
        };

        // Add the robot to the arena
        Arena.getInstance().addObject(robot);
    }

    /**
     * Removes a random robot from the arena.
     * If no robots are present, no action is performed.
     */
    private void removeRandomRobot() {
        var objects = Arena.getInstance().getObjects();
        if (!objects.isEmpty()) {
            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i) instanceof Robot) {
                    objects.remove(i);  // Remove the first robot found
                    break;
                }
            }
        }
    }

    /**
     * Main entry point for launching the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
