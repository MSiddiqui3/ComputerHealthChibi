import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class SystemInfoUI extends Application {

    // Labels to display information
    Label gpuLabel = new Label();
    Label ramLabel = new Label();
    Button onButton = new Button("On");
    Button offButton = new Button("Off");

    @Override
    public void start(Stage primaryStage) {
        // Initial text for labels
        gpuLabel.setText(getGpuInfo());
        ramLabel.setText(getRamInfo());

        // Set styles for buttons
        onButton.getStyleClass().add("toggle-button");
        offButton.getStyleClass().add("toggle-button");

        // Add button functionality
        onButton.setOnAction(event -> {
            gpuLabel.setText(getGpuInfo());
            ramLabel.setText(getRamInfo());
        });

        offButton.setOnAction(event -> {
            gpuLabel.setText("System Monitoring Off");
            ramLabel.setText("System Monitoring Off");
        });

        // Set up the layout
        VBox layout = new VBox(10); // Vertical layout with spacing
        layout.getStyleClass().add("pane");
        layout.getChildren().addAll(gpuLabel, ramLabel, onButton, offButton);

        // Create the scene and apply styles
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("System Info UI");
        primaryStage.show();

        // Set up real-time updates (refresh every 5 seconds)
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            if (onButton.isPressed()) {
                gpuLabel.setText(getGpuInfo());
                ramLabel.setText(getRamInfo());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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

    public static void main(String[] args) {
        launch(args);
    }
}
