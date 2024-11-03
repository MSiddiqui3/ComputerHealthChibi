


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;

import java.util.HashMap;
import java.util.Map;

public class ChibiManager{
    private ImageView imageView;
    private Label ramStatusLabel;
    private Label cpuStatusLabel;
    private long previousFreeRam;
    private Map<String, String> chibiNormalImages = new HashMap<>();
    private Map<String, String> chibiSadImages = new HashMap<>();

    private Map<String, String> chibiHeavyImages = new HashMap<>();
    private String currentChibi = "Chibi 1";
    private Timeline ramMonitoringTimeline;
    private Timeline cpuTemperatureMonitoringTimeline;

    private static final long STABLE_THRESHOLD = 4000 * 1024 * 1024;  //base threshold for stable ram equal to 50MB

    public ChibiManager(ImageView imageView, Label ramStatusLabel, Label cpuStatusLabel) {


        this.imageView = imageView;
        this.ramStatusLabel = ramStatusLabel;
        this.previousFreeRam = RamInfo.getFreeRam();  // Initialize with the current RAM value
        this.cpuStatusLabel = cpuStatusLabel;

        // Load normal chibi images
        chibiNormalImages.put("Chibi 1", "/images/chibi1normal.gif");
        chibiNormalImages.put("Chibi 2", "/images/chibi2normal.gif");
        chibiNormalImages.put("Chibi 3", "/images/chibi3normal.gif");

        // Load sad chibi images
        chibiSadImages.put("Chibi 1", "/images/chibi1sad.gif");
        chibiSadImages.put("Chibi 2", "/images/chibi2sad.gif");
        chibiSadImages.put("Chibi 3", "/images/chibi3sad.gif");

        //Load heavyload chibi images
        chibiHeavyImages.put("Chibi 1", "/images/chibi1heavyload.gif");
        chibiHeavyImages.put("Chibi 2", "/images/chibi2heavyload.gif");
        chibiHeavyImages.put("Chibi 3", "/images/chibi3heavyload.gif");

        // Set default chibi image
        setChibiImage(chibiNormalImages.get(currentChibi));
    }
    public void setMonitoringTracker(String trackerName) {
        stopAllMonitoring();

        //DEBUGGING
        System.out.println("SWITCHED TO: " + trackerName);

        switch (trackerName) {
            case "RAM Usage":
                ramStatusLabel.setVisible(true);
                cpuStatusLabel.setVisible(false);
                startRamMonitoring();
                break;
            case "CPU":
                cpuStatusLabel.setVisible(true);
                ramStatusLabel.setVisible(false);
                startCpuTemperatureMonitoring();
                break;
            default:
                System.out.println("Invalid tracker selected");
                break;
        }
    }
    private void stopAllMonitoring() {
        if (ramMonitoringTimeline != null) ramMonitoringTimeline.stop();
        if (cpuTemperatureMonitoringTimeline != null) cpuTemperatureMonitoringTimeline.stop();
        // Stop other timelines here as well
    }

    // Method to set the chibi image
    private void setChibiImage(String imagePath) {
        try {
            Image chibiImage = new Image(getClass().getResource(imagePath).toExternalForm());
            imageView.setImage(chibiImage);
            imageView.setFitHeight(150);  // Adjust the image height
            imageView.setPreserveRatio(true);  // Keep the image aspect ratio
        } catch (Exception e) {
            System.out.println("Error loading chibi image: " + e.getMessage());
        }
    }

    // Method to start RAM monitoring and dynamically change the chibi
    public void startRamMonitoring() {
        ramMonitoringTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            long currentFreeRam = RamInfo.getFreeRam();

            // Calculate the change in RAM
            long ramDifference = Math.abs(currentFreeRam - previousFreeRam);

            // Check if RAM usage is stable, increasing, or decreasing
            if (ramDifference > STABLE_THRESHOLD) {
                setChibiImage(chibiNormalImages.get(currentChibi));  // Switch to normal chibi
                ramStatusLabel.setText("RAM usage is Stable");
            }
             else {
                setChibiImage(chibiHeavyImages.get(currentChibi));  // Switch to heavyload chibi
                ramStatusLabel.setText("RAM usage is High");
            }

            previousFreeRam = currentFreeRam;
        }));
        ramMonitoringTimeline.setCycleCount(Timeline.INDEFINITE);  // Run indefinitely
        ramMonitoringTimeline.play();  // Start the timeline
    }

    public void startCpuTemperatureMonitoring() {
        System.out.println("Starting CPU temperature monitoring");
        cpuTemperatureMonitoringTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            SystemInfo si = new SystemInfo();
            Sensors sensors = si.getHardware().getSensors();

            double temperature = sensors.getCpuTemperature();



            //FOR TESTING PURPOSES DURING DEMO
            //UNCOMMENT THE 2 BELOW AND COMMENT OUT THE 2 ORIGINAL
            //final double STABLE_TEMP_THRESHOLD = 20;
            //final double HIGH_TEMP_THRESHOLD = 40;


            final double STABLE_TEMP_THRESHOLD = 65.0;
            final double HIGH_TEMP_THRESHOLD = 82.0;

            System.out.println(temperature);
            if (temperature < STABLE_TEMP_THRESHOLD) {
                setChibiImage(chibiNormalImages.get(currentChibi));
                cpuStatusLabel.setText("CPU temperature is Stable");
            } else if (temperature >= HIGH_TEMP_THRESHOLD) {
                setChibiImage(chibiSadImages.get(currentChibi));
                cpuStatusLabel.setText("CPU temperature is High");
            } else {
                setChibiImage(chibiNormalImages.get(currentChibi));
                cpuStatusLabel.setText("CPU temperature is Moderate");
            }
        }));
        cpuTemperatureMonitoringTimeline.setCycleCount(Timeline.INDEFINITE);
        cpuTemperatureMonitoringTimeline.play();
    }

    // Method to change chibi appearance based on the selected option
    public void changeChibiAppearance(String chibiOption) {
        currentChibi = chibiOption;
        setChibiImage(chibiNormalImages.get(chibiOption));  // Update chibi to the selected one
    }

    // Method to add a swaying animation for the chibi
    public void addSwayingAnimation() {
        TranslateTransition sway = new TranslateTransition(Duration.millis(1000), imageView);
        sway.setFromX(-20);
        sway.setToX(20);
        sway.setAutoReverse(true);
        sway.setCycleCount(TranslateTransition.INDEFINITE);
        sway.play();  // Start the animation
    }

    // Method to stop the monitoring if needed
    public void stopMonitoring() {
        if (ramMonitoringTimeline != null) {
            ramMonitoringTimeline.stop();
        }
        if (cpuTemperatureMonitoringTimeline != null){
            cpuTemperatureMonitoringTimeline.stop();
        }
    }
}
