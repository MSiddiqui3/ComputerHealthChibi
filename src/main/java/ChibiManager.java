


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class ChibiManager{
    private ImageView imageView;
    private Label ramStatusLabel;
    private long previousFreeRam;
    private Map<String, String> chibiNormalImages = new HashMap<>();
    private Map<String, String> chibiSadImages = new HashMap<>();

    private Map<String, String> chibiHappyImages = new HashMap<>();
    private String currentChibi = "Chibi 1";
    private Timeline ramMonitoringTimeline;

    private static final long STABLE_THRESHOLD = 50 * 1024 * 1024;  //base threshold for stable ram equal to 50MB

    public ChibiManager(ImageView imageView, Label ramStatusLabel) {
        this.imageView = imageView;
        this.ramStatusLabel = ramStatusLabel;
        this.previousFreeRam = RamInfo.getFreeRam();  // Initialize with the current RAM value

        // Load normal chibi images
        chibiNormalImages.put("Chibi 1", "/images/jg1n.gif");
        chibiNormalImages.put("Chibi 2", "/images/jg2n.gif");
        chibiNormalImages.put("Chibi 3", "/images/jg3n.gif");

        // Load sad chibi images
        chibiSadImages.put("Chibi 1", "/images/jg1s.gif");
        chibiSadImages.put("Chibi 2", "/images/jg2s.gif");
        chibiSadImages.put("Chibi 3", "/images/jg3s.gif");

        //Load happy chibi images
        chibiHappyImages.put("Chibi 1", "/images/jg1h.gif");
        chibiHappyImages.put("Chibi 2", "/images/jg2h.gif");
        chibiHappyImages.put("Chibi 3", "/images/jg3h.gif");

        // Set default chibi image
        setChibiImage(chibiNormalImages.get(currentChibi));
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
        ramMonitoringTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            long currentFreeRam = RamInfo.getFreeRam();

            // Calculate the change in RAM
            long ramDifference = Math.abs(currentFreeRam - previousFreeRam);

            // Check if RAM usage is stable, increasing, or decreasing
            if (ramDifference > STABLE_THRESHOLD) {
                setChibiImage(chibiNormalImages.get(currentChibi));  // Switch to normal chibi
                ramStatusLabel.setText("RAM is Stable");
            } else if (currentFreeRam < previousFreeRam) {
                setChibiImage(chibiSadImages.get(currentChibi));  // Switch to sad chibi
                ramStatusLabel.setText("RAM is Low");
            } else {
                setChibiImage(chibiHappyImages.get(currentChibi));  // Switch to happy chibi
                ramStatusLabel.setText("RAM is High");
            }

            previousFreeRam = currentFreeRam;
        }));
        ramMonitoringTimeline.setCycleCount(Timeline.INDEFINITE);  // Run indefinitely
        ramMonitoringTimeline.play();  // Start the timeline
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

    // Method to stop the RAM monitoring if needed
    public void stopRamMonitoring() {
        if (ramMonitoringTimeline != null) {
            ramMonitoringTimeline.stop();
        }
    }
}
