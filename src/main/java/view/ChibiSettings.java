package view;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ChibiSettings {
    private ImageView imageView;

    public ChibiSettings(ImageView imageView) {
        this.imageView = imageView;
    }

    // Initialize with default chibi image
    public void initializeChibi() {
        setChibiImage("/images/chibi1.png"); // Default chibi image
    }

    // Method to change the chibi appearance based on user selection
    public void changeChibiAppearance(String chibiOption) {
        switch (chibiOption) {
            case "Chibi 1":
                setChibiImage("/images/chibi1.png");
                break;
            case "Chibi 2":
                setChibiImage("/images/chibi2.png");
                break;
            case "Chibi 3":
                setChibiImage("/images/chibi3.png");
                break;
            default:
                setChibiImage("/images/chibi1.png"); // Fallback to default if option is invalid
                break;
        }
    }

    // Set the image for the chibi character
    private void setChibiImage(String imagePath) {
        try {
            Image chibiImage = new Image(getClass().getResource(imagePath).toExternalForm());
            imageView.setImage(chibiImage); // Set the image in the ImageView
            imageView.setFitHeight(150); // Adjust image height
            imageView.setPreserveRatio(true); // Preserve aspect ratio
        } catch (Exception e) {
            System.out.println("Error loading chibi image: " + e.getMessage());
        }
    }

    // Add a swaying animation for the chibi character
    public void addSwayingAnimation() {
        TranslateTransition sway = new TranslateTransition(Duration.millis(1000), imageView);
        sway.setFromX(-20); // Sway from -20px
        sway.setToX(20); // Sway to +20px
        sway.setAutoReverse(true); // Reverse the animation automatically
        sway.setCycleCount(TranslateTransition.INDEFINITE); // Repeat indefinitely
        sway.play(); // Start the animation
    }
}
