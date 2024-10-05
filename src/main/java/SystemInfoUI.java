import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.List;

public class SystemInfoUI extends Application {

    // Labels to display system information
    Label gpuLabel = new Label();
    Label ramLabel = new Label();
    Label cpuLabel = new Label("CPU Usage:");

    // ImageView to display images at the bottom
    ImageView imageView = new ImageView();

    // Timeline for real-time monitoring
    Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        // Initial text for system information labels
        gpuLabel.setText(getGpuInfo());
        ramLabel.setText(getRamInfo());
        cpuLabel.setText(getCpuUsage());

        // On/Off buttons for system monitoring
        Button onButton = new Button("On");
        Button offButton = new Button("Off");

        onButton.setOnAction(event -> {
            gpuLabel.setText(getGpuInfo());
            ramLabel.setText(getRamInfo());
            cpuLabel.setText(getCpuUsage());
            timeline.play(); // Resume monitoring
        });

        offButton.setOnAction(event -> {
            gpuLabel.setText("System Monitoring Off");
            ramLabel.setText("System Monitoring Off");
            cpuLabel.setText("System Monitoring Off");
            timeline.pause(); // Pause monitoring
        });

        // Set default image when the application starts
        imageView.setImage(new Image(getClass().getResource("/images/chibi1.png").toExternalForm()));
        imageView.setFitHeight(150); // Set height for the image
        imageView.setPreserveRatio(true); // Maintain aspect ratio

        // Add the animation for swaying from side to side
        addSwayingAnimation(imageView);

        // Menu Bar and Menu Items
        MenuBar menuBar = new MenuBar();

        // File menu with exit option
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenuItem);

        // Edit menu for future avatar customization
        Menu editMenu = new Menu("Edit");
        MenuItem customizeChibiMenuItem = new MenuItem("Customize Chibi");
        editMenu.getItems().add(customizeChibiMenuItem);

        // Add action to the Customize Chibi option
        customizeChibiMenuItem.setOnAction(event -> openChibiSelectionDialog());

        // Help menu with about option
        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        helpMenu.getItems().add(aboutMenuItem);

        // Add menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        // Vertical layout for system info and buttons
        VBox layout = new VBox(10);
        layout.getChildren().addAll(gpuLabel, ramLabel, cpuLabel, onButton, offButton);

        // Add the ImageView at the bottom
        layout.getChildren().add(imageView);

        // BorderPane layout for MenuBar at the top and the VBox layout in the center
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar); // Set the menu bar at the top
        borderPane.setCenter(layout); // Set the system information in the center

        // Create the scene
        Scene scene = new Scene(borderPane, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("System Info with Menu and Image");
        primaryStage.show();

        // Real-time updates for system information every 5 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            gpuLabel.setText(getGpuInfo());
            ramLabel.setText(getRamInfo());
            cpuLabel.setText(getCpuUsage());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();  // Start the timeline when the application launches
    }

    // Method to add swaying animation to the chibi image
    private void addSwayingAnimation(ImageView imageView) {
        // Create a TranslateTransition for horizontal swaying
        TranslateTransition sway = new TranslateTransition(Duration.millis(1000), imageView);
        sway.setFromX(-20);  // Move 20 pixels left
        sway.setToX(20);     // Move 20 pixels right
        sway.setAutoReverse(true); // Automatically reverse after each cycle
        sway.setCycleCount(TranslateTransition.INDEFINITE); // Repeat indefinitely
        sway.play();  // Start the animation
    }

    // Method to open a dialog for chibi selection
    private void openChibiSelectionDialog() {
        // Create a dialog for selecting chibi avatars
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Customize Chibi");

        // Create radio buttons for the three chibi images
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton chibi1Option = new RadioButton("Chibi 1");
        RadioButton chibi2Option = new RadioButton("Chibi 2");
        RadioButton chibi3Option = new RadioButton("Chibi 3");

        chibi1Option.setToggleGroup(toggleGroup);
        chibi2Option.setToggleGroup(toggleGroup);
        chibi3Option.setToggleGroup(toggleGroup);

        // Set the initial selection to Chibi 1
        chibi1Option.setSelected(true);

        // VBox to hold the radio buttons
        VBox dialogContent = new VBox(10, chibi1Option, chibi2Option, chibi3Option);
        dialog.getDialogPane().setContent(dialogContent);

        // Add OK and Cancel buttons
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Show the dialog and wait for the result
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Check which option was selected and change the image
                if (chibi1Option.isSelected()) {
                    imageView.setImage(new Image(getClass().getResource("/images/chibi1.png").toExternalForm()));
                } else if (chibi2Option.isSelected()) {
                    imageView.setImage(new Image(getClass().getResource("/images/chibi2.png").toExternalForm()));
                } else if (chibi3Option.isSelected()) {
                    imageView.setImage(new Image(getClass().getResource("/images/chibi3.png").toExternalForm()));
                }
            }
        });
    }

    // Method to get GPU info using OSHI
    public String getGpuInfo() {
        SystemInfo systemInfo = new SystemInfo();
        List<GraphicsCard> gpus = systemInfo.getHardware().getGraphicsCards();
        StringBuilder gpuInfo = new StringBuilder("GPU Information:\n");
        for (GraphicsCard gpu : gpus) {
            gpuInfo.append("GPU Name: ").append(gpu.getName()).append("\n");
            gpuInfo.append("VRAM: ").append(gpu.getVRam() / (1024 * 1024)).append(" MB\n");
        }
        return gpuInfo.toString();
    }

    // Method to get RAM info using OperatingSystemMXBean
    public String getRamInfo() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;

        return "RAM Information:\n" +
                "Total RAM: " + (totalMemory / (1024 * 1024)) + " MB\n" +
                "Used RAM: " + (usedMemory / (1024 * 1024)) + " MB\n" +
                "Free RAM: " + (freeMemory / (1024 * 1024)) + " MB";
    }

    // Method to get CPU usage using OperatingSystemMXBean
    public String getCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double cpuLoad = osBean.getSystemCpuLoad() * 100;

        if (cpuLoad < 0) {
            return "CPU Usage: Data not available";
        }

        return String.format("CPU Usage: %.2f%%", cpuLoad);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
